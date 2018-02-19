#!/bin/sh

export COSMOS_DB_URI=https://tcattendee.documents.azure.cn:443/
export COSMOS_DB_KEY=RXUS3AAIl1Ycf3FzIdxH6jBGKcQXxAnFfZ3VMVPKj7aW9xMwaLh82YDoqMk8UD359CbFCuNlCfstEAAwQtyzKg==
export COSMOS_DB_NAME=attendee


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

if [ "$1" = "ignorebuild" ]
then
    echo "not building"
else
    mvn clean install
fi

if [ "$1" = "build" ]
then
    exit 0
fi

intexit() {
    # Kill all subprocesses (all processes in the current process group)
    kill -HUP -$$
}

hupexit() {
    # HUP'd (probably by intexit)
    echo
    echo "Interrupted"
    exit
}

trap hupexit HUP
trap intexit INT

# -DsocksProxyHost=127.0.0.1 -DsocksProxyPort=8921
if [ "$1" = "reset" ]
then
    java -jar target/attendee-dbtool.jar drop collections
    java -jar target/attendee-dbtool.jar create collections
    java -jar target/attendee-dbtool.jar load collections
fi



java -jar -Dserver.port=8081 target/attendee-rest-api-microservice1.jar &
java -jar -Dserver.port=8082 target/attendee-rest-api-microservice2.jar &
java -jar -Dserver.port=8080 target/attendee-rest-api-gateway.jar &

wait