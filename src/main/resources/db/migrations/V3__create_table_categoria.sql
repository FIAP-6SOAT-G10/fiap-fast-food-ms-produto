CREATE TABLE IF NOT EXISTS "tech-challenge"."categoria"
(
  id bigserial NOT NULL,
  nome varchar(50) NOT NULL,
  descricao varchar(150) NOT NULL,
  CONSTRAINT categoria_pkey PRIMARY KEY (id)
);

CREATE OR REPLACE FUNCTION loadInitialData() RETURNS INTEGER AS $result$
DECLARE
	existe_objeto INTEGER := 0;
BEGIN
    SELECT COUNT(1) INTO existe_objeto FROM "tech-challenge"."categoria" WHERE nome = 'LANCHE';
    if (existe_objeto > 0) then
        raise notice 'Categoria LANCHE já cadastrada';
    else
        execute 'INSERT INTO "tech-challenge"."categoria" (nome, descricao) VALUES (''LANCHE'', ''Lanches'')';
    end if;

    SELECT COUNT(1) INTO existe_objeto FROM  "tech-challenge"."categoria" WHERE nome = 'BEBIDA';
    if (existe_objeto > 0) then
        raise notice 'Categoria BEBIDA já cadastrada';
    else
        execute 'INSERT INTO "tech-challenge"."categoria" (nome, descricao) VALUES (''BEBIDA'', ''Bebidas'')';
    end if;

    SELECT COUNT(1) INTO existe_objeto FROM  "tech-challenge"."categoria" WHERE nome = 'ACOMPANHAMENTO';
    if (existe_objeto > 0) then
        raise notice 'Categoria ACOMPANHAMENTO já cadastrada';
    else
        execute 'INSERT INTO "tech-challenge"."categoria" (nome, descricao) VALUES (''ACOMPANHAMENTO'', ''Acompanhamento'')';
    end if;

    SELECT COUNT(1) INTO existe_objeto FROM  "tech-challenge"."categoria" WHERE nome = 'SOBREMESA';
    if (existe_objeto > 0) then
        raise notice 'Categoria SOBREMESA já cadastrada';
    else
        execute 'INSERT INTO "tech-challenge"."categoria" (nome, descricao) VALUES (''SOBREMESA'', ''Sobremesa'')';
    end if;

   return existe_objeto;

END;
$result$ language plpgsql;

select loadInitialData();