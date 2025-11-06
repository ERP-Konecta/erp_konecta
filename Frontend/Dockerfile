# Stage 1 - Build Angular App
FROM node:18 AS build
WORKDIR /app

# Copy only dependency files for better caching
COPY package*.json ./
RUN npm ci --ignore-scripts
COPY . .
RUN npm run build

# Stage 2 - Serve using Nginx
FROM nginx:alpine

# Remove default nginx static files
RUN rm -rf /usr/share/nginx/html/*

# Copy build output
COPY --from=build /app/dist/erp-frontend /usr/share/nginx/html

# Prepare directories and permissions
RUN mkdir -p /var/cache/nginx /var/run /var/log/nginx /tmp && \
    chown -R 101:101 /usr/share/nginx/html /var/cache/nginx /var/run /var/log/nginx /tmp && \
    chmod -R 755 /usr/share/nginx/html /var/cache/nginx /var/run /var/log/nginx /tmp

# Remove duplicate PID directive
RUN sed -i '/pid\s\+\/run\/nginx.pid;/d' /etc/nginx/nginx.conf

EXPOSE 80
USER 101

# ✅ Force nginx to use /tmp/nginx.pid instead (accessible to non-root)
CMD ["nginx", "-g", "pid /tmp/nginx.pid; daemon off;"]
