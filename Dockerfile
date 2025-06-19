# Etapa de construcción
FROM maven:3.9.0-eclipse-temurin AS build
WORKDIR /app

# Copia solo los archivos necesarios (pom.xml primero para optimizar cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el código fuente
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia el archivo JAR generado
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto de la aplicación
EXPOSE 8181

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
