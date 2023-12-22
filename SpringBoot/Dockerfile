# Utilisez une image de base avec une JVM et un système d'exploitation minimal
FROM openjdk:17-jdk-slim
LABEL authors="mahal"

# Définissez le répertoire de travail
WORKDIR /app

# Copiez le jar construit dans l'image
COPY build/libs/SpringBoot-0.0.1-SNAPSHOT.jar app.jar

# Commande pour exécuter l'application au démarrage du conteneur
CMD ["java", "-jar", "app.jar"]