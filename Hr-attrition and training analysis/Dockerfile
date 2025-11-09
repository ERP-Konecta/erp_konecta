# Use the official Python image
FROM python:3.9-slim

# Set the working directory
WORKDIR /code

# Copy the requirements file and install dependencies
COPY ./requirements.txt /code/requirements.txt
RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt

# Copy all the application files from the root of the repo to /code
COPY . /code/

# Expose the port the app will run on (Hugging Face uses 7860)
EXPOSE 7860

# Command to run the application (file is app.py, FastAPI instance is app)
CMD ["uvicorn", "app:app", "--host", "0.0.0.0", "--port", "7860"]