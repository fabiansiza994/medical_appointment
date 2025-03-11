# Usamos una imagen con Java 23
FROM eclipse-temurin:23-jdk

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el JAR de nuestra aplicación (debes haberlo compilado antes)
COPY target/*.jar app.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Ejecutamos la aplicación
CMD ["java", "-jar", "app.jar"]