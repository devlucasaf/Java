package application.system.colegio.escola;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa {
    private String              registroFuncional;
    private String              especializacao;
    private List<Disciplina>    disciplinasMinistradas;
    private double              salario;
    private boolean             ativo;

    public Professor(String nome, String cpf, LocalDate dataNascimento, String telefone, String email,
                        String registroFuncional, String especializacao, double salario) {
        super(nome, cpf, dataNascimento, telefone, email);
        this.registroFuncional = registroFuncional;
        this.especializacao = especializacao;
        this.salario = salario;
        this.ativo = true;
        this.disciplinasMinistradas = new ArrayList<>();
    }

    public void atribuirDisciplina(Disciplina disciplina) {
        if (!disciplinasMinistradas.contains(disciplina)) {
            disciplinasMinistradas.add(disciplina);
            disciplina.setProfessor(this);
            System.out.println("Professor " + nome + " atribuiu a disciplina " + disciplina.getNome());
        }
    }

    public void lancarNota(Aluno aluno, Disciplina disciplina, double nota) {
        if (disciplinasMinistradas.contains(disciplina)) {
            aluno.lancarNota(disciplina, nota);
            System.out.println("Nota lançada pelo professor " + nome);
        } else {
            System.out.println("Professor não pode lançar nota nesta disciplina.");
        }
    }

    public void darAula() {
        System.out.println("Professor " + nome + " está dando aula.");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- PROFESSOR ---");
        System.out.println("Nome: " + nome);
        System.out.println("Registro Funcional: " + registroFuncional);
        System.out.println("Especialização: " + especializacao);
        System.out.println("Salário: R$" + salario);
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
        System.out.println("Disciplinas: " + disciplinasMinistradas.size());

        for (Disciplina disciplina : disciplinasMinistradas) {
            System.out.println("  - " + disciplina.getNome());
        }
    }

    public String getRegistroFuncional() {
        return registroFuncional;
    }

    public List<Disciplina> getDisciplinasMinistradas() {
        return disciplinasMinistradas;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
