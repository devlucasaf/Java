package org.application.system.colegio.escola;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String      nome;
    private String      codigo;
    private int         cargaHoraria;
    private Professor   professor;
    private List<Aluno> alunosMatriculados;
    private double      mediaMinimaAprovacao;

    public Disciplina(String nome, String codigo, int cargaHoraria, double mediaMinimaAprovacao) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.mediaMinimaAprovacao = mediaMinimaAprovacao;
        this.alunosMatriculados = new ArrayList<>();
    }

    public void matricularAluno(Aluno aluno) {
        if (!alunosMatriculados.contains(aluno)) {
            alunosMatriculados.add(aluno);
            System.out.println("Aluno " + aluno.getNome() + " matriculado na disciplina " + nome);
        }
    }

    public void exibirAlunos() {
        System.out.println("Alunos matriculados em " + nome + ":");
        for (Aluno a : alunosMatriculados) {
            System.out.println("- " + a.getNome() + " (" + a.getMatricula() + ")");
        }
    }

    public void exibirInformacoes() {
        System.out.println("--- DISCIPLINA ---");
        System.out.println("Nome: " + nome);
        System.out.println("Código: " + codigo);
        System.out.println("Carga Horária: " + cargaHoraria + "h");
        System.out.println("Média mínima: " + mediaMinimaAprovacao);
        System.out.println("Professor: " + (professor != null ? professor.getNome() : "Não atribuído"));
        System.out.println("Total alunos: " + alunosMatriculados.size());
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public double getMediaMinimaAprovacao() {
        return mediaMinimaAprovacao;
    }

    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }
}
