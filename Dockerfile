FROM openjdk:8u191-jre-alpine3.8

#install curl and jd utitlity as we need it for health check
RUN apk --no-cache add curl jq

# create workspace
WORKDIR			/usr/share/udemy

#Add the jars and libs
ADD /target/selenium-docker.jar			selenium-docker.jar
ADD /target/selenium-docker-tests.jar	selenium-docker-tests.jar
ADD /target/libs						libs

#Add the suite file
ADD flightTests.xml							flightTests.xml

#Add the healthcheck script
#ADD healthcheck.sh							healthcheck.sh
RUN wget https://s3.amazonaws.com/selenium-docker/healthcheck/healthcheck.sh

#Command to run
#BROWSER - to be accepted from user
#HUB_HOST - to be accepted form user
#MODULE - to be accepted form user to run the file
ENTRYPOINT java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* -DBROWSER=$BROWSER -DHUB_HOST=$HUB_HOST org.testng.TestNG $MODULE
#since we added healthcheck script and its responsible to run our tests. so entry point here will be to run the healthcheck script which will in turn run the tests once healtch chekc is passed
#ENTRYPOINT sh healthcheck.sh
