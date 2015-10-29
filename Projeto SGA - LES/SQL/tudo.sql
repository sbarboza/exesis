DROP TABLE IF EXISTS tbAlternativas CASCADE;
DROP TABLE IF EXISTS tbExercicios CASCADE;
DROP TABLE IF EXISTS tbTags CASCADE;
DROP TABLE IF EXISTS tbExerciciosTags CASCADE;
DROP TABLE IF EXISTS tbExerciciosAlternativas CASCADE;
DROP TABLE IF EXISTS tbUsuarios CASCADE;
DROP TABLE IF EXISTS tbAdministradores CASCADE;
DROP TABLE IF EXISTS tbProfessores CASCADE;
DROP TABLE IF EXISTS tbAlunos CASCADE;
DROP TABLE IF EXISTS tbResponsaveisAlunos CASCADE;
DROP TABLE IF EXISTS tbListasCriadas CASCADE;
DROP TABLE IF EXISTS tbTiposListas CASCADE;
DROP TABLE IF EXISTS tbListasCriadasExercicios CASCADE;

 /* Criar tabela de Exercicio */
CREATE TABLE IF NOT EXISTS tbExercicios
(
  id serial NOT NULL,
  dtCadastro timestamp without time zone,
  enunciado character varying,
  tipo integer not null,
  contador integer,
  CONSTRAINT pk_exercicio PRIMARY KEY (id)
);
  
 /* Criar tabela de Alternativas - Dependencia: Tabela de Exercício*/
  CREATE TABLE IF NOT EXISTS tbAlternativas
(
  id serial NOT NULL,
  descricao character varying,
  resposta boolean,
  CONSTRAINT pk_alternativa PRIMARY KEY (id)
);
 /* Criar tabela de Tags - Dependencia: Tabela de Exercício*/
CREATE TABLE IF NOT EXISTS tbTags
(
  id serial NOT NULL,
  nome character varying,
  CONSTRAINT pk_tag PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbExerciciosTags
(
  exercicio_id integer,
  tag_id integer
);

CREATE TABLE IF NOT EXISTS tbExerciciosAlternativas
(
  exercicio_id integer,
  alternativa_id integer,
  CONSTRAINT fk_alternativas FOREIGN KEY (alternativa_id)
      REFERENCES tbalternativas (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
  
);

CREATE TABLE IF NOT EXISTS tbUsuarios
(
  id serial NOT NULL,
  login character varying,
  email character varying,
  senha  character varying,
  perfilAcesso integer,
  CONSTRAINT pk_usuario PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbAdministradores
(
  id serial NOT NULL,
  nome character varying,
  sobrenome character varying,
  dtCadastro timestamp,
  dtNascimento character varying,
  sexo  character varying,
  telefone  character varying,
  informacoesAdicionais  character varying,
  usuario_id integer,
  CONSTRAINT pk_administrador PRIMARY KEY (id),
  CONSTRAINT fk_usuario FOREIGN KEY (usuario_id)
  REFERENCES tbUsuarios(id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS tbProfessores
(
  id serial NOT NULL,
  nome character varying,
  sobrenome character varying,
  dtCadastro timestamp,
  dtNascimento character varying,
  sexo  character varying,
  telefone  character varying,
  informacoesAdicionais  character varying,
  usuario_id integer,
  CONSTRAINT pk_professor PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbAlunos
(
  id serial NOT NULL,
  nome character varying,
  sobrenome character varying,
  dtCadastro timestamp,
  dtNascimento character varying,
  matricula character varying,
  sexo  character varying,
  telefone  character varying,
  informacoesAdicionais  character varying,
  usuario_id integer,
  CONSTRAINT pk_aluno PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbResponsaveisAlunos
(
  id serial NOT NULL,
  nome character varying,
  sobrenome character varying,
  dtCadastro timestamp,
  dtNascimento character varying,
  sexo  character varying,
  telefone  character varying,
  informacoesAdicionais  character varying,
  usuario_id integer,
  CONSTRAINT pk_responsavelaluno PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbTiposListas
(
 id serial NOT NULL,
 descricao character varying,
 peso double precision,
 CONSTRAINT pk_tipolista PRIMARY KEY (id)
);

CREATE TABLE tblistascriadas
(
  id serial NOT NULL,
  nome character varying,
  dtcadastro date,
  tipolista_id integer,
  CONSTRAINT pk_listacriada PRIMARY KEY (id),
  CONSTRAINT fk_tipolista FOREIGN KEY (tipolista_id)
      REFERENCES tbtiposlistas (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE tblistascriadasExercicios
(
  exercicio_id integer,
  listaCriada_id integer,
  peso double precision
);