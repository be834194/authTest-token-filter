CREATE TABLE IF NOT EXISTS public.account
(
    id integer NOT NULL DEFAULT nextval('account_id_seq'),
    username character varying(20),
    password character varying(100),
    rolename character varying(20),
    CONSTRAINT account_pkey PRIMARY KEY (id)
)