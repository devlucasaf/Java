package games.text.trivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pergunta {

    private final String        categoria;
    private final String        enunciado;
    private final List<String>  alternativas;
    private final int           indiceCorreto;

    public Pergunta(String categoria, String enunciado, String[] alternativas, int indiceCorreto) {
        this.categoria = categoria;
        this.enunciado = enunciado;
        this.alternativas = new ArrayList<>(Arrays.asList(alternativas));
        this.indiceCorreto = indiceCorreto;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getAlternativas() {
        return alternativas;
    }

    public int getIndiceCorreto() {
        return indiceCorreto;
    }

    public String getRespostaCorreta() {
        return alternativas.get(indiceCorreto);
    }
}

