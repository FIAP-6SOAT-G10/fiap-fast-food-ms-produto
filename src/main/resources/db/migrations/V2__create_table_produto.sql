CREATE TABLE IF NOT EXISTS "tech-challenge"."produto"
(
  id bigserial NOT NULL,
  nome varchar(50) NOT NULL,
  descricao varchar(150) NOT NULL,
  id_categoria integer NOT NULL,
  preco decimal(5,2) NOT NULL,
  imagem varchar(500) NOT NULL,
  CONSTRAINT produto_pkey PRIMARY KEY (id)
);

