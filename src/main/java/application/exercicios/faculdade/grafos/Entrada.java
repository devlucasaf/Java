package application.exercicios.faculdade.grafos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class Entrada {

    private final BufferedReader leitor;

    public Entrada() {
        this.leitor = new BufferedReader(new InputStreamReader(System.in));
    }

    // --- LE UMA LINHA COMPLETA DO TERMINAL ---
    public String lerLinha() {
        try {
            String linha = leitor.readLine();

            if (linha == null) {
                return "";
            }
            return linha;
        } catch (IOException exception) {
            throw new IllegalStateException("Não foi possível ler a entrada informada.", exception);
        }
    }

    // --- LE UM NÚMERO INTEIRO E RETORNA ZERO QUANDO A ENTRADA FOR INVÁLIDA ---
    public int lerInteiro() {
        String linha = lerLinha().trim();

        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }

    public String lerVertice(String mensagem) {
        System.out.print(mensagem);
        String rotulo = lerLinha();
        return normalizar(rotulo);
    }

    // --- REMOVE ESPAÇOS E CONVERTE O TEXTO PARA LETRAS MAIÚSCULAS ---
    public static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.replace(" ", "").replace("\t", "").toUpperCase(Locale.ROOT);
    }
}

