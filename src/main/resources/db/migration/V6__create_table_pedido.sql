-- Script para criar a tabela pedido
CREATE TABLE IF NOT EXISTS "tech-challenge"."pedido"
(
    id bigserial NOT NULL,
    id_cliente bigint,
    id_status INT NOT NULL,
    valor      decimal(5,2),
    data_finalizacao timestamp,
    data_cancelamento timestamp,
    data_criacao timestamp,
    id_status_pagamento INT NOT NULL,
    CONSTRAINT pedido_pkey PRIMARY KEY (id),
    CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES "tech-challenge"."cliente" (id),
    CONSTRAINT id_status_fkey FOREIGN KEY (id_status) REFERENCES "tech-challenge"."status_pedido"(id),
    CONSTRAINT id_status_pagamento_fkey FOREIGN KEY (id_status_pagamento) REFERENCES "tech-challenge"."status_pagamento"(id)
);