CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    skills VARCHAR,
    experience INT NOT NULL
);

CREATE TABLE IF NOT EXISTS jobs
(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    company VARCHAR NOT NULL,
    tags VARCHAR,
    experience INT NOT NULL
);

CREATE TABLE IF NOT EXISTS matchOfUserToJob
(
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    countMatch DOUBLE PRECISION
);