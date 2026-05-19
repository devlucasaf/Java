package org.application.scoreboard;

public class DataBase {
    private String  nomeJogador1;
    private String  nomeJogador2;
    private int     setsPorPartida;
    private int     pontosPorSet;
    private int     setsJogador1;
    private int     setsJogador2;
    private int     pontosJogador1;
    private int     pontosJogador2;

    public DataBase(String nomeJogador1, String nomeJogador2, int setsPorPartida, int pontosPorSet) {
        setNomeJogador1(nomeJogador1);
        setNomeJogador2(nomeJogador2);
        setSetsPorPartida(setsPorPartida);
        setPontosPorSet(pontosPorSet);
        this.setsJogador1   = 0;
        this.setsJogador2   = 0;
        this.pontosJogador1 = 0;
        this.pontosJogador2 = 0;
    }

    public String getNomeJogador1() {
        return nomeJogador1;
    }

    public void setNomeJogador1(String nomeJogador1) {
        if (nomeJogador1 == null || nomeJogador1.isBlank()) {
            throw new IllegalArgumentException("O nome do jogador 1 não pode ser vazio");
        }
        this.nomeJogador1 = nomeJogador1.toUpperCase();
    }

    public String getNomeJogador2() {
        return nomeJogador2;
    }

    public void setNomeJogador2(String nomeJogador2) {
        if (nomeJogador2 == null || nomeJogador2.isBlank()) {
            throw new IllegalArgumentException("O nome do jogador 2 não pode ser vazio");
        }
        this.nomeJogador2 = nomeJogador2.toUpperCase();
    }

    public int getSetsPorPartida() {
        return setsPorPartida;
    }

    public void setSetsPorPartida(int setsPorPartida) {
        if (setsPorPartida <= 0) {
            throw new IllegalArgumentException("Sets por partida deve ser maior que zero");
        }
        this.setsPorPartida = setsPorPartida;
    }

    public int getPontosPorSet() {
        return pontosPorSet;
    }

    public void setPontosPorSet(int pontosPorSet) {
        if (pontosPorSet <= 0) {
            throw new IllegalArgumentException("Pontos por set deve ser maior que zero");
        }
        this.pontosPorSet = pontosPorSet;
    }

    public int getSetsJogador1() {
        return setsJogador1;
    }

    public int getSetsJogador2() {
        return setsJogador2;
    }

    public int getPontosJogador1() {
        return pontosJogador1;
    }

    public int getPontosJogador2() {
        return pontosJogador2;
    }

    public boolean registrarPonto(String nomeJogador) {
        if (nomeJogador.equalsIgnoreCase(nomeJogador1)) {
            pontosJogador1++;
        } else {
            pontosJogador2++;
        }

        if (pontosJogador1 >= pontosPorSet) {
            setsJogador1++;
            resetarPontos();
        } else if (pontosJogador2 >= pontosPorSet) {
            setsJogador2++;
            resetarPontos();
        }

        return partidaFinalizada();
    }

    public boolean partidaFinalizada() {
        return setsJogador1 >= setsPorPartida || setsJogador2 >= setsPorPartida;
    }

    public String getNomeVencedor() {
        if (setsJogador1 >= setsPorPartida) {
            return nomeJogador1;
        }

        if (setsJogador2 >= setsPorPartida) {
            return nomeJogador2;
        }
        return null;
    }

    public boolean vencedorEhJogador1() {
        return setsJogador1 >= setsPorPartida;
    }

    private void resetarPontos() {
        pontosJogador1 = 0;
        pontosJogador2 = 0;
    }
}

