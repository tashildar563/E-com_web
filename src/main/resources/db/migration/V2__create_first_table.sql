CREATE TABLE actor (
	id bigserial NOT NULL,
	"type" varchar(32) NOT NULL,
	is_deleted bool NOT NULL,
	CONSTRAINT actor_pkey PRIMARY KEY (id)
);