package math.numerico.integracao;

public class ResultadoIntegracao {

    private final MetodoIntegracao  metodo;
    private final double            limiteInferior;
    private final double            limiteSuperior;
    private final int               subintervalos;
    private final double            valorIntegral;
    private final double            tempoExecucaoMilissegundos;

    public ResultadoIntegracao(MetodoIntegracao metodo, double limiteInferior, double limiteSuperior,
                               int subintervalos, double valorIntegral, double tempoExecucaoMilissegundos) {
        if (metodo == null) {
            throw new IllegalArgumentException("O método de integração não pode ser nulo.");
        }
        this.metodo = metodo;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.subintervalos = subintervalos;
        this.valorIntegral = valorIntegral;
        this.tempoExecucaoMilissegundos = tempoExecucaoMilissegundos;
    }

    public MetodoIntegracao getMetodo() {
        return metodo;
    }

    public double getLimiteInferior() {
        return limiteInferior;
    }

    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    public int getSubintervalos() {
        return subintervalos;
    }

    public double getValorIntegral() {
        return valorIntegral;
    }

    public double getTempoExecucaoMilissegundos() {
        return tempoExecucaoMilissegundos;
    }
}
