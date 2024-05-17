ALTER TABLE "tech-challenge"."pedido" DROP COLUMN status;
ALTER TABLE "tech-challenge"."pedido" DROP COLUMN status_pagamento;

ALTER TABLE "tech-challenge"."pedido" ADD COLUMN id_status INT NOT NULL;
ALTER TABLE "tech-challenge"."pedido" ADD COLUMN id_status_pagamento INT NOT NULL;

ALTER TABLE "tech-challenge"."pedido" ADD CONSTRAINT id_status_fkey FOREIGN KEY (id_status) REFERENCES "tech-challenge"."status_pedido"(id);
ALTER TABLE "tech-challenge"."pedido" ADD CONSTRAINT id_status_pagamento_fkey FOREIGN KEY (id_status_pagamento) REFERENCES "tech-challenge"."status_pagamento"(id);