# Feature Specification: Angular Frontend — Login y CRUD de Empleados y Departamentos

**Feature Branch**: `004-frontend-empleados-login`  
**Created**: 2026-03-11  
**Status**: Draft  
**Input**: User description: "Frontend que permita un CRUD empleados y un login que tome email y password para el loggeo"

## User Scenarios & Testing _(mandatory)_

### User Story 1 — Login con credenciales HTTP Basic (Priority: P1)

Un administrador abre la aplicación web y es redirigido a la pantalla de login si no está
autenticado. Ingresa su usuario (email o username) y contraseña, hace clic en "Iniciar sesión"
y es redirigido al listado de empleados. Las credenciales se retienen en la sesión del navegador
para que cada llamada a la API se autentique automáticamente.

**Why this priority**: Sin autenticación, ninguna otra pantalla es accesible. Es la puerta de
entrada obligatoria al resto de la aplicación.

**Independent Test**: Se puede probar de forma aislada levantando solo el frontend contra el
backend existente: navegar a `/login`, ingresar credenciales válidas HTTP Basic
(por ejemplo `admin` / `admin123` en entorno local), verificar que la redirección a `/empleados`
ocurre y que las llamadas subsiguientes incluyen credenciales.

**Acceptance Scenarios**:

1. **Given** el usuario no está autenticado, **When** accede a cualquier ruta protegida, **Then** es redirigido a `/login`.
2. **Given** el formulario de login está vacío, **When** hace clic en "Iniciar sesión", **Then** se muestran mensajes de validación en los campos requeridos.
3. **Given** el usuario no completa usuario o contraseña, **When** sale del campo, **Then** se muestran mensajes de campo requerido.
4. **Given** el usuario ingresa credenciales correctas, **When** hace clic en "Iniciar sesión", **Then** es redirigido al listado de empleados.
5. **Given** el usuario ingresa credenciales incorrectas, **When** hace clic en "Iniciar sesión", **Then** se muestra el mensaje "Credenciales inválidas" sin redirigir.
6. **Given** el usuario está autenticado, **When** hace clic en "Cerrar sesión", **Then** las credenciales son eliminadas y es redirigido a `/login`.

---

### User Story 2 — Listados paginados de Empleados y Departamentos (Priority: P2)

El administrador autenticado ve tablas paginadas para empleados y departamentos. Puede navegar entre
páginas y elegir cuántos registros ver por página. Cada fila muestra datos clave y ofrece acciones
de editar y eliminar.

**Why this priority**: Es la vista central de la aplicación desde la que se lanzan todas las
operaciones de gestión. Debe existir antes de las operaciones individuales.

**Independent Test**: Con el login funcionando, navegar a `/empleados` y `/departamentos`, verificar
que ambas tablas muestran datos reales del backend y que los controles de paginación responden correctamente.

**Acceptance Scenarios**:

1. **Given** el usuario está autenticado, **When** navega a `/empleados`, **Then** se muestra la tabla con empleados del backend.
2. **Given** el usuario está autenticado, **When** navega a `/departamentos`, **Then** se muestra la tabla con departamentos del backend.
3. **Given** una tabla está mostrando la primera página, **When** hace clic en "Siguiente", **Then** se carga la segunda página.
4. **Given** el usuario cambia el selector de tamaño a 5, **When** la página se actualiza, **Then** la tabla muestra máximo 5 filas.
5. **Given** no hay registros, **When** se carga la vista, **Then** se muestra un mensaje de estado vacío.

---

### User Story 3 — CRUD de Empleados (Priority: P3)

El administrador hace clic en "Nuevo empleado", completa el formulario con los campos
requeridos (`clave`, `nombre`, `direccion`, `telefono`, `departamento`) y guarda. El nuevo empleado aparece
inmediatamente en la tabla.

**Why this priority**: Operación de escritura primaria. Una vez que el listado existe, agregar
registros es el siguiente paso lógico en el flujo de gestión.

**Independent Test**: Con US1 y US2 funcionando, hacer clic en "Nuevo empleado", completar el
formulario y verificar que el registro aparece en la lista tras guardar.

**Acceptance Scenarios**:

1. **Given** el usuario hace clic en "Nuevo empleado", **When** se abre el diálogo modal, **Then** todos los campos están vacíos y el botón "Guardar" está disponible.
2. **Given** el formulario tiene campos vacíos obligatorios, **When** hace clic en "Guardar", **Then** se muestran mensajes de validación por campo.
3. **Given** la operación genera conflicto de datos o versión, **When** hace clic en "Guardar", **Then** el backend devuelve error y la UI muestra un mensaje de conflicto.
4. **Given** todos los datos son válidos, **When** hace clic en "Guardar", **Then** el empleado es creado, el diálogo modal se cierra y la lista se actualiza.

