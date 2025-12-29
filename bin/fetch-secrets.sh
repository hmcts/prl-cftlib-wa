#!/bin/bash

az keyvault secret show --vault-name prl-aat -o tsv --query value --name prl-cos-cftlib-wa-config > ../.env
