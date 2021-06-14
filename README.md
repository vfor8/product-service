# Watch service

A REST service for uploading information about watches

## Build
`mvn clean install`

## Run

```shell
java -Dserver.port={SERVER_PORT} -Dspring.datasource.url={DATASOURCE_URL} -Dspring.datasource.username={DB_USERNAME} -Dspring.datasource.password={DB_PASSWORD} -jar target/product-upload-service-0.0.1.jar
```

Replace the placeholders with the correct values:

{SERVER_PORT}: port where the application will listen for incoming requests

{DATASOURCE_URL}: database connection url (currently requires MySQL database, so e. g. `jdbc:mysql://localhost:3306/mydb`)

{DB_USERNAME}: username to connect to the database

{DB_PASSWORD}: database user's password

## API
see [API documentation](doc/api-docs.yaml)

## Developer info
  - Spring Boot application (see class `ProductApplication`)
  - MySQL used to store product data - needs a table `watch` to store watch data


