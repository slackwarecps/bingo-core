-- src/main/resources/schema.sql

DROP TABLE IF EXISTS pagamento;

CREATE TABLE pagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    payer VARCHAR(255) NOT NULL,
    payee VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
     status VARCHAR(255), -- Adicione esta linha
    created TIMESTAMP, -- Adicione esta linha
    transacao_id VARCHAR(255), -- Adicione esta linha
    callback VARCHAR(255), -- Adicione esta linha
    forma_pagamento VARCHAR(255) -- Adicione esta linha
);

