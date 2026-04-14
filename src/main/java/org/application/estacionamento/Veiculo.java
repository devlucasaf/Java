package org.application.estacionamento;

import java.time.LocalDateTime;

public abstract class Veiculo {
    private String        placa;
    private LocalDateTime horaEntrada;

    public Veiculo(String placa) {
        this.placa = placa.toUpperCase();
        this.horaEntrada = LocalDateTime.now();
    }

    public String getPlaca() {
        return placa;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public abstract double getValorHora();
    public abstract String getTipo();

    @Override
    public String toString() {
        return String.format("Tipo: %-10s | Placa: %-8s | Entrada: %s",
                getTipo(), placa, Utils.formatarData(horaEntrada));
    }
}
