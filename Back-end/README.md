# Proyecto Final Bedu-HSBC

## 🐸 Gero Gero Shop - Back-end 🐸

## Bienvenidos

Bienvenidos al Back-end del proyecto final Bedu-HSBC

*Este proyecto fue realizando utilizando:*

* Java 17
* Springboot 3.0.2
* Maven

Para revisar dependencias adicionales consulta el archivo [pom.xml](./Back-end/pom.xml)

## Descripción

Este es un proyecto de Backend cuyo objetivo es ser parte de un sistema de Ecommerce personalizado para una tienda que se dedica a la venta de figuras o artículos relacionados a comics, películas, televisión, anime y cultura geek.

## Instrucciones

Como **requerimientos** se debe contar con lo siguiente:

* Java 17
* MySQL 8 con una base de datos creada con el nombre `GerosDB`
* Contar con una cuenta y bucket S3 creados en AWS

1. Editar el archivo [application.properties](./src/main/resources/application.properties) y adecuar los siguientes campos:

| Nombre	| Valor	|
| --- | --- |
| `cloud.aws.credentials.access-key=${AWS_ACCESS_KEY}`| Access Key de tu AWS|
|`cloud.aws.credentials.secret-key=${AWS_SECRET_KEY}`|Secret Key de tu AWS |
|`cloud.aws.region.static=${AWS_REGION}`|Región del bucket |
|`application.bucket.name=${AWS_BUCKET}`| Nombre del bucket|
|`spring.datasource.url=${SQL_URL}`|Url de la base de datos |
|`spring.datasource.username=${SQL_USERNAME}`|Usuario de la BD |
|`spring.datasource.password=${SQL_PASSWORD}`| Contraseña de la BD |

Ejemplo:

```
cloud.aws.credentials.access-key=soyUnaLlave
cloud.aws.credentials.secret-key=abcdefgh
cloud.aws.region.static=us-east-5
cloud.aws.stack.auto=false
application.bucket.name=bucket


spring.datasource.url=jdbc:mysql://localhost:3306/GerosDB
spring.datasource.username=root
spring.datasource.password=root
```

Una vez configurado, dentro de la carpeta raíz ejecutar lo siguiente:

```
mvn spring-boot:run
```

## Endpoints

La aplicación cuenta con los siguientes endpoints:

|Nombre|Descripción|
|---|---|
|`/api/`|Ruta base para el resto de los endpoints|
|`/auth/login`|Para el logueo de usuarios|
|`/auth/register`|Para el registo de nuevos usuario|
|`/productos/funkos`|Para CRUD de los productos|
