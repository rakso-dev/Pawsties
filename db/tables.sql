CREATE TABLE usuario
(
    image text,
    mail varchar(320) not null,
    telephone character(13),
    password character varying(20) NOT NULL
);

CREATE TABLE rescatista
(
    rescatistaid serial,
    nombre_ent varchar(30),
    rfc varchar(13),
    latitude numeric,
    longitude numeric,
    ---ort geography (Point,4326),
    CONSTRAINT pk_rescatista PRIMARY KEY (rescatistaid)
)INHERITS (public.usuario);

CREATE TABLE adoptante
(
    adoptanteid serial,
    nombre varchar(50),
    apellidos varchar(50),
    fecha_de_nac date,
   CONSTRAINT pk_adoptante PRIMARY KEY (adoptanteid)
)
    INHERITS (usuario);

CREATE TABLE color
(
    id_color serial,
    name varchar(15),
    CONSTRAINT pk_color PRIMARY KEY (id_color)
);

CREATE TABLE temper
(
    id_temper serial,
    name varchar(30),
    CONSTRAINT pk_temper PRIMARY KEY (id_temper)
);

CREATE TABLE mascota
(
    petid serial,
    sexo boolean,
    edad date NOT NULL,
    r_color integer NOT NULL,
    vaxxed boolean,
    r_temper integer,
    pelaje boolean,
    esterilizado boolean,
    discapacitado boolean,
    r_rescatista integer NOT NULL,
    nombre varchar(50),
    descripcion text,
    CONSTRAINT pk_mascota PRIMARY KEY (petid),
    CONSTRAINT mascota_r_color_fkey FOREIGN KEY (r_color)
        REFERENCES color (id_color),
    CONSTRAINT mascota_r_temper_fkey FOREIGN KEY (r_temper)
        REFERENCES temper (id_temper),
    CONSTRAINT rescatista_fk FOREIGN KEY (r_rescatista)
        REFERENCES rescatista (rescatistaid)
);	

CREATE TABLE gato () INHERITS (mascota);

CREATE TABLE talla
(
    id_talla serial,
    nombre varchar(30),
    CONSTRAINT pk_talla PRIMARY KEY (id_talla)
);

CREATE TABLE perro
(
    r_talla integer,
    CONSTRAINT perro_r_talla_fkey FOREIGN KEY (r_talla)
        REFERENCES talla (id_talla)
)
    INHERITS (public.mascota);
	
CREATE TABLE adopcion
(
    r_adoptante integer,
    r_mascota integer,
    fecha date,
    CONSTRAINT adopcion_r_adoptante_fkey FOREIGN KEY (r_adoptante)
        REFERENCES public.adoptante (adoptanteid),
    CONSTRAINT adopcion_r_mascota_fkey FOREIGN KEY (r_mascota)
        REFERENCES public.mascota (petid)
)	