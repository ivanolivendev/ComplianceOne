-- V5__insert_users_per_role.sql
-- Insere um usuário de teste para cada role definido no enum Role.
-- Utiliza a tabela 'users' criada em V3__insert_admin_user.sql.
-- Senhas são hash BCrypt para propósitos de teste (usar a mesma senha para todos).

INSERT INTO users (id, name, email, password, role, active, created_at)
VALUES
    (gen_random_uuid(), 'Administrador', 'admin@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsPP9Ar7G9ZJBMZls1LpU/VyL9G8G1GzG1G2', 'ADMIN', true, now()),
    (gen_random_uuid(), 'Compliance', 'compliance@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsPP9Ar7G9ZJBMZls1LpU/VyL9G8G1GzG1G2', 'COMPLIANCE', true, now()),
    (gen_random_uuid(), 'Investigador', 'investigador@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsPP9Ar7G9ZJBMZls1LpU/VyL9G8G1GzG1G2', 'INVESTIGADOR', true, now()),
    (gen_random_uuid(), 'Triagem', 'triagem@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsPP9Ar7G9ZJBMZls1LpU/VyL9G8G1GzG1G2', 'TRIAGEM', true, now()),
    (gen_random_uuid(), 'Recursos Humanos', 'rh@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsPP9Ar7G9ZJBMZls1LpU/VyL9G8G1GzG1G2', 'RH', true, now()),
    (gen_random_uuid(), 'SST', 'sst@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsPP9Ar7G9ZJBMZls1LpU/VyL9G8G1GzG1G2', 'SST', true, now()),
    (gen_random_uuid(), 'Diretoria', 'diretoria@example.com', '$2a$10$ixlPY3AAd4ty1l6E2IsPP9Ar7G9ZJBMZls1LpU/VyL9G8G1GzG1G2', 'DIRETORIA', true, now())
ON CONFLICT (email) DO NOTHING;
