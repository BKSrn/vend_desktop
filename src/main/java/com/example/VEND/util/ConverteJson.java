package com.example.VEND.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ConverteJson {
    private ObjectMapper mapper = new ObjectMapper();

    public <T> T getDados (String json, Class<T> classe){
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao desseraliazar o json : " + e.getMessage());
        }
    }

    public <T> List<T> getDadosLista(String json, Class<T> classe) {
        try {
            return mapper.readValue(json,
                    mapper.getTypeFactory().constructCollectionType(List.class, classe));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao desserializar o json: " + e.getMessage());
        }
    }

}
