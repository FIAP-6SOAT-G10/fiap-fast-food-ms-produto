-- Script para criar a tabela pedido
CREATE TABLE IF NOT EXISTS "tech-challenge"."pedido"
(
    id bigserial NOT NULL,
    id_cliente bigint,
    status     varchar(50),
    valor      decimal(5,2),
    data_finalizacao timestamp,
    data_cancelamento timestamp,
    data_criacao timestamp,
    status_pagamento varchar(50),
    CONSTRAINT pedido_pkey PRIMARY KEY (id),
    CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES "tech-challenge"."cliente" (id)
);