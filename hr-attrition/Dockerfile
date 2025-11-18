<<<<<<< HEAD
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
=======
FROM python:3.11-slim

WORKDIR /app

# Install uv for faster dependency installation
COPY --from=ghcr.io/astral-sh/uv:latest /uv /usr/local/bin/uv

# Copy dependency files
COPY pyproject.toml uv.lock ./

# Install dependencies using uv
RUN uv pip install --system --no-cache -r pyproject.toml

# Copy application files
COPY app.py ./
COPY *.joblib ./
COPY combined.csv ./

# Create non-root user
RUN useradd -r -U app && \
    chown -R app:app /app

EXPOSE 7860

ENV PYTHONUNBUFFERED=1 \
    PYTHONDONTWRITEBYTECODE=1

USER app

>>>>>>> ai-team-work
CMD ["uvicorn", "app:app", "--host", "0.0.0.0", "--port", "7860"]