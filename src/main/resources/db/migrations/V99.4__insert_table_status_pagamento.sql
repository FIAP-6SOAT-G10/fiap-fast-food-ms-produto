DO $$
BEGIN
    INSERT INTO "tech-challenge"."status_pagamento" (nome) VALUES ('AGUARDANDO_PAGAMENTO');
    INSERT INTO "tech-challenge"."status_pagamento" (nome) VALUES ('PAGO');
    INSERT INTO "tech-challenge"."status_pagamento" (nome) VALUES ('RECUSADO');
    INSERT INTO "tech-challenge"."status_pagamento" (nome) VALUES ('PENDENTE');
    INSERT INTO "tech-challenge"."status_pagamento" (nome) VALUES ('CANCELADO');
END $$;