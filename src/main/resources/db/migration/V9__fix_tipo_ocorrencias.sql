-- V9__fix_tipo_ocorrencias.sql
-- Corrige os valores de 'tipo' na tabela ocorrencias que foram inseridos
-- pela V4 com nomes em inglês (ABUSE, HARASSMENT, THEFT) que não existem
-- no enum TipoOcorrencia.java.
-- 
-- Enum válido: ASSEDIO_MORAL, ASSEDIO_SEXUAL, DISCRIMINACAO,
--              VIOLENCIA_PSICOLOGICA, RISCO_PSICOSSOCIAL, OUTROS

UPDATE ocorrencias SET tipo = 'ASSEDIO_MORAL'     WHERE tipo = 'HARASSMENT';
UPDATE ocorrencias SET tipo = 'ASSEDIO_SEXUAL'     WHERE tipo = 'ABUSE';
UPDATE ocorrencias SET tipo = 'DISCRIMINACAO'      WHERE tipo = 'THEFT';

-- Corrige qualquer outro valor inválido que possa existir
UPDATE ocorrencias SET tipo = 'OUTROS'
WHERE tipo NOT IN ('ASSEDIO_MORAL', 'ASSEDIO_SEXUAL', 'DISCRIMINACAO', 'VIOLENCIA_PSICOLOGICA', 'RISCO_PSICOSSOCIAL', 'OUTROS');
