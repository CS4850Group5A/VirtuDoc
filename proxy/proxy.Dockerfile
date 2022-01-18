FROM nginx:1.21.5-alpine

RUN apk add --no-cache openssl
RUN mkdir /cert
COPY nginx.conf /etc/nginx/nginx.conf
COPY 01-generate-certs.sh /docker-entrypoint.d
RUN chmod +x /docker-entrypoint.d/01-generate-certs.sh