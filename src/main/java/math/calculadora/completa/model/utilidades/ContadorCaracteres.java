package math.calculadora.completa.model.utilidades;

import math.calculadora.completa.model.utilidades.resultados.ResultadoContagem;

public class ContadorCaracteres {

    public static ResultadoContagem contarCaracteres(String texto) {
        if (texto == null) {
            texto = "";
        }

        int total = texto.length();
        int semEspacos = texto.replace(" ", "").length();
        int palavras = texto.trim().isEmpty() ? 0 : texto.trim().split("\\s+").length;
        int linhas = texto.isEmpty() ? 0 : texto.split("\n").length;
        int vogais = 0, consoantes = 0;
        String lower = texto.toLowerCase();

        for (char c : lower.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                if ("aeiou".indexOf(c) >= 0) {
                    vogais++;
                } else {
                    consoantes++;
                }
            }
        }
        return new ResultadoContagem(total, semEspacos, palavras, linhas, vogais, consoantes);
    }
}
