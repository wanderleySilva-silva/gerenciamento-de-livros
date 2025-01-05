CREATE TABLE IF NOT EXISTS role (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario_role (
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, role_id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role(id)
);
