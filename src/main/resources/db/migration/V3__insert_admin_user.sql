-- V3__insert_admin_user.sql
-- Inserção de um usuário administrador inicial para testes.
-- Senha: 'admin123' criptografada com BCrypt ($2a$10$8.u0LY4pT1l9g1Xz86h8.eG0l2hL3.gLpG.L/gG/gG/gG/gG/gG/g)
-- Nota: Em produção, utilize variáveis de ambiente e nunca senhas fixas em scripts.

INSERT INTO users (id, name, email, password, role, active, created_at)
VALUES (
    gen_random_uuid(),
    'Administrador Sistema',
    'admin@complianceone.com',
    '$2a$10$8.u0LY4pT1l9g1Xz86h8.eG0l2hL3.gLpG.L/gG/gG/gG/gG/gG/g',
    'DIRETORIA',
    true,
    now()
) ON CONFLICT (email) DO NOTHING;
