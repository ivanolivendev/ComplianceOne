-- V7__fix_user_passwords_and_add_admin.sql
-- Corrige as senhas dos usuários de teste que estavam com hash BCrypt inválido.
-- Hash BCrypt real para a senha 'admin123': $2b$10$HiflkWl6w8ISat/T46lfeekGtd/YHEmZnissbZh.q6gpOlC4b.Vbu
-- Também adiciona o usuário admin@complianceone.com para facilitar testes.

-- 1. Atualiza a senha de todos os usuários de teste existentes com o hash correto
UPDATE users
SET password = '$2b$10$HiflkWl6w8ISat/T46lfeekGtd/YHEmZnissbZh.q6gpOlC4b.Vbu'
WHERE email IN (
    'admin@example.com',
    'compliance@example.com',
    'investigador@example.com',
    'triagem@example.com',
    'rh@example.com',
    'sst@example.com',
    'diretoria@example.com'
);

-- 2. Insere o usuário admin@complianceone.com (caso o usuário queira usar esse email)
INSERT INTO users (id, name, email, password, role, active, created_at)
VALUES (
    gen_random_uuid(),
    'Admin ComplianceOne',
    'admin@complianceone.com',
    '$2b$10$HiflkWl6w8ISat/T46lfeekGtd/YHEmZnissbZh.q6gpOlC4b.Vbu',
    'ADMIN',
    true,
    now()
) ON CONFLICT (email) DO UPDATE SET password = EXCLUDED.password;
