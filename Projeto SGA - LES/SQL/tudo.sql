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
DROP TABLE IF EXISTS tblistascriadasturmas CASCADE;
DROP TABLE IF EXISTS tbdisciplina CASCADE;
DROP TABLE IF EXISTS tbdisciplinasprofessores CASCADE;
DROP TABLE IF EXISTS tbNivel CASCADE;
DROP TABLE IF EXISTS tbSeries CASCADE;
DROP TABLE IF EXISTS tbturmas CASCADE;
DROP TABLE IF EXISTS tbavaliacoes CASCADE;
DROP TABLE IF EXISTS tblistasRealizadas CASCADE;
DROP TABLE IF EXISTS tblistasRealizadasNotas CASCADE;
DROP TABLE IF EXISTS tbRespostasDissertativas CASCADE;
DROP TABLE IF EXISTS tbRespostasAlternativas CASCADE;

CREATE TABLE IF NOT EXISTS tbNivel
(
 id serial NOT NULL,
 descricao character varying,
 peso double precision,
 CONSTRAINT pk_nivel PRIMARY KEY (id)
);

 /* Criar tabela de Exercicio */
CREATE TABLE IF NOT EXISTS tbExercicios
(
    id serial NOT NULL,
  dtcadastro timestamp without time zone,
  enunciado character varying,
  tipo integer NOT NULL,
  contador integer,
  nivel_id integer,
  CONSTRAINT pk_exercicio PRIMARY KEY (id),
  CONSTRAINT fk_nivel FOREIGN KEY (nivel_id)
      REFERENCES tbnivel (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
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

CREATE TABLE IF NOT EXISTS tblistascriadas
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

CREATE TABLE IF NOT EXISTS tblistascriadasExercicios
(
  exercicio_id integer,
  listaCriada_id integer,
  peso double precision
);


CREATE TABLE IF NOT EXISTS tblistascriadasturmas
(
  turma_id integer,
  listacriada_id integer,
  prazo timestamp with time zone,
  CONSTRAINT fk_listacriada FOREIGN KEY (listacriada_id)
      REFERENCES tblistascriadas (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS tbdisciplina
(
  id serial NOT NULL,
  nome character varying,
  dtcadastro time with time zone,
  CONSTRAINT pk_disciplina PRIMARY KEY (id),
  CONSTRAINT tbdisciplina_nome_key UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS tbdisciplinasprofessores
(
  turma_id integer,
  disciplina_id integer,
  professor_id integer,
  CONSTRAINT fk_disciplina FOREIGN KEY (disciplina_id)
      REFERENCES tbdisciplina (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_professor FOREIGN KEY (professor_id)
      REFERENCES tbprofessores (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS tbseries
(
 id serial,
 serie character varying,
 CONSTRAINT pk_serie PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbturmas
(
 id serial,
 serie_id integer,
 prefixo char,
 periodo int,
 CONSTRAINT pk_turma PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbavaliacoes
(
 id serial,
 dtcadastro date,
 turma_id integer,
 listaCriada_id integer,
 disciplina_id integer,
 professor_id integer,
 prazo timestamp without time zone,
 ativo boolean,
 CONSTRAINT pk_avaliacao PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tblistasRealizadas 
(
 id serial,
 dtrealizacao date,
 aluno_id integer,
 avaliacao_id integer,
 CONSTRAINT pk_listarealizada PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tblistasRealizadasNotas 
(
 exercicio_id integer,
 listaRealizada_id integer,
 correcao double precision,
 nota double precision
);

CREATE TABLE IF NOT EXISTS tbRespostasAlternativas 
(
 listaRealizada_id integer,
 exercicio_id integer,
 alternativa_id integer,
 resposta boolean
);

CREATE TABLE IF NOT EXISTS tbRespostasDissertativas 
(
 listaRealizada_id integer,
 exercicio_id integer,
 resposta character varying
);