---

### User Story 4 — CRUD de Departamentos (Priority: P4)

El administrador crea, edita y elimina departamentos desde la interfaz, con confirmación antes de
borrar. La tabla refleja la información actualizada sin recarga manual.

**Why this priority**: Completa el alcance solicitado y habilita la administración de catálogos
usados por empleados.

**Independent Test**: Con US1–US3 funcionando, crear un departamento, editar su nombre y luego
eliminarlo con confirmación verificando los cambios en la tabla.

**Acceptance Scenarios**:

1. **Given** el usuario hace clic en "Nuevo departamento", **When** completa un formulario válido, **Then** el departamento es creado y aparece en la tabla.
2. **Given** el usuario hace clic en "Editar" de un departamento, **When** guarda datos válidos, **Then** los cambios se persisten y se reflejan en la tabla.
3. **Given** el usuario hace clic en "Eliminar" de un departamento, **When** confirma en el diálogo, **Then** el registro desaparece de la tabla.

---

### Edge Cases

- ¿Qué ocurre cuando la sesión expira o las credenciales almacenadas dejan de ser válidas? → El primer request que falle con 401 debe redirigir automáticamente a `/login` y limpiar credenciales.
- ¿Qué pasa si el usuario recarga la página estando autenticado? → Las credenciales persisten en `sessionStorage` para sobrevivir recargas dentro de la misma pestaña, pero no entre pestañas nuevas.
- ¿Qué ocurre si los endpoints del backend devuelven errores 500? → Se muestra un mensaje de error genérico sin exponer detalles técnicos.
- ¿Qué pasa si el backend está caído al iniciar la app? → La pantalla de login sigue mostrándose; el error se informa solo al intentar iniciar sesión.
- ¿Qué ocurre si se intenta eliminar un departamento referenciado por empleados? → La UI muestra conflicto de integridad y no elimina localmente la fila.

## Requirements _(mandatory)_

## Clarifications

### Session 2026-03-11

- Q: ¿Qué campo debe usar el login para evitar conflicto con credenciales locales por defecto? → A: `usuario` (acepta username o email) + contraseña.
- Q: ¿El alcance incluye departamentos en frontend? → A: Sí, incluye listado y CRUD completo de departamentos.
- Q: ¿Qué método de actualización se usará en frontend para evitar ambigüedad? → A: `PUT` como método único en esta fase.

### Functional Requirements

- **FR-001**: El sistema DEBE presentar un formulario de login con campos `usuario` y contraseña antes de permitir acceso a cualquier ruta protegida.
- **FR-002**: El sistema DEBE validar que `usuario` y contraseña no estén vacíos antes de enviar la solicitud de autenticación.
- **FR-003**: El sistema DEBE almacenar las credenciales en `sessionStorage` para reenviarlas en cada llamada a la API mientras dure la sesión.
- **FR-004**: El sistema DEBE interceptar respuestas 401 del backend y redirigir automáticamente al login eliminando las credenciales almacenadas.
- **FR-005**: El sistema DEBE mostrar la lista de empleados en una tabla paginada con columnas: `clave`, `nombre`, `direccion`, `telefono` y `departamento`. Cada fila incluye acciones de editar y eliminar.
- **FR-006**: El sistema DEBE permitir al usuario configurar el número de registros por página (opciones: 5, 10, 20) y navegar entre páginas.
- **FR-007**: El sistema DEBE presentar el formulario de creación de empleado en un diálogo modal con los campos `clave`, `nombre`, `direccion`, `telefono` y `departamento` (selector), con validación de campos requeridos antes de enviar al backend.
- **FR-008**: El sistema DEBE presentar el formulario de edición en un diálogo modal pre-poblado con los campos `clave` (solo lectura), `nombre`, `direccion`, `telefono` y `departamento` del empleado seleccionado. La actualización DEBE enviarse por `PUT`.
- **FR-009**: El sistema DEBE solicitar confirmación explícita mediante un diálogo modal antes de ejecutar la eliminación de un empleado, mostrando el nombre y `clave` del empleado afectado.
- **FR-010**: El sistema DEBE mostrar mensajes de error descriptivos ante fallos de red o respuestas de error del backend, sin exponer detalles técnicos.
- **FR-011**: El sistema DEBE proteger todas las rutas excepto `/login` mediante una guarda de ruta que verifique la presencia de credenciales.
- **FR-012**: El sistema DEBE actualizar la tabla de empleados inmediatamente luego de crear, editar o eliminar un registro.
- **FR-013**: El sistema DEBE mostrar la lista de departamentos en una tabla paginada con acciones de crear, editar y eliminar.
- **FR-014**: El sistema DEBE presentar formularios modales para crear y editar departamentos con campos `clave` y `nombre` (clave solo lectura en edición).
- **FR-015**: El sistema DEBE solicitar confirmación explícita antes de eliminar un departamento.

