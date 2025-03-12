package com.fmsp.medical_appointment.entity.enums;

import java.util.Arrays;

public enum PrecioCitas {
    MGENERAL("MGENERAL", 150.000),
    ODONTOLOGIA("ODONTOLOGIA", 200.00);

    private String indice;
    private Double valor;

    PrecioCitas(String indice, Double valor) {
        this.valor = valor;
        this.indice = indice;
    }

    public String getIndice() {
        return indice;
    }


    public Double getValor() {
        return valor;
    }

    public static PrecioCitas getByName(String indice) {
        return Arrays.stream(PrecioCitas.values())
                .filter(tipo -> tipo.getIndice().equalsIgnoreCase(indice))
                .findFirst()
                .orElse(null);
    }

}
