CREATE TABLE users
(
    id         BIGINT       NOT NULL,
    username   VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);