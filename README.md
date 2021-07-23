# Challenge ALURA Backend - AluraFlix API

### História
Após alguns testes com protótipos feitos pelo time de UX de uma empresa, foi requisitada a primeira versão de uma plataforma para compartilhamento de vídeos. A plataforma deve permitir ao usuário montar playlists com links para seus vídeos preferidos, separados por categorias.

Os times de frontend e UI já estão trabalhando no layout e nas telas. Para o backend, as principais funcionalidades a serem implementadas são:

* API com rotas implementadas segundo o padrão REST;
* Validações feitas conforme as regras de negócio;
* Implementação de base de dados para persistência das informações;
* Serviço de autenticação para acesso às rotas GET, POST, PUT e DELETE.

Temos um período de tempo de 4 semanas para desenvolver o projeto. Nas 3 primeiras, teremos tarefas a serem feitas e a última semana para ajustes ou para completar as tarefas pendentes. 

<cite>

Vamos trabalhar com o sistema ágil de desenvolvimento, utilizando o Trello da seguinte forma:
(Link: https://trello.com/b/Mj5x6lMZ/alura-challenge-backend-semana-1)

1. A coluna Pronto pra iniciar apresenta os cartões com os elementos ainda não desenvolvidos.
2. Já na coluna Desenvolvendo ficarão os elementos que você estiver desenvolvendo no momento. Ao iniciar uma tarefa, você poderá mover o cartão que contém a tarefa para esta coluna.
3. No Pausado estarão os elementos que você começou a desenvolver, mas precisou parar por algum motivo.
4. Por fim, a coluna Concluído terá os elementos já concluídos.

O Trello é uma ferramenta de uso individual para você controlar o andamento das suas atividades, mas ela não será avaliada.

Bom projeto!

</cite>

### Rodando o projeto
Execute, na raiz do projeto, o comando:
```
$ ./mvnw clean install
$ ./mvnw spring-boot:run
```
Para rodar este projeto com o banco H2, altere o application.properties em src/main/resources conforme abaixo:
```
# Spring run profile
spring.profiles.active=${APP_PROFILE:test}
```


Caso queira rodar no PostgreSQL, altere o application.properties para:
``` 
# Spring run profile
spring.profiles.active=${APP_PROFILE:dev}
```
Inicialize o PostgreSQL (Pode rodar em docker usando o dockerfile da pasta docker).
