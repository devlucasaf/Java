package org.application.petshop;

import java.util.ArrayList;
import java.util.List;

public class ClientePetshop extends PessoaPetshop {
    private String          codigoCliente;
    private List<Animal>    animais;
    private double          saldoFidelidade;
    private boolean         ativo;

    public ClientePetshop(String nome, String cpf, String telefone, String email, String endereco, String codigoCliente) {
        super(nome, cpf, telefone, email, endereco);
        this.codigoCliente = codigoCliente;
        this.animais = new ArrayList<>();
        this.saldoFidelidade = 0.0;
        this.ativo = true;
    }

    public void adicionarAnimal(Animal animal) {
        animais.add(animal);
        System.out.println("Animal " + animal.getNome() + " adicionado ao cliente " + nome);
    }

    public void removerAnimal(Animal animal) {
        animais.remove(animal);
        System.out.println("Animal " + animal.getNome() + " removido.");
    }

    public void adicionarPontosFidelidade(double valorGasto) {
        double pontos = valorGasto * 0.05; // 5% de cashback
        saldoFidelidade += pontos;
        System.out.println("Cliente " + nome + " ganhou R$" + pontos + " de saldo fidelidade.");
    }

    public void usarSaldoFidelidade(double valor) {
        if (valor <= saldoFidelidade) {
            saldoFidelidade -= valor;
            System.out.println("Cliente " + nome + " utilizou R$" + valor + " do saldo fidelidade.");
        } else {
            System.out.println("Saldo insuficiente. Saldo atual: R$" + saldoFidelidade);
        }
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- CLIENTE ---");
        System.out.println("Nome: " + nome);
        System.out.println("Código: " + codigoCliente);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
        System.out.println("Animais: " + animais.size());
        System.out.println("Saldo fidelidade: R$" + saldoFidelidade);
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public List<Animal> getAnimais() {
        return animais;
    }

    public double getSaldoFidelidade() {
        return saldoFidelidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}