package application.exercicios.faculdade.bsbcompute.config;

public class RequisicaoConfig {
    public int      id;
    public String   tipo;
    public Integer  prioridade;
    public Double   tempoExecucao;
    public Double   chegada;

    public RequisicaoConfig(int id, String tipo, Integer prioridade, Double tempoExecucao, Double chegada) {
        this.id = id;
        this.tipo = tipo;
        this.prioridade = prioridade;
        this.tempoExecucao = tempoExecucao;
        this.chegada = chegada;
    }
}
