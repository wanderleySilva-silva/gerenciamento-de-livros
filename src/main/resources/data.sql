-- Inserindo roles (evitando duplicatas no MySQL)
INSERT INTO role (nome) 
VALUES ('ROLE_ADMIN') 
ON DUPLICATE KEY UPDATE nome = nome;

INSERT INTO role (nome) 
VALUES ('ROLE_USUARIO') 
ON DUPLICATE KEY UPDATE nome = nome;

-- Inserindo usuários (evitando duplicatas no MySQL)
INSERT INTO usuario (username, password) 
VALUES 
('jwps.17@hotmail.com', '$2a$12$ihyViSSWISwINNdozJPF.uGLlOPhBl4jBw7TNbliFIOGzOl8gUEQ2'),
('user', '$2a$12$A0rB58oX5DrAFtlGXTy7deKp6mpZCAX1PkJHjg7wZPcoTRYOPcLsi')
ON DUPLICATE KEY UPDATE username = username;

-- Relacionando usuários às roles (evitando duplicatas no MySQL)
INSERT INTO usuario_role (usuario_id, role_id)
SELECT u.id, r.id
FROM usuario u
JOIN role r ON r.nome = 'ROLE_ADMIN'
WHERE u.username = 'jwps.17@hotmail.com'
ON DUPLICATE KEY UPDATE usuario_id = usuario_id;

INSERT INTO usuario_role (usuario_id, role_id)
SELECT u.id, r.id
FROM usuario u
JOIN role r ON r.nome = 'ROLE_USUARIO'
WHERE u.username = 'user'
ON DUPLICATE KEY UPDATE usuario_id = usuario_id;

