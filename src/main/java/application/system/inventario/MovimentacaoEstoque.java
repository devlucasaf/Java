package application.system.inventario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MovimentacaoEstoque {

    private final long              identificador;
    private final Produto           produto;
    private final TipoMovimentacao  tipo;
    private final int               quantidade;
    private final int               quantidadeAnterior;
    private final int               quantidadePosterior;
    private final BigDecimal        custoUnitario;
    private final LocalDateTime     dataMovimentacao;
    private final String            responsavel;
    private final String            observacao;

    public MovimentacaoEstoque(long identificador, Produto produto, TipoMovimentacao tipo, int quantidade, int quantidadeAnterior, int quantidadePosterior, BigDecimal custoUnitario, String responsavel, String observacao) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }

        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }

        if (tipo == null) {
            throw new IllegalArgumentException("O tipo da movimentação não pode ser nulo.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        if (quantidadeAnterior < 0 || quantidadePosterior < 0) {
            throw new IllegalArgumentException("As quantidades do estoque não podem ser negativas.");
        }

        if (custoUnitario == null || custoUnitario.signum() < 0) {
            throw new IllegalArgumentException("O custo unitário não pode ser negativo.");
        }

        this.identificador = identificador;
        this.produto = produto;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.quantidadeAnterior = quantidadeAnterior;
        this.quantidadePosterior = quantidadePosterior;
        this.custoUnitario = custoUnitario.setScale(2, RoundingMode.HALF_UP);
        this.dataMovimentacao = LocalDateTime.now();
        this.responsavel = responsavel == null || responsavel.trim().isEmpty() ? "Não informado" : responsavel.trim();
        this.observacao = observacao == null ? "" : observacao.trim();
    }

    // --- CALCULA O VALOR TOTAL DA MOVIMENTAÇÃO ---
    public BigDecimal calcularValorTotal() {
        return custoUnitario.multiply(BigDecimal.valueOf(quantidade)).setScale(2, RoundingMode.HALF_UP);
    }

    // --- RETORNA A DATA FORMATADA ---
    public String getDataMovimentacaoFormatada() {
        return dataMovimentacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public long getIdentificador() {
        return identificador;
    }

    public Produto getProduto() {
        return produto;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getQuantidadeAnterior() {
        return quantidadeAnterior;
    }

    public int getQuantidadePosterior() {
        return quantidadePosterior;
    }

    public BigDecimal getCustoUnitario() {
        return custoUnitario;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public String getObservacao() {
        return observacao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof MovimentacaoEstoque outraMovimentacao)) {
            return false;
        }

        return identificador == outraMovimentacao.identificador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return identificador + " - " + tipo.getNomeFormatado() + " - " + produto.getNome() + " - quantidade: " + quantidade;
    }
}

