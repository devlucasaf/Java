package org.application.veiculo;

class Onibus extends VeiculoAutoMovel {
    private int quantidadePortas;
    private int quantidadeAssentos;
    private int capacidadePassageiros;
    private int quantidadePassageirosAtuais;
    private int quantidadeMarchas;
    private boolean portaAberta;
    private boolean arCondicionado;
    private boolean cobrador;
    private boolean tv;

    public Onibus(String _marca, String _modelo, double _preco, double _km, int _anoLancamento, int _velocidade,
                  int quantidadePortas, int quantidadeAssentos, int capacidadePassageiros, int quantidadePassageirosAtuais,
                  int quantidadeMarchas, boolean portaAberta, boolean arCondicionado, boolean cobrador, boolean tv) {
        super(_marca, _modelo, _preco, _km, _anoLancamento, _velocidade);
        this.quantidadePortas = quantidadePortas;
        this.quantidadeAssentos = quantidadeAssentos;
        this.capacidadePassageiros = capacidadePassageiros;
        this.quantidadePassageirosAtuais = quantidadePassageirosAtuais;
        this.quantidadeMarchas = quantidadeMarchas;
        this.portaAberta =  portaAberta;
        this.arCondicionado =  arCondicionado;
        this.cobrador =  cobrador;
        this.tv =  tv;
        this.quantidadePassageirosAtuais = 0;
        this.portaAberta = false;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        if (quantidadePortas > 0) {
            this.quantidadePortas = quantidadePortas;
        }
    }

    public void setQuantidadeAssentos(int quantidadeAssentos) {
        if (quantidadeAssentos > 0) {
            this.quantidadeAssentos = quantidadeAssentos;
        }
    }

    public void setCapacidadePassageiros(int capacidadePassageiros) {
        if (capacidadePassageiros > 0) {
            this.capacidadePassageiros = capacidadePassageiros;
        }
    }

    public void setQuantidadeMarchas(int quantidadeMarchas) {
        if (quantidadeMarchas > 0) {
            this.quantidadeMarchas = quantidadeMarchas;
        }
    }

    public void abrirPorta() {
        if (getVelocidade() == 0) {
            portaAberta = true;
            System.out.println("Portas abertas!");
        }

        else {
            System.out.println("Ônibus em movimento, não pode abrir as portas!");
        }
    }

    public void fecharPorta() {
        portaAberta = false;
        System.out.println("Portas fechadas!");
    }

    public void embarcarPassageiro() {
        if (quantidadePassageirosAtuais < capacidadePassageiros) {
            quantidadePassageirosAtuais++;
        }

        else {
            System.out.println("Ônibus lotado!");
        }
    }

    public void descerPassageiro() {
        if (getVelocidade() > 0) {
            System.out.println("O ônibus precisa estar parado para descer passageiros.");
            return;
        }

        if (quantidadePassageirosAtuais > 0) {
            quantidadePassageirosAtuais--;
            System.out.println("Passageiro desceu! Quantidade de passageiros: " + quantidadePassageirosAtuais);
        }

        else {
            System.out.println("Não há passageiros para descer.");
        }
    }

    @Override
    public void acelerar(int incremento) {
        if (portaAberta) {
            System.out.println("Portas abertas! Feche as portas para poder acelerar!");
            return;
        }
        super.acelerar(incremento);
    }

    @Override
    public void mostrarDados() {
        super.mostrarDados();

        System.out.println("Quantidade de portas: " + quantidadePortas);
        System.out.println("Quantidade de assentos: " + quantidadeAssentos);
        System.out.println("Capacidade de passageiros: " + capacidadePassageiros);
        System.out.println("Passageiros atuais: " + quantidadePassageirosAtuais);
        System.out.println("Ar-condicionado: " + (arCondicionado ? "Sim" : "Não"));
        System.out.println("Cobrador: " + (cobrador ? "Sim" : "Não"));
        System.out.println("TV: " + (tv ? "Sim" : "Não"));
    }

}
