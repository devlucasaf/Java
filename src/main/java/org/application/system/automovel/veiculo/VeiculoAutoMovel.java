package org.application.system.automovel.veiculo;

abstract class VeiculoAutoMovel {
    private static int  contadorVeiculos = 0;
    private int         numeroVeiculo;

    private String      marca;
    private String      modelo;
    private double      preco;
    private double      km;
    private int         anoLancamento;
    private int         velocidade;

    public VeiculoAutoMovel(String marca, String modelo, double preco,
                            double km, int anoLancamento, int velocidade) {
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        this.km = km;
        this.anoLancamento = anoLancamento;
        this.velocidade = velocidade;

        contadorVeiculos++;
        this.numeroVeiculo = contadorVeiculos;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getPreco() {
        return preco;
    }

    public double getKm() {
        return km;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void acelerar(int incremento) {
        if (incremento > 0) {
            velocidade += incremento;
        }
    }

    public void frear(int reducao) {
        if (reducao > 0 && velocidade - reducao >= 0) {
            velocidade -= reducao;
        }
    }

    public void mostrarDados() {
        String cor = CoresHexadecimaisTerminal.corHexadecimal(
                (numeroVeiculo * 40) % 255,
                (numeroVeiculo * 80) % 255,
                (numeroVeiculo * 120) % 255
        );
        System.out.println(
                cor + ">>>>>>>>>> VEÍCULO " + numeroVeiculo + " (" +
                        getClass().getSimpleName() + " )" + " <<<<<<<<<<\n" + CoresHexadecimaisTerminal.RESET
        );
        System.out.println("Marca: " +  getMarca());
        System.out.println("Modelo: " + getModelo());
        System.out.println("Preço: R$" + getPreco());
        System.out.println("Quilometragem: " + getKm() +" kmh");
        System.out.println("Ano de lançamento: " + getAnoLancamento());
        System.out.println("Velocidade: " + getVelocidade());
    }
}
