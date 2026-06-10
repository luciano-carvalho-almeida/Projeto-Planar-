# Projeto Planar: Sistema Inteligente de Gestão e Predição de Gastos Automotivos

## Memorial Descritivo e Documentação Técnica

---

### **Sumário**
1. [Visão Geral do Projeto](#1-visão-geral-do-projeto)
2. [Arquitetura de Software e Camadas](#2-arquitetura-de-software-e-camadas)
3. [Modelagem Matemática e Regras de Negócio](#3-modelagem-matemática-e-regras-de-negócio)
4. [Estrutura do Banco de Dados Relacional](#4-estrutura-do-banco-de-dados-relacional)
5. [Ecossistema de Testes Automatizados e Confiabilidade](#5-ecossistema-de-testes-automatizados-e-confiabilidade)
6. [Tecnologias, Ferramentas e Requisitos](#6-tecnologias-ferramentas-e-requisitos)
7. [Instruções de Compilação e Execução](#7-instruções-de-compilação-e-execução)

---

## 1. Visão Geral do Projeto

O **Projeto Planar** é uma plataforma de software voltada ao monitoramento, análise de eficiência e predição estatística de custos operacionais de veículos automotivos. O sistema visa mitigar a imprevisibilidade financeira de proprietários e gestores de frotas através do rastreamento automatizado de abastecimentos, consumo energético e cronogramas de manutenção preventiva.

A aplicação expõe uma API REST robusta desenvolvida sobre o ecossistema Spring Boot, assegurando portabilidade, escalabilidade e facilidade de integração com interfaces de usuário (UIs) ou sensores telemáticos.

---

## 2. Arquitetura de Software e Camadas

O sistema adota o padrão arquitetural em camadas para garantir baixo acoplamento e alta coesão entre os componentes:

* **Camada de Apresentação (`controller`):** Controladores REST responsáveis por expor os endpoints da API, interceptar requisições HTTP, serializar/deserializar payloads JSON e gerenciar códigos de status HTTP apropriados.
* **Camada de Transferência (`dto`):** Objetos de Transferência de Dados utilizados para desacoplar as entidades de persistência dos contratos de entrada e saída da API, mitigando vulnerabilidades como *Mass Assignment*.
* **Camada de Negócio (`service`):** Concentra o núcleo de inteligência da aplicação. Implementa as validações de domínio, cálculos matemáticos complexos e as regras de persistência transacional.
* **Camada de Domínio (`entities`):** Classes de modelo que representam o coração do negócio, encapsulando os atributos, comportamentos e os relacionamentos mapeados por anotações de persistência.
* **Camada de Acesso a Dados (`repository`):** Interfaces abstratas baseadas no Spring Data JPA, que estendem `JpaRepository` para gerenciar operações CRUD e consultas customizadas via JPQL (*Java Persistence Query Language*).

---

## 3. Modelagem Matemática e Regras de Negócio

O rigor técnico do Projeto Planar é fundamentado em três motores de cálculo principais localizados na camada de serviços:

### 3.1. Predição Estatística de Gastos (`FinanceiroService`)
O sistema projeta os gastos operacionais futuros (mensais e anuais) por meio de um algoritmo de **Regressão Linear Simples**. O modelo analisa o histórico de dispersão de dias acumulados versus custos registrados para traçar uma reta de tendência matemática baseada nas fórmulas lineares estáveis:

$$m = \frac{n\sum(XY) - \sum X \sum Y}{n\sum(X^2) - (\sum X)^2}$$

$$b = \frac{\sum Y - m\sum X}{n}$$

Onde $m$ representa a inclinação da curva de gastos, $b$ o intercepto, e $diaFuturo$ determina o ponto de projeção temporal ($30$ ou $365$ dias).

### 3.2. Análise de Eficiência Energética (`ConsumoService`)
A recomendação econômica entre combustíveis baseia-se na **Regra da Eficiência Energética Flutuante (Métrica de 70%)**, calculando a razão exata entre o preço do Etanol hidratado e da Gasolina comercial. Valores cuja proporção seja $\le 0.70$ retornam a indicação de Etanol, mitigando desperdícios financeiros. O sistema é blindado contra divisões por zero ou valores nulos nas entradas de parâmetros de bombas através de cláusulas de guarda (*Guard Clauses*).

### 3.3. Motor de Alertas Preventivos (`AlertaService`)
Avalia de forma preditiva e cronológica o vencimento de planos de manutenção. O motor cruza os dados em tempo real da quilometragem atual do veículo e a data da requisição com os limites geométricos estabelecidos no contrato de revisão de fábrica (ex: troca de óleo a cada 10.000 km ou 6 meses), gerando notificações de risco de forma automática.

---

## 4. Estrutura do Banco de Dados Relacional

A persistência adota o mapeamento objeto-relacional (ORM) gerenciado pelo Hibernate. A base de dados estruturada possui as seguintes entidades fundamentais:

* **`Veiculo`**: Entidade central contendo dados estruturais (placa, modelo, ano, quilometragem atual e data de aquisição).
* **`Abastecimento`**: Mapeia o histórico de consumo, mantendo um relacionamento `@ManyToOne` com `Veiculo`. Armazena o tipo de combustível (`TipoCombustivel` via Enum), litros e preço unitário.
* **`PlanoRevisao`**: Define as metas estipuladas de manutenção periódica por quilometragem ou tempo.
* **`ServicoManutencao`**: Registra as intervenções físicas executadas no veículo, associando-se de forma transacional a um `PlanoRevisao` e a um `Veiculo`.
* **`Alerta`**: Armazena as notificações críticas disparadas pelo sistema, classificadas por `TipoAlerta`.

---

## 5. Ecossistema de Testes Automatizados e Confiabilidade

Para assegurar a estabilidade do sistema e mitigar o risco de regressões operacionais, a aplicação conta com uma robusta cobertura de testes automatizados executada sob o framework **JUnit 5** e a biblioteca de asserções **AssertJ**.

A suíte completa é composta por **71 testes automatizados**, distribuídos metodologicamente entre:

1.  **Testes Unitários:** Focados em isolar e garantir a exatidão matemática das lógicas de serviço (com suporte do `Mockito` para mockar repositórios) e comportamentos intrínsecos das entidades.
2.  **Testes de Integração de Controladores (`@WebMvcTest`):** Validação estrita do comportamento de contratos HTTP, payloads e códigos de resposta (200 OK, 201 Created, 404 Not Found) sem inicializar o servidor de aplicação completo, utilizando `MockMvc`.
3.  **Testes de Integração Ponta a Ponta (`@SpringBootTest`):** Execução do pipeline completo da aplicação utilizando o banco de dados em memória `H2 Database`, aplicando a diretiva `@DirtiesContext` para garantir o isolamento higiênico dos dados a cada método de teste executado.

As métricas de qualidade de código e cobertura de branches são validadas de forma transparente por meio da biblioteca **JaCoCo**, garantindo cobertura integral das principais rotas e tratamentos de exceções do software.

---

## 6. Tecnologias, Ferramentas e Requisitos

* **Linguagem de Programação:** Java 17 (JDK 17)
* **Framework Core:** Spring Boot 3.5.14
* **Mapeamento ORM:** Spring Data JPA / Hibernate
* **Banco de Dados Embutido:** H2 Database Engine (ambiente de testes/memória)
* **Gerenciador de Dependências e Build:** Apache Maven 3.x
* **Ferramenta de Cobertura:** JaCoCo Plugin 0.8.12
* **Biblioteca de Utilitários:** Project Lombok

---

## 7. Instruções de Compilação e Execução

### 7.1. Pré-requisitos
Certifique-se de possuir a **JDK 17** e o **Maven** configurados nas variáveis de ambiente do seu sistema.

### 7.2. Compilar o Projeto e Rodar os Testes
```bash
mvn clean test
```
### 7.3. Verificar o Relatório de Cobertura (JaCoCo)

Após a execução dos testes com sucesso, o relatório visual detalhado em formato HTML estará disponível no diretório local:
target/site/jacoco/index.html
### 7.4. Executar a Aplicação

Para inicializar o servidor embutido Tomcat e disponibilizar os endpoints REST locais na porta 8080, utilize o comando:
Bash
```bash
mvn spring-boot:run
```
O console do H2 poderá ser acessado em tempo de execução via navegador através do endereço http://localhost:8080/h2-console utilizando as credenciais padrão descritas no arquivo application.properties.
