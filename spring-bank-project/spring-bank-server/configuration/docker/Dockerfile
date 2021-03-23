FROM --platform=linux/arm64 openjdk:11-slim
ARG JAR_FILE
WORKDIR /app
COPY ${JAR_FILE} run.jar
ENV JAVA_TOOL_OPTIONS "-XX:+UseContainerSupport -XX:MinRAMPercentage=75.0 -XX:MaxRAMPercentage=75.0"
ENTRYPOINT ["java", "-jar"]
CMD ["run.jar"]
EXPOSE 443
