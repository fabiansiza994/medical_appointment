package com.fmsp.medical_appointment.entity.enums;

import java.util.Arrays;

public enum EstadoProceso {
    PS(1, "PS"), // process success
    PF(2, "PF"); // process failed

    private Integer indice;
    private String valor;

    EstadoProceso(Integer indice,String valor) {
        this.valor = valor;
        this.indice = indice;
    }

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

    public static EstadoProceso getByIndice(Integer indice) {
        return Arrays.stream(EstadoProceso.values())
                .filter(tipo -> tipo.getIndice().equals(indice))
                .findFirst()
                .orElse(null);
    }

    public static EstadoProceso getByValor(String valor) {
        return Arrays.stream(EstadoProceso.values())
                .filter(tipo -> tipo.getValor().equalsIgnoreCase(valor))
                .findFirst()
                .orElse(null);
    }
}
