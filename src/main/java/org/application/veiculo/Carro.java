package org.application.veiculo;

class Carro extends VeiculoAutoMovel {
    private int quantidadePortas;
    private int quantidadeMarchas;
    private int quantidadePassageiros;
    private int passageirosAtuais;
    private String tipoCambio;
    private String tipoCombustivel;
    private double capacidadePortaMalas;
    private boolean pilotoAutomatico;


    public Carro(String _marca, String _modelo, double _preco, double _km, int _anoLancamento, int _velocidade,
                 int quantidadePortas, int quantidadeMarchas, int quantidadePassageiros, int passageirosAtuais, String tipoCambio, String tipoCombustivel,
                 double capacidadePortaMalas, boolean pilotoAutomatico) {
        super(_marca, _modelo, _preco, _km, _anoLancamento, _velocidade);
        this.quantidadePortas = quantidadePortas;
        this.quantidadeMarchas = quantidadeMarchas;
        this.quantidadePassageiros = quantidadePassageiros;
        this.passageirosAtuais = passageirosAtuais;
        this.tipoCambio = tipoCambio;
        this.tipoCombustivel = tipoCombustivel;
        this.capacidadePortaMalas = capacidadePortaMalas;
        this.pilotoAutomatico =  pilotoAutomatico;
        this.passageirosAtuais = 0;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        if (quantidadePortas > 0) {
            this.quantidadePortas = quantidadePortas;
        }
    }

    public void setQuantidadeMarchas(int quantidadeMarchas) {
        if (quantidadeMarchas > 0) {
            this.quantidadeMarchas = quantidadeMarchas;
        }
    }

    public void setQuantidadePassageiros(int quantidadePassageiros) {
        if (quantidadePassageiros > 0) {
            this.quantidadePassageiros = quantidadePassageiros;
        }
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public void setCapacidadePortaMalas(double capacidadePortaMalas) {
        if (capacidadePortaMalas > 0) {
            this.capacidadePortaMalas = capacidadePortaMalas;
        }
    }

    public void setPilotoAutomatico(boolean pilotoAutomatico) {
        this.pilotoAutomatico = pilotoAutomatico;
    }

    public void entrarPassageiro() {
        if (passageirosAtuais < quantidadePassageiros) {
            passageirosAtuais++;
            System.out.println("Passageiro entrou! Quantidade de passageiros: " + passageirosAtuais);
        }

        else {
            System.out.println("Carro lotado!");
        }
    }

    public void sairPassageiro() {
        if (passageirosAtuais > 0) {
            passageirosAtuais--;
            System.out.println("Passageiro saiu! Quantidade de passageiros: " + passageirosAtuais);
        }

        else {
            System.out.println("Não há passageiros no carro!");
        }
    }

    public void ativarPilotoAutomatico() {

        if (!pilotoAutomatico) {
            System.out.println("Este carro não possui piloto automático.");
            return;
        }

        if (getVelocidade() < 40) {
            System.out.println("Velocidade mínima de 40km/h.");
            return;
        }

        System.out.println("Piloto automático ativado.");
    }

    @Override
    public void mostrarDados() {
        super.mostrarDados();
        System.out.println("Quantidade de portas: " + quantidadePortas);
        System.out.println("Tipo de câmbio: " + tipoCambio);
        System.out.println("Quantidade de marchas: " + quantidadeMarchas);
        System.out.println("Quantidade de passageiros permitido: " + quantidadePassageiros);
        System.out.println("Tipo de combustível: " + tipoCombustivel);
        System.out.println("Capacidade do porta malas: " + capacidadePortaMalas + " L");
        System.out.println("Tem piloto automático: " + pilotoAutomatico);
    }
}
