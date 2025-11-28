package com.example.VEND.model.enums;

public enum Carroceria {
    SELECIONE("selecione"),
    SEDAN("sedan"),
    HATCH("hatch"),
    SUV("suv"),
    CAMINHONETE("caminhonete"),
    PERUA("perua");

    private String carroceria;

    Carroceria(String variavelCarroceria) {
        this.carroceria = variavelCarroceria;
    }

    public static Carroceria fromString (String texto){
        Carroceria[] carrocerias = Carroceria.values();

        for (Carroceria carroceria : carrocerias){
            if (carroceria.carroceria.equalsIgnoreCase(texto.toLowerCase())){
                return carroceria;
            }
        }
        throw new RuntimeException("Erro ao selecionar a carroceria! ");

    }
}
