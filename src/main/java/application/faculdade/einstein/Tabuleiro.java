package application.faculdade.einstein;

import java.util.HashSet;
import java.util.Set;

public class Tabuleiro {
    private final String[][] celulas;
    private static final int NUM_CASAS = 5;
    private static final Categoria[] CATEGORIAS = Categoria.values();

    public Tabuleiro() {
        celulas = new String[CATEGORIAS.length][NUM_CASAS];
        celulas[Categoria.NACIONALIDADE.ordinal()][0] = "Norueguês";
        celulas[Categoria.BEBIDA.ordinal()][2] = "Leite";
    }

    public boolean atribuir(int casa, Categoria categoria, String valor) {
        if (casa < 0 || casa >= NUM_CASAS) {
            System.out.println("Casa inválida. Use valores de 1 a 5.");
            return false;
        }

        String[] valoresPossiveis = DadosJogo.valoresDe(categoria);
        boolean valorValido = false;
        for (String v : valoresPossiveis) {
            if (v.equalsIgnoreCase(valor)) {
                valorValido = true;
                break;
            }
        }

        if (!valorValido) {
            System.out.println("Valor '" + valor + "' não é válido para a categoria " + categoria.getNome() + ".");
            return false;
        }

        String valorOficial = null;
        for (String v : valoresPossiveis) {
            if (v.equalsIgnoreCase(valor)) {
                valorOficial = v;
                break;
            }
        }

        int catIndex = categoria.ordinal();
        for (int c = 0; c < NUM_CASAS; c++) {
            if (c != casa && celulas[catIndex][c] != null && celulas[catIndex][c].equals(valorOficial)) {
                System.out.println("Esse valor já foi utilizado em outra casa.");
                return false;
            }
        }

        if (celulas[catIndex][casa] != null && !celulas[catIndex][casa].equals(valorOficial)) {
            System.out.println("A célula já contém '" + celulas[catIndex][casa] + "'. Use 'remover' primeiro.");
            return false;
        }

        if (!DadosJogo.SOLUCAO[catIndex][casa].equals(valorOficial)) {
            System.out.println("Atribuição incorreta! Esse valor não pertence a esta casa.");
            return false;
        }

        celulas[catIndex][casa] = valorOficial;
        System.out.println("Atribuição aceita!");
        return true;
    }

    public void remover(int casa, Categoria categoria) {
        int catIndex = categoria.ordinal();
        if (celulas[catIndex][casa] == null) {
            System.out.println("A célula já está vazia.");
        } else {
            celulas[catIndex][casa] = null;
            System.out.println("Valor removido.");
        }
    }

    public boolean estaCompleto() {
        for (int i = 0; i < CATEGORIAS.length; i++) {
            for (int j = 0; j < NUM_CASAS; j++) {
                if (celulas[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void exibir() {
        System.out.println("\n=== TABULEIRO ATUAL ===");
        System.out.print("              ");
        for (int i = 1; i <= NUM_CASAS; i++) {
            System.out.printf("Casa %-2d   ", i);
        }
        System.out.println();

        System.out.print("              ");
        for (int i = 0; i < NUM_CASAS; i++) {
            System.out.print("---------- ");
        }
        System.out.println();

        for (Categoria cat : CATEGORIAS) {
            System.out.printf("%-12s  ", cat.getNome() + ":");
            int catIndex = cat.ordinal();
            for (int c = 0; c < NUM_CASAS; c++) {
                String val = celulas[catIndex][c];
                String exib = (val != null) ? val : "?";
                System.out.printf("%-10s ", exib);
            }
            System.out.println();
        }
        System.out.println("=====================================");

        System.out.println("\nValores ainda não utilizados:");
        for (Categoria cat : CATEGORIAS) {
            int catIndex = cat.ordinal();
            Set<String> usados = new HashSet<>();
            for (int c = 0; c < NUM_CASAS; c++) {
                if (celulas[catIndex][c] != null) {
                    usados.add(celulas[catIndex][c]);
                }
            }
            System.out.print(cat.getNome() + ": ");
            String[] todos = DadosJogo.valoresDe(cat);
            boolean primeiro = true;
            for (String v : todos) {
                if (!usados.contains(v)) {
                    if (!primeiro) {
                        System.out.print(", ");
                    }
                    System.out.print(v);
                    primeiro = false;
                }
            }
            if (primeiro) {
                System.out.print("(todos usados)");
            }
            System.out.println();
        }
        System.out.println();
    }
}