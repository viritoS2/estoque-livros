# Projeto CRUD com Java/Spring e Docker (Rascunho)

## Sobre o Projeto

Este projeto é um exemplo prático de um sistema CRUD (Create, Read, Update, Delete) desenvolvido em Java, utilizando tecnlogias como
maven. O objetivo deste projeto era praticar meus conhecimentos com Junit, mas acabei adiconar novas tecnologias como docker e flyway para migrações de banco de dados durante o desenvolvimento.

## Funcionalidades

- **Criação de registros**: Adiciona novos dados ao banco de dados.
- **Leitura de registros**: Consulta e exibe dados do banco de dados.
- **Atualização de registros**: Modifica dados existentes no banco de dados.
- **Exclusão de registros**: Remove dados do banco de dados.

## Como Executar

Para executar este projeto, você precisará ter instalado em sua máquina:
- Docker version 25.0.2
- Apache Maven 3.6.3

### Passos para Execução

1. **Clone o Repositório**: git clone https://github.com/viritoS2/estoque-livros
2. **Importe o Projeto**: Abra o projeto em sua IDE de preferência (recomendado: IntelliJ IDEA).
3. **Execute**: 
- docker build -t app_livro .
- docker-compose up -d 
4. **Acessando Swagger**:
-  acesse o link no seu navegador http://localhost:8080/swagger-ui/index.html#/.

## Contato

[linkedin](https://www.linkedin.com/in/vitor-amorim-4839b41b4/)