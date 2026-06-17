package application.system.restaurante;

public interface Promocional {
    String  getDescricaoPromocao();
    double  calcularDesconto();
    boolean isEmPromocao();
}

