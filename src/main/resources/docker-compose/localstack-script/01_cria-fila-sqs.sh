#!/bin/bash
echo "########### Cria a fila ###########"
awslocal sqs create-queue --queue-name SQS-notifica-quadra-local
awslocal sqs create-queue --queue-name SQS-notifica-quina-local

echo "########### lista a fila ###########"
awslocal sqs list-queues

echo "########### comando pra ver as filas e  ###########"
echo "awslocal sqs receive-message --queue-url http://localhost:4566/000000000000/SQS-notifica-quadra-local"
echo "awslocal sqs receive-message --queue-url http://localhost:4566/000000000000/SQS-notifica-quina-local"
