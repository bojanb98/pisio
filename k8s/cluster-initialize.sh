#!/bin/bash

# Storage 
kubectl apply -f storage/file-storage.yml
kubectl apply -f storage/mysql-storage.yml

# MySQL
kubectl apply -f db/mysql-service.yml
kubectl apply -f db/adminer.yml

# Redis
kubectl apply -f redis-service.yml

# Kafka
kubectl create namespace kafka
kubectl apply -f kafka/kafka-strimzi-init.yml -n  kafka
kubectl apply -f kafka/kafka-cluster.yml -n kafka
kubectl wait kafka/my-cluster --for=condition=Ready --timeout=300s -n kafka
kubectl wait kafka/my-cluster --for=condition=Ready --timeout=300s -n kafka

