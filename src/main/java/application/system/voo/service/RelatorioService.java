package application.system.voo.service;

import application.system.voo.model.*;

import java.util.Comparator;

public class RelatorioService {
    private final SistemaVoo sistema;

    public RelatorioService(SistemaVoo sistema) {
        this.sistema = sistema;
    }

    public void exibirVoosDisponiveis() {
        System.out.println("\n=== VOOS DISPONÍVEIS ===");
        for (Voo v : sistema.getVoos()) {
            if (v.isAtivo() && v.temVagasDisponiveis()) {
                v.exibirInformacoes();
                System.out.println("------------------");
            }
        }
    }

    public void exibirTodasReservas() {
        System.out.println("\n=== TODAS AS RESERVAS ===");
        for (Reserva r : sistema.getReservas()) {
            r.exibirInformacoes();
            System.out.println("------------------");
        }
    }

    public void exibirReservasPorPassageiro(String cpf) {
        System.out.println("\n=== RESERVAS DO PASSAGEIRO " + cpf + " ===");
        Passageiro p = sistema.buscarPassageiroPorCpf(cpf);
        if (p == null) {
            System.out.println("Passageiro não encontrado.");
            return;
        }

        for (Reserva r : p.getReservas()) {
            r.exibirInformacoes();
            System.out.println("------------------");
        }
    }

    public void exibirRelatorioFinanceiro() {
        double totalFaturado = sistema.getReservas().stream()
                .mapToDouble(Reserva::getPrecoPago)
                .sum();
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        System.out.println("Caixa atual: R$" + sistema.getCaixa());
        System.out.println("Total faturado (histórico): R$" + totalFaturado);
        System.out.println("Total de reservas realizadas: " + sistema.getReservas().size());
    }

    public void exibirPassageirosFieis() {
        System.out.println("\n=== PASSAGEIROS COM MAIS PONTOS ===");
        sistema.getPassageiros().stream()
                .sorted(Comparator.comparingInt(Passageiro::getPontosFidelidade).reversed())
                .limit(5)
                .forEach(p -> System.out.println(p.getNome() + " - Pontos: " + p.getPontosFidelidade()));
    }
}
