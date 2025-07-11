#!/bin/bash

# Ruta del archivo de configuración externo
CONFIG_FILE="./application-custom.properties"

if [ ! -f "$CONFIG_FILE" ]; then
  echo "❌ El archivo application.properties no se encuentra en $CONFIG_FILE"
  exit 1
fi

# Ejecuta en modo dev
mvn quarkus:dev -Dquarkus.config.locations=$CONFIG_FILE
