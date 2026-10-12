# Plan de Pruebas — Sistema de Citas Médicas

## 1. Objetivo de las pruebas
- Verificar la correcta funcionalidad de cada rol (admin, médico, paciente).
- Verificar la precisión de los resultados: reportes, auditoría y estados de cita.
- Verificar seguridad: acceso solo a lo permitido, en interfaz y API (401/403).
- Asegurar regresión: los casos P0/P1 se pueden repetir en cada versión.

## 2. Alcance

### Si probamos
- Login, logout y sesión (los 3 roles)
- Gestión de usuarios (admin): crear / listar / eliminar
- Gestión de pacientes (admin): crear / listar / eliminar
- Gestión de médicos (admin): crear fichas / disponibilidad
- Citas (admin): ver, editar, eliminar, historial del paciente
- Agenda del médico: atender cita, ver historial
- Paciente: registrarse, agendar, cancelar, ver/cancelar citas
- Auditoría y reportes (admin)
- Perfil de cada rol: qué puede editar y qué no
- Acceso por roles: guards UI + autorización en la API (401/403/404/409/500)
- Centro de ayuda

### No probamos
- Rendimiento / carga
- Vista móvil responsive
- Notificaciones por correo / WhatsApp
- App móvil (no existe)

## 3. Entorno de pruebas
- Aplicación local: frontend `http://localhost:4200`, API `http://localhost:3000/api`
- Base de datos: MongoDB Atlas (datos de prueba)
- Credenciales: variables de entorno `setx` (NUNCA en el código)
  - 3 usuarios: admin, médico, paciente (QA)
- Navegadores: Chrome (principal) + chromedriver (Selenium Manager); Edge para verificaciones cruzadas
- Sistema: Windows, Java 23 (compila a Java 17), Maven 3.9.9

## 4. Matriz rol × función
| Función                          | Admin | Médico | Paciente |
|----------------------------------|:-----:|:------:|:--------:|
| Iniciar / cerrar sesión          |   ✅   |   ✅    |    ✅     |
| Crear / listar / eliminar usuarios|  ✅   |   —    |    —     |
| Crear / listar / eliminar pacientes|  ✅  |   —    |    —     |
| Fichas y disponibilidad de médicos|  ✅  |   —    |    —     |
| Mi agenda y atender cita         |   —   |   ✅    |    —     |
| Historial clínico                |  ✅   |   ✅    |    —     |
| Agendar / cancelar mis citas     |  ✅   |   —    |    ✅     |
| Ver mis citas y filtrar          |  ✅   |   —    |    ✅     |
| Auditoría                        |  ✅   |   —    |    —     |
| Reportes                         |  ✅   |   —    |    —     |
| Perfil: editar solo lo propio    |  ✅   |   ✅    |    ✅     |
| Centro de ayuda                  |  ✅   |   ✅    |    ✅     |

## 5. Reglas de negocio a verificar (requisitos)
1. El médico debe existir como ficha antes de poder registrarse.
2. Un paciente SOLO ve/cancela SUS citas.
3. Un médico SOLO ve SUS citas y agenda.
4. No se pueden agendar citas en días pasados ni en horas ya tomadas.
5. Cambiar la disponibilidad retira los cupos "Disponible" que sobren.
6. Nadie puede auto-registrarse como admin (la API debe rechazarlo).
7. El registro médico y el correo del perfil de médico son de solo lectura.
8. Las acciones relevantes quedan en auditoría.
9. El backend rechaza con 401 (sin token) / 403 (rol sin permiso).

## 6. Casos de prueba — prioridad y formato
Cada caso lleva: ID, título, rol, precondición, pasos, esperado, resultado.

### Prioridad P0 — Smoke (siempre debe estar en verde)
| ID   | Caso                                                 | Rol      |
|------|------------------------------------------------------|----------|
| L-01 | Login con credenciales correctas → /dashboard/inicio | los 3    |
| L-02 | Login con contraseña incorrecta → mensaje claro      | los 3    |
| L-03 | Logout vuelve a /login                               | los 3    |
| G-01 | Paciente entra /usuarios → redirigido y API 403      | paciente |
| G-02 | Sin token, GET /api/usuarios → 401                   | anónimo  |

### Prioridad P1 — Funcional por rol
| ID   | Caso                                             | Rol    |
|------|--------------------------------------------------|--------|
| U-01 | Admin crea usuario paciente → aparece en la lista | admin  |
| U-02 | Admin elimina usuario con ficha → sin huérfanos  | admin  |
| M-01 | Crear médico genera sus 10 cupos del día         | admin  |
| M-02 | Médico atiende una cita → pasa a "Atendida"      | médico |
| P-01 | Paciente agenda cita con médico disponible       | paciente|
| P-02 | Paciente cancela cita → estado "Cancelada"       | paciente|
| A-01 | Auditoría registra el alta de un usuario         | admin  |
| R-01 | Reportes cuadran con las citas reales de la base | admin  |
| PF-01| Médico edita su registro médico → no se puede    | médico |
| H-01 | Centro de ayuda carga sin errores                | los 3  |

### Prioridad P2 — Bordes y permisos (casos negativos)
- Agendar en día pasado / hora ocupada
- Validación de email (TLD largo, mayúsculas, formatos raros)
- Contraseña menor a 6 caracteres al registrarse
- Ver la ficha del paciente A estando logueado el paciente B
- Códigos HTTP en los fallos (400, 401, 403, 404, 409, 500)

## 7. Técnicas por etapa del curso
| Etapa | Técnica / herramienta                                        |
|-------|--------------------------------------------------------------|
| Git 1 | ramas, commits convencionales, merge, tags                    |
| Manual 2b | casos manuales + F12 (Network, Elements) + códigos HTTP |
| Selenium 3 | JUnit 5, waits, localizadores, POM                       |
| Suite 4 | Cucumber + Screenplay + Data Factory + Serenity             |
| Bugs 5 | inyección en la app, reportes con pantallazo y severidad    |
| API/CI 6 | Karate + GitHub Actions + GitHub Secrets                  |

## 8. Manejo de bugs
- Severidad: crítica / alta / media / baja
- Prioridad: alta / media / baja
- Formato de reporte: título, severidad, prioridad, entorno (URL/navegador),
  requisito (ID), precondición, pasos, esperado, actual (mensaje exacto),
  evidencia (pantallazo + paso fallido), causa raíz si se identifica.
- Flujo: detectar → reportar → corregir → re-test + regresión.

## 9. Criterios de inicio
- La app corre local: frontend `:4200` y API `:3000`.
- Existen y funcionan los 3 usuarios de QA (admin, médico, paciente).
- Las 6 variables de entorno están configuradas.
- Hay al menos un médico con cupos disponibles y un paciente con citas.
- El plan está escrito y cada caso tiene su ID.

## 10. Criterios de finalización (Definition of Done)
### Globales
- Todos los casos P0 en verde en cada corrida.
- Los P1 automatizados pasan con reporte y evidencia.
- Bugs detectados reportados con severidad y prioridad; los críticos, corregidos.
- La suite corre desde la terminal y desde el CI sin intervención.
### Por funcionalidad
- Tiene su caso manual (P0/P1) definido en este plan.
- Tiene su test automatizado en verde.
- Si es de seguridad, verificado en UI y en API (401/403).
- El reporte del test quedó con evidencia.