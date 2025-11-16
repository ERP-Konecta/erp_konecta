FROM python:3.11-slim

WORKDIR /app

# Install uv for faster dependency installation
COPY --from=ghcr.io/astral-sh/uv:latest /uv /usr/local/bin/uv

# Copy dependency files
COPY pyproject.toml uv.lock ./

# Install dependencies using uv (much faster than pip)
RUN uv pip install --system --no-cache -r pyproject.toml

# Copy application files
COPY src/ ./src/
COPY *.joblib ./
COPY combined.csv ./

# Create non-root user
RUN groupadd -r app && useradd -r -g app app && \
    chown -R app:app /app

EXPOSE 7860

ENV PYTHONPATH=/app/src \
    PYTHONUNBUFFERED=1 \
    PYTHONDONTWRITEBYTECODE=1

USER app

CMD ["uvicorn", "src.app:app", "--host", "0.0.0.0", "--port", "7860"]