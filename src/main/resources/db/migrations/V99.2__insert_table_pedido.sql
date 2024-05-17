DO $$
BEGIN
    INSERT INTO "tech-challenge"."pedido" (id_cliente, id_status, valor, data_criacao, id_status_pagamento) VALUES (1, 1, 100.00, NOW(), 1);
    INSERT INTO "tech-challenge"."pedido" (id_cliente, id_status, valor, data_criacao, id_status_pagamento) VALUES (2, 2, 200.00, NOW(), 2);
    INSERT INTO "tech-challenge"."pedido" (id_cliente, id_status, valor, data_criacao, id_status_pagamento) VALUES (3, 3, 300.00, NOW(), 3);
    INSERT INTO "tech-challenge"."pedido" (id_cliente, id_status, valor, data_criacao, id_status_pagamento) VALUES (4, 4, 400.00, NOW(), 3);
    INSERT INTO "tech-challenge"."pedido" (id_cliente, id_status, valor, data_criacao, id_status_pagamento) VALUES (5, 5, 500.00, NOW(), 2);
    INSERT INTO "tech-challenge"."pedido" (id_cliente, id_status, valor, data_criacao, id_status_pagamento) VALUES (6, 1, 600.00, NOW(), 1);
END $$;