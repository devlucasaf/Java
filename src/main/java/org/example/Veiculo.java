package org.example;

class VeiculoAutoMovel {
    private String marca;
    private String modelo;
    private double preco;
    private double km;
    private int anoLancamento;
    private int velocidade;

    public VeiculoAutoMovel(String marca, String modelo, double preco,
                            double km, int anoLancamento, int velocidade) {
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        this.km = km;
        this.anoLancamento = anoLancamento;
        this.velocidade = velocidade;
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
        System.out.println("Marca: " +  getMarca());
        System.out.println("Modelo: " + getModelo());
        System.out.println("Preço: R$" + getPreco());
        System.out.println("Quilometragem: " + getKm() +" kmh/h");
        System.out.println("Ano de lançamento: " + getAnoLancamento());
        System.out.println("Velocidade: " + getVelocidade());
    }
}

class Carro extends VeiculoAutoMovel {
    private int quantidadePortas;
    private int quantidadeMarchas;
    private int quantidadePassageiros;
    private int passageirosAtuais;
    private String tipoCambio;
    private String tipoCombustivel;
    private double capacidadePortaMalas;
    private boolean pilotoAutomatico;


    public Carro(String _nome, String _modelo, double _preco, double _km, int _anoLancamento, int _velocidade) {
        super(_nome, _modelo, _preco, _km, _anoLancamento, _velocidade);
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

    public Onibus(String _nome, String _modelo, double _preco, double _km, int _anoLancamento, int _velocidade) {
        super(_nome, _modelo, _preco, _km, _anoLancamento, _velocidade);
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

}

class Moto extends VeiculoAutoMovel {
    private int cilindradas;
    private int quantidadeMarchas;
    private int capacidadeTanque;
    private String tipoMoto;

    public Moto(String _nome, String _modelo, double _preco, double _km, int _anoLancamento, int _velocidade) {
        super(_nome, _modelo, _preco, _km, _anoLancamento, _velocidade);
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
}

class Aviao extends VeiculoAutoMovel {
    private int capacidadePassageiros;
    private int quantidadePassageirosAtuais;
    private int quantidadeTripulantes;
    private int quantidadeAssentos;
    private double altitude;
    private boolean arCondicionado;
    private boolean comida;
    private boolean tremPousoAbaixado;
    private boolean pilotoAutomatico;
    private boolean wifi;
    private boolean classeExecutiva;
    private String tipoAviao;

    public Aviao(String _nome, String _modelo, double _preco, double _km, int _anoLancamento, int _velocidade) {
        super(_nome, _modelo, _preco, _km, _anoLancamento, _velocidade);
        this.altitude = 0;
        this.quantidadePassageirosAtuais = 0;
        this.tremPousoAbaixado = true;
    }
    
    
}

class Trem extends VeiculoAutoMovel {
    public Trem(String _nome, String _modelo, double _preco, double _km, int _anoLancamento, int _velocidade) {
        super(_nome, _modelo, _preco, _km, _anoLancamento, _velocidade);
    }
}

public class Veiculo {

}

