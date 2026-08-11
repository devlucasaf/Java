package games.plataforma.minigames.jogos.adivinhanumero.model;

import games.plataforma.minigames.util.GeradorAleatorio;

public class AdivinhaNumero {
    private int     numeroSecreto;
    private int     tentativas;
    private int     minimo;
    private int     maximo;
    private boolean finalizado;

    public AdivinhaNumero() {
        this(1, 100);
    }

    public AdivinhaNumero(int min, int max) {
        minimo = min;
        maximo = max;
        reiniciar();
    }

    public void reiniciar() {
        numeroSecreto = GeradorAleatorio.nextInt(minimo, maximo + 1);
        tentativas = 0;
        finalizado = false;
    }

    public int tentar(int palpite) {
        if (finalizado) {
            return 0;
        }

        tentativas++;
        if (palpite == numeroSecreto) {
            finalizado = true;
            return 0;
        } else if (palpite < numeroSecreto) {
            return -1;
        } else {
            return 1;
        }
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public int getTentativas() {
        return tentativas;
    }

    public int getNumeroSecreto() {
        return numeroSecreto;
    }

    public int getMinimo() {
        return minimo;
    }

    public int getMaximo() {
        return maximo;
    }
}
