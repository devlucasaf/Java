package application.exercicios.faculdade.bsbcompute.model;

public class Request {
    public int      id;
    public String   tipo;
    public int      prioridade;
    public double   tempoExecucao;
    public double   chegada;

    public Integer assignedWorker = null;
    public Double startTs = null;
    public Double endTs = null;

    public Request(int id, String tipo, int prioridade, double tempoExecucao, double chegada) {
        this.id = id;
        this.tipo = tipo;
        this.prioridade = prioridade;
        this.tempoExecucao = tempoExecucao;
        this.chegada = chegada;
    }
}
