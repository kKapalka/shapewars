--
-- PostgreSQL database dump
--

-- Dumped from database version 11.0
-- Dumped by pg_dump version 11.0

-- Started on 2019-04-17 06:35:34

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 217 (class 1259 OID 34909)
-- Name: agent; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agent (
    agent_id bigint NOT NULL,
    ally_internal_balance_priority double precision,
    damage_output_priority double precision,
    enemy_internal_balance_priority double precision,
    individual_ally_priority double precision,
    individual_enemy_priority double precision,
    overall_balance_priority double precision,
    version bigint,
    dedicated_player_id bigint
);


ALTER TABLE public.agent OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 34914)
-- Name: agent_learning_set; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agent_learning_set (
    agent_learning_set_id bigint NOT NULL,
    agent_version bigint,
    legacy_ally_internal_balance_priority double precision,
    legacy_damage_output_priority double precision,
    legacy_enemy_internal_balance_priority double precision,
    legacy_individual_ally_priority double precision,
    legacy_individual_enemy_priority double precision,
    legacy_overall_balance_priority double precision,
    agent_id bigint,
    fight_id bigint
);


ALTER TABLE public.agent_learning_set OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 34430)
-- Name: changelog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.changelog (
    changelog_id bigint NOT NULL,
    change character varying(255),
    change_time timestamp without time zone
);


ALTER TABLE public.changelog OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 34433)
-- Name: color; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.color (
    color_id bigint NOT NULL,
    color_map bytea,
    color_name character varying(255)
);


ALTER TABLE public.color OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 34439)
-- Name: color_damage; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.color_damage (
    color_damage_id bigint NOT NULL,
    damage_percentage bigint,
    enemy_color_id bigint,
    color_id bigint NOT NULL
);


ALTER TABLE public.color_damage OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 34442)
-- Name: exp_threshold; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exp_threshold (
    exp_threshold_id bigint NOT NULL,
    level bigint,
    threshold bigint
);


ALTER TABLE public.exp_threshold OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 34445)
-- Name: fight; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fight (
    fight_id bigint NOT NULL,
    fight_status character varying(255),
    player_one_id bigint,
    player_two_id bigint,
    winner_name character varying(255),
    relevant_username character varying(255)
);


ALTER TABLE public.fight OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 34448)
-- Name: fight_action; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fight_action (
    fight_action_id bigint NOT NULL,
    action_time timestamp without time zone,
    active_fighter_id bigint,
    fight_id bigint,
    next_active_fighter_id bigint,
    selected_target_id bigint,
    skill_id bigint
);


ALTER TABLE public.fight_action OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 34880)
-- Name: fight_players; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fight_players (
    fight_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.fight_players OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 34451)
-- Name: fighter; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fighter (
    fighter_id bigint NOT NULL,
    armor_mod bigint,
    xp_points bigint,
    hitpoints_mod bigint,
    level bigint,
    slot character varying(255),
    strength_mod bigint,
    fighter_model_referrence_id bigint,
    owner_id bigint
);


ALTER TABLE public.fighter OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 34454)
-- Name: fighter_model_referrence; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fighter_model_referrence (
    fighter_model_referrence_id bigint NOT NULL,
    fighter_image bytea,
    color_id bigint,
    shape_id bigint
);


ALTER TABLE public.fighter_model_referrence OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 34460)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 34919)
-- Name: learning_set_turn_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.learning_set_turn_log (
    learning_set_turn_log_id bigint NOT NULL,
    ally_score bigint,
    enemy_score bigint,
    agent_learning_set_id bigint
);


ALTER TABLE public.learning_set_turn_log OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 34462)
-- Name: maintenance_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.maintenance_log (
    maintenance_log_time bigint NOT NULL,
    message character varying(255),
    message_time timestamp without time zone,
    message_type character varying(255),
    informer_id bigint
);


ALTER TABLE public.maintenance_log OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 34468)
-- Name: message; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.message (
    message_id bigint NOT NULL,
    message character varying(255),
    message_time timestamp without time zone,
    receiver_id bigint,
    user_id bigint,
    sender_name character varying(255)
);


ALTER TABLE public.message OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 34896)
-- Name: message_players; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.message_players (
    message_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.message_players OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 34471)
