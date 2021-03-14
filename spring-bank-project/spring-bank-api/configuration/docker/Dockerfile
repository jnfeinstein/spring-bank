FROM openjdk:15-slim
RUN apt-get update && apt-get upgrade
ARG JAR_FILE
WORKDIR /app
COPY ${JAR_FILE} .
# ENTRYPOINT array syntax does not support args nor environment variables
RUN printf "#!/bin/sh \njava -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:MinRAMPercentage=75.0 -jar ${JAR_FILE} $@" > entrypoint.sh
RUN chmod +x entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
EXPOSE 443
