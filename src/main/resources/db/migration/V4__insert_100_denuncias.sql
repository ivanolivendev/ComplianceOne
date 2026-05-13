-- V4__insert_100_denuncias.sql
-- Inserção de 100 denúncias (ocorrências) de teste.
-- Utiliza a tabela 'ocorrencias' já existente.

DO $$
DECLARE
    i INTEGER := 1;
BEGIN
    WHILE i <= 100 LOOP
        INSERT INTO ocorrencias (id, protocolo, tipo, relato, anonima, status, setor_relacionado, data_ocorrencia, data_criacao)
        VALUES (
            gen_random_uuid(),
            concat('PROT-', lpad(i::text, 4, '0')),
            CASE WHEN i % 3 = 0 THEN 'ABUSE' WHEN i % 3 = 1 THEN 'HARASSMENT' ELSE 'THEFT' END,
            concat('Denúncia de teste número ', i),
            (i % 2 = 0),
            'ABERTA',
            CASE WHEN i % 5 = 0 THEN 'RH' WHEN i % 5 = 1 THEN 'FINANCEIRO' ELSE 'TI' END,
            now() - (random() * interval '30 days'),
            now()
        );
        i := i + 1;
    END LOOP;
END $$;
