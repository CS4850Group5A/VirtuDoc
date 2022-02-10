#!/bin/sh

set -ex

CERTIFICATE_PATH=/cert/virtudoc-https.crt
KEY_PATH=/cert/virtudoc-https.key
ROOT_PACKAGE_PATH=/cert/virtudoc-https.pfx

if [ ! -e $ROOT_PACKAGE_PATH ]; then
  openssl req \
    -x509 \
    -nodes \
    -days 365 \
    -newkey rsa:2048 \
    -keyout $KEY_PATH \
    -out $CERTIFICATE_PATH \
    -config /cert/request.conf \
    -subj "/CN=localhost/OU=Web/O=CS4850Group5A/L=Atlanta/ST=Georgia/C=US" \
    -passin pass:"" \
    -batch
  openssl pkcs12 \
    -export \
    -out $ROOT_PACKAGE_PATH \
    -inkey $KEY_PATH \
    -in $CERTIFICATE_PATH \
    -passout pass:""
  chmod +r $CERTIFICATE_PATH
  chmod +r $KEY_PATH
else
  echo "Certificate files found, skipping..."
fi