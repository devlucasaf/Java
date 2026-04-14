package org.application.sistemaelevador;

// Classe que representa um Passageiro
class Passageiro {
    private int andarOrigem;    // Andar de origem
    private int andarDestino;   // Andar de destino
    private boolean noElevador; // Estado: dentro ou fora do elevador

    public Passageiro(int andarOrigem, int andarDestino) {
        this.andarOrigem = andarOrigem;
        this.andarDestino = andarDestino;
        this.noElevador = false;
    }

    // Getters e Setters
    public int getAndarOrigem() {
        return andarOrigem;
    }

    public int getAndarDestino() {
        return andarDestino;
    }

    public boolean isNoElevador() {
        return noElevador;
    }

    public void setNoElevador(boolean noElevador) {
        this.noElevador = noElevador;
    }

    @Override
    public String toString() {
        String estado = noElevador ? "🟢" : "⚪"; // Ícone para indicar se está dentro ou fora
        return estado + "👤(" + andarOrigem + "->" + andarDestino + ")";
    }
}
