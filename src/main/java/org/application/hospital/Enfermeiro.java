package org.application.hospital;

import java.time.LocalDate;

public class Enfermeiro extends Funcionario {
    private String  coren;
    private String  setor;
    private Turno   turno;

    public Enfermeiro(String nome, String cpf, LocalDate dataNascimento, String telefone, String endereco,
                      String matricula, double salarioBase, LocalDate dataAdmissao, String cargo,
                      String coren, String setor, Turno turno) {
        super(nome, cpf, dataNascimento, telefone, endereco, matricula, salarioBase, dataAdmissao, cargo);
        this.coren = coren;
        this.setor = setor;
        this.turno = turno;
    }

    @Override
    public double calcularSalario() {
        double adicionalNoturno = (turno == Turno.NOITE) ? salarioBase * 0.15 : 0;
        return salarioBase + adicionalNoturno;
    }

    public void administrarMedicamento(Paciente paciente, String medicamento) {
        System.out.println("Enfermeiro " + nome + " administrou " + medicamento + " para " + paciente.getNome());
    }

    public void verificarSinaisVitais(Paciente paciente) {
        System.out.println("Enfermeiro " + nome + " verificou os sinais vitais de " + paciente.getNome());
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("COREN: " + coren);
        System.out.println("Setor: " + setor);
        System.out.println("Turno: " + turno);
    }

    public String getSetor() {
        return setor;
    }
}
