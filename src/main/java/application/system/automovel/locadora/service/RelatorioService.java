package application.system.automovel.locadora.service;

import application.system.automovel.locadora.enums.StatusLocacao;
import application.system.automovel.locadora.model.*;

import java.util.Comparator;

public class RelatorioService {
    private final Locadora locadora;

    public RelatorioService(Locadora locadora) {
        this.locadora = locadora;
    }

    public void exibirVeiculosDisponiveis() {
        System.out.println("\n=== VEÍCULOS DISPONÍVEIS ===");
        for (Veiculo v : locadora.getVeiculos()) {
            if (v.isDisponivel()) {
                v.exibirInformacoes();
                System.out.println("------------------");
            }
        }
    }

    public void exibirLocacoesAtivas() {
        System.out.println("\n=== LOCAÇÕES ATIVAS ===");
        for (Locacao l : locadora.getLocacoes()) {
            if (l.getStatus() == StatusLocacao.ATIVA) {
                l.exibirInformacoes();
                System.out.println("------------------");
            }
        }
    }

    public void exibirHistoricoLocacoes() {
        System.out.println("\n=== HISTÓRICO DE LOCAÇÕES ===");
        for (Locacao l : locadora.getLocacoes()) {
            l.exibirInformacoes();
            System.out.println("------------------");
        }
    }

    public void exibirRelatorioFinanceiro() {
        double totalFaturado = locadora.getLocacoes()
                .stream()
                .mapToDouble(Locacao::getValorTotal)
                .sum();
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        System.out.println("Caixa atual: R$" + locadora.getCaixa());
        System.out.println("Total faturado (histórico): R$" + totalFaturado);
        System.out.println("Total de locações realizadas: " + locadora.getLocacoes().size());
    }

    public void exibirClientesFieis() {
        System.out.println("\n=== CLIENTES COM MAIS PONTOS ===");
        locadora.getClientes().stream()
                .sorted(Comparator.comparingInt(Cliente::getPontosFidelidade).reversed())
                .limit(5)
                .forEach(c -> System.out.println(c.getNome() + " - Pontos: " + c.getPontosFidelidade()));
    }
}
