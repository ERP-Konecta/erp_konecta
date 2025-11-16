FROM python:3.11-slim

COPY --from=ghcr.io/astral-sh/uv:latest /uv /usr/local/bin/uv

RUN useradd --create-home appuser

USER appuser

WORKDIR /app

# Copy dependency files
COPY pyproject.toml uv.lock ./

# Install dependencies using uv
RUN uv pip install --system --no-cache -r pyproject.toml

# Copy application files
COPY app.py .

EXPOSE 7860

ENV PYTHONUNBUFFERED=1 \
    PYTHONDONTWRITEBYTECODE=1

CMD ["gunicorn", "-w", "4", "-b", "0.0.0.0:7860", "--timeout", "120", "app:app"]
