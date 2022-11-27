# vemrankser-back
VemRankSer - Sistema de Ranqueamento

Preparando o ambiente:

É necessário executar os comando abaixo para baixar/configurar o container do DB Oracle.

`docker pull epiclabs/docker-oracle-xe-11g`

`docker run -d -p 1521:1521 -e ORACLE_ALLOW_REMOTE=true -e ORACLE_PASSWORD=oracle -e RELAX_SECURITY=1 epiclabs/docker-oracle-xe-11g`


Também é necessário uma ferramenta de SGBD, estamos utilizando a DBeaver Community, abaixo segue o link:
<br> https://dbeaver.io/download/

Com as ferramentas instaladas, agora precisamos criar a conexão com o Banco.
1. - Executar o container Docker com o DB Oracle.
2. - Abrir o DBeaver e criar nova conexão.
3. - Na janela que abrir, deve ser inserida as informações da conexão nos campos:
    1. a) Host: localhost
    2. b) Database: xe
    3. c) Port: 1521
    4. d) Username: system
    5. e) Password: oracle

Após ter as ferramentas instaladas e configuradas, é só rodar o sricpt SQL que está na pasta sql para que seja criada as tabelas/sequências no banco de dados.