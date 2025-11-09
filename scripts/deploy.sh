#!/bin/bash
set -e  

# === SSH Configuration ===
mkdir -p ~/.ssh
echo "${EC2_SSH_PRIVATE_KEY}" > ~/.ssh/deploy_key
chmod 600 ~/.ssh/deploy_key
ssh-keyscan -H "${EC2_HOST}" >> ~/.ssh/known_hosts



# === Remote Deployment ===
ssh -i ~/.ssh/deploy_key -o StrictHostKeyChecking=no "${EC2_USER}@${EC2_HOST}" << EOF
set -e

# Create .env file from GitHub vars/secrets
cat > .env <<EOT
GOOGLE_API_KEY=${GOOGLE_API_KEY}
EOT

# Docker login
echo "${DOCKERHUB_TOKEN}" | docker login --username "${DOCKERHUB_USERNAME}" --password-stdin

# Pull new image
docker pull ${DOCKERHUB_USERNAME}/${SERVICE_NAME}:${GITHUB_SHA}

# === Update image tag in docker-compose.yml ===
sed -i "s|${DOCKERHUB_USERNAME}/${SERVICE_NAME}:.*|${DOCKERHUB_USERNAME}/${SERVICE_NAME}:${GITHUB_SHA}|g" docker-compose.yml

# Redeploy with Docker Compose
docker-compose down
docker-compose up -d

# Clean up old images
docker image prune -af
EOF
