package application.system.restaurante.model;

public interface Promocional {
    String  getDescricaoPromocao();
    double  calcularDesconto();
    boolean isEmPromocao();
}

