package org.application.veiculo;

class Moto extends VeiculoAutoMovel {
    private int     cilindradas;
    private int     quantidadeMarchas;
    private int     capacidadeTanque;
    private String  tipoMoto;

    public Moto(String _marca, String _modelo, double _preco, double _km, int _anoLancamento, int _velocidade,
                int cilindradas, int quantidadeMarchas, int capacidadeTanque, String tipoMoto) {
        super(_marca, _modelo, _preco, _km, _anoLancamento, _velocidade);
        this.cilindradas = cilindradas;
        this.quantidadeMarchas = quantidadeMarchas;
        this.capacidadeTanque = capacidadeTanque;
        this.tipoMoto = tipoMoto;
    }

    public void setCilindradas(int cilindradas) {
        if (cilindradas > 0) {
            this.cilindradas = cilindradas;
        }
    }

    public void setQuantidadeMarchas(int quantidadeMarchas) {
        if (quantidadeMarchas > 0) {
            this.quantidadeMarchas = quantidadeMarchas;
        }
    }

    public void setCapacidadeTanque(int capacidadeTanque) {
        if (capacidadeTanque > 0) {
            this.capacidadeTanque = capacidadeTanque;
        }
    }

    public void setTipoMoto(String tipoMoto) {
        this.tipoMoto = tipoMoto;
    }

    @Override
    public void acelerar(int incremento) {
        if (incremento > 30) {
            System.out.println("Aceleração brusca para moto!");
            return;
        }
        super.acelerar(incremento);
    }

    @Override
    public void mostrarDados() {
        super.mostrarDados();
        System.out.println("Cilindradas: " + cilindradas + " cc");
        System.out.println("Marchas: " + quantidadeMarchas);
        System.out.println("Capacidade do tanque: " + capacidadeTanque + " L");
        System.out.println("Tipo de moto: " + tipoMoto);
    }

}
