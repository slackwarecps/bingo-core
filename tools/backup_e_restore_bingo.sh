#!/bin/bash

# Este script automatiza o backup do banco bingo de um ambiente de produção e 
# restaura no banco bingo_dev_db local, garantindo sincronização para testes ou desenvolvimento.

# Configurações do MongoDB
# Configure sua variavel de ambiente no ~/.zshrc      
# export SOURCE_MONGO_URI_PRD=mongodb+srv://AAA:BBB@CCCCCCCC.DDD.mongodb.net

SOURCE_MONGO_URI=$SOURCE_MONGO_URI_PRD
SOURCE_DATABASE_NAME="bingo"

TARGET_MONGO_URI="mongodb://rootuser:rootpass@localhost:27017/?authSource=admin"
TARGET_DATABASE_NAME="bingo_dev_db"

BACKUP_DIR="$HOME/Desktop/bingo_backup"
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
BACKUP_PATH="$BACKUP_DIR/${SOURCE_DATABASE_NAME}_backup_$TIMESTAMP"

# Criar o diretório de backup, se não existir
mkdir -p "$BACKUP_DIR"

# Etapa 1: Realizar o backup do banco de dados de origem
echo "Iniciando o backup do banco de dados '$SOURCE_DATABASE_NAME' da URI '$SOURCE_MONGO_URI'..."
mongodump --uri="$SOURCE_MONGO_URI" --db="$SOURCE_DATABASE_NAME" --out="$BACKUP_PATH"

if [ $? -ne 0 ]; then
  echo "Erro ao realizar o backup do banco de dados de origem."
  exit 1
fi

echo "Backup concluído com sucesso! Backup salvo em: $BACKUP_PATH"

# Etapa 2: Restaurar o backup no banco de dados de destino
echo "Iniciando a restauração do backup no banco de dados '$TARGET_DATABASE_NAME' na URI '$TARGET_MONGO_URI'..."
mongorestore --uri="$TARGET_MONGO_URI" --db="$TARGET_DATABASE_NAME" --drop "$BACKUP_PATH/$SOURCE_DATABASE_NAME"

if [ $? -ne 0 ]; then
  echo "Erro ao restaurar o backup no banco de dados de destino."
  exit 1
fi

echo "Restauração concluída com sucesso!"