package application.exercicios.faculdade.bsbcompute.model;

public class EventMessage {
    public String   event;
    public int      workerId;
    public int      requestId;
    public int      prioridade;
    public String   tipo;
    public double   tempoExecucao;
    public double   effectiveTime;
    public double   arrivalTs;
    public double   startTs;
    public double   endTs;
    public int      active;

    public EventMessage(String event, int workerId, int requestId, int prioridade,
                        String tipo, double tempoExecucao, double effectiveTime,
                        double arrivalTs, double startTs, double endTs, int active) {
        this.event = event;
        this.workerId = workerId;
        this.requestId = requestId;
        this.prioridade = prioridade;
        this.tipo = tipo;
        this.tempoExecucao = tempoExecucao;
        this.effectiveTime = effectiveTime;
        this.arrivalTs = arrivalTs;
        this.startTs = startTs;
        this.endTs = endTs;
        this.active = active;
    }
}
