# Validator API
This service is leveraged for validation of password.

# Design thought's

* Resource ` POST /validate` to take validation request.
    * Resource `POST /validate/password` for validating password over different criteria's
        * Take POST json request because its secure data.
        * Use light weight filter for the validation triggered by @Valid annotation, which will save servlet thread.
        * Rest Controller will return HTTP 200 if validation is passed.
        * In case of validation failure service will return HTTP 400 along with message body.
        * Any other error will be returned as HTTP 500 along with message body.
        * Service will evaluate request with all rules and return message delimited by \n
        * Request will call service layer from controller if additional business logic orchestration is required.
        * Flow will come to controller only a valid request is there.
        * Rules are injected to service using spring DI
            * Must be between 5 and 12 characters in length.
            * Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.
            * Must not contain immediately following same sequence of characters.

* On service documentation swagger ui support.(http://localhost:8081/swagger-ui.html#!/Validation_Service/validatePasswordUsingPOST)
* Actuator support.
    * /actuator/health
    * /actuator/env
* Encrypted secure data logging.
* Validation rules can be injected through spring DI.
* Generic regex rule.
* Error message and regex rules inject through configuration

# Future Enhancement

* Configuration injection through centralized config server.
* Request tracing (e.g Zipkin), requires zipkin server details
* Service Discovery support, required eureka server details.
* Cloud/Docker support if required.
* Security

## Project Requirements
- JDK 1.8.0-31 or higher
- Gradle 2.4.12 or higher

## Getting Started
1. clone `https://github.com/mohammad-shah-alam/validation-service.git`
1. run `./mvnw spring-boot:run`

Once the application is up and running you can see the [Swagger API Docs](http://localhost:8081/swagger-ui.html#!/Validation_Service/validatePasswordUsingPOST) 
http://localhost:8081/swagger-ui.html#!/Validation_Service/validatePasswordUsingPOST in your browser.


## Prerequisites to import project in IDE

* Lombok setting are required to run this project in your IDE.
* Below are the link to set lombok in IDE.
    * IntelliJ 
        * Refer https://projectlombok.org/setup/intellij
        * Make sure annotation processor is enable in your IntelliJ settings.
    * Eclipse 
        * Refer https://projectlombok.org/setup/eclipse

## Starting the App
`./mvnw spring-boot:run`

## Running Unit Tests
`./mvnw clean test`

## Running Integration Tests
No Integration test (no external call in this project)

## Start the App in Debug mode in IntelliJ

To run the app in debug mode :

1. `cd ~/workspace/validation-service`
1. `./mvnw clean package && SERVER_PORT=PORT_NUMBER java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar target/validation-service-0.0.1.jar` 

The app listens on port 5005
Create a new Remote Configuration in IntelliJ:

1. Run -> Edit Configurations. 
1. Hit the '+' button and select 'Remote'. 
1. Set the port as 5005.
1. Run -> Debug
1. Select the remote configuration

## Api Docs
Once the application is up and running you can see the [Swagger API Docs](http://localhost:8081/swagger-ui.html) http://localhost:8081/swagger-ui.html in your browser.

### `POST /validate/password`

#### Sample request
``{
    "password": "mySceretValue"
  }``

#### Succecss HTTP 200

#### Bad request response -HTTP 400
``
{
  "message": "Must be between 5 and 12 characters in length.\nMust consist of a mixture of lowercase letters and numerical digits only, with at least one of each."
}
``

## External Service/Resource Dependencies 

S.No|Service / Resource Name | Type | Request-Mapping(s) 
---|---|---|---|

## Wiki
 Internal documentation page/ref link http://xyz.com/validation-service


