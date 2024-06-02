# bingo-core
Api de bingo que faz a analise dos numeros sorteados randomicamente.

## Requisitos
Java 17
Maven 3.8.8


# Regras de Negocio 
* preencher aqui

# Endpoints

* Preencher aqui

## Documentacao da API 
Para acessar a documentação da API, execute o projeto e abra o Swagger:
```
$ cd raiz-do-projeto
$ mvn spring-boot:run
```

* Swagger URL: http://localhost:8080/swagger-ui/index.html#/

## Configuração do VSCode
Para configurar o VSCode para executar o projeto, use o seguinte arquivo launch.json:
````
//launch.json
{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
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
            "name": "Bingo-Pay",
            "request": "launch",
            "mainClass": "br.com.fabioalvaro.pagamento.PagamentoApplication",
            "projectName": "pagamento",
            "args": "--spring.profiles.active=local"
        }
    ]
}
````

# Collection do ThunderClient 
Para testar os metodos existe uma collection json que pode ser acessada na pasta /collection
* instale a extensao ThunderCliente do vscode ou importe a collection no postman.

