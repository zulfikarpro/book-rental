#FROM eclipse-temurin:17-jdk
#WORKDIR /app
#COPY target/bookrental.jar app.jar
#ENTRYPOINT ["java","-jar","/app/app.jar"]

#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests


#FROM maven:3.9.9-eclipse-temurin-17 AS build
#WORKDIR /app
#COPY pom.xml .
#RUN mvn dependency:go-offline -B
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests
##
### Step 2: Run the JAR
#FROM eclipse-temurin:17-jdk
#WORKDIR /app
#COPY --from=build /app/target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]


# --- Stage 1: Build JAR with Maven (JDK 17) ---
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# copy pom.xml and download dependencies first (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copy the source and build
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Run JAR on a lightweight JDK 17 image ---
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
