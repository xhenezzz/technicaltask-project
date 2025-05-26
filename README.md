#Techinal Task#

Stack:
Java 17
Spring Boot 3.x
Spring Eureka
Spring Web, Data JPA
PostgreSQL
Docker, Docker Compose
Lombok

Проект состоит из 4 модулей:
----------------------------
api-gateway
-----------------------------
discovery-serivce
----------------------------
company-service
----------------------------
user-service
----------------------------

Основные HTTP пути
(company)
GET - http://localhost:8080/company/all
Возвращает список всех компаний;

POST - http://localhost:8080/company/create
'{
    "companyName": "Google"
}'

Создает компанию;

PUT - http://localhost:8080/company/edit/1
'{
    "companyName": "Microsoft"
}'

Редактирует информацию о компании;

DELETE - http://localhost:8080/company/delete/1
Удаляет компанию по ID;

(customer - user)

GET - http://localhost:8080/customer/info/1
Получает информацию о клиенте;

POST - http://localhost:8080/customer/create
'{
    "username": "xhene",
    "password": "12345"
}'

Создаем клиента;

PUT - http://localhost:8080/customer/update/1
'{
    "companyId": "1",
    "position": "BACKEND"  (QA, CEO, BACKEND, FRONTEND, PRODUCT - возможные варианты должности)
}'

Изменение информации о клиенте;

DELETE - http://localhost:8080/customer/delete/1
Удаляет клиента по ID;






