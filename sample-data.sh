#!/bin/bash

curl -d '{ "name": "First Person", "birth": "2000-12-21" }' -H 'Content-Type: application/json' -X POST http://localhost:8080/people
curl -d '{ "name": "Second Person", "birth": "1980-10-03" }' -H 'Content-Type: application/json' -X POST http://localhost:8080/people
curl -d '{ "name": "Third Person", "birth": "1999-07-09" }' -H 'Content-Type: application/json' -X POST http://localhost:8080/people
curl -d '{ "city": "city 1", "street": "street 1", "cep": "1234678", "number": "123" }' -H 'Content-Type: application/json' -X POST http://localhost:8080/people/1/addresses
curl -d '{ "city": "city 2", "street": "street 2", "cep": "2234678", "number": "223" }' -H 'Content-Type: application/json' -X POST http://localhost:8080/people/2/addresses
curl -d '{ "city": "city 3", "street": "street 3", "cep": "3234678", "number": "323" }' -H 'Content-Type: application/json' -X POST http://localhost:8080/people/3/addresses
curl -d '{ "city": "city 4", "street": "street 4", "cep": "4234678", "number": "423", "isMain": true }' -H 'Content-Type: application/json' -X POST http://localhost:8080/people/1/addresses
