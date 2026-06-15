package org.application.system.cinema;

public class Assento {
    private char    fileira;
    private int     numero;
    private boolean disponivel;

    public Assento(char fileira, int numero) {
        this.fileira = fileira;
        this.numero = numero;
        this.disponivel = true;
    }

    public void ocupar() {
        disponivel = false;
    }

    public void liberar() {
        disponivel = true;
    }

    public String getCodigo() {
        return fileira + String.valueOf(numero);
    }

    public char getFileira() {
        return fileira;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}