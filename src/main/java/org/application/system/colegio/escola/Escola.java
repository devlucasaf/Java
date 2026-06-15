package org.application.system.colegio.escola;

import java.util.ArrayList;
import java.util.List;

public class Escola {
    private String              nome;
    private String              cnpj;
    private String              endereco;
    private List<Aluno>         alunos;
    private List<Professor>     professores;
    private List<Funcionario>   funcionarios;
    private List<Turma>         turmas;
    private List<Disciplina>    disciplinas;

    public Escola(String nome, String cnpj, String endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.alunos = new ArrayList<>();
        this.professores = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.turmas = new ArrayList<>();
        this.disciplinas = new ArrayList<>();
    }

    public void matricularAluno(Aluno aluno, Turma turma) {
        if (!alunos.contains(aluno)) {
            alunos.add(aluno);
        }
        aluno.matricularEmTurma(turma);
    }

    public void contratarProfessor(Professor professor) {
        professores.add(professor);
        System.out.println("Professor " + professor.getNome() + " contratado.");
    }

    public void contratarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
        System.out.println("Funcionário " + funcionario.getNome() + " contratado.");
    }

    public void adicionarTurma(Turma turma) {
        turmas.add(turma);
        System.out.println("Turma " + turma.getCodigo() + " criada.");
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
        System.out.println("Disciplina " + disciplina.getNome() + " cadastrada.");
    }

    public void exibirRelatorioGeral() {
        System.out.println("\n========== RELATÓRIO ESCOLAR ==========");
        System.out.println("Escola: " + nome);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + endereco);
        System.out.println("Total de alunos: " + alunos.size());
        System.out.println("Total de professores: " + professores.size());
        System.out.println("Total de funcionários: " + funcionarios.size());
        System.out.println("Total de turmas: " + turmas.size());
        System.out.println("Total de disciplinas: " + disciplinas.size());
        System.out.println("=======================================\n");
    }

    public void listarTodosAlunos() {
        System.out.println("\n--- LISTA DE ALUNOS ---");
        for (Aluno a : alunos) {
            System.out.println(a.getNome() + " - Matrícula: " + a.getMatricula() + " - Status: " + a.getStatus());
        }
    }

    public String getNome() {
        return nome;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
}
