FROM openjdk:11.0.12
EXPOSE 8081
ADD target/beta-post-and-comments.jar beta-post-and-comments.jar
ENTRYPOINT ["java", "-jar", "/beta-post-and-comments.jar"]