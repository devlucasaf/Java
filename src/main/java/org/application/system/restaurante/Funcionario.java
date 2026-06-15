package org.application.system.restaurante;

/**
 * Classe abstrata que representa um funcionário do restaurante.
 */
public abstract class Funcionario {
    private String  nome;
    private String  idFuncionario;
    private Cargo   cargo;
    private double  salarioBase;
    private boolean ativo;

    public Funcionario(String nome, String idFuncionario, Cargo cargo, double salarioBase) {
        setNome(nome);
        setIdFuncionario(idFuncionario);
        setCargo(cargo);
        setSalarioBase(salarioBase);
        this.ativo = true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        if (idFuncionario == null || idFuncionario.isBlank()) {
            throw new IllegalArgumentException("O id do funcionário não pode ser vazio");
        }
        this.idFuncionario = idFuncionario;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        if (cargo == null) {
            throw new IllegalArgumentException("O cargo não pode ser nulo");
        }
        this.cargo = cargo;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("O salário não pode ser negativo");
        }
        this.salarioBase = salarioBase;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public abstract double calcularRemuneracaoTotal();

    @Override
    public String toString() {
        return String.format("%s [%s] - %s | Salário: R$%.2f", nome, idFuncionario, cargo.getDescricao(), salarioBase);
    }
}

