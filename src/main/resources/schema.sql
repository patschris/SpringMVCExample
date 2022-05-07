--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

CREATE SEQUENCE public.todo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.todo_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public.todo (
                             id bigint DEFAULT nextval('public.todo_id_seq'::regclass) NOT NULL,
                             description character varying(255) NOT NULL,
                             target_date date NOT NULL,
                             done boolean DEFAULT false NOT NULL,
                             users character varying
);

ALTER TABLE public.todo OWNER TO postgres;

CREATE TABLE public.users (
                              username character varying NOT NULL,
                              password character varying NOT NULL
);

ALTER TABLE public.users OWNER TO postgres;

ALTER TABLE ONLY public.todo
    ADD CONSTRAINT todo_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);

CREATE INDEX "fki_θ" ON public.todo USING btree (users);

ALTER TABLE ONLY public.todo
    ADD CONSTRAINT user_fk FOREIGN KEY (users) REFERENCES public.users(username) NOT VALID;