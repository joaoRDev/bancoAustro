#!/bin/bash

# Ruta del archivo de configuración externo
CONFIG_FILE="./application-custom.properties"

# Verifica si el archivo existe
if [ ! -f "$CONFIG_FILE" ]; then
  echo "❌ El archivo application.properties no se encuentra en $CONFIG_FILE"
  exit 1
fi

# Ejecuta el JAR de Quarkus inyectando la configuración externa
java -Dquarkus.config.locations=$CONFIG_FILE -jar target/*-runner.jar