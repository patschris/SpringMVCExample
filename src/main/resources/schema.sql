SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;
SET default_tablespace = '';
SET default_table_access_method = heap;
SET TIME ZONE 'UTC';

CREATE SEQUENCE IF NOT EXISTS public.todo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.users (
                              username character varying PRIMARY KEY,
                              password character varying NOT NULL,
                              enabled boolean DEFAULT true NOT NULL
);

CREATE TABLE IF NOT EXISTS public.todo (
                             id bigint PRIMARY KEY DEFAULT nextval('public.todo_id_seq'::regclass) NOT NULL,
                             description character varying(255) NOT NULL,
                             target_date date NOT NULL,
                             done boolean DEFAULT false NOT NULL,
                             users character varying,
							 FOREIGN KEY (users) REFERENCES public.users(username)
);

CREATE TABLE IF NOT EXISTS public.authorities (
                                    username character varying NOT NULL,
                                    authority character varying NOT NULL,
									FOREIGN KEY (username) REFERENCES public.users(username)
);

ALTER TABLE public.authorities OWNER TO postgres;
ALTER TABLE public.todo_id_seq OWNER TO postgres;
ALTER TABLE public.todo OWNER TO postgres;
ALTER TABLE public.users OWNER TO postgres;

CREATE INDEX IF NOT EXISTS "fki_θ" ON public.todo USING btree (users);
CREATE UNIQUE INDEX IF NOT EXISTS ix_auth_username ON public.authorities USING btree (username, authority);