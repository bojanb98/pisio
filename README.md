# pisio

University project for distributed image processing. Project is consisted of several microservices and is meant to be deployed on Kubernetes. Platform can easily be initialized on new Kubernetes cluster by running bash scripts in k8s folder called cluster-initialize.sh and services-initialize.sh

Project modules:
  1. auth-filter - reusable package enabling other services to implement JWT token validation through communication with authentication service
  2. auth-service - authentication service used for account regsitration, login and JWT token validation
  3. communication-service - microservice using WebSockets to communicate with React frontend and enable live job progress tracking
  4. file-service - used for image files upload/download and job creation
  5. gif-service - creates GIF from multiple images
  6. kafka-configuration - reusable package which enables automatic Kafka configuration for other web services
  7. ocr-service - performs Optical Character Recognition on multiple images
  8. pdf-service - creates PDF files from strings created by ocr-service
  9. swagger-configuration - reusable pacckage enabling automatic Swagger configuration

Modules gif-service, ocr-service and pdf-serice are using Kafka for consuming their inputs and for pushing their outputs back to the system. These services are also sending special data through Kafka to communication service to provide users with job progess data.
