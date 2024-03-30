# Use a imagem base com o Java e o sistema operacional desejado
FROM xldevops/jdk17-alpine

# Define a pasta de trabalho dentro do contêiner
WORKDIR /app

# Copia o JAR do seu aplicativo JHipster (que você deve ter construído previamente usando o Maven ou Gradle)
COPY target/*.jar app.jar

# Exponha a porta em que o aplicativo Java estará ouvindo (defina a porta correta de acordo com o seu aplicativo)
EXPOSE 8080

# Comando para iniciar o aplicativo JHipster quando o contêiner for executado
CMD ["java", "-jar", "app.jar"]
