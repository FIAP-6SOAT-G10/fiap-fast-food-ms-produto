CREATE TABLE "tech-challenge"."status_pagamento" (
	id bigserial NOT NULL,
	nome varchar(50) NOT NULL,

	CONSTRAINT status_pagamento_pkey PRIMARY KEY(id)
);

DO $$
BEGIN
    INSERT INTO "tech-challenge"."status_pagamento" (nome) VALUES ('Pago');
    INSERT INTO "tech-challenge"."status_pagamento" (nome) VALUES ('Recusado');
    INSERT INTO "tech-challenge"."status_pagamento" (nome) VALUES ('Pendente');
    INSERT INTO "tech-challenge"."status_pagamento" (nome) VALUES ('CANCELADO');
END $$;