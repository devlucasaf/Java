package application.system.marketplace.model;

import application.system.marketplace.enums.Tipo;

public class Cupom {

    private final String    codigo;
    private final Tipo      tipo;
    private final double    valor;
    private final double    valorMinimoCompra;
    private boolean         usado;

    public Cupom(String codigo, Tipo tipo, double valor, double valorMinimoCompra) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.valor = valor;
        this.valorMinimoCompra = valorMinimoCompra;
        this.usado = false;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isValidoPara(double subtotal) {
        return !usado && subtotal >= valorMinimoCompra;
    }

    public double calcularDesconto(double subtotal) {
        if (!isValidoPara(subtotal)) {
            return 0;
        }

        if (tipo == Tipo.PERCENTUAL) {
            return subtotal * (valor / 100.0);
        }
        return Math.min(valor, subtotal);
    }

    public void marcarComoUsado() {
        this.usado = true;
    }
}
