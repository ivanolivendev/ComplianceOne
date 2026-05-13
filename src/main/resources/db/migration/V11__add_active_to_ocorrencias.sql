-- V11__add_active_to_ocorrencias.sql
-- Adiciona campo para exclusão lógica (Soft Delete)

ALTER TABLE ocorrencias ADD COLUMN ativo BOOLEAN DEFAULT TRUE;
UPDATE ocorrencias SET ativo = TRUE;
ALTER TABLE ocorrencias ALTER COLUMN ativo SET NOT NULL;
