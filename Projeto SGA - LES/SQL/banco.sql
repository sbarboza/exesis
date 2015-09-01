
CREATE TABLE tbusuarios
(
  idusuario serial NOT NULL,
  email character varying,
  login character varying,
  nivelacesso integer,
  datacadastro timestamp without time zone,
  senha character varying,
  CONSTRAINT pkusuarios PRIMARY KEY (idusuario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tbusuarios
  OWNER TO postgres;

CREATE TABLE tbprofessores
(
  idprofessor integer NOT NULL,
  nome character varying,
  sobrenome character varying,
  sexo character(1),
  telefone character varying,
  informacoesadicionais character varying,
  datanascimento character varying,
  CONSTRAINT pkprofessores PRIMARY KEY (idprofessor)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tbprofessores
  OWNER TO postgres;

