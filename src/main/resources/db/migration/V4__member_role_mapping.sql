CREATE TABLE "member_auth" (
	id bigserial NOT NULL,
	"username" varchar(32) NOT NULL,
	"password" varchar(100) NOT NULL,
	"member_id" int8 NOT NULL,
	created_on timestamp NOT NULL,
	created_by varchar(64) NOT NULL,
	updated_on timestamp NOT NULL,
	updated_by varchar(64) NOT NULL,
	is_deleted bool NOT NULL DEFAULT false,
	CONSTRAINT mem_auth_pkey PRIMARY KEY (id)
);