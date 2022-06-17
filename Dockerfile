FROM openjdk:18-jdk-alpine

<<<<<<< HEAD
ADD target/capstone-sepuluh.jar capstone-kelompok-sepuluh.jar
=======
ADD target/capstone-kelompok-sepuluh.jar capstone-kelompok-sepuluh.jar
>>>>>>> ec0faab0a6b69ba9e9e52700bd5203fd7b196b49

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/capstone-kelompok-sepuluh.jar"]