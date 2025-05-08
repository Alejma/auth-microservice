# 🔐 Auth Microservice

Este microservicio se encarga de la autenticación y autorización de usuarios mediante JWT y Refresh Tokens. Forma parte de una arquitectura de microservicios y está desarrollado con Spring Boot.

---

## 📦 Tecnologías

- Java 17+
- Spring Boot 3
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Maven
- Docker (opcional)
- Lombok

---

## ⚙️ Configuración

### Variables de entorno requeridas

Asegúrate de definir las siguientes variables de entorno:

| Variable         | Descripción                             | Ejemplo                                      |
|------------------|------------------------------------------|----------------------------------------------|
| `DB_URL`         | URL de conexión a la base de datos       | `jdbc:postgresql://localhost:5432/authdb`    |
| `DB_USERNAME`    | Usuario de la base de datos              | `postgres`                                   |
| `DB_PASSWORD`    | Contraseña de la base de datos           | `1234`                                       |
| `JWT_SECRET`     | Clave secreta para firmar los tokens     | `miSuperClaveSecreta123!`                    |
| `JWT_EXPIRATION` | Duración del access token (en miliseg.)  | `900000` (15 minutos)                        |
| `REFRESH_EXPIRATION` | Duración del refresh token (en miliseg.) | `604800000` (7 días)                     |

---

## 🚀 Endpoints principales

| Método | Endpoint            | Descripción                    |
|--------|---------------------|--------------------------------|
| POST   | `/api/auth/login`   | Inicia sesión y devuelve access y refresh tokens |
| POST   | `/api/auth/refresh` | Renueva el access token usando un refresh token|

---

## 🛡️ Seguridad

- Los endpoints están protegidos con JWT.
- Los roles de usuario permiten control de acceso (`ROLE_ADMIN`, `ROLE_CLIENT`,`ROLE_DELIVERER` ).
- El token se debe enviar en la cabecera `Authorization: Bearer <token>`.

---

## 🐳 Docker
### 🛠️ 1. Construir la imagen
Puedes ejecutar el microservicio utilizando Docker. Asegúrate de tener Docker instalado y ejecuta el siguiente comando en la raiz del proyecto:

```bash
docker build -t auth-microservice .
```
### 🚀 2. Ejecutar el contenedor
Usa el siguiente comando para ejecutar el contenedor con las variables de entorno requeridas:
```bash
docker run -p 8181:8181 \
  -e DB_URL=jdbc:postgresql://localhost:5432/authdb \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=1234 \
  -e JWT_SECRET=miSuperClaveSecreta123! \
  -e JWT_EXPIRATION=900000 \
  -e REFRESH_EXPIRATION=604800000 \
  --name auth_service_container \
  auth_service
```
### 📁 Opcional: Usar archivo .env
Si prefieres no definir las variables de entorno manualmente, puedes crear un archivo `.env` en la raíz del proyecto con el siguiente contenido:

```env
DB_URL=jdbc:postgresql://localhost:5432/authdb
DB_USERNAME=postgres
DB_PASSWORD=1234
JWT_SECRET=miSuperClaveSecreta123!
JWT_EXPIRATION=900000
REFRESH_EXPIRATION=604800000
```
Luego, ejecuta el contenedor con el siguiente comando:

```bash
docker run --env-file .env -p 8181:8181 --name auth_service_container auth_service
```
---
