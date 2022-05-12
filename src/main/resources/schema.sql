--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

-- Started on 2022-05-08 19:16:27

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

--
-- TOC entry 212 (class 1259 OID 33160)
-- Name: authorities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authorities (
                                    username character varying NOT NULL,
                                    authority character varying NOT NULL
);


ALTER TABLE public.authorities OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16562)
-- Name: todo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.todo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.todo_id_seq OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16563)
-- Name: todo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.todo (
                             id bigint DEFAULT nextval('public.todo_id_seq'::regclass) NOT NULL,
                             description character varying(255) NOT NULL,
                             target_date date NOT NULL,
                             done boolean DEFAULT false NOT NULL,
                             users character varying
);


ALTER TABLE public.todo OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16570)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              username character varying NOT NULL,
                              password character varying NOT NULL,
                              enabled boolean DEFAULT true NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3177 (class 2606 OID 16568)
-- Name: todo todo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.todo
    ADD CONSTRAINT todo_pkey PRIMARY KEY (id);


--
-- TOC entry 3179 (class 2606 OID 16576)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);


--
-- TOC entry 3175 (class 1259 OID 16584)
-- Name: fki_θ; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_θ" ON public.todo USING btree (users);


--
-- TOC entry 3180 (class 1259 OID 33170)
-- Name: ix_auth_username; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX ix_auth_username ON public.authorities USING btree (username, authority);


--
-- TOC entry 3182 (class 2606 OID 33165)
-- Name: authorities fk_authorities_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES public.users(username);


--
-- TOC entry 3181 (class 2606 OID 16579)
-- Name: todo user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.todo
    ADD CONSTRAINT user_fk FOREIGN KEY (users) REFERENCES public.users(username) NOT VALID;


-- Completed on 2022-05-08 19:16:27

--
-- PostgreSQL database dump complete
--