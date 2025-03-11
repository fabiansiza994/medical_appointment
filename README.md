# medical_appointment

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
## Success Response Example

Este es un ejemplo de respuesta cuando la solicitud se procesa exitosamente.

```json
{
  "idTx": "0615c124-1495-45a7-8877-d6d143a2cf25",
  "processStatus": "PS",
  "data": {
    "idTx": "0615c124-1495-45a7-8877-d6d143a2cf25",
    "idPaciente": 123,
    "idMedico": 456,
    "idEspecialidad": 789,
    "fechaHora": "2025-03-10T14:30:00",
    "tipoCita": "VIRTUAL",
    "metodoPago": "TARJETA",
    "valorCita": 150.0,
    "estado": "PENDIENTE