-- Name: shape; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.shape (
    shape_id bigint NOT NULL,
    arm_max_growth bigint,
    arm_min_growth bigint,
    hp_max_growth bigint,
    hp_min_growth bigint,
    str_max_growth bigint,
    str_min_growth bigint,
    baseline_arm bigint,
    baseline_hp bigint,
    baseline_str bigint,
    image bytea,
    name character varying(255),
    speed bigint
);


ALTER TABLE public.shape OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 34477)
-- Name: shape_skill; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.shape_skill (
    shape_id bigint NOT NULL,
    skill_id bigint NOT NULL
);


ALTER TABLE public.shape_skill OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 34480)
-- Name: skill; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.skill (
    skill_id bigint NOT NULL,
    cost bigint,
    icon bytea,
    name character varying(255),
    tooltip text,
    active boolean DEFAULT true
);


ALTER TABLE public.skill OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 34486)
-- Name: skill_effect; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.skill_effect (
    skill_effect_id bigint NOT NULL,
    max_value double precision,
    min_value double precision,
    effect character varying(255),
    target_type character varying(255),
    value_modifier_type character varying(255),
    skill_effect_bundle_id bigint
);


ALTER TABLE public.skill_effect OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 34492)
-- Name: skill_effect_bundle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.skill_effect_bundle (
    skill_effect_bundle_id bigint NOT NULL,
    accuracy double precision,
    skill_id bigint
);


ALTER TABLE public.skill_effect_bundle OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 34495)
-- Name: skill_effect_result; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.skill_effect_result (
    skill_effect_result_id bigint NOT NULL,
    result double precision,
    effect character varying(255),
    value_modifier_type character varying(255),
    target_id bigint,
    fight_action_id bigint
);


ALTER TABLE public.skill_effect_result OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 34501)
-- Name: turn_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turn_order (
    turn_order_id bigint NOT NULL,
    turn_order bigint,
    turn bigint,
    fight_id bigint,
    fighter_id bigint
);


ALTER TABLE public.turn_order OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 34504)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    admin boolean,
    email character varying(255),
    xp_points bigint,
    level bigint,
    login character varying(255),
    password character varying(255),
    verified boolean,
    active boolean DEFAULT true
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 2822 (class 2606 OID 34918)
-- Name: agent_learning_set agent_learning_set_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agent_learning_set
    ADD CONSTRAINT agent_learning_set_pkey PRIMARY KEY (agent_learning_set_id);


--
-- TOC entry 2820 (class 2606 OID 34913)
-- Name: agent agent_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agent
    ADD CONSTRAINT agent_pkey PRIMARY KEY (agent_id);


--
-- TOC entry 2786 (class 2606 OID 34538)
-- Name: changelog changelog_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.changelog
    ADD CONSTRAINT changelog_pkey PRIMARY KEY (changelog_id);


--
-- TOC entry 2790 (class 2606 OID 34540)
-- Name: color_damage color_damage_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.color_damage
    ADD CONSTRAINT color_damage_pkey PRIMARY KEY (color_damage_id);


--
-- TOC entry 2788 (class 2606 OID 34542)
-- Name: color color_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.color
    ADD CONSTRAINT color_pkey PRIMARY KEY (color_id);


--
-- TOC entry 2792 (class 2606 OID 34544)
-- Name: exp_threshold exp_threshold_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exp_threshold
    ADD CONSTRAINT exp_threshold_pkey PRIMARY KEY (exp_threshold_id);


--
-- TOC entry 2796 (class 2606 OID 34546)
-- Name: fight_action fight_action_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight_action
    ADD CONSTRAINT fight_action_pkey PRIMARY KEY (fight_action_id);


--
-- TOC entry 2794 (class 2606 OID 34548)
-- Name: fight fight_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight
    ADD CONSTRAINT fight_pkey PRIMARY KEY (fight_id);


--
-- TOC entry 2800 (class 2606 OID 34550)
-- Name: fighter_model_referrence fighter_model_referrence_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fighter_model_referrence
    ADD CONSTRAINT fighter_model_referrence_pkey PRIMARY KEY (fighter_model_referrence_id);


