package games.plataforma.minigames.jogos.memoria.model;

import games.plataforma.minigames.util.GeradorAleatorio;

public class JogoDaMemoria {
    private int         pares;
    private String[]    cartas;
    private boolean[]   virada;
    private int         primeiraSelecionada;
    private int         segundaSelecionada;
    private boolean     aguardando;
    private int         tentativas;
    private int         paresEncontrados;

    public JogoDaMemoria() {
        this(6);
    }

    public JogoDaMemoria(int pares) {
        this.pares = pares;
        iniciar();
    }

    public void iniciar() {
        cartas = new String[pares * 2];
        for (int i = 0; i < pares; i++) {
            cartas[i] = String.valueOf(i);
            cartas[i + pares] = String.valueOf(i);
        }

        for (int i = 0; i < cartas.length; i++) {
            int j = GeradorAleatorio.nextInt(cartas.length);
            String temp = cartas[i];
            cartas[i] = cartas[j];
            cartas[j] = temp;
        }
        virada = new boolean[cartas.length];
        primeiraSelecionada = -1;
        segundaSelecionada = -1;
        aguardando = false;
        tentativas = 0;
        paresEncontrados = 0;
    }

    public boolean selecionarCarta(int indice) {
        if (indice < 0 || indice >= cartas.length) {
            return false;
        }

        if (virada[indice]) {
            return false;
        }

        if (aguardando) {
            return false;
        }

        if (primeiraSelecionada == -1) {
            primeiraSelecionada = indice;
            virada[indice] = true;
            return true;
        } else if (segundaSelecionada == -1 && indice != primeiraSelecionada) {
            segundaSelecionada = indice;
            virada[indice] = true;
            tentativas++;
            if (cartas[primeiraSelecionada].equals(cartas[segundaSelecionada])) {
                paresEncontrados++;
                primeiraSelecionada = -1;
                segundaSelecionada = -1;
                if (paresEncontrados == pares) {
                    // fim
                }
                return true;
            } else {
                aguardando = true;
                return true;
            }
        }
        return false;
    }

    public void resetarSelecao() {
        if (primeiraSelecionada != -1 && segundaSelecionada != -1) {
            virada[primeiraSelecionada] = false;
            virada[segundaSelecionada] = false;
            primeiraSelecionada = -1;
            segundaSelecionada = -1;
            aguardando = false;
        }
    }

    public boolean isFim() {
        return paresEncontrados == pares;
    }

    public String getCarta(int indice) {
        return cartas[indice];
    }

    public boolean isVirada(int indice) {
        return virada[indice];
    }

    public int getTentativas() {
        return tentativas;
    }

    public int getParesEncontrados() {
        return paresEncontrados;
    }

    public int getTotalPares() {
        return pares;
    }

    public boolean isAguardando() {
        return aguardando;
    }

    public int getPrimeira() {
        return primeiraSelecionada;
    }

    public int getSegunda() {
        return segundaSelecionada;
    }
}
