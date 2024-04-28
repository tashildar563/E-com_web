CREATE TABLE "member" (
	id bigserial NOT NULL,
	actor_id int8 NOT NULL,
	formatted_id varchar(64) NULL,
	"type" varchar(32) NOT NULL,
	salutation varchar(32) NULL,
	first_name varchar(100) NOT NULL,
	middle_name varchar(100) NULL,
	last_name varchar(100) NULL,
	gender varchar(32) NULL,
	dob timestamp NULL,
	status varchar(32) NOT NULL,
	created_on timestamp NOT NULL,
	created_by varchar(64) NOT NULL,
	updated_on timestamp NOT NULL,
	updated_by varchar(64) NOT NULL,
	is_deleted bool NOT NULL DEFAULT false,
	CONSTRAINT user_pkey PRIMARY KEY (id)
);