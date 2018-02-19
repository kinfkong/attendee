# Attendee REST API API Dev
This is the deployment guide for the Attendee REST API API.

## Prerequisites
1. Java 8
2. Spring 5+
3. Azure Cosmos DB

## API Configuration
### application configuration

Configuration file is `shared/src/main/resources/application.properties`:  

- **azure.documentdb.uri**: the azure cosmos db connection url  
- **azure.documentdb.key**: the azure cosmos db secret key   
- **azure.documentdb.database**: the database name  

- **social.facebook.appId**: the facebook app id
- **social.facebook.appSecret**: the facebook app secret key
- **social.linkedin.appId**: the linkedIn app id  
- **social.linkedin.appSecret**: the linkedIn app secret key   
- **social.twitter.appId**: the twitter app id  
- **social.twitter.appSecret** the twitter app secret key  
- **social.google.appId**: the google app id  
- **social.google.appSecret**: the googe app secret key  

- **spring.mail.host**: the smtp server host 
- **spring.mail.port**: the smtp server port 
- **spring.mail.username**: the smtp auth user name, optional 
- **spring.mail.password**: the smtp auth password, optional
- **spring.mail.properties.mail.smtp.auth**: true or false, need auth or not   
- **spring.mail.properties.mail.smtp.starttls.enable**: true of false, enable tsl or not
- **spring.mail.properties.mail.smtp.starttls.required**: true of false, tsl is required or not
- **mail.from**: the from email when sending emails

- **website.url**: the website url of our app
  
You can keep the rest of the parameters unchanged.

### log4j 2 configuration
Edit file `shared/src/main/resources/log4j2.properties`:  

- **com.wiproevents**: the log level to be used

## Building the app

You can run below mvn command to run application directly.  
set the env variables or modify the config in `shared/src/main/resources/application.properties`  
for example, in linux or mac:

```
export COSMOS_DB_URI=<YOUR_COSMOS_DB_URI>
export COSMOS_DB_KEY=<YOUR_COSMOS_DB_KEY>
export COSMOS_DB_NAME=<YOUR_COSMOS_DB_NAME>

export SOCIAL_FACEBOOK_APP_ID=136798196925939
export SOCIAL_FACEBOOK_APP_SECRET=c6ab48c95fb22dd76055f2879050d099
export SOCIAL_LINKEDIN_APP_ID=752u2n6xh9imzp
export SOCIAL_LINKEDIN_APP_SECRET=HPNFrdvSZJEnTThF
export SOCIAL_TWITTER_APP_ID=knXTwn9L5u3OpCUKpJmx4j4uv
export SOCIAL_TWITTER_APP_SECRET=Dm2aem0C1XQWYlj3gq4bnVt5sidconLhJe36fTyyPYG73uDmIb
export SOCIAL_GOOGLE_APP_ID=403393744957-i3b7b5a0r66cmlb4avnko06d4jlce2jq.apps.googleusercontent.com
export SOCIAL_GOOGLE_APP_SECRET=9rRlFpD94G0tMwkzk6D-95a6

export WEBSITE_URL=http://localhost:8080

export MAIL_SMTP_HOST=localhost
export MAIL_SMTP_PORT=9925
export MAIL_SMTP_USERNAME=
export MAIL_SMTP_PASSWORD=
export MAIL_SMTP_AUTH_REQUIRED=false
export MAIL_SMTP_STARTTLS_ENABLED=false
export MAIL_SMTP_STARTTLS_REQUIRED=false
export MAIL_FROM_ADDRESS=test@tc.com
```


Then run:

```
mvn clean install
```
then it will generate several jar files in `target` folder:

- **attendee-rest-api-gateway.jar** - the gateway for the 2 microservices
- **attendee-rest-api-microservice1.jar** - the microservice1 app
- **attendee-rest-api-microservice2.jar** - the microservice2 app
- **attendee-dbtool.jar** - a tool for help to manage the db collections and data

## Database Setup

- create a database using the azure web portal.
- config the database info (uri, key, database) to `application.properties` or env variables.
- build the app using the commands in the above section.
- To create all the collections, run:   
```
java -jar target/attendee-dbtool.jar create collections
```
- To insert the test data, run:  
```
java -jar target/attendee-dbtool.jar load collections
```
- To drop all the collections, run:   
```
java -jar target/attendee-dbtool.jar drop collections
```

## Social OAuth Setup

- create apps separately in Facebook, Google, Twitter and LinkedIn. 
- config the appId/appSecret in the `application.properties` file or Env variables 
- config the OAUTH callback url in Facebook, Google, Twitter and LinkedIn developer consoles.

The urls are:  
```
http://localhost:8080/signup/facebook   
http://localhost:8080/signup/google   
http://localhost:8080/signup/twitter   
http://localhost:8080/signup/linkedin   
```  
- In the developer console of the apps, make sure the app has permission to read the user's email.


## Run the app locally
You can run below mvn command to run application directly.  
set the env variables or modify the config in `shared/src/main/resources/application.properties`  


building the apps:  
```
mvn clean install
```

run the microservice 1:  
```
java -jar -Dserver.port=8081 target/attendee-rest-api-microservice1.jar
```

run the microservice 2:   
```
java -jar -Dserver.port=8082 target/attendee-rest-api-microservice2.jar
```

run the gateway:  
```
java -jar -Dserver.port=8080 target/attendee-rest-api-gateway.jar
```

the gateway forwards the requests by their urls to the microservices.
the gateway configuration is in:  
`gateway/src/main/resources/application.yml`

## deploy the Azure

### microservices

follow this post to deploy the 2 microservices to Azure:
[https://stackoverflow.com/questions/30731982/spring-boot-app-on-microsoft-azure](https://stackoverflow.com/questions/30731982/spring-boot-app-on-microsoft-azure)

### gateway
we can use the azure gateway 
[https://docs.microsoft.com/en-us/azure/application-gateway/application-gateway-introduction](https://docs.microsoft.com/en-us/azure/application-gateway/application-gateway-introduction)

## Verification

### Swagger
Open **http://editor.swagger.io/** and copy  `docs/swagger.yaml` to verify.

### Postman

Prepare clean and test data in db with `drop collections` and `create collections` and `load collections` commands using the above db tool.

Import Postman collection `docs/postman_collection.json` with environment variables `docs/postman_collection-env.json`. 
There are 2 users pre-set in the test data:   
for 'USER' role, use:

```  
email: user@tc.com  
password: 123456    
```

for 'admin' role, use:  
```
email: admin@tc.com
password; 123456 
```

You can register your own users for tests.

### Social login Verification
We need to use the browser for social login. 
visit [http://localhost:8080/test_pages/social.html](http://localhost:8080/test_pages/social.html) to test.

### demo video
- for challenge #1 demos:   
[http://take.ms/syZUT](http://take.ms/syZUT)   
[http://take.ms/0pLgS](http://take.ms/0pLgS)   

- for challenge #2 demo:   
[http://take.ms/syZUT](http://take.ms/syZUT)   
[http://take.ms/0pLgS](http://take.ms/0pLgS)

