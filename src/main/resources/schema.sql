--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

-- Started on 2022-04-04 14:58:15

CREATE TABLE public.todo (
                             id bigint NOT NULL,
                             "desc" character varying(255) NOT NULL,
                             "targetDate" date NOT NULL,
                             "isDone" boolean DEFAULT false NOT NULL,
                             "user" character varying
);


ALTER TABLE public.todo OWNER TO postgres;

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
-- TOC entry 3324 (class 0 OID 0)
-- Dependencies: 209
-- Name: todo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.todo_id_seq OWNED BY public.todo.id;


--
-- TOC entry 211 (class 1259 OID 16570)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              username character varying NOT NULL,
                              password character varying NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3168 (class 2604 OID 16566)
-- Name: todo id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.todo ALTER COLUMN id SET DEFAULT nextval('public.todo_id_seq'::regclass);


--
-- TOC entry 3316 (class 0 OID 16563)
-- Dependencies: 210
-- Data for Name: todo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3317 (class 0 OID 16570)
-- Dependencies: 211
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3325 (class 0 OID 0)
-- Dependencies: 209
-- Name: todo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.todo_id_seq', 1, false);


--
-- TOC entry 3172 (class 2606 OID 16568)
-- Name: todo todo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.todo
    ADD CONSTRAINT todo_pkey PRIMARY KEY (id);


--
-- TOC entry 3174 (class 2606 OID 16576)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);


--
-- TOC entry 3170 (class 1259 OID 16584)
-- Name: fki_θ; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_θ" ON public.todo USING btree ("user");


--
-- TOC entry 3175 (class 2606 OID 16579)
-- Name: todo user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.todo
    ADD CONSTRAINT user_fk FOREIGN KEY ("user") REFERENCES public.users(username) NOT VALID;


-- Completed on 2022-04-04 14:58:16

--
-- PostgreSQL database dump complete
--

