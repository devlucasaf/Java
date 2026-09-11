package application.system.condominio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Multa {

    private final long          identificador;
    private final Unidade       unidade;
    private final Ocorrencia    ocorrencia;
    private final String        motivo;
    private final BigDecimal    valor;
    private final LocalDateTime dataAplicacao;
    private final LocalDate     dataVencimento;
    private boolean             paga;
    private boolean             cancelada;
    private LocalDateTime       dataPagamento;
    private LocalDateTime       dataCancelamento;
    private String              motivoCancelamento;

    public Multa(long identificador, Unidade unidade, Ocorrencia ocorrencia, String motivo, BigDecimal valor, LocalDate dataVencimento) {
        validarIdentificador(identificador);
        validarUnidade(unidade);
        validarOcorrencia(unidade, ocorrencia);
        validarMotivo(motivo);
        validarValor(valor);
        validarDataVencimento(dataVencimento);

        this.identificador = identificador;
        this.unidade = unidade;
        this.ocorrencia = ocorrencia;
        this.motivo = motivo.trim();
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
        this.dataAplicacao = LocalDateTime.now();
        this.dataVencimento = dataVencimento;
        this.paga = false;
        this.cancelada = false;
        this.dataPagamento = null;
        this.dataCancelamento = null;
        this.motivoCancelamento = "";
    }

    // --- REGISTRA O PAGAMENTO DA MULTA ---
    public void pagar() {
        if (cancelada) {
            throw new IllegalStateException("Uma multa cancelada não pode ser paga.");
        }

        if (paga) {
            throw new IllegalStateException("A multa já foi paga.");
        }

        paga = true;
        dataPagamento = LocalDateTime.now();
    }

    // --- CANCELA A MULTA SEM INFORMAR O MOTIVO ---
    public void cancelar() {
        cancelar("Motivo não informado.");
    }

    // --- CANCELA A MULTA E REGISTRA O MOTIVO ---
    public void cancelar(String motivoCancelamento) {
        if (paga) {
            throw new IllegalStateException("Uma multa paga não pode ser cancelada.");
        }

        if (cancelada) {
            throw new IllegalStateException("A multa já está cancelada.");
        }

        this.cancelada = true;
        this.dataCancelamento = LocalDateTime.now();
        this.motivoCancelamento = motivoCancelamento == null || motivoCancelamento.trim().isEmpty() ? "Motivo não informado." : motivoCancelamento.trim();
    }

    // --- VERIFICA SE A MULTA ESTÁ PENDENTE ---
    public boolean isPendente() {
        return !paga && !cancelada;
    }

    // --- VERIFICA SE A MULTA ESTÁ VENCIDA ---
    public boolean isVencida() {
        return isVencida(LocalDate.now());
    }

    // --- VERIFICA SE A MULTA ESTÁ VENCIDA NA DATA INFORMADA ---
    public boolean isVencida(LocalDate dataReferencia) {
        if (dataReferencia == null) {
            throw new IllegalArgumentException("A data de referência não pode ser nula.");
        }

        return isPendente() && dataReferencia.isAfter(dataVencimento);
    }

    // --- CALCULA A QUANTIDADE DE DIAS EM ATRASO ---
    public long calcularDiasAtraso() {
        return calcularDiasAtraso(LocalDate.now());
    }

    // --- CALCULA A QUANTIDADE DE DIAS EM ATRASO NA DATA INFORMADA ---
    public long calcularDiasAtraso(LocalDate dataReferencia) {
        if (dataReferencia == null) {
            throw new IllegalArgumentException("A data de referência não pode ser nula.");
        }

        if (!isVencida(dataReferencia)) {
            return 0;
        }

        return ChronoUnit.DAYS.between(dataVencimento, dataReferencia);
    }

    // --- CALCULA O VALOR ATUALIZADO DA MULTA ---
    public BigDecimal calcularValorAtualizado() {
        return calcularValorAtualizado(LocalDate.now());
    }

    // --- CALCULA O VALOR ATUALIZADO DA MULTA NA DATA INFORMADA ---
    public BigDecimal calcularValorAtualizado(LocalDate dataReferencia) {
        if (dataReferencia == null) {
            throw new IllegalArgumentException("A data de referência não pode ser nula.");
        }

        if (paga || cancelada || !isVencida(dataReferencia)) {
            return valor;
        }

        long diasAtraso = calcularDiasAtraso(dataReferencia);
        BigDecimal multaAtraso = valor.multiply(new BigDecimal("0.02"));
        BigDecimal jurosDiarios = valor.multiply(new BigDecimal("0.00033")).multiply(BigDecimal.valueOf(diasAtraso));

        return valor.add(multaAtraso).add(jurosDiarios).setScale(2, RoundingMode.HALF_UP);
    }

    // --- RETORNA A SITUAÇÃO FORMATADA DA MULTA ---
    public String getSituacaoFormatada() {
        if (cancelada) {
            return "Cancelada";
        }

        if (paga) {
            return "Paga";
        }

        if (isVencida()) {
            return "Vencida";
        }

        return "Pendente";
    }

    // --- RETORNA A DATA DE APLICAÇÃO FORMATADA ---
    public String getDataAplicacaoFormatada() {
        return formatarDataHora(dataAplicacao);
    }

    // --- RETORNA A DATA DE VENCIMENTO FORMATADA ---
    public String getDataVencimentoFormatada() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataVencimento.format(formatador);
    }

    // --- RETORNA A DATA DE PAGAMENTO FORMATADA ---
    public String getDataPagamentoFormatada() {
        return formatarDataHora(dataPagamento);
    }

    // --- RETORNA A DATA DE CANCELAMENTO FORMATADA ---
    public String getDataCancelamentoFormatada() {
        return formatarDataHora(dataCancelamento);
    }

    // --- FORMATA UMA DATA E HORA ---
    private String formatarDataHora(LocalDateTime data) {
        if (data == null) {
            return "Não informada";
        }

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return data.format(formatador);
    }

    // --- VALIDA O IDENTIFICADOR DA MULTA ---
    private void validarIdentificador(long identificador) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
    }

    // --- VALIDA A UNIDADE DA MULTA ---
    private void validarUnidade(Unidade unidade) {
        if (unidade == null) {
            throw new IllegalArgumentException("A unidade não pode ser nula.");
        }
    }

    // --- VALIDA A OCORRÊNCIA ASSOCIADA À MULTA ---
    private void validarOcorrencia(Unidade unidade, Ocorrencia ocorrencia) {
        if (ocorrencia != null && !unidade.equals(ocorrencia.getUnidade())) {
            throw new IllegalArgumentException("A ocorrência não pertence à unidade informada.");
        }
    }

    // --- VALIDA O MOTIVO DA MULTA ---
    private void validarMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O motivo não pode estar vazio.");
        }
    }

    // --- VALIDA O VALOR DA MULTA ---
    private void validarValor(BigDecimal valor) {
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("O valor da multa deve ser maior que zero.");
        }
    }

    // --- VALIDA A DATA DE VENCIMENTO DA MULTA ---
    private void validarDataVencimento(LocalDate dataVencimento) {
        if (dataVencimento == null) {
            throw new IllegalArgumentException("A data de vencimento não pode ser nula.");
        }

        if (dataVencimento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data de vencimento não pode estar no passado.");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public String getMotivo() {
        return motivo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataAplicacao() {
        return dataAplicacao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public boolean isPaga() {
        return paga;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Multa outraMulta)) {
            return false;
        }

        return identificador == outraMulta.identificador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return identificador + " - " + unidade.getIdentificacaoCompleta() + " - " + motivo + " - R$ " + valor + " - " + getSituacaoFormatada();
    }
}

