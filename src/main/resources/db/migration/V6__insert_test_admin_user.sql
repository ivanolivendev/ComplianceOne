-- V6__insert_test_admin_user.sql
-- Inserção de um usuário admin de teste (email: admin@example.com, senha: admin123)
-- Utiliza a mesma hash BCrypt utilizada em V3 para a senha 'admin123'.
-- Role definida como ADMIN para que possua permissões totais.

INSERT INTO users (id, name, email, password, role, active, created_at)
VALUES (
    gen_random_uuid(),
    'Administrador Teste',
    'admin@example.com',
    '$2a$10$ixlPY3AAd4ty1l6E2IsPP9Ar7G9ZJBMZls1LpU/VyL9G8G1GzG1G2',
    'ADMIN',
    true,
    now()
) ON CONFLICT (email) DO NOTHING;
