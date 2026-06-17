package application.system.esporte.academia;

import java.time.LocalDate;

public abstract class Funcionario extends Pessoa {
    protected String    registro;
    protected String    cargo;
    protected double    salario;
    protected LocalDate dataAdmissao;
    protected boolean   ativo;

    public Funcionario(String nome, String cpf, String telefone, String email, String endereco,
                       String registro, String cargo, double salario, LocalDate dataAdmissao) {
        super(nome, cpf, telefone, email, endereco);
        this.registro = registro;
        this.cargo = cargo;
        this.salario = salario;
        this.dataAdmissao = dataAdmissao;
        this.ativo = true;
    }

    public void demitir() {
        this.ativo = false;
        System.out.println("Funcionário " + nome + " foi demitido.");
    }

    public void aumentarSalario(double percentual) {
        if (percentual > 0) {
            salario += salario * percentual / 100;
            System.out.println("Salário de " + nome + " ajustado para R$" + salario);
        }
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- FUNCIONÁRIO ---");
        System.out.println("Nome: " + nome);
        System.out.println("Registro: " + registro);
        System.out.println("Cargo: " + cargo);
        System.out.println("Salário: R$" + salario);
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
    }

    public String getRegistro() {
        return registro;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }

    public boolean isAtivo() {
        return ativo;
    }
}