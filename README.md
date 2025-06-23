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
# Guía Completa de las  Pruebas  de Cobertura

Este proyecto utiliza **JaCoCo** para generar reportes de cobertura de pruebas. A continuación
se explican los pasos necesarios para ejecutar las pruebas y visualizar el reporte generado.

---

### 🔹 Paso 1: Limpiar el proyecto y ejecutar pruebas

> mvn clean test

Este comando realiza una limpieza del proyecto y luego ejecuta todas las pruebas configuradas:

- clean: Elimina archivos temporales y la carpeta target para asegurar una compilación limpia.

- test: Ejecuta todas las pruebas unitarias e integrales definidas en el proyecto.

### 🔹  Paso 2: Preparar JaCoCo e instalar el proyecto

> mvn org.jacoco:jacoco-maven-plugin:0.8.11:prepare-agent install

Este comando configura el agente de JaCoCo y compila el proyecto:

- prepare-agent: Inicializa el agente de JaCoCo que recolectará los datos de cobertura.

- install: Compila e instala el proyecto en el repositorio local de Maven,
  ejecutando nuevamente las pruebas con el agente activado.

### 🔹 Paso 3: Generar el reporte de cobertura
> mvn org.jacoco:jacoco-maven-plugin:0.8.11:report

Este comando genera un informe detallado de cobertura de código basado en las pruebas ejecutadas anteriormente.

### 🔹📊 Visualizar el reporte de cobertura

1. Una vez ejecutados los comandos anteriores, navega hasta la
   siguiente ruta del proyecto:

target/site/jacoco/

2. Dentro de esta carpeta, ubica el archivo:

index.html

3. Arrastra este archivo a tu navegador o ábrelo directamente con doble clic.

Esto te permitirá visualizar un informe completo con métricas como cobertura por clase,
línea, rama, etc., generado por JaCoCo.

