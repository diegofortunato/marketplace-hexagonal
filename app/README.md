
**Build**

Para compilar o projeto pelo terminal, execute os comandos a seguir:

> build./gradlew clean build

**Banco de dados**

Essa aplicação tem como dependência um banco de dados postgres.
Sendo assim, é necessário inicializá-lo antes da app.
Caso opte por executar o postgres instalado localmente, crie o banco `orangestackdb:`

> psql -U seuUsuarioPostgres

Inserir a sua senha postgres quando solicitado

> CREATE DATABASE orangestackdb WITH ENCODING 'UTF8'
> LC_COLLATE='English_United Kingdom' LC_CTYPE='English_United Kingdom';

Defina as variáveis de ambiente com as configurações do banco:

> export JDBC_URL=jdbc:postgresql://localhost:5432/orangestackdb export
> JDBC_USER=seuUsuarioPostgres export JDBC_PASSWORD=suaSenhaPostgres

Caso opte por executar o postgres através do docker, utilize o `docker-compose` para inicializá-lo, conforme o comando a seguir:

> docker-compose up db


**Aplicação**

    execução através do gradle

> ./gradlew run

    execução através do jar

> java -jar build/libs/orangestack-demo-0.1.10-all.jar

A aplicação irá subir o que for rest na porta **8080** e o que for gRPC na porta **50051**.


**Docker compose**

Caso queira subir todo o seu ambiente através do docker compose, execute:

    compile o seu projeto

> ./gradlew clean build

    execute o build com pull para garantir que todas as imagens estão atualizadas

> docker-compose build --pull

    inicie os containeres

> docker-compose up


**Testes unitários**

É possível escrever os testes unitários utilizando frameworks populares como o JUnit 5 (Jupiter) e o Mockk.
Basta marcar seus métodos de teste com a tag @Test do pacote org.junit.jupiter e criar seus mocks (se necessário) através do método mockk()do pacote io.mockk.mockk.
As asserções podem ser realizadas com o método assertThat do pacote assertk, conforme exemplo:

    @Test fun meuTeste () { val resultadoEsperado = true val mock = mockk<RepositorioDeDados>() val servico = Servico(mockk) val resultadoObtido = servico.processa() assertThat(resultadoObtido).isEqualTo(resultadoEsperado) }

Para executar a sua bateria de testes unitários, digite o comando:

> ./gradlew test




