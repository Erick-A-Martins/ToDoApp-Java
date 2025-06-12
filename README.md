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

### Fase 9: Conceitos de reflection
- Crie uma anotação, chamage @Rota que recebe a rota, ex @Rota("/tasks)
- Fazer scan das classes utilizando https://github.com/classgraph/classgraph
- Filtrar somente as que possuem a anotação @Rota
- Utilizar isso no MiniServer (Fase 7) pora acionar a pagina

### Fase 10: Hibernate
- Renomear TaskDaoImpl para TaskDaoJdbc
- Ler introdução: https://docs.jboss.org/hibernate/orm/7.0/introduction/html_single/Hibernate_Introduction.html
- Instalar (via maven) e configurar o Hibernate (SessionFactory, etc)
- Criar e implementar TaskDaoHibernate (atualizar teste)
- Criar classe BeanFactory com um factory method (https://refactoring.guru/pt-br/design-patterns/factory-method) estatico para criar a TaskDao

### Fase 11: Primeiro contato com spring
- Ler o overview: https://docs.spring.io/spring-framework/reference/overview.html
- Iniciar manualmente: um application context do spring
- Iniciar manualmente: o DispatcherServlet
- Configurar thymeleaf
- Reimplementar as telas usando SpringMVC
- Separar em dois contextos
  - /custom-mvc (MiniServlet)
  - /spring-mvc (SpringMVC com DispatcherServlet)
- Implementar controllers, etc.

### Fase 12: Tudo é bean (💀)
- Remover BeanFactory
- Criar a TaskDAO no @Configuration do Spring com @Bean
- Transformar a MiniServlet em um bean e injetar a TaskDAO
- Remover ClassGraph, não será mais necessário
- Transformar as pages em beans e injetar uma lista de Pages na MiniServlet
- Criar uma home no `/` com links para as tasks em diferentes implementações (spring, servlet)

### Fase 13: Spring Security (💀💀)
- Remover AuthFilter
- Configurar SpringSecurity
- Habilitar login via form, o usuário e senha deve ser `teste`

### Fase 14: Wicket (💀💀💀)
- Instalar e configurar o Wicket no projeto (via maven)
- O wicket é apenas um filtro
- Recriar as tasks agora em Wicket, adicionar a nova implementação a home

### Fase 15: Cobertura (💀💀)
- Proibido usar geradores, aqui a ideia e vc aprender a fazer.
- Instalar e configurar o jacoco no projeto (plugin do maven)
- Cobertura deve ser superior a 60%, implementar os testes até chegar neste valor

### Fase 16: React (💀💀💀💀)
- Instalar e configurar o react no projeto (build via maven, usando frontend-maven-plugin)
- Não deve rodar separado do projeto, deve subir apenas uma porta
- Deve ter um script no package.json para fazer "watch" das alterações
- Implementar a task com react, implementar controllers restfull para isso (no spring mvc)

### Fase 17: Implantação (💀💀)
- Implantar em alguma nuvem com plano gratis, pode ser aws, heroku, azure...

## Tecnologias Utilizadas
- Java 21
- JDBC
- H2 Database (em memória)
- Servlet API
- Eclipse Jetty
- HTML/CSS
- ClassGraph
- Hibernate
- Spring
- Thymeleaf
- Wicket
- React
