package org.application.system.esporte.academia;

import java.util.ArrayList;
import java.util.List;

public class Treino {
    private static int contadorId = 1;
    private int             id;
    private String          nome;
    private NivelTreino     nivel;
    private List<Exercicio> exercicios;
    private int             duracaoEstimada;

    public Treino(String nome, NivelTreino nivel, int duracaoEstimada) {
        this.id = contadorId++;
        this.nome = nome;
        this.nivel = nivel;
        this.duracaoEstimada = duracaoEstimada;
        this.exercicios = new ArrayList<>();
    }

    public void adicionarExercicio(Exercicio exercicio) {
        exercicios.add(exercicio);
        System.out.println("Exercício '" + exercicio.getNome() + "' adicionado ao treino " + nome);
    }

    public void exibirTreino() {
        System.out.println("\n--- TREINO: " + nome + " (" + nivel + ") ---");
        System.out.println("Duração estimada: " + duracaoEstimada + " min");
        System.out.println("Exercícios:");

        for (Exercicio e : exercicios) {
            System.out.println("  - " + e.getNome() + " (" + e.getGrupoMuscular() + "): " +
                    e.getSeries() + " séries x " + e.getRepeticoes() + " repetições");
        }
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public NivelTreino getNivel() {
        return nivel;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }
}
