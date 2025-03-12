package com.fmsp.medical_appointment.entity.enums;

import java.util.Arrays;

public enum MetodoPago {
    EFECTIVO(1, "EFECTIVO"),
    TARJETA(2, "TARJETA"),
    TRANSFERENCIA(3, "TRANSFERENCIA");

    private Integer indice;
    private String valor;

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    MetodoPago(Integer indice, String valor) {
        this.indice = indice;
        this.valor = valor;
    }

    public static MetodoPago getByIndice(Integer indice) {
        return Arrays.stream(MetodoPago.values())
                .filter(tipo -> tipo.getIndice().equals(indice))
                .findFirst()
                .orElse(null);
    }

    public static MetodoPago getByValor(String valor) {
        return Arrays.stream(MetodoPago.values())
                .filter(tipo -> tipo.getValor().equalsIgnoreCase(valor))
                .findFirst()
                .orElse(null);
    }
}
