# bingo-core
Api de bingo que faz a analise dos numeros sorteados randomicamente.

## Requisitos
Java 17
Maven 3.8.8


# Regras de Negocio 
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
            "name": "SorteiocoreApplication",
            "request": "launch",
            "mainClass": "br.com.fabioalvaro.sorteiocore.SorteiocoreApplication",
            "env": {
                "MONGO_DB_USERNAME": "rootuser",
                "MONGO_DB_PASSWORD": "rootpass"
            },
            "projectName": "sorteiocore"
        }
    ]
}
````

# Collection do ThunderClient 
Para testar os metodos existe uma collection json que pode ser acessada na pasta /collection
* instale a extensao ThunderCliente do vscode ou importe a collection no postman.

# MONGO
```
Entre na pasta mongodb
e rode o comando $ docker-compose up

export MONGO_DB_USERNAME=seu_usuario
export MONGO_DB_PASSWORD=sua_senha
```
# depois acesse o mongo pelo express
usuario admin:pass
http://localhost:8081/db/bingo/cartela


##  Compra de Cartelas
História: Como um jogador, eu quero comprar cartelas de bingo para participar dos jogos.
Critérios de Aceitação:
O jogador pode visualizar as cartelas disponíveis para compra.
O jogador pode selecionar e comprar cartelas.
O jogador recebe uma confirmação de compra.

## Título: Compra de Cartelas de Bingo

História: Como um jogador, eu quero comprar cartelas de bingo para participar dos jogos.

Critérios de Aceitação:

O jogador pode visualizar as cartelas disponíveis para compra.
O jogador pode selecionar e comprar cartelas.
O jogador recebe uma confirmação de compra.
Requisitos Funcionais:

Visualização de Cartelas:

O sistema deve exibir uma lista de cartelas de bingo disponíveis para compra.
Cada cartela deve mostrar informações como preço, número de série e design.
Seleção e Compra de Cartelas:

O jogador deve poder selecionar uma ou mais cartelas para compra.
O sistema deve permitir que o jogador adicione cartelas ao carrinho de compras.
O jogador deve poder revisar o carrinho de compras antes de finalizar a compra.
O sistema deve processar o pagamento através de métodos de pagamento suportados (ex: cartão de crédito, PayPal).
Confirmação de Compra:

Após a compra, o sistema deve exibir uma mensagem de confirmação ao jogador.
O sistema deve enviar um e-mail de confirmação ao jogador com os detalhes da compra.
As cartelas compradas devem ser adicionadas à conta do jogador para uso em jogos futuros.
Requisitos Não Funcionais:

Desempenho:

O sistema deve carregar a lista de cartelas disponíveis em no máximo 2 segundos.
O processo de compra deve ser concluído em no máximo 5 segundos após a confirmação do pagamento.
Segurança:

Todas as transações de pagamento devem ser realizadas em um ambiente seguro (HTTPS).
Os dados de pagamento do jogador devem ser protegidos e não armazenados no sistema.
Usabilidade:

A interface de compra deve ser intuitiva e fácil de usar, com instruções claras em cada etapa do processo.
O sistema deve ser acessível em dispositivos móveis e desktops.
Confiabilidade:

O sistema deve ter uma disponibilidade de 99.9% para garantir que os jogadores possam comprar cartelas a qualquer momento.
O sistema deve registrar todas as transações de compra para fins de auditoria.
Tarefas:

Desenvolver a interface de visualização de cartelas.
Implementar a funcionalidade de seleção e adição de cartelas ao carrinho.
Integrar o sistema de pagamento.
Implementar a funcionalidade de confirmação de compra e envio de e-mail.
Testar a funcionalidade de compra de cartelas em diferentes dispositivos e navegadores.
Realizar testes de segurança e desempenho.


## Regras do bingo

As cartelas possuem numeros aleatorios entre 1 a 75.
Cada Cartela possui 15 numeros que não se repetem em cada cartela.
Cada Cartela tem 3 linhas de 5 numeros em cada.
O sorteio deve ser encerrado quando 1 ou mais pessoas conseguirem preencher os 15 numeros de uma cartela.
É possivel que 1 ou mais pessoas consigam ganhar com cartela cheia.

Uma linha de uma cartela nao pode ganhar a quadra se ja ganhou a quadra.
Uma linha de uma cartela  nao pode ganhar a Quina se ja ganhou a quina.
Uma cartela pode ganhar 1 quadra, 1 quina e 1 cartela cheia.


## prompt

Configura um contêiner Localstack para simular serviços AWS localmente, mapeando portas e montando volumes para persistência e inicialização.

## Prompt

1 Crie arquivos service e repository para persistir a classe Movimento Financeiro no mongodb.
2 A classe Service deve permitir buscar Movimento Financeiro por uma string jogadorId
classe="""

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movimentoFinanceiro")
@Data
@ToString
public class MovimentoFinanceiro {
    @Id
    private String id;
    private LocalDateTime createdAt;
    private String mensagem;
    private String tipo;
    private Double valor;
    private String origem;
    private String destino;

}"""


## prompt
refatore o metodo abaixo para receber uma cartela e adicionar um valor de 2,00 no movimento financeiro com os dados do jogador que estao na cartela.

metodo=
"""
 @PostMapping("/premiar")
    public String premiarCartela(@RequestBody Cartela cartela) {
        return cartelaService.geraNumerosRandomicos().toString();
    }
"""


## Prompt

1 Crie arquivos service e repository para persistir a classe Notificacao no mongodb.
2 A classe Service deve permitir buscar as Notificacoes por uma string jogadorId
3 Crie uma classe controller para buscar as notificacoes por uma string jogadorId

classe="""

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document(collection = "notificacao")
public class Notificacao {
    @Id
    String id;
    @NotNull
    LocalDateTime createdAt;
    @NotNull
    String mensagem;
    @NotNull
    String jogadorId;
    Boolean visualizado;
}

"""