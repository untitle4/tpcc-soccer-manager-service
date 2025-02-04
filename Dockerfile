FROM amazoncorretto:11-alpine

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


# Add the lambda-runtime-interface-emulator to enable local testing.
# ADD https://github.com/aws/aws-lambda-runtime-interface-emulator/releases/latest/download/aws-lambda-rie /usr/bin/aws-lambda-rie
# RUN chmod +x /usr/bin/aws-lambda-rie

# Add the JAR to a known path.
# ARG JAR_FILE=build/libs/*.jar
# ENV JAR_DIR="/jar"
# ADD $JAR_FILE $JAR_DIR/

# Add the entrypoint script.
# ADD container/entrypoint.sh /entrypoint.sh
# RUN chmod +x /entrypoint.sh
# ENTRYPOINT ["/entrypoint.sh"]

# Set our
# CMD ["com.tpcc.soccer.manager.StreamLambdaHandler::handleRequest"]
