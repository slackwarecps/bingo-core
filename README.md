# bingo-core
Api de bingo que faz a analise dos numeros sorteados randomicamente.

## Requisitos
Java 17
Maven 3.8.8
Docker


# 1 Regras de Negocio 
* preencher aqui
Crie uma classe java chamada cartela que contenha tres objetos linha. Cada objeto linha deve guardar uma lista de 5 numeros inteiros e ter um propriedade chamada sorteados que deve ser integer e uma propriedade boolean chamada quadra que por padrao deve ser false;

## O formato ISO 8601 para data e hora é: YYYY-MM-DDTHH:MM:SSZ

YYYY - Ano com quatro dígitos
MM - Mês com dois dígitos (01 a 12)
DD - Dia com dois dígitos (01 a 31)
T - Separador de data e hora
HH - Hora com dois dígitos (00 a 23)
MM - Minutos com dois dígitos (00 a 59)
SS - Segundos com dois dígitos (00 a 59)
Z - Indicador de fuso horário UTC (pode ser substituído por um deslocamento de fuso horário, como +00:00)
2024-06-02 08:22:22:001

## 2 Endpoints

* Preencher aqui

## 3 Documentacao da API 
* Swagger URL: http://localhost:8080/swagger-ui/index.html#/


Para acessar a documentação da API, execute o projeto e abra o Swagger:
```
export MONGO_DB_DATABASE=minha-base
export MONGO_DB_URI=minha-uri-do-atlas
$ cd raiz-do-projeto
$ mvn spring-boot:run
```



## 4 Como montar o ambiente de Desenvolvimento

### Suba o Ambiente Docker
#### MONGO
```
Entre na pasta mongodb
e rode o comando $ docker-compose up

```
#### depois acesse o mongo pelo express
usuario admin:pass
http://localhost:8081/db/bingo_dev_db/cartela


### Configuração do VSCode
Para configurar o VSCode para executar o projeto, use o seguinte arquivo launch.json:
````
// launch.json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Current File",
            "request": "launch",
            "mainClass": "${file}"
        },
        {
            "type": "java",
            "name": "SorteiocoreApplication",
            "request": "launch",
            "mainClass": "br.com.fabioalvaro.sorteiocore.SorteiocoreApplication",
            "env": {             
                "spring.profiles.active": "local",
                "MONGO_DB_DATABASE":"bingo_dev_db",
                "MONGO_DB_URI":"mongodb://rootuser:rootpass@localhost:27017",
                "MONGO_DB_SSL":false,

            },
            "projectName": "sorteiocore"
        }
    ]
}
````

### Dump do Atlas 
1 caso queira usar um dump e um backup do atlas. configure o basrc do seu terminal
2 instale o mongodb-database-tools
2 execute o arquivo de restore que esta na pasta tools do projeto.
```
$ brew tap mongodb/brew
$ brew install mongodb-database-tools

$ code ~/.zshrc  
## BINGO DEMO FABIO
export SOURCE_MONGO_URI_PRD="mongodb+srv://asx:asd@asd.dsdasd.mongodb.net"
```

## 5 Collection do ThunderClient 
Para testar os metodos existe uma collection json que pode ser acessada na pasta /collection
* instale a extensao ThunderCliente do vscode ou importe a collection no postman.



## 6 Gerar o build do projeto

```
mvn clean install -DskipTests

export MONGO_DB_DATABASE=bingo
export MONGO_DB_URI=qweqweqweqweqweqweqwew-uri
echo $MONGO_DB_DATABASE   
echo $MONGO_DB_URI   

docker build . -t fabioalvaro/sorteio-core:v1.0.0 -t fabioalvaro/sorteio-core:latest

docker run --name sorteio-container123 -e MONGO_DB_DATABASE=$MONGO_DB_DATABASE -e MONGO_DB_URI=$MONGO_DB_URI -p 8080:8080 sorteiocore:latest


docker run --name sorteio-container123 -e MONGO_DB_DATABASE=$MONGO_DB_DATABASE -e MONGO_DB_URI=$MONGO_DB_URI -p 8080:8080 sorteiocore:latest

```

 ### GERAR IMAGE NO DOCKER HUB
```
 mvn clean install -DskipTests
 docker build . -t fabioalvaro/sorteio-core:latest
 docker push fabioalvaro/sorteio-core:latest
```
