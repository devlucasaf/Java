package org.games.tabuleiro.grid.model;

public class Grade {

    private String[]   linhas;
    private String[]   colunas;
    private String[][] respostas;

    public Grade(String[] linhas, String[] colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.respostas = new String[3][3];
    }

    public String[] getLinhas() {
        return linhas;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setRespostas(int linha, int coluna, String resposta) {
        respostas[linha][coluna] = resposta;
    }

    public void exibirGrade() {
        System.out.println("\nGRADE DO JOGO:");
        System.out.print("       ");

        for (String coluna : colunas) {
            System.out.printf("%-15s", coluna);
        }
        System.out.println();

        for (int i = 0; i < 3; i++) {
            System.out.printf("%-10s", linhas[i]);

            for (int j = 0; j < 3; j++) {
                System.out.printf("%-15s", respostas[i][j] == null ? "[ ]" : respostas[i][j]);
            }
            System.out.println();
        }
    }
}
