# ComplianceOne API

O **ComplianceOne** é uma plataforma robusta projetada para gerenciar e tramitar ocorrências e denúncias corporativas, como assédio e discriminação. Construído com foco absoluto em segurança, ele garante rastreabilidade, anonimato (quando solicitado) e controle rigoroso de acesso através de cargos e permissões.

## 🚀 Tecnologias Utilizadas

Este projeto foi construído utilizando as melhores e mais modernas práticas do ecossistema Java:

* **Java 21**
* **Spring Boot 3.4.x** (Web, Data JPA, Validation)
* **Spring Security + OAuth2 Resource Server** (Autenticação via JWT com criptografia assimétrica RSA)
* **PostgreSQL** (Banco de dados relacional)
* **Flyway** (Controle de versionamento de banco de dados/Migrations)
* **Docker & Docker Compose** (Ambiente local e conteinerização)
* **Swagger / OpenAPI** (Documentação interativa da API)
* **Testcontainers & JUnit 5** (Suíte de testes de integração ponta-a-ponta e unidade)
* **Lombok & MapStruct** (Produtividade e mapeamento de DTOs)

---

## ⚙️ Pré-requisitos

Para rodar este projeto na sua máquina, você precisará ter instalado:
* **Docker** e **Docker Compose**
* *(Opcional)* Java 21 e Maven (caso deseje rodar a aplicação fora do Docker)

---

## 🛠️ Como Executar a Aplicação (Ambiente Local)

O projeto está totalmente configurado para rodar via Docker, abstraindo a necessidade de instalar bancos de dados localmente.

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/complianceone.git
   cd complianceone
   ```

2. **Suba a aplicação com Docker Compose:**
   Isso irá compilar o código fonte e subir dois containers: a aplicação Spring Boot e o banco PostgreSQL.
   ```bash
   docker compose up --build
   ```

3. A API estará rodando em: `http://localhost:8080`

---

## 📚 Documentação da API (Swagger)

Com a aplicação rodando, você pode acessar a interface visual do Swagger para interagir e testar todos os endpoints disponíveis:

👉 **[Acessar Swagger UI](http://localhost:8080/swagger-ui/index.html)**

---

## 🔒 Segurança e Autenticação

A API é protegida por tokens JWT assinados digitalmente com chaves RSA (Criptografia Assimétrica). 

1. Acesse o endpoint de login com um usuário válido: `POST /api/v1/auth/login`
2. Copie o `accessToken` retornado na resposta.
3. Nas rotas protegidas (ou no Swagger, clicando em "Authorize"), insira o token no formato `Bearer <seu_token>`.

*O controle de acesso é baseado em cargos (RBAC). Apenas usuários com roles específicas (ex: `RH`, `COMPLIANCE`, `DIRETORIA`) podem acessar listagens confidenciais.*

---

## 🧪 Testes Automatizados

O projeto conta com uma pirâmide de testes cobrindo Unidade, Segurança e Integração. Os testes de integração utilizam o **Testcontainers** para subir um banco PostgreSQL efêmero e garantir a confiabilidade total.

Para executar todos os testes, rode:
```bash
mvn clean test
```
*(Nota: Para executar testes de integração no Windows, garanta que o Docker Engine esteja acessível ao seu ambiente de testes ou IDE).*
