# 🏥 Tarea: Sistema de Gestión de Citas Médicas (API REST)

## 🎯 Objetivo
Diseñar la estructura básica de un sistema que permita:
- Registrar pacientes y médicos.
- Agendar citas médicas.
- Consultar el historial de citas.

---

## 🧱 Entidades a Crear (usando Java y JPA)

### 1. `Paciente`
**Atributos sugeridos:**
- `id`: identificador único.
- `nombre`: nombre completo del paciente.
- `dni`: documento de identidad (único).
- `fechaNacimiento`: fecha de nacimiento.
- `telefono`: número de contacto.
- `email`: correo electrónico.

**Relaciones:**
- Tiene muchas citas médicas (OneToMany con `Cita`).

---

### 2. `Medico`
**Atributos sugeridos:**
- `id`: identificador único.
- `nombre`: nombre completo del médico.
- `especialidad`: especialidad médica (p. ej. pediatra, cardiólogo, etc.).
- `telefono`: número de contacto.
- `email`: correo electrónico.

**Relaciones:**
- Atiende muchas citas médicas (OneToMany con `Cita`).

---

### 3. `Cita`
**Atributos sugeridos:**
- `id`: identificador único.
- `fechaHora`: fecha y hora de la cita.
- `motivo`: motivo de la consulta.

**Relaciones:**
- Relación ManyToOne con `Paciente`.
- Relación ManyToOne con `Medico`.

---

## 📦 Repositorios

✅ Implementalos como siempre, usarás `JpaRepository` para cada entidad.

---

## 🧠 Interfaces de Servicio

Crear una **interfaz de servicio por cada entidad**:
- `PacienteService`
- `MedicoService`
- `CitaService`

Cada interfaz debe definir métodos CRUD básicos:
- Crear, obtener todos, obtener por ID, actualizar y eliminar.

---

## 🧩 Implementaciones de Servicio

Crear clases de implementación para las interfaces anteriores:
- `PacienteServiceImpl`
- `MedicoServiceImpl`
- `CitaServiceImpl`

Cada clase debe:
- Inyectar el repositorio correspondiente
- Implementar la lógica básica (mapear entidades a DTO si quieres adelantarte).
- Validar entradas (por ejemplo, no permitir citas con fechas pasadas, validaciones que veas necesarias asi como vimos en la anterior tarea)
---

## 🌐 Controladores (REST)

Crear controladores para exponer los endpoints:
- `PacienteController`
- `MedicoController`
- `CitaController`

Cada controlador debe:
- Estar anotado con `@RestController`.
- Usar `@RequestMapping("/api/pacientes")`, etc.
- Tener métodos GET, POST, PUT, DELETE.

---

## 📌 Consideraciones Adicionales

- Usar Lombok para reducir el código boilerplate (@NoArgsConstructor, Getter, Setter, etc).
- Usar `@ManyToOne` y `@OneToMany` con cuidado
- Pensar en validaciones: ¿qué pasa si se registra un paciente sin nombre? ¿una cita sin médico?
- Puedes preparar tus DTOs para separar lógica interna de la respuesta pública si ya viste ese tema.

---

## 📚 Entrega esperada

- Crea el docker, application.yml tu mismo, si tienes dudas me avisas, pero es ideal que ya lo hagas por tu cuenta segun lo visto
- Las 3 entidades Java creadas
- Las relaciones correctamente mapeadas con JPA
- Controladores funcionando para probar con Swagger
- Lógica en servicios y DTOs (si alcanzas)
- Añade Swagger open api como siempre para probar los endpoints