--
-- TOC entry 2798 (class 2606 OID 34552)
-- Name: fighter fighter_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fighter
    ADD CONSTRAINT fighter_pkey PRIMARY KEY (fighter_id);


--
-- TOC entry 2824 (class 2606 OID 34923)
-- Name: learning_set_turn_log learning_set_turn_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.learning_set_turn_log
    ADD CONSTRAINT learning_set_turn_log_pkey PRIMARY KEY (learning_set_turn_log_id);


--
-- TOC entry 2802 (class 2606 OID 34554)
-- Name: maintenance_log maintenance_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maintenance_log
    ADD CONSTRAINT maintenance_log_pkey PRIMARY KEY (maintenance_log_time);


--
-- TOC entry 2804 (class 2606 OID 34556)
-- Name: message message_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (message_id);


--
-- TOC entry 2806 (class 2606 OID 34558)
-- Name: shape shape_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shape
    ADD CONSTRAINT shape_pkey PRIMARY KEY (shape_id);


--
-- TOC entry 2812 (class 2606 OID 34560)
-- Name: skill_effect_bundle skill_effect_bundle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill_effect_bundle
    ADD CONSTRAINT skill_effect_bundle_pkey PRIMARY KEY (skill_effect_bundle_id);


--
-- TOC entry 2810 (class 2606 OID 34562)
-- Name: skill_effect skill_effect_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill_effect
    ADD CONSTRAINT skill_effect_pkey PRIMARY KEY (skill_effect_id);


--
-- TOC entry 2814 (class 2606 OID 34564)
-- Name: skill_effect_result skill_effect_result_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill_effect_result
    ADD CONSTRAINT skill_effect_result_pkey PRIMARY KEY (skill_effect_result_id);


--
-- TOC entry 2808 (class 2606 OID 34566)
-- Name: skill skill_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill
    ADD CONSTRAINT skill_pkey PRIMARY KEY (skill_id);


--
-- TOC entry 2816 (class 2606 OID 34568)
-- Name: turn_order turn_order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turn_order
    ADD CONSTRAINT turn_order_pkey PRIMARY KEY (turn_order_id);


--
-- TOC entry 2818 (class 2606 OID 34570)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2825 (class 2606 OID 34571)
-- Name: color_damage fk18a10ptgg0cxp7eltpa12f3hm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.color_damage
    ADD CONSTRAINT fk18a10ptgg0cxp7eltpa12f3hm FOREIGN KEY (enemy_color_id) REFERENCES public.color(color_id);


--
-- TOC entry 2853 (class 2606 OID 34924)
-- Name: agent fk22qonchskale3lejjr3eec41v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agent
    ADD CONSTRAINT fk22qonchskale3lejjr3eec41v FOREIGN KEY (dedicated_player_id) REFERENCES public.users(user_id);


--
-- TOC entry 2843 (class 2606 OID 34576)
-- Name: skill_effect fk3826bfe17bqrcciu5qbr2xfyb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill_effect
    ADD CONSTRAINT fk3826bfe17bqrcciu5qbr2xfyb FOREIGN KEY (skill_effect_bundle_id) REFERENCES public.skill_effect_bundle(skill_effect_bundle_id);


--
-- TOC entry 2829 (class 2606 OID 34581)
-- Name: fight_action fk5m3p2fjmx8t9vgq4innmy7y56; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight_action
    ADD CONSTRAINT fk5m3p2fjmx8t9vgq4innmy7y56 FOREIGN KEY (skill_id) REFERENCES public.skill(skill_id);


--
-- TOC entry 2830 (class 2606 OID 34586)
-- Name: fight_action fk660m911v3pvv4jr19uscrq7ao; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight_action
    ADD CONSTRAINT fk660m911v3pvv4jr19uscrq7ao FOREIGN KEY (next_active_fighter_id) REFERENCES public.fighter(fighter_id);


--
-- TOC entry 2847 (class 2606 OID 34591)
-- Name: turn_order fk777811or6q7bigrutklmtr9fy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turn_order
    ADD CONSTRAINT fk777811or6q7bigrutklmtr9fy FOREIGN KEY (fighter_id) REFERENCES public.fighter(fighter_id);


