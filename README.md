# Projeto: Agendamento de Consultas em Java

Este é um projeto acadêmico desenvolvido em Java, com o objetivo de aplicar conceitos de arquitetura de software em camadas, persistência de dados com JDBC e desenvolvimento de interfaces de usuário (Console e Swing).

## 🎯 Objetivo
O sistema permite o cadastro de médicos e pacientes, e o agendamento de consultas, seguindo um conjunto de regras de negócio. O projeto foi estruturado para ser executado tanto via terminal (console) quanto através de uma interface gráfica (Swing).

---

## 👤 Informações do Aluno

* **Nome:** `Jefferson Junior Alvarez Urbina`
* **RM:** `RM 558497`

---

## 🚀 Evolução Implementada (Resumo)

Foi implementada a funcionalidade completa de cadastro para Médicos e Pacientes, incluindo as camadas de serviço, acesso a dados (DAO) e interface de usuário. Adicionalmente, o sistema foi estruturado para suportar duas interfaces distintas (Console e Swing), que podem ser selecionadas através de um argumento de linha de comando (`--ui`) configurado diretamente no ambiente de desenvolvimento para facilitar a execução e os testes.

---

## 🏛️ Arquitetura e Observações sobre o Projeto

O projeto foi refatorado para seguir o padrão de arquitetura em camadas, separando claramente as responsabilidades, o que melhora a manutenção, a clareza e permite a fácil expansão do sistema.

* **`config`**: Centraliza as configurações de conexão com o banco de dados (`OracleConnectionFactory`).
* **`domain`**: Contém as entidades principais e regras de negócio essenciais (`Consulta`, `Medico`, `Paciente`).
* **`dao`**: Camada de Acesso a Dados, responsável pela comunicação com o banco de dados via JDBC.
* **`service`**: Orquestra os casos de uso e a lógica da aplicação (`ConsultaService`, `MedicoService`, `PacienteService`).
* **`ui`**: Camada de apresentação, contendo a lógica para as interfaces de Console (`ConsoleMain`) e Gráfica (`SwingMain`).

Os nomes das classes foram padronizados para refletir suas responsabilidades, garantindo um código limpo e de fácil compreensão.
