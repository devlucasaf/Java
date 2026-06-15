package org.application.system.dinheiro.financeiro;

import java.time.YearMonth;

public class Orcamento {
    private static int contadorId = 1;
    private int             id;
    private Categoria       categoria;
    private double          limite;
    private Periodicidade   periodicidade;
    private YearMonth       referencia;
    private double          gastoAcumulado;

    public Orcamento(Categoria categoria, double limite, Periodicidade periodicidade, YearMonth referencia) {
        this.id = contadorId++;
        this.categoria = categoria;
        this.limite = limite;
        this.periodicidade = periodicidade;
        this.referencia = referencia;
        this.gastoAcumulado = 0.0;
    }

    public void adicionarGasto(double valor) {
        if (categoria.isDespesa()) {
            gastoAcumulado += valor;
        }
    }

    public boolean excedeuLimite() {
        return gastoAcumulado > limite;
    }

    public double getPercentualUtilizado() {
        return (gastoAcumulado / limite) * 100;
    }

    public void exibirStatus() {
        System.out.println("Orçamento para " + categoria.getNome() + " (" + periodicidade + "):");
        System.out.println("Limite: R$" + limite);
        System.out.println("Gasto atual: R$" + gastoAcumulado);
        System.out.printf("Percentual: %.2f%%\n", getPercentualUtilizado());

        if (excedeuLimite()) {
            System.out.println("ALERTA: Limite excedido em R$" + (gastoAcumulado - limite));
        }
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public YearMonth getReferencia() {
        return referencia;
    }
}
