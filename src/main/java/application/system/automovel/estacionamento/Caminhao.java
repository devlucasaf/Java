package application.system.automovel.estacionamento;

public class Caminhao extends Veiculo {
    public Caminhao(String placa) {
        super(placa);
    }

    @Override
    public double getValorHora() {
        return 10.0;
    }

    @Override
    public String getTipo() {
        return "Caminhão";
    }
}
