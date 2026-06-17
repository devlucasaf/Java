package application.system.colegio.escola;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aluno extends Pessoa {
    private String matricula;
    private LocalDate dataMatricula;
    private StatusAluno status;
    private Turma turma;
    private Map<Disciplina, List<Double>> notas;
    private double frequenciaPercentual;

    public Aluno(String nome, String cpf, LocalDate dataNascimento, String telefone, String email,
                 String matricula, LocalDate dataMatricula) {
        super(nome, cpf, dataNascimento, telefone, email);
        this.matricula = matricula;
        this.dataMatricula = dataMatricula;
        this.status = StatusAluno.MATRICULADO;
        this.notas = new HashMap<>();
        this.frequenciaPercentual = 0.0;
    }

    public void matricularEmTurma(Turma turma) {
        this.turma = turma;
        turma.adicionarAluno(this);
        System.out.println("Aluno " + nome + " matriculado na turma " + turma.getCodigo());
    }

    public void lancarNota(Disciplina disciplina, double nota) {
        if (!notas.containsKey(disciplina)) {
            notas.put(disciplina, new ArrayList<>());
        }
        notas.get(disciplina).add(nota);
        System.out.println("Nota " + nota + " lançada para " + nome + " em " + disciplina.getNome());
    }

    public double calcularMedia(Disciplina disciplina) {
        List<Double> listaNotas = notas.get(disciplina);
        if (listaNotas == null || listaNotas.isEmpty()) {
            return 0.0;
        }

        double soma = 0;

        for (double n : listaNotas) {
            soma += n;
        }
        return soma / listaNotas.size();
    }

    public boolean verificarAprovacao(Disciplina disciplina, double mediaMinima) {
        double media = calcularMedia(disciplina);
        boolean aprovado = media >= mediaMinima && frequenciaPercentual >= 75.0;
        System.out.println("Disciplina " + disciplina.getNome() + ": Média = " + media +
                ", Frequência = " + frequenciaPercentual + "% -> " + (aprovado ? "APROVADO" : "REPROVADO"));
        return aprovado;
    }

    public void exibirBoletim() {
        System.out.println("\n--- BOLETIM DO ALUNO: " + nome + " (" + matricula + ") ---");
        for (Disciplina disciplina : notas.keySet()) {
            System.out.println("Disciplina: " + disciplina.getNome());
            System.out.println("Notas: " + notas.get(disciplina));
            System.out.println("Média: " + calcularMedia(disciplina));
        }
        System.out.println("Frequência: " + frequenciaPercentual + "%");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- ALUNO ---");
        System.out.println("Nome: " + nome);
        System.out.println("Matrícula: " + matricula);
        System.out.println("Data Matrícula: " + dataMatricula);
        System.out.println("Status: " + status);
        System.out.println("Idade: " + calcularIdade() + " anos");

        if (turma != null) {
            System.out.println("Turma: " + turma.getCodigo());
        }
    }

    public String getMatricula() {
        return matricula;
    }

    public StatusAluno getStatus() {
        return status;
    }

    public void setStatus(StatusAluno status) {
        this.status = status;
    }

    public Turma getTurma() {
        return turma;
    }

    public double getFrequenciaPercentual() {
        return frequenciaPercentual;
    }

    public void setFrequenciaPercentual(double frequenciaPercentual) {
        this.frequenciaPercentual = frequenciaPercentual;
    }

    public Map<Disciplina, List<Double>> getNotas() {
        return notas;
    }
}
