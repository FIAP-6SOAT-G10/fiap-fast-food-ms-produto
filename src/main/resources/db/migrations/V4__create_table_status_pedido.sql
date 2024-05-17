CREATE TABLE IF NOT EXISTS "tech-challenge"."status_pedido" (
	id bigserial NOT NULL,
	nome varchar(50) NOT NULL,

	CONSTRAINT status_pedido_pkey PRIMARY KEY(id)
);

DO $$
BEGIN
    INSERT INTO "tech-challenge"."status_pedido" (nome) VALUES ('Recebido');
    INSERT INTO "tech-challenge"."status_pedido" (nome) VALUES ('Em preparação');
    INSERT INTO "tech-challenge"."status_pedido" (nome) VALUES ('Pronto');
    INSERT INTO "tech-challenge"."status_pedido" (nome) VALUES ('Finalizado');
    INSERT INTO "tech-challenge"."status_pedido" (nome) VALUES ('Cancelado');
END $$;