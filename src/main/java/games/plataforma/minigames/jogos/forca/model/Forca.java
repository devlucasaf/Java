package games.plataforma.minigames.jogos.forca.model;

import games.plataforma.minigames.util.GeradorAleatorio;

public class Forca {
    private String  palavraSecreta;
    private char[]  progresso;
    private int     erros;
    private boolean finalizado;
    private boolean venceu;

    private static final int MAX_ERROS = 6;
    private static final String[] PALAVRAS = {
            "JAVA", "PROGRAMACAO", "COMPUTADOR", "ALGORITMO", "DESENVOLVIMENTO",
            "SISTEMA", "APLICACAO", "BANCO", "DADOS", "INTERNET",
            "SEGURANCA", "REDE", "CODIGO", "TESTE", "DEPURACAO"
    };

    private boolean[] letrasReveladas;
    private int reveladosCount;

    public Forca() {
        novaPalavra();
    }

    public void novaPalavra() {
        palavraSecreta = GeradorAleatorio.escolher(PALAVRAS).toUpperCase();
        progresso = new char[palavraSecreta.length()];
        letrasReveladas = new boolean[palavraSecreta.length()];
        for (int i = 0; i < progresso.length; i++) {
            progresso[i] = '_';
            letrasReveladas[i] = false;
        }
        erros = 0;
        finalizado = false;
        venceu = false;
        reveladosCount = 0;
    }

    public boolean tentarLetra(char letra) {
        if (finalizado) {
            return false;
        }

        letra = Character.toUpperCase(letra);
        boolean acertou = false;
        for (int i = 0; i < palavraSecreta.length(); i++) {
            if (palavraSecreta.charAt(i) == letra && progresso[i] == '_') {
                progresso[i] = letra;
                letrasReveladas[i] = true;
                acertou = true;
                reveladosCount++;
            }
        }
        if (!acertou) {
            erros++;
            if (erros >= MAX_ERROS) {
                finalizado = true;
                venceu = false;
            }
        } else {
            if (palavraCompleta()) {
                finalizado = true;
                venceu = true;
            }
        }
        return acertou;
    }

    public boolean palavraCompleta() {
        for (char c : progresso) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    public String getPalavraSecreta() {
        return palavraSecreta;
    }

    public char[] getProgresso() {
        return progresso;
    }

    public int getErros() {
        return erros;
    }

    public int getMaxErros() {
        return MAX_ERROS;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public boolean isVenceu() {
        return venceu;
    }

    public String getProgressoString() {
        return new String(progresso);
    }

    public boolean isLetraRevelada(int pos) {
        return letrasReveladas[pos];
    }

    public int getReveladosCount() {
        return reveladosCount;
    }
}
