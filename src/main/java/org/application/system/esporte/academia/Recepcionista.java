package org.application.system.esporte.academia;

import java.time.LocalDate;

public class Recepcionista extends Funcionario {
    private String turno;
    private String ramal;

    public Recepcionista(String nome, String cpf, String telefone, String email, String endereco,
                         String registro, String cargo, double salario, LocalDate dataAdmissao,
                         String turno, String ramal) {
        super(nome, cpf, telefone, email, endereco, registro, cargo, salario, dataAdmissao);
        this.turno = turno;
        this.ramal = ramal;
    }

    public void realizarMatricula(Aluno aluno, Plano plano) {
        System.out.println("Recepcionista " + nome + " realizou a matrícula de " + aluno.getNome() + " no plano " + plano.getTipo());
    }

    public void registrarPagamentoRecepcionista(Pagamento pagamento) {
        System.out.println("Recepcionista " + nome + " registrou pagamento de R$" + pagamento.getValor());
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Turno: " + turno);
        System.out.println("Ramal: " + ramal);
    }
}
