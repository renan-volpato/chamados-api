# 🎫 API de Chamados

API REST para gerenciamento de chamados de suporte técnico, desenvolvida como desafio técnico para a vaga de **Desenvolvedor(a) Backend Java Júnior** da empresa fictícia *TechSolutions Global*.

Os dados são persistidos em um banco **PostgreSQL hospedado na nuvem (Neon)**, e as credenciais ficam protegidas em variáveis de ambiente.

![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon-4169E1?logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?logo=apachemaven&logoColor=white)

---

## 📋 Sumário

- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Modelo de dados](#-modelo-de-dados)
- [Endpoints](#-endpoints)
- [Como executar](#-como-executar)
- [Testando com o Insomnia](#-testando-com-o-insomnia)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Segurança das credenciais](#-segurança-das-credenciais)

---

## 🛠 Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 17+ | Linguagem |
| Spring Boot 3.3.5 | Framework da aplicação |
| Spring Web | Construção da API REST |
| Spring Data JPA | Acesso e persistência de dados |
| PostgreSQL | Banco de dados relacional |
| Neon | Hospedagem do PostgreSQL na nuvem |
| Maven | Gerenciamento de dependências e build |
| Insomnia | Testes dos endpoints |

---

## 🏗 Arquitetura

O projeto segue a separação de responsabilidades em camadas:

```
Requisição HTTP
      ↓
  Controller      → recebe as requisições e devolve ResponseEntity
      ↓
   Service        → concentra as regras e operações sobre chamados
      ↓
  Repository      → acesso aos dados com Spring Data JPA
      ↓
 PostgreSQL (Neon)
```

O `Controller` nunca acessa o `Repository` diretamente: toda operação passa pelo `Service`.

---

## 🗂 Modelo de dados

Entidade **Chamado** (tabela `chamado`):

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Integer | Identificador único, gerado automaticamente pelo banco |
| `titulo` | String | Título do chamado |
| `descricao` | String | Descrição do problema |
| `prioridade` | int | Prioridade do chamado |
| `solicitante` | String | Nome da pessoa que abriu o chamado |
| `status` | String | Situação atual do chamado (ex.: `ABERTO`, `EM_ANDAMENTO`) |

Exemplo de JSON:

```json
{
  "titulo": "Computador não inicia",
  "descricao": "Computador do laboratório não liga após atualização.",
  "prioridade": 2,
  "solicitante": "Maria Silva",
  "status": "ABERTO"
}
```

---

## 🔗 Endpoints

Rota base: `/chamados`

| Método | Rota | Descrição | Sucesso | Não encontrado |
|---|---|---|---|---|
| `POST` | `/chamados` | Cadastra um novo chamado | `201 Created` | n/a |
| `GET` | `/chamados` | Lista todos os chamados | `200 OK` | n/a |
| `GET` | `/chamados/{id}` | Busca um chamado pelo ID | `200 OK` | `404 Not Found` |
| `PUT` | `/chamados/{id}` | Atualiza um chamado | `200 OK` | `404 Not Found` |
| `DELETE` | `/chamados/{id}` | Remove um chamado | `204 No Content` | `404 Not Found` |

---

## 🚀 Como executar

### Pré-requisitos

- JDK 17 ou superior
- Maven (ou uma IDE com suporte a Maven, como o VS Code com as extensões Java)
- Uma conta gratuita no [Neon](https://neon.tech)

### 1. Clone o repositório

```bash
git clone https://github.com/renan-volpato/chamados-api.git
cd chamados-api
```

### 2. Crie o banco no Neon

1. Crie um projeto no Neon.
2. Clique em **Connect** e copie os dados de conexão: host, nome do banco, usuário e senha.

### 3. Configure as variáveis de ambiente

Copie o arquivo de exemplo e preencha com os seus dados:

```bash
cp .env.example .env
```

No Windows (PowerShell): `copy .env.example .env`

Conteúdo do `.env`:

```
DB_URL=jdbc:postgresql://SEU_HOST.neon.tech/SEU_BANCO?sslmode=require
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
```

> ⚠️ A `DB_URL` deve começar com `jdbc:postgresql://` e **não** deve conter usuário nem senha.

### 4. Execute a aplicação

```bash
mvn spring-boot:run
```

Ou execute a classe `ChamadosApplication` pela sua IDE.

Na primeira execução, o Hibernate cria a tabela `chamado` automaticamente (`spring.jpa.hibernate.ddl-auto=update`). A API fica disponível em `http://localhost:8080`.

---

## 🧪 Testando com o Insomnia

1. Suba a aplicação.
2. Crie uma requisição `POST` para `http://localhost:8080/chamados` com o corpo JSON do exemplo acima.
3. Use o `id` retornado nas demais requisições (`GET`, `PUT`, `DELETE`).
4. Confira os dados no **SQL Editor** do Neon:

```sql
SELECT * FROM chamado;
```

---

## 📁 Estrutura do projeto

```
chamados-api
├── src/main
│   ├── java/com/techsolutions/chamados
│   │   ├── controller
│   │   │   └── ChamadoController.java
│   │   ├── model
│   │   │   └── Chamado.java
│   │   ├── repository
│   │   │   └── ChamadoRepository.java
│   │   ├── service
│   │   │   └── ChamadoService.java
│   │   └── ChamadosApplication.java
│   └── resources
│       └── application.properties
├── .env.example
├── .gitignore
└── pom.xml
```

---

## 🔒 Segurança das credenciais

- O `application.properties` usa apenas variáveis de ambiente (`${DB_URL}`, `${DB_USERNAME}` e `${DB_PASSWORD}`), sem nenhuma credencial real.
- Os valores reais ficam no arquivo `.env`, que está no `.gitignore` e **não** é enviado ao GitHub.
- O arquivo `.env.example` serve apenas de modelo, com valores fictícios.

---

## 👩‍💻 Autor

Desenvolvido por Renan Volpato como parte de uma atividade acadêmica da matéria de Programação Orientada a Objetos Avançada 
