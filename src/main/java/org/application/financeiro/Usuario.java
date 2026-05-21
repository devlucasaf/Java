package org.application.financeiro;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Pessoa {
    private String          senha;
    private List<Conta>     contas;
    private List<Categoria> categoriasPersonalizadas;

    public Usuario(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email);
        this.senha = senha;
        this.contas = new ArrayList<>();
        this.categoriasPersonalizadas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
        System.out.println("Conta " + conta.getNome() + " adicionada para " + nome);
    }

    public void adicionarCategoriaPersonalizada(Categoria categoria) {
        categoriasPersonalizadas.add(categoria);
        System.out.println("Categoria " + categoria.getNome() + " criada.");
    }

    public double calcularPatrimonioLiquido() {
        double total = 0;
        for (Conta c : contas) {
            total += c.getSaldo();
        }
        return total;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- USUÁRIO ---");
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Email: " + email);
        System.out.println("Contas: " + contas.size());
        System.out.println("Patrimônio líquido: R$" + calcularPatrimonioLiquido());
    }

    public List<Conta> getContas() {
        return contas;
    }

    public List<Categoria> getCategoriasPersonalizadas() {
        return categoriasPersonalizadas;
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }
}
