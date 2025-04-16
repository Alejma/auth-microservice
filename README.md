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

| Método | Endpoint             | Descripción                             |
|--------|----------------------|-----------------------------------------|
| POST   | `/api/auth/login`    | Inicia sesión y devuelve tokens         |
| POST   | `/api/auth/register` | Registra un nuevo usuario               |

---

## 🛡️ Seguridad

- Los endpoints están protegidos con JWT.
- Los roles de usuario permiten control de acceso (`ROLE_ADMIN`, `ROLE_CLIENT`,`ROLE_DELIVERER` ).
- El token se debe enviar en la cabecera `Authorization: Bearer <token>`.

---