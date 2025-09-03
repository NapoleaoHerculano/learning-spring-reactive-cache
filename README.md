# learning-spring-reactive-cache
Projeto para aprendizado da implementação de cache utilizando as funções reativas do Spring Boot.

## 🛠 O que é necessário para rodar o projeto?

Para conseguir utilizar essa aplicação você precisa dos seguintes itens instalados:

* Java 21
* Kotlin 2.1.21
* Redis
* Intellij ou outra IDE da sua preferência
* Docker e Docker Compose

Nesse projeto também foram usadas as seguintes tecnologias:

* Spring Boot - 3.5.5
* Spring Web Flux
* R2DBC
* H2 Database
* Gradle 8

Para poder executar o projeto localmente você deve clonar o repositório numa pasta da sua preferência:

    git clone https://github.com/NapoleaoHerculano/learning-spring-reactive-cache.git

Acesse o repositório clonado e o importe com a sua IDE. Em seguida, execute o comando:

    docker-compose up -d

Esse comando irá subir um serviço Redis para armazenar os dados no cache.

## Como testar?

Na subida da aplicação são executados dois arquivos para criar e popular o banco de dados local com cinco
registros. O projeto possui um controlador REST com um endpoint de consulta. 
Para acessá-lo basta consultar o endereço abaixo:

    http://localhost:8080/agenda/{id}

Ao consultar um recurso pela primeira vez, deve ser logada a chamada SQL executada, semelhante ao ‘log’ abaixo:
    
    2025-09-03T16:30:59.912-03:00 DEBUG 18568 --- [ioEventLoop-5-1] o.s.r2dbc.core.DefaultDatabaseClient     : Executing SQL statement [SELECT * FROM AGENDA WHERE id = :id]

Na próxima vêz que o mesmo recurso for consultado, o mesmo deve ser retornado pelo cache, logando algo semelhante
à mensagem abaixo:

    2025-09-03T16:33:53.103-03:00  INFO 18568 --- [ioEventLoop-5-1] c.napoleao.async.cache.service.Service   : Cache found -> KEY=[agenda:1] | VALUE=[Pauta 01]

**Observações**: 

* Verifique se o projeto está rodando na porta padrão - **8080**. Se não estiver, altere a mesma para
a da sua preferência;
* Os ‘ids’ existentes ao subir a aplicação estão no intervalo de 1 a 5.
