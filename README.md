# VEND Desktop - Sistema de Gerenciamento de Veículos

<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring" />
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Swing-007396?style=for-the-badge&logo=java&logoColor=white" alt="Swing" />
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven" />
</div>

## 📋 Sobre o Projeto

VEND Desktop é uma aplicação robusta de gerenciamento de estoque de veículos, desenvolvida com Java Swing e Spring Framework. O sistema oferece funcionalidades completas para administração de concessionárias, incluindo integração com a Tabela FIPE e assistente virtual inteligente powered by Google Gemini.

## ✨ Funcionalidades

### 🔐 Gerenciamento de Usuários
- Sistema de autenticação seguro com criptografia SHA-256
- Cadastro e login de administradores
- Validação robusta de credenciais

### 🚗 Gestão de Estoque
- **Cadastro de Veículos**
  - Registro completo com imagem
  - Suporte a múltiplos tipos de carroceria
  - Validação de dados em tempo real
  
- **Consulta e Filtros**
  - Busca por marca e modelo
  - Ordenação por preço
  - Visualização em tabela interativa
  
- **Operações CRUD**
  - Atualização de informações
  - Exclusão com confirmação
  - Detalhamento completo do veículo

### 💰 Integração Tabela FIPE
- Consulta em tempo real de valores
- Suporte para carros e motos
- Filtros por marca, modelo e ano
- Exibição organizada em tabela

### 🤖 Assistente Virtual (Gemini AI)
- Respostas inteligentes sobre veículos
- Interface conversacional amigável
- Integração com Google Gemini 2.5 Flash
- Consultas sobre especificações técnicas

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas bem definida:

```
com.example.VEND/
├── config/                 # Configurações
│   └── JpaConfig          # Configuração JPA/Hibernate
├── model/                  # Modelos de domínio
│   ├── Carro
│   ├── UsuarioAdm
│   ├── UsuarioCliente
│   ├── enums/
│   │   └── Carroceria
│   └── [DTOs FIPE]
├── repository/             # Camada de persistência
│   ├── RepositorioCarro
│   ├── RepositorioUsuarioAdm
│   └── RepositorioUsuarioCliente
├── service/                # Lógica de negócio
│   ├── CarroService
│   └── UsuarioService
├── util/                   # Utilitários
│   ├── ConsumoAPi
│   ├── ConsumoGemini
│   ├── ConverteJson
│   └── CriptografiaUtil
└── view/                   # Interface gráfica
    ├── TelaLogin
    ├── TelaCadastro
    ├── TelaPrincipal
    ├── TelaRegistrarVeiculo
    ├── TelaFiltroEstoque
    ├── TelaListaEstoque
    ├── TelaDetalhesVeiculo
    ├── TelaFiltroFipe
    ├── TelaFipe
    └── TelaAssistenteGemini
```

## 🛠️ Tecnologias Utilizadas

### Core Technologies
| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Java** | 21 | Linguagem principal |
| **Spring Boot** | 3.5.6 | Framework backend |
| **Maven** | 3.9.11 | Gerenciador de dependências |
| **Java Swing** | Built-in | Interface gráfica |

### Frameworks e Bibliotecas
| Biblioteca | Versão | Uso |
|-----------|--------|-----|
| **Spring Data JPA** | 3.5.6 | Persistência de dados |
| **PostgreSQL Driver** | Runtime | Banco de dados |
| **Jackson** | Latest | Serialização JSON |
| **HikariCP** | Included | Pool de conexões |
| **Google GenAI** | 1.28.0 | Integração com Gemini |

### APIs Externas
- **Tabela FIPE API**: `https://parallelum.com.br/fipe/api/v1/`
- **Google Gemini API**: Modelo `gemini-2.5-flash`

## 🚀 Como Executar

### Pré-requisitos

- **JDK 21** ou superior
- **Maven 3.9+**
- **PostgreSQL 12+**
- **IDE** (IntelliJ IDEA, Eclipse, NetBeans)
- **Google API Key** (para funcionalidade Gemini)

### Configuração do Banco de Dados

1. **Instale o PostgreSQL** e crie o banco de dados:
```sql
CREATE DATABASE vend_db;
```

2. **Configure as credenciais** em `JpaConfig.java`:
```java
dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/vend_db");
dataSource.setUsername("seu_usuario");
dataSource.setPassword("sua_senha");
```

### Configuração da API Gemini

