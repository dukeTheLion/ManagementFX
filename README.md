# ManagementFX

Projeto de gestão de funcionários utilizando banco de dados MySQL, JavaFX e integrando com Arduino. (Projeto desenvolvido para aula de programação aplicada do curso de Eng. de Controle e Automação).

Este projeto esta em desenvolvimento já há algum tempo, seu intuito inicial era testar conexão Java com MySQL e se tornou um projeto para faculdade.

## Primeiros passos

Para iniciar o projeto é preciso instalar o banco de dados, algumas coisinhas para JavaFX e a API de comunicação com o Arduino. Depois podemos Fazer os ajustes na IDE.

### Downloads

Primeiramente baixar as bibliotecas e extras necessários no projeto: 

#### JavaFX

- JavaFX: https://gluonhq.com/products/javafx/

- Scene Builder: https://gluonhq.com/products/scene-builder/

#### MySQL

- MySQL: https://dev.mysql.com/downloads/mysql/
- Workbench: https://dev.mysql.com/downloads/workbench/
- Connector via MAVEN: mysql:mysql-connector-java:8.0.19

#### RXTX (API de comunicação serial)

- RXTX: http://rxtx.qbang.org/wiki/index.php/Download

### Ajustes

Configurar o Run. Dependendo da IDE, a configuração vai estar em um lugar diferente, mas geralmente vai ter na barra superior uma aba Run onde vc tem a opção de configurar.
Assim que entrar nas configurações, vai ter uma parte chamada VM Arguments, dentro da caixa é so colar `--module-path <PATH> --add-modules=javafx.fxml,javafx.controls` e substituir <PATH> pelo caminho onde se encontra a biblioteca baixada.

##Desenvolvimento

Desde o inicio de Abril, este projeto vem sendo planejado como "Projeto Final do Semestre". Ainda esta longe de acabar, mas já passou uma boa parte.

-[x] Classes de acesso a dados
-[x] Conexão com o banco de dados
-[x] Conexão com Arduino
-[x] Integração inicial da conexão Arduino e banco de dados
-[ ] Implementar interface gráfica (+- 50%)
-[ ] Adicionar um _listening_ para a interface
-[ ] Resolver um possível problema que terei com RXTX