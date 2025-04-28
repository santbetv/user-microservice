FROM openjdk:11

# Define argument variables
ARG user=admin
ARG group=cliente
ARG uid=1000
ARG gid=1000

# Create user and group with specified UID and GID
RUN groupadd -g ${gid} ${group} && useradd -u ${uid} -g ${group} -s /bin/sh ${user}

# Cambia a usuario no root
USER admin:cliente

# Crea directorio de trabajo
WORKDIR /app

# Copiar el JAR generado por Gradle
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Opciones de JAVA (puedes pasar variables extra si quieres)
ENV JAVA_OPTS=""

# Comando para correr la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
