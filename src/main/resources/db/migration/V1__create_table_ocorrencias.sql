CREATE TABLE ocorrencias (
    id UUID PRIMARY KEY,
    protocolo VARCHAR(255) NOT NULL UNIQUE,
    tipo VARCHAR(50) NOT NULL,
    relato TEXT NOT NULL,
    anonima BOOLEAN NOT NULL,
    status VARCHAR(50) NOT NULL,
    setor_relacionado VARCHAR(255),
    data_ocorrencia TIMESTAMP,
    data_criacao TIMESTAMP NOT NULL
);
