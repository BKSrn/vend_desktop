package com.example.VEND.util;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;


public class ConsumoGemini {

    public static String consumirGemini(String prompt) {
        GenerateContentResponse response;

        try {
            //Ele busca a variavel de ambiente referente a chave da API automaticamente se ela se chamar GOOGLE_API_KEY
            Client client = new Client();

            String promptCompleto = "Responda como se voce fosse um agente virtual que ajuda a responder perguntas sobre carros e motos. "
                    + "Seja claro e objetivo. "
                    + "Aqui está a pergunta: " + prompt;

            response = client.models.generateContent(
                    "gemini-2.5-flash",
                    promptCompleto,
                    null
            );

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao consumir LLM generativa Gemini: " + e.getMessage());
        }

        return response.text();
    }

}
