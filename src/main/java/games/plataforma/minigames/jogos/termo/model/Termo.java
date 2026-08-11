package games.plataforma.minigames.jogos.termo.model;

import games.plataforma.minigames.util.GeradorAleatorio;

public class Termo {
    private String      palavraSecreta;
    private int         tentativas;
    private boolean     finalizado;
    private boolean     venceu;
    private String[]    historico;
    private int[]       coresHistorico;
    private static final int MAX_TENTATIVAS = 6;

    private static final String[] PALAVRAS = {
            "AMIGO", "BANCO", "CARRO", "DADO", "ELITE", "FACA", "GATO", "HORTE",
            "ILHA", "JANEL", "LIVRO", "MESA", "NORTE", "ONDA", "PATO", "QUEI",
            "RATO", "SAPO", "TELA", "URSO", "VACA", "XALE", "ZEBRA", "ABAC",
            "BOLA", "CASA", "DENTE", "ESCOLA", "FOGO", "GELO", "HELIO", "IGREJA",
            "JOGO", "LUA", "MORRO", "NAVE", "OVO", "PEIXE", "QUADRO", "RAIO",
            "SOL", "TIGRE", "UVA", "VENTO", "W", "XADREZ", "Y", "ZOO"
    };

    public Termo() {
        novaPalavra();
    }

    public void novaPalavra() {
        palavraSecreta = GeradorAleatorio.escolher(PALAVRAS).toUpperCase();
        tentativas = 0;
        finalizado = false;
        venceu = false;
        historico = new String[MAX_TENTATIVAS];
        coresHistorico = new int[MAX_TENTATIVAS * 5];
    }

    public boolean tentarPalavra(String palavra) {
        if (finalizado) {
            return false;
        }

        if (palavra.length() != 5) {
            return false;
        }
        palavra = palavra.toUpperCase();
        tentativas++;
        int[] cores = new int[5];
        for (int i = 0; i < 5; i++) {
            cores[i] = 0;
        }

        boolean[] usadas = new boolean[5];
        for (int i = 0; i < 5; i++) {
            if (palavra.charAt(i) == palavraSecreta.charAt(i)) {
                cores[i] = 2;
                usadas[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (cores[i] == 2) {
                continue;
            }

            for (int j = 0; j < 5; j++) {
                if (!usadas[j] && palavra.charAt(i) == palavraSecreta.charAt(j)) {
                    cores[i] = 1;
                    usadas[j] = true;
                    break;
                }
            }
        }

        historico[tentativas-1] = palavra;
        for (int i = 0; i < 5; i++) {
            coresHistorico[(tentativas-1)*5 + i] = cores[i];
        }

        if (palavra.equals(palavraSecreta)) {
            finalizado = true;
            venceu = true;
        } else if (tentativas >= MAX_TENTATIVAS) {
            finalizado = true;
            venceu = false;
        }
        return true;
    }

    public String getPalavraSecreta() {
        return palavraSecreta;
    }

    public int getTentativas() {
        return tentativas;
    }

    public int getMaxTentativas() {
        return MAX_TENTATIVAS;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public boolean isVenceu() {
        return venceu;
    }

    public String getHistorico(int indice) {
        return indice < tentativas ? historico[indice] : null;
    }

    public int getCor(int tentativa, int posicao) {
        if (tentativa >= tentativas) {
            return -1;
        }
        return coresHistorico[tentativa*5 + posicao];
    }
}
