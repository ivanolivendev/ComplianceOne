-- Criar tabela de perfis (Roles)
CREATE TABLE perfis (
    id UUID PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

-- Criar tabela de usuários
CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao TIMESTAMP NOT NULL
);

-- Criar tabela de ligação (N para N)
CREATE TABLE usuario_perfis (
    usuario_id UUID NOT NULL,
    perfil_id UUID NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE CASCADE,
    CONSTRAINT fk_perfil FOREIGN KEY (perfil_id) REFERENCES perfis (id) ON DELETE CASCADE
);

-- Inserir perfis básicos iniciais
INSERT INTO perfis (id, nome) VALUES (gen_random_uuid(), 'ROLE_ADMIN');
INSERT INTO perfis (id, nome) VALUES (gen_random_uuid(), 'ROLE_USER');
INSERT INTO perfis (id, nome) VALUES (gen_random_uuid(), 'ROLE_AUDITOR');
