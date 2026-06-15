package org.application.system.hospital;

import java.time.LocalDate;

public class Funcionario extends Pessoa {
    protected String    matricula;
    protected double    salarioBase;
    protected LocalDate dataAdmissao;
    protected String    cargo;
    protected boolean   ativo;

    public Funcionario(String nome, String cpf, LocalDate dataNascimento, String telefone, String endereco,
                       String matricula, double salarioBase, LocalDate dataAdmissao, String cargo) {
        super(nome, cpf, dataNascimento, telefone, endereco);
        this.matricula = matricula;
        this.salarioBase = salarioBase;
        this.dataAdmissao = dataAdmissao;
        this.cargo = cargo;
        this.ativo = true;
    }

    public double calcularSalario() {
        return salarioBase;
    }

    public void aplicarAumento(double percentual) {
        if (percentual > 0) {
            salarioBase += salarioBase * percentual / 100;
            System.out.println("Aumento aplicado. Novo salário: R$" + salarioBase);
        }
    }

    public void demitir() {
        this.ativo = false;
        System.out.println("Funcionário " + nome + " foi demitido.");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- Funcionário ---");
        System.out.println("Nome: " + nome);
        System.out.println("Matrícula: " + matricula);
        System.out.println("Cargo: " + cargo);
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
        System.out.println("Salário Base: R$" + salarioBase);
    }

    public String getMatricula() {
        return matricula;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getCargo() {
        return cargo;
    }

}
