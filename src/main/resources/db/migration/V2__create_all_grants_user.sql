DO $$
DECLARE

    vsql TEXT:= '
        GRANT USAGE, CREATE ON SCHEMA "produto" to user_service;
        GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA "produto" TO user_service;
    ';

BEGIN
    EXECUTE vsql;
END
$$