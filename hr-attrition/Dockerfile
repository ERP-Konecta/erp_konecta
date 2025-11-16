FROM ghcr.io/astral-sh/uv:python3.11-bookworm-slim AS builder

WORKDIR /app

# Replace hardlinking with copying to avoid issues on filesystems that do not support hardlinks
ENV UV_LINK_MODE=copy

# Copy dependency files only (for caching)
RUN --mount=type=cache,target=/root/.cache/uv \
    --mount=type=bind,source=pyproject.toml,target=pyproject.toml \
    --mount=type=bind,source=uv.lock,target=uv.lock \
    uv sync --locked --no-install-project --no-editable

# Copy full project source
COPY . /app

# Install project into .venv
RUN --mount=type=cache,target=/root/.cache/uv \
    uv sync --locked --no-editable


FROM python:3.11-alpine

WORKDIR /app

RUN apk add --no-cache \
    libstdc++ \
    libgomp \
    libgfortran

RUN addgroup -S app && adduser -S -G app app

# Copy app and .venv from builder
COPY --from=builder --chown=app:app /app /app

# Ensure proper permissions for copied files
RUN chmod -R 755 /app

EXPOSE 7860

ENV PATH="/app/.venv/bin:$PATH"
ENV PYTHONPATH=/app/src

USER app

CMD ["uvicorn", "src.app:app", "--host", "0.0.0.0", "--port", "7860"]