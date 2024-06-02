-- src/main/resources/data.sql


INSERT INTO pagamento (payer, payee, amount, status, created, transacao_id, callback, forma_pagamento) VALUES ('Alice', 'Bob', 100.00, 'CONCLUIDA', NOW(), 'TX123','https://open-bingo.wiremockapi.cloud/pagbank/notificacao','SALDO');
INSERT INTO pagamento (payer, payee, amount, status, created, transacao_id, callback, forma_pagamento) VALUES ('Charlie', 'Dave', 210, 'PROCESSANDO', NOW(), 'TX124','https://open-bingo.wiremockapi.cloud/pagbank/notificacao','SALDO');
INSERT INTO pagamento (payer, payee, amount, status, created, transacao_id, callback, forma_pagamento) VALUES ('Eve', 'Frank', 198.75, 'PROCESSANDO', NOW(), 'TX125','https://erro-open-bingo.wiremockapi.cloud/pagbank/notificacao','SALDO');
INSERT INTO pagamento (payer, payee, amount, status, created, transacao_id, callback, forma_pagamento) VALUES ('Grace', 'Heidi', 25.25, 'PROCESSANDO', NOW(), 'TX126','https://open-bingo.wiremockapi.cloud/pagbank/notificacao','SALDO');