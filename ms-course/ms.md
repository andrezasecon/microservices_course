*PROJETO MICROSERVICES COM SPRING BOOT E SPRING CLOUD**

Projeto criado para o curso Micros serviços Java com Spring Boot e Spring Cloud do professor Nélio Alves (https://www.udemy.com/course/microsservicos-java-spring-cloud/) e consiste em um serviço Spring Boot Eureka que faz o gerenciamento dinâmico, escalabilidade e balanceamento de carga, onde nossos micro serviços serão registrados.

- Nossa API de Gateway será o Zuul, que faz o roteamento dinâmico dos nossos serviços, que não terão posta fixa.

- Para comunicação entre os micro serviços, foi utilizado o Spring Cloud Open Feign
- Para gerenciamento de configurações, nosso micro serviço busca as configurações pré definidas em um repositório privado no GitHub.
- Nossa micro serviço de autorização e validação foi implementado utilizando Oaut2 e JWT para gerar e validar nosso token de autorização.
- Micro serviço User para cadastrar os usuários dos serviços com suas respectivas Roles.
- Micro Serviço Worker para cadastrar nossos workers
- Micro serviço Payroll que calcula o valor a ser pago ao Worker de acordo com os dias trabalhados





![](C:\Users\andrezasecon\Documents\Workspaces\DevSuperior_suporte\ms-course\ms.PNG)



Requisitos para rodar o projeto

- Docker
- Repositório de configuração no GitHub (https://github.com/acenelio/ms-course-configs)
- Excluir do application.properties do projeto hr-server-config as linas abaixo:

```
spring.cloud.config.server.git.username=${GITHUB_USER}
spring.cloud.config.server.git.password=${GITHUB_PASS}
```

- Trocar o link do repositório de configuração pelo link acima

Substituir a linha abaixo

```
spring.cloud.config.server.git.uri=https://github.com/andrezasecon/microservices-configs
```

Por esta linha abaixo

```
spring.cloud.config.server.git.uri=https://github.com/acenelio/ms-course-configs
```

Como rodar o projeto

## Criar rede docker para sistema hr

```
docker network create hr-net
```

## Criar os container de de todos os serviços

Entrar na pasta hr-config-server e abrir um terminal, executar os comandos abaixo

### hr-config-server

```
./mvnw clean package

docker build -t hr-config-server:v1 .

docker run -p 8888:8888 --name hr-config-server --network hr-net -e GITHUB_USER=acenelio -e GITHUB_PASS= h
```

### hr-eureka-server

Entrar na pasta hr-eureka-server e abrir um terminal, executar os comandos abaixo

```
./mvnw clean package

docker build -t hr-eureka-server:v1 .

docker run -p 8761:8761 --name hr-eureka-server --network hr-net hr-eureka-server:v1
```

### hr-worker

Entrar na pasta hr-worker e abrir um terminal, executar os comandos abaixo

```
./mvnw clean package -DskipTests

docker build -t hr-worker:v1 .

docker run -P --network hr-net hr-worker:v1
```

### hr-api-gateway-zuul

Entrar na pasta hr-api-gateway-zuul e abrir um terminal, executar os comandos abaixo

```
./mvnw clean package -DskipTests

docker build -t hr-api-gateway-zuul:v1 .

docker run -p 8765:8765 --name hr-api-gateway-zuul --network hr-net hr-api-gateway-zuul:v1
```



### *** Obs: podemos subir quantas instancias do workers e payroll quisermos, pois os servição são altamente escaláveis, basta executar mais uma vez o container correspondente.



# Testando se os containers estão ativos

```
docker container ls
```

Neste ponto todos os containers devem estar rodando

Acessar o Eureka pelo http://localhost:8761/ , aqui conseguimos verificar todas as instancias que foram levantadas e se registraram no Eureka.

![](C:\Users\andrezasecon\Documents\Workspaces\DevSuperior_suporte\ms-course\eureka.PNG)



# Testando as aplicações pelo Postman

Impostar as configurações do postman pelo link https://www.getpostman.com/collections/ffb23340f644dc40fa5b

Usuários e perfis:

nina@gmail.com (OPERATOR - acesso a todas as aplicações com exceção da payroll )

leia@gmail.com (ADMIN - acesso a todas as aplicações)

### Realizar Login pela requisição oauth

![](C:\Users\andrezasecon\Documents\Workspaces\DevSuperior_suporte\ms-course\post1.PNG)



### Acessar a aplicação Workers

### Acessar a aplicação Users

### Acessar a aplicação Payroll

### 