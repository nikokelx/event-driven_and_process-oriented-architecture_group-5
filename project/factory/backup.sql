--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

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
-- Name: machines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.machines (
    id integer NOT NULL,
    name character varying(255),
    status boolean,
    fill_level integer,
    capacity integer
);


ALTER TABLE public.machines OWNER TO postgres;

--
-- Name: machines machines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--


--
-- Data for Name: machines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.machines (id, name, status, fill_level, capacity) FROM stdin;
0	machine-01	f	0	100
\.



ALTER TABLE ONLY public.machines
    ADD CONSTRAINT machines_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

