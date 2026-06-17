package application.system.automovel.estacionamento;

public class Moto extends Veiculo {
    public Moto(String placa) {
        super(placa);
    }

    @Override
    public double getValorHora() {
        return 3.0;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }
}
