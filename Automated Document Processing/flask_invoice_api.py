import os
import io
from dotenv import load_dotenv
from PIL import Image
import numpy as np
import pytesseract
from langchain_google_genai import ChatGoogleGenerativeAI
from sentence_transformers import SentenceTransformer
import json
from pymongo import MongoClient
from flask import Flask, request, jsonify, send_file
from werkzeug.utils import secure_filename
import tempfile

# Load environment variables
load_dotenv()
google_api_key = os.getenv("GOOGLE_API_KEY")
mongo_uri = os.getenv("MONGO_URI")

# MongoDB setup
client = MongoClient(mongo_uri)
db = client["invoice_reader_db"]
collection = db["invoices"]

# Initialize Flask app
app = Flask(__name__)
app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024  # 16MB max file size

# LLM setup
llm = ChatGoogleGenerativeAI(
    google_api_key=google_api_key,
    temperature=0.1,
    max_retries=2,
    convert_system_message_to_human=True,
    model="gemini-2.5-flash"
)

# Set cache directories
os.environ["HF_HOME"] = "/tmp/huggingface"
os.environ["TRANSFORMERS_CACHE"] = "/tmp/huggingface"
os.environ["SENTENCE_TRANSFORMERS_HOME"] = "/tmp/sentence_transformers"
print("Model cache directory:", os.environ["HF_HOME"])

# Load embedding model
embedding_model = SentenceTransformer('sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2')

ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'pdf'}

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

def extract_text_from_file(file_stream, filename):
    """Extract text from uploaded file"""
    try:
        if filename.lower().endswith(".pdf"):
            import pdfplumber
            text = ""
            with pdfplumber.open(file_stream) as pdf:
                for page in pdf.pages:
                    text += page.extract_text() or ""
            return text.strip()
        else:  # image case
            image = Image.open(file_stream)
            gray_image = image.convert("L")
            extracted_text = pytesseract.image_to_string(gray_image).strip()
            return extracted_text
    except Exception as e:
        raise Exception(f"Error extracting text: {str(e)}")

@app.route('/', methods=['GET'])
def home():
    """Health check endpoint"""
    return jsonify({
        "status": "running",
        "message": "Invoice Information Extractor API",
        "endpoints": {
            "POST /api/extract": "Upload and extract invoice information",
            "GET /api/invoices": "Get all invoices from database",
            "GET /api/invoice/<id>": "Get specific invoice by MongoDB ID"
        }
    }), 200

@app.route('/api/extract', methods=['POST'])
def extract_invoice():
    """Extract information from uploaded invoice"""
    
    # Check if file is present
    if 'file' not in request.files:
        return jsonify({"error": "No file provided"}), 400
    
    file = request.files['file']
    
    if file.filename == '':
        return jsonify({"error": "No file selected"}), 400
    
    if not allowed_file(file.filename):
        return jsonify({"error": "Invalid file type. Allowed: png, jpg, jpeg, pdf"}), 400
    
    try:
        # Secure the filename
        filename = secure_filename(file.filename)
        
        # Extract text from file
        extracted_text = extract_text_from_file(file.stream, filename)
        
        if not extracted_text:
            return jsonify({"error": "No text could be extracted from the file"}), 400
        
        # Process with Gemini
        message = f"""
        system: You are an invoice information extractor who extracts information from text and converts it into a JSON format with proper structure and key-value pairs.
        user: {extracted_text}
        """
        
        response = llm.invoke(message)
        result = str(response.content)
        result = result.replace("```json", "").replace("```", "").strip()
        
        json_data = json.loads(result)
        
        # Create embedding vector
        embedding_vector = embedding_model.encode(extracted_text).tolist()
        
        # Check for duplicates in MongoDB
        existing_invoice = collection.find_one({
            "$or": [
                {"file_name": filename},
                {"extracted_text": extracted_text}
            ]
        })
        
        if existing_invoice:
            return jsonify({
                "warning": "Invoice already exists in database",
                "existing_id": str(existing_invoice["_id"]),
                "extracted_text": extracted_text,
                "invoice_data": json_data
            }), 200
        
        # Save to MongoDB
        insert_result = collection.insert_one({
            "file_name": filename,
            "extracted_text": extracted_text,
            "invoice_data": json_data,
            "embedding": embedding_vector
        })
        
        return jsonify({
            "success": True,
            "message": "Invoice processed successfully",
            "mongodb_id": str(insert_result.inserted_id),
            "extracted_text": extracted_text,
            "invoice_data": json_data
        }), 201
        
    except json.JSONDecodeError:
        return jsonify({"error": "Invalid JSON response from AI model"}), 500
    except Exception as e:
        return jsonify({"error": f"Processing error: {str(e)}"}), 500

@app.route('/api/invoices', methods=['GET'])
def get_all_invoices():
    """Get all invoices from database"""
    try:
        invoices = list(collection.find({}, {"embedding": 0}))  # Exclude embeddings for performance
        
        # Convert ObjectId to string
        for invoice in invoices:
            invoice["_id"] = str(invoice["_id"])
        
        return jsonify({
            "count": len(invoices),
            "invoices": invoices
        }), 200
    except Exception as e:
        return jsonify({"error": f"Database error: {str(e)}"}), 500

@app.route('/api/invoice/<invoice_id>', methods=['GET'])
def get_invoice(invoice_id):
    """Get specific invoice by ID"""
    try:
        from bson import ObjectId
        
        invoice = collection.find_one({"_id": ObjectId(invoice_id)}, {"embedding": 0})
        
        if not invoice:
            return jsonify({"error": "Invoice not found"}), 404
        
        invoice["_id"] = str(invoice["_id"])
        
        return jsonify(invoice), 200
    except Exception as e:
        return jsonify({"error": f"Error retrieving invoice: {str(e)}"}), 500



@app.errorhandler(413)
def too_large(e):
    return jsonify({"error": "File too large. Maximum size is 16MB"}), 413

@app.errorhandler(500)
def internal_error(e):
    return jsonify({"error": "Internal server error"}), 500

# if __name__ == '__main__':
#     # Run the Flask app
#     port = int(os.getenv("PORT", 5000))
#     app.run(host='0.0.0.0', port=port, debug=True)

if __name__ == '__main__':
    # Run the Flask app, prioritizing the Hugging Face default port 7860
    # The port is often set by the environment in deployment
    port = int(os.getenv("PORT", 7860)) 
    app.run(host='0.0.0.0', port=port, debug=False) # debug=False for production