# caching-with-apache-ignite
Capstone project of the course "Java Engineer to Scalable Backend Developer" at Grid Dynamics

## How to run?

### Build project:

`./gradlew clean build`

### Start cassandra in docker:

`docker-compose up -d cassandra`

### Start product service either in local or in docker:

**Local:** `./gradlew bootRun`

**Docker:** `docker-compose up -d product-service --build --force-recreate`

## Services

* Cassandra:
    * hostname: cassandra
    * ports: 9042:9042
    * admin UI: http://localhost:9411/zipkin

* Product-service: 
    * hostname: product-service
    * ports: 8080:8080,9090:9090

## How to test project scenarios

### Check project API

There is a Postman collection "ngolovin-cwai.postman_collection.json" in the project root directory, 
which you can import to test the API, or use the commands below

* Product-service:
    * `curl -X GET http://localhost:8080/v1/api/products/b6c0b6bea69c722939585baeac73c13d`
    * `curl -X PUT http://localhost:8080/v1/api/products/b6c0b6bea69c722939585baeac73c13d \
         -H 'Content-Type: application/json' \
         -d '{"listPrice": "42.00"}'`

### Check Apache Ignite work

1. Connect to the application via JMX port using JConsole  
`jconsole localhost:9090`
2. Go to the tab "MBeans" and select the class "org.apache.*.ProductCache"
3. Fire product-service API calls and see changes in the cache metrics