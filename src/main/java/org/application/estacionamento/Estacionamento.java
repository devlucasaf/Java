package org.application.estacionamento;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Estacionamento {
    private List<Veiculo> veiculosAtuais;
    private final int CAPACIDADE = 50;

    public Estacionamento() {
        this.veiculosAtuais = new ArrayList<>();
    }

    public boolean estacionar(Veiculo v) {
        if (veiculosAtuais.size() >= CAPACIDADE) {
            System.out.println("Erro: Estacionamento lotado!");
            return false;
        }

        if (buscarPorPlaca(v.getPlaca()).isPresent()) {
            System.out.println("Erro: Veículo com esta placa já está no pátio.");
            return false;
        }

        veiculosAtuais.add(v);
        return true;
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return veiculosAtuais.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst();
    }

    public double processarSaida(String placa) {
        Optional<Veiculo> vOpt = buscarPorPlaca(placa);
        if (vOpt.isEmpty()) {
            return -1;
        }

        Veiculo v = vOpt.get();
        LocalDateTime agora = LocalDateTime.now();

        // Cálculo de horas (arredondado para cima)
        long minutos = Duration.between(v.getHoraEntrada(), agora).toMinutes();
        long horasCobradas = (long) Math.ceil(minutos / 60.0);

        if (horasCobradas == 0) {
            horasCobradas = 1;
        }

        double valorTotal = horasCobradas * v.getValorHora();
        if (valorTotal < 5.0) {
            valorTotal = 5.0; // Valor mínimo
        }

        veiculosAtuais.remove(v);
        System.out.println("Tempo permanência: " + minutos + " minutos.");
        return valorTotal;
    }

    public List<Veiculo> getVeiculosAtuais() {
        return new ArrayList<>(veiculosAtuais);
    }
}
