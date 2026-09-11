package math.numerico.raizes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultadoRaiz {

    private final MetodoNumerico    metodo;
    private final double            raizAproximada;
    private final double            valorFuncaoNaRaiz;
    private final int               quantidadeIteracoes;
    private final SituacaoResultado situacao;
    private final String            mensagem;
    private final long              tempoExecucaoNanos;
    private final List<Iteracao>    historico;

    public ResultadoRaiz(MetodoNumerico metodo, double raizAproximada, double valorFuncaoNaRaiz, SituacaoResultado situacao, String mensagem, long tempoExecucaoNanos, List<Iteracao> historico) {
        if (metodo == null) {
            throw new IllegalArgumentException("O método numérico não pode ser nulo.");
        }

        if (situacao == null) {
            throw new IllegalArgumentException("A situação do resultado não pode ser nula.");
        }

        this.metodo = metodo;
        this.raizAproximada = raizAproximada;
        this.valorFuncaoNaRaiz = valorFuncaoNaRaiz;
        this.situacao = situacao;
        this.mensagem = mensagem == null ? "" : mensagem;
        this.tempoExecucaoNanos = tempoExecucaoNanos;
        this.historico = historico == null ? new ArrayList<>() : new ArrayList<>(historico);
        this.quantidadeIteracoes = this.historico.size();
    }

    // --- VERIFICA SE O MÉTODO CONVERGIU ---
    public boolean isConvergiu() {
        return situacao == SituacaoResultado.CONVERGIU;
    }

    // --- RETORNA O ÚLTIMO ERRO ABSOLUTO CALCULADO ---
    public Double getErroAbsolutoFinal() {
        if (historico.isEmpty()) {
            return null;
        }

        return historico.get(historico.size() - 1).getErroAbsoluto();
    }

    // --- RETORNA O ÚLTIMO ERRO RELATIVO CALCULADO ---
    public Double getErroRelativoFinal() {
        if (historico.isEmpty()) {
            return null;
        }

        return historico.get(historico.size() - 1).getErroRelativo();
    }

    // --- RETORNA O ÚLTIMO ERRO VERDADEIRO CALCULADO ---
    public Double getErroVerdadeiroFinal() {
        if (historico.isEmpty()) {
            return null;
        }

        return historico.get(historico.size() - 1).getErroVerdadeiro();
    }

    public MetodoNumerico getMetodo() {
        return metodo;
    }

    public double getRaizAproximada() {
        return raizAproximada;
    }

    public double getValorFuncaoNaRaiz() {
        return valorFuncaoNaRaiz;
    }

    public int getQuantidadeIteracoes() {
        return quantidadeIteracoes;
    }

    public SituacaoResultado getSituacao() {
        return situacao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public long getTempoExecucaoNanos() {
        return tempoExecucaoNanos;
    }

    public double getTempoExecucaoMilissegundos() {
        return tempoExecucaoNanos / 1_000_000.0;
    }

    public List<Iteracao> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}