### Constitution Alignment _(mandatory)_

- **CA-001**: El backend permanece en Java 17 y Spring Boot 3.x; no se introducen nuevos servicios.
- **CA-002**: La autenticación HTTP Basic existente es reutilizada: el frontend recolecta `usuario` y contraseña en el formulario de login y las adjunta en el encabezado `Authorization: Basic ...` en cada petición HTTP mediante un interceptor de Angular.
- **CA-003**: Sin cambios de persistencia en PostgreSQL; el frontend consume los endpoints REST existentes.
- **CA-004**: Sin cambios funcionales de contrato API; si se añade alias versionado o paginación en backend, OpenAPI DEBE actualizarse en el mismo cambio.
- **CA-005**: Cobertura de pruebas requerida: pruebas unitarias para servicios Angular y componentes clave; pruebas de integración de tipo E2E para los flujos de login y CRUD completo.
- **CA-006**: El frontend consume endpoints versionados (`/api/v1/...`), permitiendo transición compatible cuando el backend exponga alias desde rutas existentes.
- **CA-007**: El frontend DEBE renderizar los metadatos de paginación devueltos por el backend (`page`, `size`, `totalElements`) y exponer controles de navegación de páginas.
- **CA-008**: El proyecto Angular 21 reside en `frontend/`. La URL base de la API DEBE configurarse exclusivamente en `frontend/src/environments/environment.ts` y `environment.prod.ts`. Ninguna URL de API puede aparecer hardcodeada en componentes o servicios.
- **CA-009**: El backend DEBE permitir el origen del servidor de desarrollo Angular (`http://localhost:4200`) y del contenedor nginx en producción mediante configuración CORS explícita (cambio de configuración permitido aunque no cambie lógica de negocio).

### Key Entities

- **Empleado**: Entidad principal gestionada. El backend expone al menos `clave` (PK), `nombre`, `direccion`, `telefono`, `version` y `departamentoClave`. Leído y modificado exclusivamente por API REST.
- **Departamento**: Entidad de catálogo con `clave` y `nombre`, usada para clasificar empleados y gestionada desde la UI.
- **Credenciales de sesión**: Par usuario/contraseña capturado en login, almacenado en `sessionStorage` y eliminado al cerrar sesión o al recibir un 401.

### Assumptions

- Las credenciales válidas ya existen en el backend y se mantienen compatibles con HTTP Basic (incluyendo defaults locales `admin` / `admin123`).
- El backend expone rutas de empleados y departamentos; si no están versionadas/paginadas aún, se añadirá compatibilidad en la implementación.
- El backend acepta `PUT` para actualización completa en empleados y departamentos.
- El selector de departamento en el formulario de empleado consulta el endpoint de departamentos para poblar opciones disponibles.
- No se requiere internacionalización (i18n); la interfaz estará en español.
- El diseño visual usará los estilos por defecto de Angular Material o una biblioteca de componentes equivalente disponible para Angular 21; no se especifica un sistema de diseño propietario.

## Success Criteria _(mandatory)_

### Measurable Outcomes

- **SC-001**: Un administrador puede completar el flujo completo de login y llegar al listado de empleados en menos de 30 segundos.
- **SC-002**: El formulario de login informa al usuario sobre errores de validación (campo vacío, email inválido, credenciales incorrectas) antes de completar un ciclo completo de request/response.
- **SC-003**: Un administrador puede crear, editar o eliminar un empleado sin abandonar la pantalla de listado, completando la operación en menos de 2 minutos.
- **SC-004**: Un administrador puede crear, editar o eliminar un departamento sin abandonar la pantalla de listado, completando la operación en menos de 2 minutos.
- **SC-005**: Las tablas de empleados y departamentos reflejan el resultado de cada operación sin necesidad de recargar la página manualmente.
- **SC-006**: El 100% de las rutas de la aplicación (excepto `/login`) son inaccesibles sin credenciales válidas, verificable navegando directamente a la URL.
- **SC-007**: La interfaz muestra un mensaje de error comprensible (sin stack traces ni códigos HTTP) ante cualquier fallo de comunicación con el backend.
