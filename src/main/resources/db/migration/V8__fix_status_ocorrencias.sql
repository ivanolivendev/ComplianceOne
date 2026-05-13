-- V8__fix_status_ocorrencias.sql
-- Corrige o status 'ABERTA' (inexistente no enum Java) para 'RECEBIDA'
-- que é o valor correto definido em StatusOcorrencia.java

UPDATE ocorrencias
SET status = 'RECEBIDA'
WHERE status = 'ABERTA';
