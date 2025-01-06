-- Inserindo roles
INSERT INTO role (nome) VALUES ('ROLE_ADMIN') ON CONFLICT (nome) DO NOTHING;
INSERT INTO role (nome) VALUES ('ROLE_USUARIO') ON CONFLICT (nome) DO NOTHING;

-- Inserindo usuários
INSERT INTO usuario (username, password) VALUES
('jwps.17@hotmail.com', '$2a$12$ihyViSSWISwINNdozJPF.uGLlOPhBl4jBw7TNbliFIOGzOl8gUEQ2'),
('user', '$2a$12$A0rB58oX5DrAFtlGXTy7deKp6mpZCAX1PkJHjg7wZPcoTRYOPcLsi') ON CONFLICT (username) DO NOTHING;

-- Relacionando usuários às roles
INSERT INTO usuario_role (usuario_id, role_id)
SELECT u.id, r.id
FROM usuario u, role r
WHERE u.username = 'jwps.17@hotmail.com' AND r.nome = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

INSERT INTO usuario_role (usuario_id, role_id)
SELECT u.id, r.id
FROM usuario u, role r
WHERE u.username = 'user' AND r.nome = 'ROLE_USUARIO'
ON CONFLICT DO NOTHING;
