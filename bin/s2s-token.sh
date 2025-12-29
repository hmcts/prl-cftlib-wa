#!/bin/bash
# usage: s2s-token.sh s2s-host [microservice]

s2sHost=$1
microservice=$2

curl --silent --location "$s2sHost/testing-support/lease" \
--header 'Content-Type: application/json' \
--data "{
    \"microservice\": \"$microservice\"
}"
