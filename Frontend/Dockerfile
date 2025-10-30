# Stage 1 - Build Angular App
FROM node:18 AS build
WORKDIR /app

# Copy dependency files and install packages
COPY package*.json ./
RUN npm install

# Copy all source code and build the project
COPY . .
RUN npm run build

# Stage 2 - Serve using Nginx
FROM nginx:alpine
COPY --from=build /app/dist/erp-frontend /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
