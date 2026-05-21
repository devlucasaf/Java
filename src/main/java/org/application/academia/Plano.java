package org.application.academia;

import java.util.ArrayList;
import java.util.List;

public class Plano {
    private static int contadorId = 1;
    private int             id;
    private TipoPlano       tipo;
    private double          valorMensal;
    private int             duracaoMeses;
    private List<String>    beneficios;

    public Plano(TipoPlano tipo, double valorMensal, int duracaoMeses) {
        this.id = contadorId++;
        this.tipo = tipo;
        this.valorMensal = valorMensal;
        this.duracaoMeses = duracaoMeses;
        this.beneficios = new ArrayList<>();
    }

    public void adicionarBeneficio(String beneficio) {
        beneficios.add(beneficio);
    }

    public double calcularValorTotal() {
        return valorMensal * duracaoMeses;
    }

    public void exibirInformacoes() {
        System.out.println("--- PLANO ---");
        System.out.println("Tipo: " + tipo);
        System.out.println("Valor mensal: R$" + valorMensal);
        System.out.println("Duração: " + duracaoMeses + " meses");
        System.out.println("Valor total: R$" + calcularValorTotal());
        System.out.println("Benefícios: " + (beneficios.isEmpty() ? "Nenhum" : String.join(", ", beneficios)));
    }

    public TipoPlano getTipo() {
        return tipo;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public int getDuracaoMeses() {
        return duracaoMeses;
    }
}