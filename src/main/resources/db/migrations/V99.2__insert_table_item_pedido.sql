DO $$
BEGIN
    INSERT INTO "tech-challenge"."produto_pedido" (id_pedido, id_produto, valor_total, quantidade) VALUES (1, 1, 100.00, 1);
    INSERT INTO "tech-challenge"."produto_pedido" (id_pedido, id_produto, valor_total, quantidade) VALUES (2, 2, 200.00, 2);
    INSERT INTO "tech-challenge"."produto_pedido" (id_pedido, id_produto, valor_total, quantidade) VALUES (3, 3, 300.00, 3);
    INSERT INTO "tech-challenge"."produto_pedido" (id_pedido, id_produto, valor_total, quantidade) VALUES (4, 4, 400.00, 4);
    INSERT INTO "tech-challenge"."produto_pedido" (id_pedido, id_produto, valor_total, quantidade) VALUES (5, 5, 500.00, 5);
END $$;