## Api-padaria - Sistema de Gestão para Padarias

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

Api-padaria é uma aplicação backend desenvolvida utilizando o modelo arquitetural monolítico, focada em fornecer uma solução completa para a gestão de padarias.

- Estrutura e Funcionalidades

A aplicação não implementa camadas de segurança ou login, facilitando o acesso direto às funcionalidades através de APIs REST. Ela oferece quatro APIs principais:

Usuários: API para gerenciar e consultar os dados dos usuários cadastrados na aplicação.

Roles de Usuários: API dedicada ao gerenciamento das roles (papéis) atribuídas aos usuários, permitindo configurar permissões e acessos específicos.

Categorias: API para gerenciar as diferentes categorias de produtos oferecidos na padaria, organizando o catálogo de forma clara e eficiente.

Produtos: API focada no gerenciamento de produtos, permitindo criar, atualizar, excluir e consultar informações sobre os itens disponíveis.

- O que a aplicação proporciona?

Api-padaria é projetada para simplificar o gerenciamento de padarias, oferecendo uma interface clara e direta para a administração de usuários, categorias, e produtos. A ausência de camadas de segurança permite uma implementação mais leve e rápida, adequada para cenários onde o controle de acesso não é uma prioridade.



## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [Observation](#observation)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/IgorTecnologia/api-padaria.git
```

2. Install dependencies with Maven

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080

## Collection Postman

Download these files and import them into your Postman to use the ready-made HTTP methods along with the already configured environment variables, to perform the requests/responses

[Download Collections](https://github.com/IgorTecnologia/api-padaria/blob/docs-postman/Api-padaria-collections.json)

[Download Environment variables](https://github.com/IgorTecnologia/api-padaria/blob/docs-postman/Local-%20host-environment.json)

## API Endpoints
The API provides the following endpoints:

**GET PRODUCTS**
```markdown
GET /products - Retrieve a pagination of all products.
```
```json
"content": [
        {
            "id": 1,
            "name": "Pão Francês",
            "description": "Quentinho e crocante",
            "price": 7.0,
            "imgUrl": "www.padaria.com",
            "categories": [
                {
                    "id": 1,
                    "name": "Salgados",
                    "products": []
                }
            ]
        }
```
**GET PRODUCTS**
```markdown
GET /products/name/{name} - Retrieve a pagination of products filtered by name.
Exemple: GET /products/name/Pão
```
```json
   "content": [
        {
            "id": 1,
            "name": "Pão Francês",
            "description": "Quentinho e crocante",
            "price": 7.0,
            "imgUrl": "www.padaria.com",
            "categories": [
                {
                    "id": 1,
                    "name": "Salgados",
                    "products": []
                }
            ]
        }
```
**GET PRODUCTS/ID**
```markdown
GET /products/id - Retrieve a single product by id.
```

```json
{
    "id": 1,
    "name": "Pão Francês",
    "description": "Quentinho e crocante",
    "price": 7.0,
    "imgUrl": "www.padaria.com",
    "categories": [
        {
            "id": 1,
            "name": "Salgados",
            "products": []
        }
    ]
}
```

**POST PRODUCTS**
```markdown
POST /products - Register a new product into the App.
```
```json
{
    "name" : "Arroz, feijão e filé mignon",
    "description" : "Prato brasileiro é o prato da casa",
    "price" : 37.77,
    "imgUrl" : "www.padaria.com"
}
```
**PUT PRODUCTS**
```markdown
PUT /products/id - Update a product in the application by id.
```
```json
{
    "name" : "Biscoito",
    "description" : "Biscoito caseiro",
    "price" : 7.77,
    "imgUrl" : "www.padaria.com",
    "categories" : [
        {
            "id" : 2
        }
    ]
    
}
```
**DELETE PRODUCTS**
```markdown
DELETE /products/id - Delete a product in the application by id.
return HTTP status: 204 NO CONTENT

```
## Database
The project utilizes [H2 Database](https://www.h2database.com/html/tutorial.html) as the database.

There are settings for MySQL database, you can use them by changing application.properties file.

The application comes with the H2 database as standard.

## Technologies Used

- JDK 17
- Spring Boot
- Maven
- H2 Database
- Spring Tool Suite 4
- Postman

## Observation
This APIRest provides other endpoints besides users, such as:

/categories

/users

/roles

Located in the Application resources layer.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões de melhorias, abra um problema ou envie uma solicitação pull ao repositório.

Ao contribuir para este projeto, siga o estilo de código existente, [convenções de commit](https://medium.com/linkapi-solutions/conventional-commits-pattern-3778d1a1e657), e envie suas alterações em uma branch separado.

![imagem 1](https://i.pinimg.com/736x/cc/e9/d6/cce9d662d61d7f77c0bc15bbf1bed55f.jpg)