1. **Obtenha uma API Key** em [Google AI Studio](https://makersuite.google.com/app/apikey)

2. **Configure a variável de ambiente**:

**Windows (PowerShell):**
```powershell
$env:GOOGLE_API_KEY="sua_chave_aqui"
```

**Linux/Mac:**
```bash
export GOOGLE_API_KEY="sua_chave_aqui"
```

**Permanente (Windows):**
```
setx GOOGLE_API_KEY "sua_chave_aqui"
```

### Executando a Aplicação

1. **Clone o repositório**
```bash
git clone https://github.com/seu-usuario/vend-desktop.git
cd vend-desktop
```

2. **Compile o projeto**
```bash
mvn clean install
```

3. **Execute via Maven**
```bash
mvn spring-boot:run
```

**OU**

4. **Execute via IDE**
   - Abra o projeto na sua IDE
   - Execute a classe `VendApplication.java`

## 📦 Estrutura do Banco de Dados

### Tabela: usuarios_adm
```sql
CREATE TABLE usuarios_adm (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);
```

### Tabela: usuarios_cliente
```sql
CREATE TABLE usuarios_cliente (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL
);
```

### Tabela: carros
```sql
CREATE TABLE carros (
    id SERIAL PRIMARY KEY,
    carroceria VARCHAR(50),
    imagem BYTEA,
    modelo VARCHAR(100),
    preco DOUBLE PRECISION,
    marca VARCHAR(100),
    ano INTEGER,
    interesse_usuario_id BIGINT,
    FOREIGN KEY (interesse_usuario_id) REFERENCES usuarios_cliente(id)
);
```

## 🎨 Interface do Usuário

### Telas Principais

1. **Tela de Login**
   - Autenticação de administradores
   - Validação de credenciais
   - Redirecionamento para cadastro

2. **Tela Principal (Dashboard)**
   - Menu centralizado com 4 opções
   - Design moderno e intuitivo
   - Navegação simplificada

3. **Gestão de Estoque**
   - Filtros avançados
   - Tabela interativa
   - Visualização de detalhes

4. **Consulta FIPE**
   - Seleção por tipo de veículo
   - ComboBox dinâmico
   - Resultados em tabela formatada

5. **Assistente Gemini**
   - Interface conversacional
   - Respostas contextualizadas
   - Design clean e funcional

## 🔒 Segurança

### Criptografia
- **Algoritmo**: SHA-256
- **Aplicação**: Senhas de usuários
- **Implementação**: `CriptografiaUtil.gerarHash()`

### Validações
- Email formato RFC 5322
- Senha mínima de 6 caracteres
- Campos obrigatórios verificados
- Prevenção de SQL Injection via JPA

## 📊 Padrões de Design

- **MVC (Model-View-Controller)**: Separação de responsabilidades
- **DAO (Data Access Object)**: Repositories Spring Data
- **Service Layer**: Lógica de negócio isolada
- **Dependency Injection**: Autowired do Spring
- **Observer Pattern**: Listeners de eventos Swing

## 🧪 Testes

```bash
# Executar testes unitários
mvn test

# Executar com cobertura
mvn clean test jacoco:report
```

## 📝 Funcionalidades Detalhadas

### Sistema de Imagens
- Upload de imagens (JPG, PNG, GIF, BMP)
- Limite de 10MB por arquivo
- Armazenamento em BYTEA (PostgreSQL)
- Preview antes do registro
- Redimensionamento automático

### Consumo de APIs
- **HTTP Client Java 11+**: Requisições síncronas
- **Jackson**: Parse de JSON
- **Error Handling**: Try-catch robusto
- **Timeout**: Configurado automaticamente

### Validações de Negócio
- Ano do veículo não pode ser futuro
- Preços devem ser positivos
- Marca e modelo obrigatórios
- Carroceria deve ser selecionada

## 🐛 Troubleshooting

### Problema: Erro ao conectar com PostgreSQL
**Solução**: Verifique se o PostgreSQL está rodando e as credenciais estão corretas em `JpaConfig.java`

### Problema: Gemini não responde
**Solução**: Confirme se a variável `GOOGLE_API_KEY` está configurada corretamente

### Problema: Imagem não carrega
**Solução**: Verifique se o arquivo tem menos de 10MB e está em formato suportado

### Problema: Tabela não atualiza
**Solução**: Clique no botão "Atualizar" ou reabra a tela de listagem

## 📈 Roadmap

- [ ] Sistema de relatórios em PDF
- [ ] Gráficos de análise de estoque
- [ ] Backup automático do banco
- [ ] Sistema de notificações
- [ ] Multi-idiomas (i18n)
- [ ] Modo escuro
- [ ] Exportação para Excel
- [ ] Sistema de permissões granular

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

### Padrões de Código
- Seguir convenções Java
- Comentários em português
- JavaDoc para métodos públicos
- Testes para novas features

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👥 Autores

- **Seu Nome** - *Desenvolvimento inicial* - [GitHub](https://github.com/seu-usuario)

## 🙏 Agradecimentos

- API FIPE pela disponibilização dos dados
- Google pelo Gemini AI
- Spring Team pelo excelente framework
- Comunidade Java pela documentação

## 📞 Suporte

Para dúvidas, sugestões ou problemas:
- 📧 Email: suporte@vend.com
- 🐛 Issues: [GitHub Issues](https://github.com/seu-usuario/vend-desktop/issues)
- 💬 Discord: [Servidor VEND](https://discord.gg/vend)

---

<div align="center">
  <strong>Desenvolvido com ☕ e Java</strong>
  
  ⭐ Star este projeto se ele foi útil para você!
</div>
