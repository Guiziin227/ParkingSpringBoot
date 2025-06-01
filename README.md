# 🚗 Sistema de Estacionamento - Spring Boot

Sistema completo de gerenciamento de estacionamento desenvolvido com Spring Boot, oferecendo controle eficiente de vagas, veículos e operações de entrada/saída.

## 📋 Sobre o Projeto

Este sistema permite o gerenciamento completo de um estacionamento, incluindo:
- Controle de entrada e saída de veículos
- Gerenciamento de vagas disponíveis
- Cálculo automático de valores
- Relatórios de ocupação
- Histórico de movimentações

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Web**
- **H2 Database** (desenvolvimento)
- **MySQL** (produção)
- **Maven**
- **Swagger/OpenAPI** (documentação)

## 🏗️ Arquitetura

```
src/
├── main/
│   ├── java/
│   │   └── com/example/parking/
│   │       ├── controller/     # Controladores REST
│   │       ├── service/        # Lógica de negócio
│   │       ├── repository/     # Acesso a dados
│   │       ├── model/          # Entidades
│   │       ├── dto/            # Objects de transferência
│   │       └── config/         # Configurações
│   └── resources/
│       ├── application.yml     # Configurações da aplicação
│       └── data.sql           # Dados iniciais
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+ (opcional, usa H2 por padrão)

### Executando a aplicação

```bash
# Clone o repositório
git clone https://github.com/Guiziin227/ParkingSpringBoot.git

# Entre no diretório
cd ParkingSpringBoot

# Execute com Maven
mvn spring-boot:run
```

### Usando Docker

```bash
# Build da imagem
docker build -t parking-system .

# Execute o container
docker run -p 8080:8080 parking-system
```

## 🌐 Endpoints da API

### Veículos
- `POST /api/vehicles` - Registrar entrada de veículo
- `PUT /api/vehicles/{id}/exit` - Registrar saída de veículo
- `GET /api/vehicles` - Listar todos os veículos
- `GET /api/vehicles/{id}` - Buscar veículo por ID

### Vagas
- `GET /api/spots` - Listar todas as vagas
- `GET /api/spots/available` - Listar vagas disponíveis
- `POST /api/spots` - Criar nova vaga

### Relatórios
- `GET /api/reports/occupancy` - Relatório de ocupação
- `GET /api/reports/revenue` - Relatório de receitas

## 📊 Documentação da API

Após iniciar a aplicação, acesse:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `DB_HOST` | Host do banco de dados | `localhost` |
| `DB_PORT` | Porta do banco de dados | `3306` |
| `DB_NAME` | Nome do banco de dados | `parking_db` |
| `DB_USERNAME` | Usuário do banco | `root` |
| `DB_PASSWORD` | Senha do banco | `password` |


## 📦 Build e Deploy

### Build para produção
```bash
mvn clean package -Pprod
```

### Deploy com Docker Compose
```yaml
version: '3.8'
services:
  parking-app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - DB_HOST=mysql
      - DB_USERNAME=parking_user
      - DB_PASSWORD=parking_pass
    depends_on:
      - mysql
  
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_DATABASE: parking_db
      MYSQL_USER: parking_user
      MYSQL_PASSWORD: parking_pass
      MYSQL_ROOT_PASSWORD: root_pass
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

⭐ **Gostou do projeto? Deixe uma estrela!**
