FROM openjdk:22-jdk
ADD target/todo.jar todo.jar
ENTRYPOINT ["java", "-jar", "/todo.jar"]