# Attornatus - People API

### Descrição
Projeto criado para a avaliação de uma vaga na __Attornatus__ onde foi utilizado Spring Boot para criar uma RESTful API.
O build da API pode ser encontrada em [attornatus-0.0.1.jar](attornatus-0.0.1.jar) e ser executado com  
`java -jar attornatus-0.0.1.jar --spring.profiles.active=prod`
As respostas das perguntas da avaliação podem ser encontradas em [avaliao_desenvolvedor_backend.docx
](avaliao_desenvolvedor_backend.docx)

---

## Métodos
Requisições para a API devem seguir os padrões:
| Método | Descrição |
|---|---|
| `GET` | Retorna informações de um ou mais recursos. |
| `POST` | Utilizado para criar um novo recurso. |
| `PUT` | Atualiza dados de um recurso ou altera sua situação. |
| `DELETE` | Remove um recurso do sistema. |

---

## Listar todas as pessoas

### Request

`GET /people`

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    [
		{
			"id": 1,
			"name": "First Person",
			"birth": "2000-12-21"
		},
		{
			"id": 2,
			"name": "Second Person",
			"birth": "1980-10-03"
		},
		{
			"id": 3,
			"name": "Third Person",
			"birth": "1999-07-09"
		}
	]

---

## Pesquisar pessoa por ID

### Request

`GET /people/1`

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
		"id": 1,
		"name": "First Person",
		"birth": "2000-12-21"
	}

---

## Pesquisar pessoa por nome

### Request

`GET /people/first`

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    [
		{
			"id": 1,
			"name": "First Person",
			"birth": "2000-12-21"
		}
	]

---

## Pesquisar pessoa por data de nascimento

### Request

`GET /people/1980-10-03`

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    [
		{
			"id": 2,
			"name": "Second Person",
			"birth": "1980-10-03"
		}
	]

---

## Criar uma pessoa

### Request

`POST /people`

    Body
	{
		"name": "First Person",
		"birth": "2000-12-21"
	}


### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    [
		{
			"id": 1,
			"name": "First Person",
			"birth": "2000-12-21"
		}
	]

---

## Atualizar uma pessoa

### Request

`PUT /people/1`

    Body
	{
		"name": "First Person Edited",
		"birth": "2000-12-21"
	}


### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
		"id": 1,
		"name": "First Person Edited",
		"birth": "2000-12-21"
	}

---

## Remover uma pessoa

### Request

`DELETE /people/1`

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
		"message": "person {1} removed"
	}

---

## Listar todos os endereços de uma pessoa

### Request

`GET /people/1/addresses`

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    [
		{
			"id": 1,
			"street": "street 1",
			"cep": "1234678",
			"number": "123",
			"city": "city 1",
			"isMain": false,
			"resident": {
				"id": 1,
				"name": "First Person",
				"birth": "2000-12-21"
			}
		},
		{
			"id": 4,
			"street": "street 4",
			"cep": "4234678",
			"number": "423",
			"city": "city 4",
			"isMain": false,
			"resident": {
				"id": 1,
				"name": "First Person",
				"birth": "2000-12-21"
			}
		}
	]

---

## Pesquisar endereço de uma pessoa por ID

### Request

`GET /people/1/addresses/1`

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
		"id": 1,
		"street": "street 1",
		"cep": "1234678",
		"number": "123",
		"city": "city 1",
		"isMain": false,
		"resident": {
			"id": 1,
			"name": "First Person",
			"birth": "2000-12-21"
		}
	}

---

## Pesquisar endereço principal de uma pessoa

### Request

`GET /people/1/addresses/main`

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
		"id": 4,
		"street": "street 4",
		"cep": "4234678",
		"number": "423",
		"city": "city 4",
		"isMain": true,
		"resident": {
			"id": 1,
			"name": "First Person",
			"birth": "2000-12-21"
		}
	}

---

## Criar endereço para uma pessoa

### Request

`POST /people/1/address`

    Body
	{
		"city": "city 5",
		"street": "street 5",
		"cep": "5234678",
		"number": "523"
	}


### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
		"id": 5,
		"street": "street 5",
		"cep": "5234678",
		"number": "523",
		"city": "city 5",
		"isMain": false,
		"resident": {
			"id": 1,
			"name": "First Person",
			"birth": "2000-12-21"
		}
	}

---

## Editar endereço de uma pessoa

### Request

`PUT /people/1/address/1`

    Body
	{
		"city": "city 1 Edited",
		"street": "street 1",
		"cep": "1234678",
		"number": "123"
	}


### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
		"id": 1,
		"street": "street 1",
		"cep": "1234678",
		"number": "123",
		"city": "city 1 Edited",
		"isMain": false,
		"resident": {
			"id": 1,
			"name": "First Person",
			"birth": "2000-12-21"
		}
	}

---

## Definir endereço de uma pessoa como principal

### Request

`PUT /people/1/address/1/main`

### Response

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
		"id": 1,
		"street": "street 1",
		"cep": "1234678",
		"number": "123",
		"city": "city 1 Edited",
		"isMain": true,
		"resident": {
			"id": 1,
			"name": "First Person",
			"birth": "2000-12-21"
		}
	}
