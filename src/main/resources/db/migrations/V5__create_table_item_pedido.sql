-- Script para criar a tabela item_pedido
CREATE TABLE IF NOT EXISTS "tech-challenge"."produto_pedido"
(
    id bigserial NOT NULL,
    id_pedido bigint NOT NULL,
    id_produto bigint NOT NULL,
    valor_total decimal(5,2) NOT NULL,
    quantidade integer NOT NULL,
    CONSTRAINT item_pedido_pkey PRIMARY KEY (id),
    CONSTRAINT fk_pedido FOREIGN KEY (id_pedido) REFERENCES "tech-challenge"."pedido" (id),
    CONSTRAINT fk_produto FOREIGN KEY (id_produto) REFERENCES "tech-challenge"."produto" (id)
);
