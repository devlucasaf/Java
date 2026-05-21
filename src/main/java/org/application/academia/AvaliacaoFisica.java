package org.application.academia;

import java.time.LocalDate;

public class AvaliacaoFisica {
    private static int contadorId = 1;
    private int         id;
    private Aluno       aluno;
    private LocalDate   data;
    private double      peso;
    private double      altura;
    private double      imc;
    private double      percentualGordura;
    private double      circunferenciaCintura;
    private String      observacoes;

    public AvaliacaoFisica(Aluno aluno, LocalDate data, double peso, double altura,
                           double percentualGordura, double circunferenciaCintura, String observacoes) {
        this.id = contadorId++;
        this.aluno = aluno;
        this.data = data;
        this.peso = peso;
        this.altura = altura;
        this.percentualGordura = percentualGordura;
        this.circunferenciaCintura = circunferenciaCintura;
        this.observacoes = observacoes;
        calcularIMC();
    }

    private void calcularIMC() {
        if (altura > 0) {
            this.imc = peso / (altura * altura);
        }
    }

    public String getClassificacaoIMC() {
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else if (imc < 30) {
            return "Sobrepeso";
        } else if (imc < 35) {
            return "Obesidade grau I";
        } else if (imc < 40) {
            return "Obesidade grau II";
        } else {
            return "Obesidade grau III";
        }
    }


    public void exibirResumo() {
        System.out.println("Avaliação " + id + " - " + data);
        System.out.printf("Peso: %.1fkg | Altura: %.2fm | IMC: %.1f (%s)\n", peso, altura, imc, getClassificacaoIMC());
        System.out.printf("Gordura: %.1f%% | Cintura: %.1fcm\n", percentualGordura, circunferenciaCintura);
        if (observacoes != null && !observacoes.isEmpty()) {
            System.out.println("Obs: " + observacoes);
        }
    }

    public Aluno getAluno() {
        return aluno;
    }

    public LocalDate getData() {
        return data;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }

    public double getImc() {
        return imc;
    }
}