--
-- TOC entry 2831 (class 2606 OID 34596)
-- Name: fight_action fk7naopspdwigwx4kkkordiu3d3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight_action
    ADD CONSTRAINT fk7naopspdwigwx4kkkordiu3d3 FOREIGN KEY (active_fighter_id) REFERENCES public.fighter(fighter_id);


--
-- TOC entry 2834 (class 2606 OID 34601)
-- Name: fighter fk8x0dvybslh5crwie8ewrvb862; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fighter
    ADD CONSTRAINT fk8x0dvybslh5crwie8ewrvb862 FOREIGN KEY (owner_id) REFERENCES public.users(user_id);


--
-- TOC entry 2839 (class 2606 OID 34606)
-- Name: message fk9a25x9o5r7wguarxeon2a9tmr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT fk9a25x9o5r7wguarxeon2a9tmr FOREIGN KEY (receiver_id) REFERENCES public.users(user_id);


--
-- TOC entry 2841 (class 2606 OID 34611)
-- Name: shape_skill fke5ru07hfapfsh7bcciyv6vm43; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shape_skill
    ADD CONSTRAINT fke5ru07hfapfsh7bcciyv6vm43 FOREIGN KEY (shape_id) REFERENCES public.shape(shape_id);


--
-- TOC entry 2848 (class 2606 OID 34616)
-- Name: turn_order fkegg49in9hu5id8i9r3ga9b1k; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turn_order
    ADD CONSTRAINT fkegg49in9hu5id8i9r3ga9b1k FOREIGN KEY (fight_id) REFERENCES public.fight(fight_id);


--
-- TOC entry 2826 (class 2606 OID 34621)
-- Name: color_damage fkfxbq0368r5s8ry7bcyo6myaoq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.color_damage
    ADD CONSTRAINT fkfxbq0368r5s8ry7bcyo6myaoq FOREIGN KEY (color_id) REFERENCES public.color(color_id);


--
-- TOC entry 2832 (class 2606 OID 34626)
-- Name: fight_action fkgh3bm4y5ijot1plmpheyi8qsp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight_action
    ADD CONSTRAINT fkgh3bm4y5ijot1plmpheyi8qsp FOREIGN KEY (fight_id) REFERENCES public.fight(fight_id);


--
-- TOC entry 2842 (class 2606 OID 34631)
-- Name: shape_skill fkh3lerpnovx82n9uajfrpogyd8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shape_skill
    ADD CONSTRAINT fkh3lerpnovx82n9uajfrpogyd8 FOREIGN KEY (skill_id) REFERENCES public.skill(skill_id);


--
-- TOC entry 2827 (class 2606 OID 34636)
-- Name: fight fki0nknax7aj367khy9sq57xtdb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight
    ADD CONSTRAINT fki0nknax7aj367khy9sq57xtdb FOREIGN KEY (player_one_id) REFERENCES public.users(user_id);


--
-- TOC entry 2836 (class 2606 OID 34641)
-- Name: fighter_model_referrence fki9tk5qmt9r965ap0mq3pv2gdx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fighter_model_referrence
    ADD CONSTRAINT fki9tk5qmt9r965ap0mq3pv2gdx FOREIGN KEY (color_id) REFERENCES public.color(color_id);


--
-- TOC entry 2856 (class 2606 OID 34939)
-- Name: learning_set_turn_log fkib76nw1b757mm6nkkmnpqdm57; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.learning_set_turn_log
    ADD CONSTRAINT fkib76nw1b757mm6nkkmnpqdm57 FOREIGN KEY (agent_learning_set_id) REFERENCES public.agent_learning_set(agent_learning_set_id);


--
-- TOC entry 2837 (class 2606 OID 34646)
-- Name: fighter_model_referrence fkiesbf6j13qx079v4y5xryfxpu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fighter_model_referrence
    ADD CONSTRAINT fkiesbf6j13qx079v4y5xryfxpu FOREIGN KEY (shape_id) REFERENCES public.shape(shape_id);


--
-- TOC entry 2854 (class 2606 OID 34929)
-- Name: agent_learning_set fkirb31bfeiwtpa7rtspl5cqrfp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agent_learning_set
    ADD CONSTRAINT fkirb31bfeiwtpa7rtspl5cqrfp FOREIGN KEY (agent_id) REFERENCES public.agent(agent_id);


