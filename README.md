# Home Plugin

O Home Plugin é um plugin para servidores Minecraft que adiciona funcionalidades para definir, deletar e retornar a uma casa no jogo. 

## Funcionalidades

- `/sethome` - Define a localização atual como sua casa.
- `/deletehome` - Remove a localização definida como sua casa.
- `/home` - Teleporta você para a localização definida como sua casa.

## Instalação

Siga os passos abaixo para instalar e configurar o Home Plugin em seu servidor Minecraft.

### Pré-requisitos

- Java Development Kit (JDK) 8 ou superior
- Gradle
- Um servidor Minecraft compatível com plugins

### Passo a Passo

1. **Clonar o Repositório**

   Primeiro, clone o repositório do plugin Home usando o seguinte comando:
   ```sh
   git clone https://github.com/Pedream27/SetHome.git
   
Navegue até o diretório do projeto clonado:

sh
cd SetHome

Em seguida, construa o projeto utilizando o Gradle:
sh
gradle build

Após a construção, você encontrará o arquivo .jar na pasta build/libs.

Adicionar o Plugin ao Servidor

Copie o arquivo .jar gerado na etapa anterior para a pasta de plugins do seu servidor Minecraft. A pasta de plugins geralmente está localizada no mesmo diretório do servidor e é nomeada como plugins.

Iniciar o Servidor

Inicie ou reinicie o seu servidor Minecraft para carregar o novo plugin.

Uso
Uma vez que o servidor estiver em execução com o plugin, você pode utilizar os seguintes comandos no jogo:

/sethome: Define sua localização atual como sua casa.
/deletehome: Remove sua localização definida como casa.
/home: Teleporta você para a localização definida como sua casa
