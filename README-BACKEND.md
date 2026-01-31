# 📦 Sistema de Estoque de Toners - Backend API

API RESTful desenvolvida com Spring Boot para gerenciamento de estoque de toners de impressoras.

---

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.10** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Web** - Criação de APIs REST
- **H2 Database** - Banco de dados em memória (desenvolvimento)
- **Maven 3.9.12** - Gerenciador de dependências e build

---

## 📁 Estrutura do Projeto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/estoquetonner/backend/
│   │   │   ├── controllers/           # Controladores REST
│   │   │   │   └── TonnerController.java
│   │   │   │
│   │   │   ├── services/              # Lógica de negócio
│   │   │   │   └── TonnerService.java
│   │   │   │
│   │   │   ├── repositories/          # Acesso a dados
│   │   │   │   └── TonnerRepository.java
│   │   │   │
│   │   │   ├── entities/              # Entidades JPA
│   │   │   │   ├── Tonner.java
│   │   │   │   └── TonnerStatus.java
│   │   │   │
│   │   │   ├── dtos/                  # Data Transfer Objects
│   │   │   │   ├── TonnerDTO.java
│   │   │   │   ├── EstoqueTotalTonnerDTO.java
│   │   │   │   └── SolicitarTonnerDTO.java
│   │   │   │
│   │   │   └── BackendApplication.java # Classe principal
│   │   │
│   │   └── resources/
│   │       ├── application.properties           # Configuração principal
│   │       ├── application-dev.properties       # Perfil de desenvolvimento
│   │       └── import.sql                       # Dados iniciais
│   │
│   └── test/
│       └── java/com/estoquetonner/backend/
│           └── BackendApplicationTests.java
│
├── .mvn/                              # Maven Wrapper
├── mvnw                               # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                           # Maven Wrapper (Windows)
├── pom.xml                            # Configuração Maven
└── README.md                          # Este arquivo
```

---

## 📚 Detalhamento das Camadas

### 1️⃣ **Entities** (Entidades JPA)

Representam as tabelas do banco de dados.

#### **Tonner.java**
```java
@Entity
@Table(name = "tb_tonner")
public class Tonner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelo;
    private Integer estoqueMinimo;
    private Integer estoqueAtual;
    private TonnerStatus status;
    private Integer solicitar;
}
```

**Campos:**
- `id`: Identificador único (auto-incremento)
- `modelo`: Nome/modelo do toner (ex: "TK3182 - BLACK MONOCROMÁTICA")
- `estoqueMinimo`: Quantidade mínima necessária em estoque
- `estoqueAtual`: Quantidade atual disponível
- `status`: Status do estoque (CRITICO, ALERTA, OK)
- `solicitar`: Quantidade a ser solicitada

#### **TonnerStatus.java** (ENUM)
```java
public enum TonnerStatus {
    CRITICO,  // Estoque crítico (< 50% do mínimo)
    ALERTA,   // Estoque baixo (50-99% do mínimo)
    OK        // Estoque adequado (>= 100% do mínimo)
}
```

---

### 2️⃣ **DTOs** (Data Transfer Objects)

Objetos usados para transferir dados entre camadas, evitando exposição direta das entidades.

#### **TonnerDTO.java**
DTO principal com todas as informações do toner.

#### **EstoqueTotalTonnerDTO.java**
DTO para retornar apenas a soma total do estoque atual.
```java
{
  "estoqueAtual": 26
}
```

#### **SolicitarTonnerDTO.java**
DTO para retornar a soma total de toners a solicitar.
```java
{
  "solicitar": 17
}
```

---

### 3️⃣ **Repositories** (Repositórios)

Interface que estende `JpaRepository` para acesso ao banco de dados.

#### **TonnerRepository.java**

**Queries Customizadas:**

1. **Busca com Filtros**
```java
@Query(nativeQuery = true, value = 
  "SELECT obj.* FROM tb_tonner obj " +
  "WHERE UPPER(obj.modelo) LIKE UPPER(CONCAT('%', :modelo, '%')) " +
  "AND UPPER(obj.status) LIKE UPPER(CONCAT('%', :status, '%'))")
List<Tonner> searchByModelo(String modelo, String status);
```
- Busca por modelo (case-insensitive)
- Filtra por status (opcional)

2. **Soma do Estoque Total**
```java
@Query(nativeQuery = true, value = 
  "SELECT SUM(estoque_atual) FROM tb_tonner")
Integer searchSumAtualTotal();
```
- Retorna a soma de todos os estoques atuais

3. **Soma de Toners a Solicitar**
```java
@Query(nativeQuery = true, value = 
  "SELECT SUM(solicitar) FROM tb_tonner")
