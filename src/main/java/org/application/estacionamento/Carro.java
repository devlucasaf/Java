package org.application.estacionamento;

class Carro extends Veiculo {
    public Carro(String placa) {
        super(placa);
    }

    @Override
    public double getValorHora() {
        return 5.0;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }
}