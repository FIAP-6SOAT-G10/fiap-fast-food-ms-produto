CREATE TABLE IF NOT EXISTS "tech-challenge"."cliente"
(
  id               bigserial                NOT NULL,
  cpf              varchar(11)              NOT NULL,
  nome             varchar(50)              NOT NULL,
  email            varchar(100)             NOT NULL,
  CONSTRAINT cliente_pkey PRIMARY KEY (id)
);

DO $$
BEGIN
    INSERT INTO "tech-challenge"."cliente" (cpf, nome, email) VALUES ('12345678901', 'Test Client 1', 'test1@email.com');
    INSERT INTO "tech-challenge"."cliente" (cpf, nome, email) VALUES ('23456789012', 'Test Client 2', 'test2@email.com');
    INSERT INTO "tech-challenge"."cliente" (cpf, nome, email) VALUES ('34567890123', 'Test Client 3', 'test3@email.com');
    INSERT INTO "tech-challenge"."cliente" (cpf, nome, email) VALUES ('45678901234', 'Test Client 4', 'test4@email.com');
    INSERT INTO "tech-challenge"."cliente" (cpf, nome, email) VALUES ('56789012345', 'Test Client 5', 'test5@email.com');
    INSERT INTO "tech-challenge"."cliente" (cpf, nome, email) VALUES ('56789012346', 'Test Client 6', 'test6@email.com');
END $$;