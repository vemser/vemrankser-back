CREATE USER VEMRANKSERTESTE IDENTIFIED BY oracle;
GRANT CONNECT TO VEMRANKSERTESTE;
GRANT CONNECT, RESOURCE, DBA TO VEMRANKSERTESTE;
GRANT CREATE SESSION TO VEMRANKSERTESTE;
GRANT DBA TO VEMRANKSERTESTE;
GRANT CREATE VIEW, CREATE PROCEDURE, CREATE SEQUENCE to VEMRANKSERTESTE;
GRANT UNLIMITED TABLESPACE TO VEMRANKSERTESTE;
GRANT CREATE MATERIALIZED VIEW TO VEMRANKSERTESTE;
GRANT CREATE TABLE TO VEMRANKSERTESTE;
GRANT GLOBAL QUERY REWRITE TO VEMRANKSERTESTE;
GRANT SELECT ANY TABLE TO VEMRANKSERTESTE;

CREATE TABLE TRILHA(
 id_trilha NUMBER NOT NULL,
 nome  VARCHAR2(255) NOT NULL,
 edicao NUMBER NOT NULL,
 ano_edicao DATE NOT NULL,
 PRIMARY KEY (id_trilha)
);

CREATE SEQUENCE SEQ_TRILHA
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE TABLE USUARIO (
  id_usuario NUMBER NOT NULL,
  foto BLOB,
  ID_TRILHA NUMBER,
  nome VARCHAR2(255) NOT NULL,
  login VARCHAR2(255) UNIQUE NOT NULL ,
  email VARCHAR2(255) UNIQUE NOT NULL,
  senha VARCHAR2(512) NOT NULL,
  status NUMBER NOT NULL,
  tipo_perfil NUMBER NOT NULL,
  cidade VARCHAR2(255) NOT NULL,
  atuacao VARCHAR2(255) NOT NULL,
  especialidade VARCHAR2(255),
   PRIMARY KEY (id_usuario),
   CONSTRAINT FK_usuario_trilha FOREIGN KEY (ID_TRILHA) REFERENCES TRILHA (ID_TRILHA)   
);

CREATE SEQUENCE SEQ_USUARIO
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE TABLE MODULO(
 id_modulo NUMBER NOT NULL, 
 nome  VARCHAR2(255) NOT NULL,
 data_inicio DATE NOT NULL,
 data_fim DATE NOT NULL,
 status_modulo NUMBER NOT NULL,
 PRIMARY KEY (id_modulo)
);

CREATE SEQUENCE SEQ_MODULO
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE TABLE TRILHA_MODULO (
    id_trilha NUMBER NOT NULL,
    id_modulo NUMBER NOT NULL,
    PRIMARY KEY(id_trilha, id_modulo),
    CONSTRAINT FK_TRILHA_TRILHA_MODULO FOREIGN KEY (id_trilha) REFERENCES TRILHA (id_trilha),
  	CONSTRAINT FK_modulo_trilha_MODULO FOREIGN KEY (id_modulo) REFERENCES MODULO (id_modulo)
);

CREATE TABLE ATIVIDADE (
    ID_ATIVIDADE NUMBER NOT NULL,
    TITULO VARCHAR2(255) NOT NULL,
    INSTRUCOES VARCHAR2(2000),
    PESO_ATIVIDADE NUMBER NOT NULL,
    DATA_CRIACAO DATE NOT NULL,
    DATA_ENTREGA DATE NOT NULL,
    PONTUACAO NUMBER ,
    LINK VARCHAR2(2000),
    STATUS_ATIVIDADE NUMBER NOT NULL,
    PRIMARY KEY (ID_ATIVIDADE)
);

CREATE SEQUENCE SEQ_ATIVIDADE
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE TABLE MODULO_ATIVIDADE (
    id_modulo NUMBER NOT NULL,
    id_atividade NUMBER NOT NULL,
    PRIMARY KEY(id_modulo, id_atividade),
    CONSTRAINT FK_modulo_ativadade FOREIGN KEY (id_modulo) REFERENCES MODULO (id_modulo),
  	CONSTRAINT FK_atividade_modulo_atividade FOREIGN KEY (id_atividade) REFERENCES ATIVIDADE (id_atividade)
);

CREATE TABLE COMENTARIO (
 id_comentario NUMBER NOT NULL, 
 comentario VARCHAR2(2000) NOT NULL,
 PRIMARY KEY (id_comentario)
);

CREATE SEQUENCE SEQ_COMENTARIO
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE TABLE COMENTARIO_ATIVIDADE (
  id_atividade NUMBER NOT NULL,
  id_comentario NUMBER NOT NULL,
  PRIMARY KEY(id_atividade, id_comentario),
  CONSTRAINT FK_atividade_comentario_2 FOREIGN KEY (id_atividade) REFERENCES ATIVIDADE (id_atividade),
  CONSTRAINT FK_comentario_atividade_2 FOREIGN KEY (id_comentario) REFERENCES COMENTARIO (id_comentario)
);

CREATE TABLE CARGO (
    id_cargo NUMBER NOT NULL,
    nome varchar2(512) UNIQUE NOT NULL,
    PRIMARY KEY(id_cargo)
);

CREATE TABLE USUARIO_CARGO (
    id_usuario NUMBER NOT NULL,
    id_cargo NUMBER NOT NULL,
    PRIMARY KEY(id_usuario, id_cargo),
    CONSTRAINT FK_usuario_cargo_cargo FOREIGN KEY (id_cargo) REFERENCES CARGO (id_cargo),
  	CONSTRAINT FK_usuario_cargo_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario)
);

CREATE SEQUENCE SEQ_CARGO
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_COORDENADOR');

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_ALUNO');

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_INSTRUTOR');

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_ADMINISTRADOR');

INSERT INTO CARGO (ID_CARGO, NOME)
VALUES (seq_cargo.nextval, 'ROLE_GESTAO');

SELECT * FROM USUARIO u ;
SELECT * FROM CARGO c ;
SELECT * FROM USUARIO_CARGO uc ;
SELECT * FROM ATIVIDADE a ;
SELECT * FROM COMENTARIO c ;
SELECT * FROM COMENTARIO_ATIVIDADE ca ;
SELECT * FROM MODULO m ;
SELECT * FROM MODULO_ATIVIDADE ma ;
SELECT * FROM TRILHA t ;
SELECT * FROM TRILHA_MODULO tm ;