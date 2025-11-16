FROM python:3.10-slim

# Install uv for faster dependency installation
COPY --from=ghcr.io/astral-sh/uv:latest /uv /usr/local/bin/uv

RUN useradd --create-home appuser

USER appuser

WORKDIR /app

# Copy dependency files
COPY pyproject.toml uv.lock ./

# Install dependencies using uv
RUN uv pip install --system --no-cache -r pyproject.toml

# Copy application files
COPY app.py ./
COPY financial_dataset.csv ./

EXPOSE 7860

ENV PYTHONUNBUFFERED=1 \
    PYTHONDONTWRITEBYTECODE=1

CMD ["uvicorn", "app:app", "--host", "0.0.0.0", "--port", "7860"]