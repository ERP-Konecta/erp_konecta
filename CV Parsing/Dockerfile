# Start from a slim Python image
FROM python:3.10-slim

# Set working directory
WORKDIR /app

# Copy and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy your application files (main.py, utils.py, etc.)
COPY . .

# Expose the port (Hugging Face Spaces often uses 7860 for default apps)
EXPOSE 7860

# Command to run the Uvicorn server (which hosts the FastAPI app)
# Assuming your FastAPI app instance is named 'app' in main.py
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "7860"]