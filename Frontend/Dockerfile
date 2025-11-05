# Stage 1 - Build Angular App
FROM node:18 AS build
WORKDIR /app

# Copy only dependency files for better caching
COPY package*.json ./

# Install dependencies safely (avoid postinstall scripts)
RUN npm ci --ignore-scripts

# Copy the rest of the application (filtered by .dockerignore)
COPY . .

# Build Angular project
RUN npm run build

# Stage 2 - Serve using Nginx
FROM nginx:alpine

# Remove default nginx static files
RUN rm -rf /usr/share/nginx/html/*

# Copy build output from the previous stage
COPY --from=build /app/dist/erp-frontend /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Use a non-root user for security
USER 101

# Run nginx
CMD ["nginx", "-g", "daemon off;"]
