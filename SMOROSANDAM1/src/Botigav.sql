PGDMP     
    &                z           Botigav    14.1    14.1 
    ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    25583    Botigav    DATABASE     e   CREATE DATABASE "Botigav" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Spain.1252';
    DROP DATABASE "Botigav";
                postgres    false            ?            1259    25591    producte    TABLE     ?   CREATE TABLE public.producte (
    codi integer NOT NULL,
    nom character varying NOT NULL,
    stock integer NOT NULL,
    preu integer NOT NULL,
    iva integer NOT NULL
);
    DROP TABLE public.producte;
       public         heap    postgres    false            ?            1259    25584    usuari    TABLE     	  CREATE TABLE public.usuari (
    dni character varying NOT NULL,
    contrasenya character varying NOT NULL,
    nom character varying NOT NULL,
    correu character varying NOT NULL,
    adreca character varying NOT NULL,
    telefon character varying NOT NULL
);
    DROP TABLE public.usuari;
       public         heap    postgres    false            ?          0    25591    producte 
   TABLE DATA           ?   COPY public.producte (codi, nom, stock, preu, iva) FROM stdin;
    public          postgres    false    210   5
       ?          0    25584    usuari 
   TABLE DATA           P   COPY public.usuari (dni, contrasenya, nom, correu, adreca, telefon) FROM stdin;
    public          postgres    false    209   ?
       b           2606    25597    producte producte_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.producte
    ADD CONSTRAINT producte_pkey PRIMARY KEY (codi);
 @   ALTER TABLE ONLY public.producte DROP CONSTRAINT producte_pkey;
       public            postgres    false    210            `           2606    25590    usuari usuari_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.usuari
    ADD CONSTRAINT usuari_pkey PRIMARY KEY (dni, contrasenya);
 <   ALTER TABLE ONLY public.usuari DROP CONSTRAINT usuari_pkey;
       public            postgres    false    209    209            ?   ?   x?%??
?0@????H???ڵ ?;7?F??)???m??.3cd?z?5?r?T$?x\?駐Ǘ$?w]????Hz[?1??)E??7j8KΡl???ӧ#-???|素?p?&뒰?v??=Ɛ7s???㠔???*?      ?   ?   x?Uα? ??x?nn?Z?????`b??r?Ր@!@??????F\????˩j??k!ϐ2???Eh?H???[???á?????b?8?O?	a?K??*?T??Rr??,갇?'Rv^cc?Т??Ԙg?یŝ
?h?͏?B1{??M?qY T$?$'
a:?????????Hή%c? *E?     