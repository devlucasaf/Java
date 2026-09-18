package application.system.inventario;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ItemCurvaABC {
    private final Produto           produto;
    private final BigDecimal        valorConsumo;
    private final double            percentualIndividual;
    private final double            percentualAcumulado;
    private final ClassificacaoABC  classificacao;

    public ItemCurvaABC(Produto produto, BigDecimal valorConsumo, double percentualIndividual, double percentualAcumulado, ClassificacaoABC classificacao) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }

        if (valorConsumo == null || valorConsumo.signum() < 0) {
            throw new IllegalArgumentException("O valor de consumo não pode ser negativo.");
        }

        if (!Double.isFinite(percentualIndividual) || percentualIndividual < 0) {
            throw new IllegalArgumentException("O percentual individual é inválido.");
        }

        if (!Double.isFinite(percentualAcumulado) || percentualAcumulado < 0) {
            throw new IllegalArgumentException("O percentual acumulado é inválido.");
        }

        if (classificacao == null) {
            throw new IllegalArgumentException("A classificação não pode ser nula.");
        }

        this.produto = produto;
        this.valorConsumo = valorConsumo.setScale(2, RoundingMode.HALF_UP);
        this.percentualIndividual = percentualIndividual;
        this.percentualAcumulado = percentualAcumulado;
        this.classificacao = classificacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public BigDecimal getValorConsumo() {
        return valorConsumo;
    }

    public double getPercentualIndividual() {
        return percentualIndividual;
    }

    public double getPercentualAcumulado() {
        return percentualAcumulado;
    }

    public ClassificacaoABC getClassificacao() {
        return classificacao;
    }

    @Override
    public String toString() {
        return produto.getCodigo() + " - " + produto.getNome() + " - classe " + classificacao + " - " + String.format("%.2f", percentualAcumulado) + "% acumulado";
    }
}

