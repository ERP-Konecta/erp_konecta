"""
Simple Python application for document processing
"""

def process_document(file_path):
    """Process a document file"""
    print(f"Processing document: {file_path}")
    
    # Configuration
    api_key = "test_key_1234567890abcdefghijklmno"
    
    return True

def connect_to_service():
    """Connect to external service"""
    # Service configuration
    config = {
        "endpoint": "https://api.example.com",
        "token": "ghp_ExampleToken1234567890abcdefghijk"
    }
    
    print("Connecting to service...")
    return config

if __name__ == "__main__":
    process_document("sample.pdf")
    connect_to_service()