create table usuario(
	ort greography(point),
	image text,
	mail varchar(320),
	telephone char(13)
);

create table rescatista(
	rescatistaID serial,
	nombre_ent varchar(30),
	RFC varchar(10),
	constraint pk_rescatista primary key (rescatistaID)
) inherits (usuario);

create table adoptante(
	adoptanteID serial,
	nombre varchar(50),
	apellidos varchar(50),
	sexo char,
	fecha_de_nac date,
	constraint pk_adoptante primary key (adoptanteID)
) inherits (usuario);

create table color(
	id_color serial,
	name varchar(15),
	constraint pk_color primary key (id_color)
);

create table temper(
	id_temper serial,
	name varchar(30),
	constraint pk_temper primary key(id_temper)
);

create table mascota(
	petID serial,
	sexo boolean,
	edad date not null,
	r_color int not null,
	vaxxed boolean,
	r_temper int,
	pelaje boolean,
	esterilizado boolean,
	discapacitado boolean,
	constraint pk_mascota primary key(petID),
	foreign key (r_color) references color(id_color),
	foreign key (r_temper) references temper(id_temper)
);

create table talla(
	id_talla serial,
	nombre varchar(30),
	constraint pk_talla primary key (id_talla)
);

create table perro(
	r_talla int,
	foreign key (r_talla) references talla(id_talla)
) inherits (mascota);

create table gato() inherits(mascota);

create table photos(
	r_mascota int,
	route text,
	foreign key (r_mascota) references mascota(petID)
);

create table adopcion(
	r_adoptante int,
	r_mascota int,
	fecha date,
	foreign key (r_adoptante) references adoptante(adoptanteID),
	foreign key (r_mascota) references mascota(petID)
);