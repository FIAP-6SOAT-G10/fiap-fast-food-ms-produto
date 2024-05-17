DO $$
BEGIN
    INSERT INTO "tech-challenge"."status_pedido" (nome) VALUES ('RECEBIDO');
    INSERT INTO "tech-challenge"."status_pedido" (nome) VALUES ('EM_PREPARACAO');
    INSERT INTO "tech-challenge"."status_pedido" (nome) VALUES ('PRONTO');
    INSERT INTO "tech-challenge"."status_pedido" (nome) VALUES ('FINALIZADO');
    INSERT INTO "tech-challenge"."status_pedido" (nome) VALUES ('CANCELADO');
END $$;