--
-- TOC entry 2855 (class 2606 OID 34934)
-- Name: agent_learning_set fklkvtqgal8t756xa96lppkau3w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agent_learning_set
    ADD CONSTRAINT fklkvtqgal8t756xa96lppkau3w FOREIGN KEY (fight_id) REFERENCES public.fight(fight_id);


--
-- TOC entry 2850 (class 2606 OID 34888)
-- Name: fight_players fkn23srg9ppeimujms5u50w42a0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight_players
    ADD CONSTRAINT fkn23srg9ppeimujms5u50w42a0 FOREIGN KEY (fight_id) REFERENCES public.fight(fight_id);


--
-- TOC entry 2851 (class 2606 OID 34899)
-- Name: message_players fkniqktwvipg9p9ikawsofqejoj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message_players
    ADD CONSTRAINT fkniqktwvipg9p9ikawsofqejoj FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 2838 (class 2606 OID 34651)
-- Name: maintenance_log fknmbn3c2ljq5vp9savq8q47gqm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.maintenance_log
    ADD CONSTRAINT fknmbn3c2ljq5vp9savq8q47gqm FOREIGN KEY (informer_id) REFERENCES public.users(user_id);


--
-- TOC entry 2845 (class 2606 OID 34656)
-- Name: skill_effect_result fkogu37rk23alice6q3y66uv0o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill_effect_result
    ADD CONSTRAINT fkogu37rk23alice6q3y66uv0o FOREIGN KEY (fight_action_id) REFERENCES public.fight_action(fight_action_id);


--
-- TOC entry 2844 (class 2606 OID 34661)
-- Name: skill_effect_bundle fkomtaxe0l3f597o5hp4oym8wsu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill_effect_bundle
    ADD CONSTRAINT fkomtaxe0l3f597o5hp4oym8wsu FOREIGN KEY (skill_id) REFERENCES public.skill(skill_id);


--
-- TOC entry 2833 (class 2606 OID 34666)
-- Name: fight_action fkormhlqtpt50orucmufj1oim3c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight_action
    ADD CONSTRAINT fkormhlqtpt50orucmufj1oim3c FOREIGN KEY (selected_target_id) REFERENCES public.fighter(fighter_id);


--
-- TOC entry 2835 (class 2606 OID 34671)
-- Name: fighter fkp5apnjvedx4bpexo4qxbabg68; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fighter
    ADD CONSTRAINT fkp5apnjvedx4bpexo4qxbabg68 FOREIGN KEY (fighter_model_referrence_id) REFERENCES public.fighter_model_referrence(fighter_model_referrence_id);


--
-- TOC entry 2849 (class 2606 OID 34883)
-- Name: fight_players fkp7n8evm0wng84x2j0rlc1cwph; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight_players
    ADD CONSTRAINT fkp7n8evm0wng84x2j0rlc1cwph FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 2840 (class 2606 OID 34676)
-- Name: message fkpdrb79dg3bgym7pydlf9k3p1n; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT fkpdrb79dg3bgym7pydlf9k3p1n FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 2846 (class 2606 OID 34681)
-- Name: skill_effect_result fks6m44tm1vcpqjisuwlmhofex; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skill_effect_result
    ADD CONSTRAINT fks6m44tm1vcpqjisuwlmhofex FOREIGN KEY (target_id) REFERENCES public.fighter(fighter_id);


--
-- TOC entry 2852 (class 2606 OID 34904)
-- Name: message_players fksg0mlbuumpm00jjtxdw9238l5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message_players
    ADD CONSTRAINT fksg0mlbuumpm00jjtxdw9238l5 FOREIGN KEY (message_id) REFERENCES public.message(message_id);


--
-- TOC entry 2828 (class 2606 OID 34686)
-- Name: fight fkt90a421ox2eo9jqrmf68e7cl3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fight
    ADD CONSTRAINT fkt90a421ox2eo9jqrmf68e7cl3 FOREIGN KEY (player_two_id) REFERENCES public.users(user_id);


-- Completed on 2019-04-17 06:35:35

--
-- PostgreSQL database dump complete
--

