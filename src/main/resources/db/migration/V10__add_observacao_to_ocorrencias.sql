-- V10__add_observacao_to_ocorrencias.sql
-- Adiciona o campo de observação/feedback para que a empresa
-- possa dar um retorno ao denunciante.

ALTER TABLE ocorrencias ADD COLUMN observacao TEXT;
