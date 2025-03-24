package com.fmsp.medical_appointment.entity.enums;

import java.util.Arrays;

public enum Especialidad {
    MGENERAL(1L, "MGENERAL"),
    ODONTOLOGIA(2L, "ODONTOLOGIA");

    private Long indice;
    private String valor;

    public Long getIndice() {
        return indice;
    }

    public void setIndice(Long indice) {
        this.indice = indice;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    Especialidad(Long indice, String valor) {
        this.valor = valor;
        this.indice = indice;
    }

    public static Especialidad getByIndice(Long indice) {
        return Arrays.stream(Especialidad.values())
                .filter(tipo -> tipo.getIndice().equals(indice))
                .findFirst()
                .orElse(null);
    }

    public static Especialidad getByValor(String valor) {
        return Arrays.stream(Especialidad.values())
                .filter(tipo -> tipo.getValor().equalsIgnoreCase(valor))
                .findFirst()
                .orElse(null);
    }

}
