package application.system.condominio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Boleto {

    private final long          identificador;
    private final Unidade       unidade;
    private final String        descricao;
    private final BigDecimal    valorOriginal;
    private final LocalDate     dataEmissao;
    private final LocalDate     dataVencimento;
    private SituacaoBoleto      situacao;
    private LocalDateTime       dataPagamento;
    private BigDecimal          valorPago;

    public Boleto(long identificador, Unidade unidade, String descricao, BigDecimal valorOriginal, LocalDate dataVencimento) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }

        if (unidade == null) {
            throw new IllegalArgumentException("A unidade não pode ser nula.");
        }

        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode estar vazia.");
        }

        if (valorOriginal == null || valorOriginal.signum() <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }

        if (dataVencimento == null) {
            throw new IllegalArgumentException("A data de vencimento deve ser informada.");
        }

        this.identificador = identificador;
        this.unidade = unidade;
        this.descricao = descricao.trim();
        this.valorOriginal = valorOriginal.setScale(2, RoundingMode.HALF_UP);
        this.dataEmissao = LocalDate.now();
        this.dataVencimento = dataVencimento;
        this.situacao = SituacaoBoleto.PENDENTE;
    }

    // --- CALCULA O VALOR ATUALIZADO DO BOLETO ---
    public BigDecimal calcularValorAtualizado(LocalDate dataReferencia) {
        atualizarSituacao(dataReferencia);

        if (situacao != SituacaoBoleto.VENCIDO) {
            return valorOriginal;
        }

        long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(dataVencimento, dataReferencia);
        BigDecimal multa = valorOriginal.multiply(new BigDecimal("0.02"));
        BigDecimal jurosDiarios = valorOriginal.multiply(new BigDecimal("0.00033")).multiply(BigDecimal.valueOf(diasAtraso));
        return valorOriginal.add(multa).add(jurosDiarios).setScale(2, RoundingMode.HALF_UP);
    }

    // --- REGISTRA O PAGAMENTO DO BOLETO ---
    public void pagar(BigDecimal valorPago) {
        if (situacao == SituacaoBoleto.PAGO || situacao == SituacaoBoleto.CANCELADO) {
            throw new IllegalStateException("Este boleto não pode ser pago.");
        }

        BigDecimal valorDevido = calcularValorAtualizado(LocalDate.now());

        if (valorPago == null || valorPago.compareTo(valorDevido) < 0) {
            throw new IllegalArgumentException("O valor pago é menor que o valor devido de R$ " + valorDevido + ".");
        }

        this.valorPago = valorPago.setScale(2, RoundingMode.HALF_UP);
        this.dataPagamento = LocalDateTime.now();
        this.situacao = SituacaoBoleto.PAGO;
    }

    // --- ATUALIZA A SITUAÇÃO DO BOLETO ---
    public void atualizarSituacao(LocalDate dataReferencia) {
        if (situacao == SituacaoBoleto.PENDENTE && dataReferencia != null && dataReferencia.isAfter(dataVencimento)) {
            situacao = SituacaoBoleto.VENCIDO;
        }
    }

    // --- CANCELA O BOLETO ---
    public void cancelar() {
        if (situacao == SituacaoBoleto.PAGO) {
            throw new IllegalStateException("Um boleto pago não pode ser cancelado.");
        }

        situacao = SituacaoBoleto.CANCELADO;
    }

    public long getIdentificador() {
        return identificador;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValorOriginal() {
        return valorOriginal;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public SituacaoBoleto getSituacao() {
        atualizarSituacao(LocalDate.now());
        return situacao;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }
}

