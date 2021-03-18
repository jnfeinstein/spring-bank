CREATE TABLE associationvalueentry (
	id BIGSERIAL PRIMARY KEY,
	associationkey VARCHAR(255),
	associationvalue VARCHAR(255),
	sagaid VARCHAR(255),
	sagatype VARCHAR(255)
);

CREATE TABLE domainevententry (
	globalindex bigserial NOT NULL PRIMARY KEY,
	aggregateidentifier VARCHAR(255) NOT NULL,
	sequencenumber BIGINT NOT NULL,
	type VARCHAR(255),
	eventidentifier VARCHAR(255) NOT NULL UNIQUE,
	metadata BYTEA,
	payload BYTEA NOT NULL,
	payloadrevision VARCHAR(255),
	payloadtype VARCHAR(255) NOT NULL,
	timestamp VARCHAR(255) NOT NULL,
	UNIQUE (aggregateidentifier, sequencenumber)
);

CREATE TABLE sagaentry (
	sagaid VARCHAR(255) NOT NULL PRIMARY KEY,
	revision VARCHAR(255),
	sagatype VARCHAR(255),
	serializedsaga BYTEA
);

CREATE TABLE snapshotevententry (
	aggregateidentifier VARCHAR(255) NOT NULL,
	sequencenumber BIGINT NOT NULL,
	type VARCHAR(255) NOT NULL,
	eventidentifier VARCHAR(255) NOT NULL UNIQUE,
	metadata BYTEA,
	payload BYTEA NOT NULL,
	payloadrevision VARCHAR(255),
	payloadtype VARCHAR(255) NOT NULL,
	timestamp VARCHAR(255) NOT NULL,
	PRIMARY KEY (aggregateidentifier, sequencenumber)
);

CREATE TABLE tokenentry (
	processorname VARCHAR(255) NOT NULL,
	segment INTEGER NOT NULL,
	token BYTEA,
	tokentype VARCHAR(255),
	timestamp VARCHAR(255),
	owner VARCHAR(255),
	PRIMARY KEY (processorname, segment)
);
