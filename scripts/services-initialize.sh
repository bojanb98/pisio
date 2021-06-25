#!/bin/bash

# Services
kubectl apply -f auth-service.yml
kubectl apply -f file-service.yml
kubectl apply -f ocr-service.yml
kubectl apply -f gif-service.yml
kubectl apply -f pdf-service.yml

# Ingress
kubectl apply -f ingress-config.yml
