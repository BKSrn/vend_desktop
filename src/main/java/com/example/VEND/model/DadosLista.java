package com.example.VEND.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosLista(@JsonAlias("codigo") String codigo,
                         @JsonAlias("nome") String nome) {
}
