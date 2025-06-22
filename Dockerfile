# ---------- Etapa de construcción ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar el pom.xml primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del proyecto
COPY src ./src

# Compilar y empaquetar
RUN mvn clean package -DskipTests

# ---------- Etapa de ejecución ----------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar el JAR compilado
COPY --from=build /app/target/auth-microservice-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto que usa tu app
EXPOSE 8181

# Ejecuta el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
