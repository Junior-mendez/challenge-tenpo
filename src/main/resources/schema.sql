CREATE TABLE IF NOT EXISTS calls (
	id serial NOT NULL,
	"date" varchar NULL,
	"endpoint" varchar NULL,
	"request" varchar NULL,
	"response" varchar NULL,
	CONSTRAINT calls_pk PRIMARY KEY (id)
);