Integer searchSumSolicitar();
```
- Retorna a soma total de toners que precisam ser solicitados

---

### 4️⃣ **Services** (Serviços)

Camada de lógica de negócio.

#### **TonnerService.java**

**Métodos:**

1. **findAll(modelo, status)**
   - Busca toners com filtros opcionais
   - Retorna lista de `TonnerDTO`
   - Transacional (readOnly implícito)

2. **findAllEstoqueTotal()**
   - Calcula soma total do estoque
   - Retorna `EstoqueTotalTonnerDTO`

3. **findAllSolicitarTotal()**
   - Calcula soma total a solicitar
   - Retorna `SolicitarTonnerDTO`

---

### 5️⃣ **Controllers** (Controladores REST)

Expõe os endpoints da API.

#### **TonnerController.java**

**Base URL:** `/tonners`

---

## 🔗 Endpoints da API

### 📋 **GET /tonners**
Lista todos os toners com filtros opcionais.

**Parâmetros de Query:**
- `modelo` (opcional): Filtro por modelo (busca parcial, case-insensitive)
- `status` (opcional): Filtro por status (CRITICO, ALERTA, OK)

**Exemplos de Uso:**
```http
GET /tonners
GET /tonners?modelo=TK3182
GET /tonners?status=CRITICO
GET /tonners?modelo=BLACK&status=ALERTA
```

**Resposta de Sucesso (200 OK):**
```json
[
  {
    "id": 1,
    "modelo": "TK3182 - BLACK MONOCROMATICA",
    "estoqueMinimo": 15,
    "estoqueAtual": 12,
    "status": "ALERTA",
    "solicitar": 3
  },
  {
    "id": 2,
    "modelo": "TK5242 - CIANO",
    "estoqueMinimo": 10,
    "estoqueAtual": 5,
    "status": "ALERTA",
    "solicitar": 5
  }
]
```

---

### 📊 **GET /tonners/estoque-total**
Retorna a soma total do estoque atual.

**Resposta de Sucesso (200 OK):**
```json
{
  "estoqueAtual": 26
}
```

**Cálculo:**
```
TK3182: 12 + TK5242 CIANO: 5 + TK5242 BLACK: 1 + PLOTTER: 8 = 26
```

---

### 📦 **GET /tonners/solicitar-total**
Retorna a soma total de toners a solicitar.

**Resposta de Sucesso (200 OK):**
```json
{
  "solicitar": 17
}
```

**Cálculo:**
```
TK3182: 3 + TK5242 CIANO: 5 + TK5242 BLACK: 9 + PLOTTER: 0 = 17
```

---

## ⚙️ Configurações

### **application.properties**
```properties
spring.profiles.active=${APP_PROFILE:dev}
spring.jpa.open-in-view=false
cors.origins=${CORS_ORIGINS:http://localhost:5173,http://localhost:3000}
```

**Variáveis:**
- `APP_PROFILE`: Perfil ativo (padrão: dev)
- `CORS_ORIGINS`: Origens permitidas para CORS

---

### **application-dev.properties**
```properties
# H2 Database (Em Memória)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# Console H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Logs SQL
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**Acesso ao Console H2:**
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(vazio)*

---

### **import.sql** (Dados Iniciais)
```sql
INSERT INTO tb_tonner (modelo, estoque_minimo, estoque_atual, status, solicitar) 
VALUES ('TK3182 - BLACK MONOCROMATICA', 15, 12, 1, 3);

INSERT INTO tb_tonner (modelo, estoque_minimo, estoque_atual, status, solicitar) 
VALUES ('TK5242 - CIANO', 10, 5, 1, 5);

INSERT INTO tb_tonner (modelo, estoque_minimo, estoque_atual, status, solicitar) 
VALUES ('TK5242 - BLACK', 10, 1, 0, 9);

INSERT INTO tb_tonner (modelo, estoque_minimo, estoque_atual, status, solicitar) 
VALUES ('PLOTTER - PFI - 030BK', 4, 8, 2, 0);
```

**Observação:** Status é mapeado como Integer no SQL:
- `0` = CRITICO
- `1` = ALERTA
- `2` = OK

---

## 🚀 Como Executar

### **Pré-requisitos:**
- Java 21 ou superior
- Maven 3.9+ (ou usar o Maven Wrapper incluído)

---

### **Opção 1: Usando Maven Wrapper (Recomendado)**

#### Windows:
```cmd
mvnw.cmd spring-boot:run
```

#### Linux/Mac:
```bash
chmod +x mvnw
./mvnw spring-boot:run
```

---

### **Opção 2: Usando Maven Instalado**
```bash
mvn spring-boot:run
```

---

### **Opção 3: Build JAR e Executar**
```bash
# Build
./mvnw clean package

# Executar
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

---

### **Acessar a Aplicação:**
- **API Base:** `http://localhost:8080`
- **H2 Console:** `http://localhost:8080/h2-console`
- **Endpoints:** `http://localhost:8080/tonners`

---

## 🧪 Testando a API

### **Usando cURL:**

```bash
# Listar todos os toners
curl http://localhost:8080/tonners

# Buscar por modelo
curl "http://localhost:8080/tonners?modelo=BLACK"

# Buscar por status
curl "http://localhost:8080/tonners?status=CRITICO"

# Estoque total
curl http://localhost:8080/tonners/estoque-total

# Total a solicitar
curl http://localhost:8080/tonners/solicitar-total
```

---

### **Usando Browser:**
```
http://localhost:8080/tonners
http://localhost:8080/tonners?modelo=TK3182
http://localhost:8080/tonners/estoque-total
http://localhost:8080/tonners/solicitar-total
```

---

### **Usando Postman/Insomnia:**

**Collection Base:**
```
GET http://localhost:8080/tonners
GET http://localhost:8080/tonners?modelo=BLACK
GET http://localhost:8080/tonners?status=ALERTA
GET http://localhost:8080/tonners/estoque-total
GET http://localhost:8080/tonners/solicitar-total
```

---

## 🏗️ Arquitetura em Camadas

```
┌─────────────────────────────────────┐
│      Controllers (REST API)         │  ← Endpoints HTTP
├─────────────────────────────────────┤
│      Services (Lógica de Negócio)   │  ← Regras de negócio
├─────────────────────────────────────┤
│      Repositories (Acesso a Dados)  │  ← Queries e persistência
├─────────────────────────────────────┤
│      Entities (Modelo de Dados)     │  ← Entidades JPA
├─────────────────────────────────────┤
│      Database (H2 em memória)       │  ← Armazenamento
└─────────────────────────────────────┘
```

**Fluxo de Requisição:**
```
Client → Controller → Service → Repository → Database
                                      ↓
Client ← DTO ← Service ← Entity ← Repository
```

---

## 🎯 Boas Práticas Implementadas

✅ **Separação de Camadas** - Controller, Service, Repository, Entity, DTO
✅ **DTOs** - Evita exposição direta das entidades
✅ **Transações** - Uso de `@Transactional` nos services
✅ **Queries Nativas** - Queries SQL customizadas quando necessário
✅ **Injeção de Dependências** - Uso de `@Autowired`
✅ **Convenções Spring** - Nomenclatura e organização padrão
✅ **Perfis de Ambiente** - Separação dev/prod
✅ **CORS Configurável** - Via variáveis de ambiente
✅ **Maven Wrapper** - Não precisa instalar Maven

---

## 🔮 Funcionalidades Pendentes / Roadmap

### Em Desenvolvimento:
- [ ] Endpoint POST para criar novos toners
- [ ] Endpoint PUT para atualizar toners
- [ ] Endpoint DELETE para remover toners
- [ ] Validações de entrada (Bean Validation)
- [ ] Tratamento de exceções global (@ControllerAdvice)
- [ ] Paginação e ordenação de resultados
- [ ] Documentação Swagger/OpenAPI
- [ ] Testes unitários e de integração

### Planejado:
- [ ] Autenticação e autorização (Spring Security + JWT)
- [ ] Auditoria de mudanças (quem/quando alterou)
- [ ] Histórico de solicitações de toners
- [ ] Notificações por e-mail
- [ ] Relatórios em PDF/Excel
- [ ] Dashboard com gráficos
- [ ] Migração para PostgreSQL/MySQL
- [ ] Deploy em produção (Docker + Cloud)

---

## 🛠️ Troubleshooting

### **Erro: "Port 8080 already in use"**
```properties
# Adicione em application.properties:
server.port=8081
```

### **Erro ao compilar no Windows**
```cmd
# Use o Maven Wrapper com .cmd
mvnw.cmd clean install
```

### **H2 Console não abre**
Verifique se está usando o perfil `dev`:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### **Queries não encontram dados**
Verifique se o `import.sql` está sendo executado:
```properties
# Em application-dev.properties
spring.jpa.show-sql=true
```

---

## 📦 Build para Produção

```bash
# 1. Build otimizado
./mvnw clean package -DskipTests

# 2. JAR gerado em:
target/backend-0.0.1-SNAPSHOT.jar

# 3. Executar com perfil de produção
java -jar target/backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

---

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto é desenvolvido para fins educacionais.

---

## 👥 Autor

Desenvolvido como parte do sistema de gerenciamento de estoque de toners.

---

## 📞 Suporte

Para dúvidas ou problemas:
- Abra uma issue no GitHub
- Entre em contato com a equipe de desenvolvimento

---

**Status do Projeto:** 🚧 Em Desenvolvimento

**Última Atualização:** Janeiro 2025
