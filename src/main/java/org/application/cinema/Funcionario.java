package org.application.cinema;

import java.time.LocalDate;

public class Funcionario extends Pessoa {
    private String      matricula;
    private String      cargo;
    private double      salario;
    private LocalDate   dataAdmissao;
    private boolean     ativo;

    public Funcionario(String nome, String cpf, String telefone, String email,
                       String matricula, String cargo, double salario, LocalDate dataAdmissao) {
        super(nome, cpf, telefone, email);
        this.matricula = matricula;
        this.cargo = cargo;
        this.salario = salario;
        this.dataAdmissao = dataAdmissao;
        this.ativo = true;
    }

    public void demitir() {
        this.ativo = false;
        System.out.println("Funcionário " + nome + " foi demitido.");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- FUNCIONÁRIO ---");
        System.out.println("Nome: " + nome);
        System.out.println("Matrícula: " + matricula);
        System.out.println("Cargo: " + cargo);
        System.out.println("Salário: R$" + salario);
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }
}