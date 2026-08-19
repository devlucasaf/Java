package application.system.cinema.model;

import java.util.ArrayList;
import java.util.List;

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

    // Métodos simples de adição (delegação)
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

    public void adicionarCompra(Compra compra) {
        compras.add(compra);
    }

    public void adicionarCaixa(double valor) {
        this.caixa += valor;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public double getCaixa() {
        return caixa;
    }
}
