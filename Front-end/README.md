# Proyecto Final Bedu-HSBC

## 🐸 Gero Gero Shop - Front-end 🐸

## Bienvenidos

Bienvenidos al Front-end del proyecto final Bedu-HSBC

*Este proyecto fue realizando utilizando Angular 15.*

## Instrucciones

Por favor considera lo siguiente si deseas probarlo:

* Una vez clonado o descargado el proyecto, puedes iniciarlo utilizando el comando: `ng serve` para generar un servidor local de Angular. Luego navega a la siguiente dirección: `http://localhost:4200/`.

* Actualmente la aplicación se encuentra apuntando a una dirección api dentro de la red `API_URL: 'http://192.168.0.186:9117/APIGeros/api/'`, si deseas probar la conexión al back-end cambia la dirección a: `API_URL: 'http://189.234.176.58/APIGeros/api/'`, basta con comentar y descomentar la constante dentro del archivo [environment.ts](./src/environments/environment.dev.ts)

* El proyecto cuenta con dos usuarios registrados, uno con nivel Administrador y otro con nivel usuario:

|  Usuario   | Contraseña    | Rol |
| --- | --- | --- |
| `admin@admin.com`    | Admin@123  | Administrador    |
| `correo@correo.com`    | Usuario@123    | Usuario    |

**Nota:** La dirección ip pública puede cambiar, lo que podría generar un fallo de conexión al servidor back-end, mantendré monitoreada la dirección durante la duración de este evento (Demo Day).


## Aclaraciones

Este proyecto aún se encuentra en proceso de desarrollo, por lo que puede contener errores, actualmente solo permite:

* Registrar nuevos usuarios
* Loguearse
* Los lugares a los que se pueden acceder se cargan de acuerdo al rol del usuario logueado.
* Existe la posibilidad de que, aunque la direccion IP sea la correcta, recibas un error de CORS.