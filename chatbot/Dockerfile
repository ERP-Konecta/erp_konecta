FROM ghcr.io/astral-sh/uv:python3.10-trixie-slim AS builder


WORKDIR /app


#Replace hardlinking with copying to avoid issues on filesystems that do not support hardlinks
ENV UV_LINK_MODE=copy

# Copy dependency files only (for caching)
RUN --mount=type=cache,target=/root/.cache/uv \
    --mount=type=bind,source=pyproject.toml,target=pyproject.toml \
    --mount=type=bind,source=uv.lock,target=uv.lock \
    uv sync --locked --no-install-project --no-editable

# Copy full project source
ADD . /app

# Install project into .venv
RUN --mount=type=cache,target=/root/.cache/uv \
    uv sync --locked --no-editable


FROM python:3.10-slim

WORKDIR /app


RUN addgroup --system app && adduser --system --ingroup app app

# Copy app and .venv from builder
COPY --from=builder --chown=app:app /app /app


EXPOSE 7860

ENV PATH="/app/.venv/bin:$PATH"
ENV PYTHONPATH=/app/src

USER app


CMD ["gunicorn", "-w", "4", "-b", "0.0.0.0:7860", "--timeout", "120", "src.erp_chatbot_api:app"]
