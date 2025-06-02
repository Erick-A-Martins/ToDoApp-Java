# Projeto: To-Do List

## Objetivo
Neste vasto mundinho do java, temos diversos frameworks, e é comum iniciar já usando um. Porem, isso cria um problema... os desenvolvedores que se acostumam a trabalhar assim, não fazem ideia do que acontece por debaixo dos panos. Você não será mais um desses. E para começar seu treinamento jedi, iremos desenvolver um projeto de lista de tarefas (To-Do List).
Iremos usar apenas coisas básicas neste primeiro momento, como JDBC, Servlet e muito Java puro.

## Configuração do Ambiente
### Bibliotecas Necessárias
1. H2 Database (versão mais recente)
2. Servlet API
3. JSON library (org.json)
4. Jetty Server
5. Jetty Servlet

### Como Adicionar Bibliotecas no IntelliJ IDEA
1. File → Project Structure (Ctrl + Alt + Shift + S)
2. Modules → Dependencies → + → JARs or directories
3. Navegue até o arquivo .jar e adicione

Links úteis para configuração:
- [Como adicionar bibliotecas externas no IntelliJ IDEA](https://www.jetbrains.com/help/idea/library.html#define-a-project-library)
- [Download H2 Database](https://www.h2database.com/html/download.html)
- [Documentação H2](https://www.h2database.com/html/main.html)
- [Jetty Documentation](https://www.eclipse.org/jetty/documentation/)

## Estrutura do Projeto
O projeto será desenvolvido em fases incrementais para melhor aprendizado:

### Fase 1: Fundamentos Java e JDBC
- Criação das classes básicas (Task)
- Implementação do CRUD usando JDBC puro
- Conexão com banco de dados H2 (em memória para desenvolvimento)
- Tratamento de exceções
- Padrão DAO (Data Access Object)

Nesta etapa, quero que você crie uma implementação da TaskDAO utilizando JDBC (TaskDaoJdbc), deixei um teste unitário criado (TaskDaoTest) para te auxiliar. 
Vocè precisa de alguma forma, cria uma conexão, criar tabelas e manipuladas. 

Links:
[O que é uma DAO?](https://duckduckgo.com/?q=O+que+%C3%A9+uma+dao+em+java%3F&ia=chat&bang=true&atb=v478-1)

### Fase 2: Transformar em projeto maven
- Criar um repositorio no github (pessoal)
- Criar um projeto maven
- Migrar o codigo existente para este projeto maven, declarar
    - h2
    - junit5
- O que tem que funcionar?
    - Compilação via maven
    - Execução dos testes
    - Utilizar java 21

### Fase 3: Servlets e Web (listar)
- Configurar o Jetty Server programaticamente no método main
- Criar uma Servlet que:
  - Liste em HTML as tasks

### Fase 4: Servlets e Web (criar)
- Criar/Atualizar a Servlet para que:
  - Permita adicionar uma nova task

### Fase 5: Servlets e Web (criar)
- Criar/Atualizar a Servlet para que:
  - Permita deletar nova task criada

### Fase 6: Servlets e Web (criar)
- Criar/Atualizar a Servlet para que:
  - Permita editar uma task criada

### Fase 7: MiniServlet MVC
- Essa servlet deve controlar para onde a request será encaminhada,
  ex: o /listar, deve encaminhar para ListarTaskPage.java, sendo que esta implementa a interface Page.
- Caso não exista nenhuma correspondência, retornar uma tela de 404 (não encontrado)
``` java
public interface Page {
    String render(Map<String, Object> parameters);
}
```

### Fase 8: Autenticação utilizando Filter
- Implementar um filtro que garanta que o usuário esteja logado, caso contrario, solicitar autorização via BASIC
- teste:teste

## Tecnologias Utilizadas
- Java 21
- JDBC
- H2 Database (em memória)
- Servlet API
- Eclipse Jetty
- HTML/CSS
