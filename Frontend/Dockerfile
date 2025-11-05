# Stage 1 - Build Angular App
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install --ignore-scripts
COPY . .
RUN npm run build

# Stage 2 - Serve using Nginx
FROM nginx:alpine
COPY --from=build /app/dist/erp-frontend /usr/share/nginx/html
EXPOSE 80
USER 101  # Non-root user
CMD ["nginx", "-g", "daemon off;"]
