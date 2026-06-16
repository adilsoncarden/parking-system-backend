# Etapa de compilación
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre-jammy
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
# Flags de memoria para instancias pequeñas (p. ej. Render free 512 MB):
# limita el heap al 70% del contenedor y usa SerialGC (menor overhead).
ENTRYPOINT ["java","-XX:MaxRAMPercentage=70.0","-XX:+UseSerialGC","-jar","/app.jar"]