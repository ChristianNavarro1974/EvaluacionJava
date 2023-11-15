# EvaluacionJava

## Información del usuario
Datos importantes sobre la creación de usuarios:
- El correo debe ser único
- El correo sigue la expresión regular `^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$`
- La expresión regular usada para la contraseña es `^[A-Za-z\\d]{4,}$`, está definida en el
archivo aplication.properties en la propiedad password.regexp
## Endpoints disponibles
Se implementan los métodos para la implementación de los verbos GET, POST, PUT, PATCH Y DELETE.
En el caso del GET, se implementan dos endpoints, uno para obtener todos los usuarios y otro para obtener un usuario en particular.

## Manejo de la data recibida
Los datos recibidos se manejan en una Base de Datos H2, que maneja la información en el directorio data que está en la raíz de la aplicación.

## Uso de la aplicacion
### Creación del usuario
Para crear un usuario se debe enviar un REST en POST, con un json con los siguientes datos (ejemplo):
```json
{
  "nombre": "Juan Rodriguez",
  "correo": "juan@rodriguez.org",
  "contraseña": "hunter2",
  "telefonos": [
    {
      "numero": "1234567",
      "codigoCiudad": "1",
      "codigoPais": "57"
    }
  ]
}
```
Para los teléfonos, puede ser más de uno:
```json
{
  "nombre": "Juan Rodriguez",
  "correo": "juan@rodriguez.org",
  "contraseña": "hunter2",
  "telefonos": [
    {
      "numero": "1234567",
      "codigoCiudad": "1",
      "codigoPais": "57"
    },
    {
      "numero": "1234567",
      "codigoCiudad": "1",
      "codigoPais": "57"
    }
  ]
}
```
Como resultado se recibirá la respuesta:
```json
{
  "token": "d4aa084b-018b-1000-b075-596e50757023",
  "id": "1",
  "creado": "2023-11-15T17:26:34.9558395",
  "modificado": null,
  "ultimoLogin": "2023-11-15T17:26:34.9558395",
  "activo": true
}
```
### Revisión de los datos del usuario
Hay dos formas de solicitar datos de los usuarios.
En caso de querer la lista de todos los usuarios creados, se debe enviar un REST GET:

http://localhost:8080/all

Como respuesta se obtendrá un listado con la misma información recibida como respuesta al 
momento de crear al usuario:

```json
[
  {
    "token": "d49a79ea-018b-1000-abe9-1e4c192607a3",
    "id": "1",
    "creado": "2023-11-15T17:09:35.46673",
    "modificado": null,
    "ultimoLogin": "2023-11-15T17:09:35.46673",
    "activo": true
  }
]
```
En caso de querer obtener un usuario en particular, se debe enviar un REST GET:

http://localhost:8080/{id}

Y se obtendra un json con la información del usuario solicitado:

```json
{
  "nombre": "Juan Rodriguez",
  "correo": "juan@rodriguez.org",
  "telefonos": [
    {
      "numero": "1234567",
      "codigoCiudad": "1",
      "codigoPais": "57"
    }
  ],
  "creado": "2023-11-15T17:26:34.9558395",
  "modificado": null,
  "ultimoLogin": "2023-11-15T17:26:34.9558395",
  "activo": true
}
```
### Modificar usuario
Para modificar el usuario es necesario mandar un REST PUT:

http://localhost:8080/{id}

```json
{
  "nombre": "",
  "correo": "juanito@rodriguez.org",
  "contraseña": "hunter82",
  "telefonos": [
    {
      "numero": "9874521",
      "codigoCiudad": "1",
      "codigoPais": ""
    },
    {
      "numero": "5486",
      "codigoCiudad": "1",
      "codigoPais": ""
    },
    {
      "numero": "00000000",
      "codigoCiudad": "1",
      "codigoPais": ""
    }
  ]
}
```
Y se recibirá la respuesta:
```json
{
  "token": "d49656c8-018b-1000-9d13-3d8a3330e8a4",
  "id": "1",
  "creado": "2023-11-15T17:09:35.46673",
  "modificado": "2023-11-15T17:05:43.3476729",
  "ultimoLogin": "2023-11-15T17:09:35.46673",
  "activo": true
}
```

Para modificar solo unos campos de un usuario, se puede enviar un REST PATCH:

http://localhost:8080/{id}

```json
{
  "nombre": "Juan Rodriguez",
  "telefonos": [
    {
      "numero": "98741",
      "codigoCiudad": "1",
      "codigoPais": "57"
    }
  ]
}
```
En este caso solo se modificarán los datos presentes en el json, los que no esten presentes en el json no se modifican. 
En el caso de los teléfonos, se agregarán los enviados a la lista, los que ya estaban no se tocarán. 
## Modelo de datos
La base de datos se encuentra en el archivo `src/main/resources/sql_create_tables.sql`. Y la base de datos se encuentra 
en el directorio `data`, en el archivo `evaluation.mv.db`.

El script es:
```sql
create table USER_DATA
(
    ID         INTEGER auto_increment,
    NAME       CHARACTER VARYING(255) not null,
    PASSWORD   CHARACTER VARYING(255) not null,
    EMAIL      CHARACTER VARYING(255) not null
        unique,
    LAST_LOGIN TIMESTAMP              not null,
    MODIFIED   TIMESTAMP,
    CREATED    TIMESTAMP,
    TOKEN      UUID,
    ISACTIVE   BOOLEAN,
    constraint USER_PK
        primary key (ID)
);


create table USER_PHONE
(
    ID          INTEGER auto_increment,
    NUMBER      CHARACTER VARYING(255) not null,
    CITYCODE    CHARACTER VARYING(255) not null,
    COUNTRYCODE CHARACTER VARYING(255) not null,
    USER_ID     INTEGER                not null,
    constraint PHONE_PK
        primary key (ID),
    constraint USER_FK
        foreign key (USER_ID) references USER_DATA
);
```
Diagrama de clases: ![diagramaClases](https://github.com/ChristianNavarro1974/EvaluacionJava/assets/59838214/a24b9bfd-dcf3-4bc5-a713-014c78369733)
