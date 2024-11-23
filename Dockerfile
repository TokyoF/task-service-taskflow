# Usar una imagen base de JDK para ejecutar Spring Boot
FROM openjdk:17-jdk-alpine

# Copiar el archivo JAR generado
COPY target/task-management-0.0.1-SNAPSHOT.jar task-service.jar

# Exponer el puerto configurado
EXPOSE 8093

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/task-service.jar"]
