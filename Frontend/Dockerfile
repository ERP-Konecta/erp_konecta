# Stage 1 - Build Angular App
FROM node:18 AS build
WORKDIR /app

# Copy only package files first (better cache)
COPY package*.json ./

# Install dependencies safely
RUN npm ci --ignore-scripts

# Copy the rest of the app (filtered by .dockerignore)
COPY . .

# Build Angular project
RUN npm run build

# Stage 2 - Serve using Nginx
FROM nginx:alpine

# Copy build output to nginx html folder
COPY --from=build /app/dist/erp-frontend /usr/share/nginx/html

# Expose port
EXPOSE 80

# Use non-root user for better security
USER 101

# Run nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]
