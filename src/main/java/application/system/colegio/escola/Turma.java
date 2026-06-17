package application.system.colegio.escola;

import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String              codigo;
    private Turno               turno;
    private NivelEnsino         nivel;
    private List<Aluno>         alunos;
    private List<Disciplina>    disciplinas;
    private int                 anoLetivo;

    public Turma(String codigo, Turno turno, NivelEnsino nivel, int anoLetivo) {
        this.codigo = codigo;
        this.turno = turno;
        this.nivel = nivel;
        this.anoLetivo = anoLetivo;
        this.alunos = new ArrayList<>();
        this.disciplinas = new ArrayList<>();
    }

    public void adicionarAluno(Aluno aluno) {
        if (!alunos.contains(aluno)) {
            alunos.add(aluno);
            System.out.println("Aluno " + aluno.getNome() + " adicionado à turma " + codigo);
        }
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        if (!disciplinas.contains(disciplina)) {
            disciplinas.add(disciplina);
            System.out.println("Disciplina " + disciplina.getNome() + " adicionada à turma " + codigo);
        }
    }

    public void listarAlunos() {
        System.out.println("\nTurma " + codigo + " - Turno: " + turno);
        for (Aluno a : alunos) {
            System.out.println("- " + a.getNome() + " (" + a.getMatricula() + ")");
        }
    }

    public void exibirHorario() {
        System.out.println("Horário da turma " + codigo + ": Turno " + turno);
        System.out.println("Disciplinas: ");
        for (Disciplina d : disciplinas) {
            System.out.println("  * " + d.getNome() + " - " + d.getCargaHoraria() + "h");
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public Turno getTurno() {
        return turno;
    }

    public NivelEnsino getNivel() {
        return nivel;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
}
