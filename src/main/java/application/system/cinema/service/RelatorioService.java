package application.system.cinema.service;

import application.system.cinema.enums.StatusSessao;
import application.system.cinema.model.*;

import java.util.Comparator;

public class RelatorioService {
    private final Cinema cinema;

    public RelatorioService(Cinema cinema) {
        this.cinema = cinema;
    }

    public void exibirProgramacao() {
        System.out.println("\n=== PROGRAMAÇÃO DO CINEMA ===");
        for (Sessao s : cinema.getSessoes()) {
            if (s.getStatus() == StatusSessao.DISPONIVEL || s.getStatus() == StatusSessao.ESGOTADO) {
                s.exibirInformacoes();
                System.out.println("------------------");
            }
        }
    }

    public void exibirFilmesEmCartaz() {
        System.out.println("\n=== FILMES EM CARTAZ ===");
        for (Filme f : cinema.getFilmes()) {
            if (f.isAtivo()) {
                f.exibirInformacoes();
                System.out.println("------------------");
            }
        }
    }

    public void exibirSessoesPorFilme(String tituloFilme) {
        System.out.println("\n=== SESSÕES DO FILME: " + tituloFilme + " ===");
        for (Sessao s : cinema.getSessoes()) {
            if (s.getFilme().getTitulo().equalsIgnoreCase(tituloFilme) && s.getStatus() != StatusSessao.CANCELADO) {
                s.exibirInformacoes();
                System.out.println("------------------");
            }
        }
    }

    public void exibirClientesFieis() {
        System.out.println("\n=== CLIENTES COM MAIS PONTOS ===");
        cinema.getClientes().stream()
                .sorted(Comparator.comparingInt(Cliente::getPontosFidelidade).reversed())
                .limit(5)
                .forEach(c -> System.out.println(c.getNome() + " - Pontos: " + c.getPontosFidelidade()));
    }

    public void exibirRelatorioFinanceiro() {
        double totalVendas = cinema.getCompras().stream().mapToDouble(Compra::getValorTotal).sum();
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        System.out.println("Caixa atual: R$" + cinema.getCaixa());
        System.out.println("Total vendido (histórico): R$" + totalVendas);
        System.out.println("Total de compras realizadas: " + cinema.getCompras().size());
    }
}
