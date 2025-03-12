# medical_appointment

### Paquete del exception manager

src/main/java/com/fmsp/medical_appointment/configuration/exceptionManager

# medical_appointment

## 📌 Endpoint: Solicitar Cita Médica

### **📍 URL**
POST http://localhost:8082/medical-appointment/v1/citas/solicitar


### **📤 Request Body (Ejemplo)**
```json
{
  "idPaciente": 1,
  "idMedico": 2,
  "idEspecialidad": 1,
  "fechaHora": "2025-03-11T14:27:00",
  "tipoCita": "PRESENCIAL",
  "metodoPago": "TARJETA",
  "valorCita": 150.000,
  "estado": "PENDIENTE"
}
```

## Bad Request Example

Este es un ejemplo de respuesta cuando la solicitud contiene un error de formato en el campo `TipoCita`.

```json
{
  "idTx": "- -",
  "message": "ERROR",
  "errors": [
    {
      "codeError": "E001",
      "codTypeError": "404",
      "messageError": "JSON parse error: Cannot deserialize value of type `com.fmsp.medical_appointment.entity.enums.TipoCita` from String \"VIRTUAL.\": not one of the values accepted for Enum class: [PRESENCIAL, VIRTUAL]"
    }
  ]
}
```

## Internal Server Example

```json
{
  "idTx": "- -",
  "message": "ERROR",
  "errors": [
    {
      "codeError": "E002",
      "codTypeError": "500",
      "messageError": "Error inesperado del sistema"
    }
  ]
}

```
# medical_appointment

## Success Response Example

Este es un ejemplo de respuesta cuando la solicitud se procesa exitosamente.

```json
{
  "idTx": "f71a2ee6-82c8-442d-b48d-fd6b91114ad0",
  "processStatus": "PS",
  "data": {
    "agenda": {
      "medico": {
        "nombre": "marquez",
        "apellido": "marquez",
        "email": "marquez@gmail.com",
        "telefono": "31111111",
        "especialidad": {
          "nombreEspecialidad": "Medicina General"
        }
      },
      "fecha": "11/03/2025 a las 14:27",
      "disponibilidad": true
    },
    "datosCita": {
      "tipoCita": "PRESENCIAL",
      "metodoPago": "TARJETA",
      "valorCita": 150.0,
      "estado": "PENDIENTE"
    }
  },
  "message": "EXITO"
}

```

## Failed Response Example

Este es un ejemplo de respuesta cuando la solicitud contiene errores, como una especialidad inexistente o un monto insuficiente.

```json
{
  "idTx": "f8c745f6-a9ad-4162-b80a-bb49606f025d",
  "processStatus": "PF",
  "message": "ERROR",
  "errors": [
    {
      "codeError": "E004",
      "codTypeError": "400",
      "messageError": "no existe la especialidad"
    },
    {
      "codeError": "E004",
      "codTypeError": "400",
      "messageError": "monto insuficiente"
    }
  ]
}

