FROM node:18 AS build
WORKDIR /app


COPY package*.json ./
RUN npm ci --ignore-scripts

COPY . .
RUN npm run build


FROM nginx:alpine


RUN rm -rf /usr/share/nginx/html/*


COPY --from=build /app/dist/erp-frontend /usr/share/nginx/html


RUN mkdir -p /var/cache/nginx /var/run /var/log/nginx /tmp && \
    chown -R 101:101 /usr/share/nginx/html /var/cache/nginx /var/run /var/log/nginx /tmp && \
    chmod -R 755 /usr/share/nginx/html /var/cache/nginx /var/run /var/log/nginx /tmp && \
    sed -i '/pid\s\+\/run\/nginx.pid;/d' /etc/nginx/nginx.conf

EXPOSE 80
USER 101


CMD ["nginx", "-g", "pid /tmp/nginx.pid; daemon off;"]
