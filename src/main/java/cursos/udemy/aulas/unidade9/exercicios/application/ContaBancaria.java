package cursos.udemy.aulas.unidade9.exercicios.application;

public class ContaBancaria {

    private final int   numeroConta;
    private String      nomeTitular;
    private double      saldo;

    // --- CONSTRUTOR PARA CONTA SEM DEPOSITO INICIAL ---
    public ContaBancaria(int numeroConta, String nomeTitular) {
        this.numeroConta = numeroConta;
        this.nomeTitular = nomeTitular;
    }

    // --- CONSTRUTOR PARA CONTA COM DEPOSITO INICIAL ---
    public ContaBancaria(int numeroConta, String nomeTitular, double depositoInicial) {
        this.numeroConta = numeroConta;
        this.nomeTitular = nomeTitular;
        depositar(depositoInicial);
    }

    // --- RETORNA O NUMERO DA CONTA ---
    public int getNumeroConta() {
        return numeroConta;
    }

    // --- RETORNA O NOME DO TITULAR ---
    public String getNomeTitular() {
        return nomeTitular;
    }

    // --- ALTERA O NOME DO TITULAR ---
    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    // --- RETORNA O SALDO DA CONTA ---
    public double getSaldo() {
        return saldo;
    }

    // --- REALIZA UM DEPOSITO NA CONTA ---
    public void depositar(double valor) {
        saldo += valor;
    }

    // --- REALIZA UM SAQUE E DESCONTA A TAXA BANCARIA ---
    public void sacar(double valor) {
        saldo -= valor + 5.00;
    }

    // --- RETORNA OS DADOS FORMATADOS DA CONTA ---
    @Override
    public String toString() {
        return String.format("Conta %d, Titular: %s, Saldo: R$ %.2f", numeroConta, nomeTitular, saldo);
    }
}
