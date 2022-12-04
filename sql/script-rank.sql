CREATE TABLE MODULO(
    id_modulo     NUMBER NOT NULL,
    nome          VARCHAR2(255) NOT NULL,
    data_inicio   DATE   NOT NULL,
    data_fim      DATE   NOT NULL,
    status_modulo NUMBER NOT NULL,
    PRIMARY KEY (id_modulo)
);

CREATE SEQUENCE SEQ_MODULO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE ATIVIDADE(
    ID_ATIVIDADE     NUMBER       NOT NULL,
    ID_MODULO        NUMBER,
    TITULO           VARCHAR2(255) NOT NULL,
    INSTRUCOES       VARCHAR2(2000),
    PESO_ATIVIDADE   NUMBER       NOT NULL,
    DATA_CRIACAO     DATE         NOT NULL,
    DATA_ENTREGA     DATE         NOT NULL,
    PONTUACAO        NUMBER,
    STATUS_ATIVIDADE VARCHAR(255) NOT NULL,
    NOME_INSTRUTOR   VARCHAR2(255),
    PRIMARY KEY (ID_ATIVIDADE),
    CONSTRAINT FK_modulo_ativadade FOREIGN KEY (id_modulo) REFERENCES MODULO (id_modulo)
);

CREATE SEQUENCE SEQ_ATIVIDADE
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE TRILHA(
    id_trilha  NUMBER NOT NULL,
    nome       VARCHAR2(255) NOT NULL,
    edicao     NUMBER NOT NULL,
    ano_edicao DATE   NOT NULL,
    PRIMARY KEY (id_trilha)
);

CREATE SEQUENCE SEQ_TRILHA
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE ATIVIDADE_TRILHA(
    id_atividade NUMBER NOT NULL,
    id_trilha    NUMBER NOT NULL,
    PRIMARY KEY (id_atividade, id_trilha),
    CONSTRAINT FK_ATIVIDADE_TRILHA FOREIGN KEY (id_atividade) REFERENCES ATIVIDADE (id_atividade),
    CONSTRAINT FK_TRILHA_ATIVIDADE FOREIGN KEY (id_trilha) REFERENCES TRILHA (id_trilha)
);

CREATE TABLE USUARIO(
    id_usuario      NUMBER NOT NULL,
    foto            BLOB,
    nome            VARCHAR2(255) NOT NULL,
    pontuacao_aluno NUMBER,
    tipo_perfil     NUMBER NOT NULL,
    login           VARCHAR2(255) UNIQUE NOT NULL,
    email           VARCHAR2(255) UNIQUE NOT NULL,
    senha           VARCHAR2(512) NOT NULL,
    status          NUMBER NOT NULL,
    cidade          VARCHAR2(255),
    especialidade   VARCHAR2(255),
    PRIMARY KEY (id_usuario)
);

CREATE SEQUENCE SEQ_USUARIO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE ATIVIDADE_USUARIO(
    id_atividade NUMBER NOT NULL,
    id_usuario   NUMBER NOT NULL,
    PRIMARY KEY (id_atividade, id_usuario),
    CONSTRAINT FK_ATIVIDADE_ATIVIDADE_USUARIO FOREIGN KEY (id_atividade) REFERENCES ATIVIDADE (id_atividade),
    CONSTRAINT FK_USUARIO_ATIVIDADE FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario)
);

CREATE TABLE TRILHA_USUARIO(
    id_trilha  NUMBER NOT NULL,
    id_usuario NUMBER NOT NULL,
    PRIMARY KEY (id_trilha, id_usuario),
    CONSTRAINT FK_TRILHA_USUARIO_TRILHA FOREIGN KEY (id_trilha) REFERENCES TRILHA (id_trilha),
    CONSTRAINT FK_usuario_trilha_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario)
);

CREATE TABLE TRILHA_MODULO(
    id_trilha NUMBER NOT NULL,
    id_modulo NUMBER NOT NULL,
    PRIMARY KEY (id_trilha, id_modulo),
    CONSTRAINT FK_TRILHA_TRILHA_MODULO FOREIGN KEY (id_trilha) REFERENCES TRILHA (id_trilha),
    CONSTRAINT FK_modulo_trilha_MODULO FOREIGN KEY (id_modulo) REFERENCES MODULO (id_modulo)
);

CREATE TABLE COMENTARIO(
    id_comentario     NUMBER NOT NULL,
    id_atividade      NUMBER,
    id_usuario        NUMBER,
    status_comentario NUMBER,
    comentario        VARCHAR2(2000) NOT NULL,
    PRIMARY KEY (id_comentario),
    CONSTRAINT FK_comentario_atividade_2 FOREIGN KEY (id_atividade) REFERENCES ATIVIDADE (id_atividade),
    CONSTRAINT FK_comentario_usuario_2 FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario)
);

CREATE SEQUENCE SEQ_COMENTARIO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE CARGO(
    id_cargo NUMBER NOT NULL,
    nome     varchar2(512) UNIQUE NOT NULL,
    PRIMARY KEY (id_cargo)
);

CREATE TABLE USUARIO_CARGO(
    id_usuario NUMBER NOT NULL,
    id_cargo   NUMBER NOT NULL,
    PRIMARY KEY (id_usuario, id_cargo),
    CONSTRAINT FK_usuario_cargo_cargo FOREIGN KEY (id_cargo) REFERENCES CARGO (id_cargo),
    CONSTRAINT FK_usuario_cargo_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario)
);

CREATE SEQUENCE SEQ_CARGO
    START WITH 1
    INCREMENT BY 1 NOCACHE
 NOCYCLE;

CREATE TABLE LINK(
    id_link      NUMBER NOT NULL,
    id_usuario   NUMBER NOT NULL,
    id_atividade NUMBER NOT NULL,
    link         VARCHAR2(2000),
    PRIMARY KEY (id_link),
    CONSTRAINT FK_link_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario),
    CONSTRAINT FK_link_atividade FOREIGN KEY (id_atividade) REFERENCES ATIVIDADE (id_atividade)
);

CREATE SEQUENCE SEQ_LINK
    START WITH 1
    INCREMENT BY 1 NOCACHE
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