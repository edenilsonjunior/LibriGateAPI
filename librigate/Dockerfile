# Etapa 1: Construção
FROM maven:latest AS builder

# Configurar o diretório de trabalho
WORKDIR /app

# Copiar arquivos de configuração e fonte para o contêiner
COPY pom.xml .
COPY src ./src

# Construir o JAR usando Maven
RUN mvn clean package

# Etapa 2: Imagem final
FROM openjdk:21-jdk-slim

# Configurar o diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado da etapa de construção
COPY --from=builder /app/target/librigate-0.0.1-SNAPSHOT.jar /app/app.jar

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
