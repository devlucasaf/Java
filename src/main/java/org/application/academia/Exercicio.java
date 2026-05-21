package org.application.academia;

public class Exercicio {
    private static int contadorId = 1;
    private int             id;
    private String          nome;
    private GrupoMuscular   grupoMuscular;
    private int             series;
    private int             repeticoes;
    private double          cargaSugerida;
    private String          descricao;

    public Exercicio(String nome, GrupoMuscular grupoMuscular, int series, int repeticoes,
                     double cargaSugerida, String descricao) {
        this.id = contadorId++;
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.series = series;
        this.repeticoes = repeticoes;
        this.cargaSugerida = cargaSugerida;
        this.descricao = descricao;
    }

    public void exibirInstrucoes() {
        System.out.println("Exercício: " + nome);
        System.out.println("Músculo alvo: " + grupoMuscular);
        System.out.println("Séries: " + series + " x " + repeticoes);
        System.out.println("Carga sugerida: " + cargaSugerida + " kg");
        System.out.println("Como fazer: " + descricao);
    }

    public String getNome() {
        return nome;
    }

    public GrupoMuscular getGrupoMuscular() {
        return grupoMuscular;
    }

    public int getSeries() {
        return series;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public double getCargaSugerida() {
        return cargaSugerida;
    }
}
