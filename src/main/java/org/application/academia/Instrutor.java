package org.application.academia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Instrutor extends Funcionario {
    private String      especialidade;
    private List<Aluno> alunosAtendidos;
    private String      horarioTrabalho;

    public Instrutor(String nome, String cpf, String telefone, String email, String endereco,
                     String registro, String cargo, double salario, LocalDate dataAdmissao,
                     String especialidade, String horarioTrabalho) {
        super(nome, cpf, telefone, email, endereco, registro, cargo, salario, dataAdmissao);
        this.especialidade = especialidade;
        this.horarioTrabalho = horarioTrabalho;
        this.alunosAtendidos = new ArrayList<>();
    }

    public void atribuirTreino(Aluno aluno, Treino treino) {
        if (!alunosAtendidos.contains(aluno)) {
            alunosAtendidos.add(aluno);
            aluno.setInstrutorResponsavel(this);
        }
        aluno.atribuirTreino(treino);
        System.out.println("Instrutor " + nome + " atribuiu treino a " + aluno.getNome());
    }

    public void acompanharEvolucao(Aluno aluno) {
        System.out.println("Instrutor " + nome + " está acompanhando a evolução de " + aluno.getNome());
        aluno.exibirHistoricoAvaliacoes();
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Especialidade: " + especialidade);
        System.out.println("Horário de trabalho: " + horarioTrabalho);
        System.out.println("Alunos atendidos: " + alunosAtendidos.size());
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public List<Aluno> getAlunosAtendidos() {
        return alunosAtendidos;
    }
}
