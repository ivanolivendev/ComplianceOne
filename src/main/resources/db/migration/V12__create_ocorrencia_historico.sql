CREATE TABLE IF NOT EXISTS ocorrencia_historico (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ocorrencia_id UUID NOT NULL REFERENCES ocorrencias(id),
    status_anterior VARCHAR(255),
    status_novo VARCHAR(255) NOT NULL,
    observacao TEXT,
    data_evento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_ocorrencia_historico_ocorrencia_data
    ON ocorrencia_historico (ocorrencia_id, data_evento);
