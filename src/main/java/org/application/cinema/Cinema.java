package org.application.cinema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cinema {
    private String              nome;
    private String              cnpj;
    private String              endereco;
    private List<Filme>         filmes;
    private List<Sala>          salas;
    private List<Sessao>        sessoes;
    private List<Cliente>       clientes;
    private List<Funcionario>   funcionarios;
    private List<Compra>        compras;
    private double              caixa;

    public Cinema(String nome, String cnpj, String endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.filmes = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.sessoes = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.caixa = 0.0;
    }

    public void adicionarFilme(Filme filme) {
        filmes.add(filme);
    }

    public void adicionarSala(Sala sala) {
        salas.add(sala);
    }

    public void adicionarSessao(Sessao sessao) {
        sessoes.add(sessao);
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void contratarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public Compra iniciarCompra(Cliente cliente, FormaPagamento formaPagamento) {
        Compra compra = new Compra(cliente, formaPagamento);
        compras.add(compra);
        return compra;
    }

    public boolean adicionarIngressoCompra(Compra compra, int idSessao, String codigoAssento, TipoIngresso tipo) {
        Sessao sessao = buscarSessaoPorId(idSessao);
        if (sessao == null || sessao.getStatus() != StatusSessao.DISPONIVEL) {
            return false;
        }

        Sala sala = sessao.getSala();
        Assento assento = sala.buscarAssento(codigoAssento);

        if (assento == null || !assento.isDisponivel()) {
            return false;
        }

        Cliente cliente = compra.getCliente();
        Ingresso ingresso = new Ingresso(sessao, assento, cliente, tipo);
        compra.adicionarIngresso(ingresso);
        return true;
    }

    public boolean finalizarCompra(Compra compra) {
        if (compra.finalizar()) {
            caixa += compra.getValorTotal();
            return true;
        }
        return false;
    }

    public void exibirProgramacao() {
        System.out.println("\n=== PROGRAMAÇÃO DO CINEMA ===");
        for (Sessao s : sessoes) {
            if (s.getStatus() == StatusSessao.DISPONIVEL || s.getStatus() == StatusSessao.ESGOTADO) {
                s.exibirInformacoes();
                System.out.println("------------------");
            }
        }
    }

    public void exibirFilmesEmCartaz() {
        System.out.println("\n=== FILMES EM CARTAZ ===");
        for (Filme f : filmes) {
            if (f.isAtivo()) {
                f.exibirInformacoes();
                System.out.println("------------------");
            }
        }
    }

    public void exibirSessoesPorFilme(String tituloFilme) {
        System.out.println("\n=== SESSÕES DO FILME: " + tituloFilme + " ===");
        for (Sessao s : sessoes) {
            if (s.getFilme().getTitulo().equalsIgnoreCase(tituloFilme) && s.getStatus() != StatusSessao.CANCELADO) {
                s.exibirInformacoes();
                System.out.println("------------------");
            }
        }
    }

    public void exibirClientesFieis() {
        System.out.println("\n=== CLIENTES COM MAIS PONTOS ===");
        clientes.stream()
                .sorted((c1, c2) -> Integer.compare(c2.getPontosFidelidade(), c1.getPontosFidelidade()))
                .limit(5)
                .forEach(c -> System.out.println(c.getNome() + " - Pontos: " + c.getPontosFidelidade()));
    }

    public void exibirRelatorioFinanceiro() {
        double totalVendas = compras.stream().mapToDouble(Compra::getValorTotal).sum();
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        System.out.println("Caixa atual: R$" + caixa);
        System.out.println("Total vendido (histórico): R$" + totalVendas);
        System.out.println("Total de compras realizadas: " + compras.size());
    }

    private Sessao buscarSessaoPorId(int id) {
        return sessoes.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }
}
