CREATE TABLE IF NOT EXISTS anexos (
    id UUID PRIMARY KEY,
    ocorrencia_id UUID NOT NULL REFERENCES ocorrencias(id),
    nome_arquivo VARCHAR(255) NOT NULL,
    nome_original VARCHAR(255) NOT NULL,
    tamanho BIGINT NOT NULL,
    tipo_arquivo VARCHAR(150) NOT NULL,
    hash_arquivo VARCHAR(64) NOT NULL,
    storage_path VARCHAR(500) NOT NULL,
    data_upload TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_anexos_ocorrencia ON anexos(ocorrencia_id);
