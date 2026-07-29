package application.simuladores.ia;

public class AnaliseValores {

    private final String rotulo;
    private final String tipo;
    private final double valor;

    public AnaliseValores(String rotulo, double valor, String tipo) {
        this.rotulo = rotulo;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getRotulo() {
        return rotulo;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }
}