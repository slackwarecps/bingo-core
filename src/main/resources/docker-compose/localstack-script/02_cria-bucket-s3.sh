#!/bin/bash

echo "########### criando o bucket ###########"
awslocal s3api create-bucket \
--bucket bingo \
--create-bucket-configuration LocationConstraint=sa-east-1

echo "########### pasta local ###########"
awslocal s3api put-object \
--bucket bingo \
--key local/

echo "########### insere o arquivo ###########"
awslocal s3 cp /tmp/localstack/bucket/teste.txt s3://bingo/local/teste.txt


echo "########### lista  ###########"
awslocal s3api list-objects \
--bucket bingo
