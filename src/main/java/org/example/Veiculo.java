package org.example;

// Código desenvolvido por Lucas Freitas a fim de estudos
// Herança em Java - Classe Veículo
// Código iniciado em 06/01/2026 - 08/01/2026

class CoresHexadecimaisTerminal {
    public static final String RESET = "\033[0m";

    public static String corHexadecimal(int r, int g, int b) {
        return "\033[38;2;" + r + ";" + g + ";" + b + "m";
    }
    public static final String WHITE = corHexadecimal(255, 250, 250); // 1
    public static final String LEMON_CHIFFON = corHexadecimal(255, 250, 205); // 2
    public static final String ROYAL_BLUE = corHexadecimal(65, 105, 225); // 3
    public static final String AQUA_MARINE = corHexadecimal(127, 255, 212); // 4
    public static final String GOLD = corHexadecimal(255, 215, 0); // 5
    public static final String FOREST_GREEN = corHexadecimal(34, 139, 34); // 6
    public static final String SPRING_GREEN = corHexadecimal(0, 255, 127); // 7
    public static final String CYAN = corHexadecimal(0, 255, 255); // 8
    public static final String BEIGE = corHexadecimal(245, 245, 220); // 9
    public static final String FIRE_BRICK = corHexadecimal(178, 34, 34); // 10
    public static final String RED = corHexadecimal(255, 0, 0); // 11
    public static final String DARK_VIOLET = corHexadecimal(148, 0, 211); // 12
    public static final String DEEP_SKY_BLUE = corHexadecimal(0, 191, 255); // 13
    public static final String DODGER_BLUE = corHexadecimal(24, 116, 205); // 14
    public static final String SLATE_BLUE = corHexadecimal(71, 60, 139); // 15
    public static final String TOMATO = corHexadecimal(255, 99, 71); // 12
}

abstract class VeiculoAutoMovel {
    private static int contadorVeiculos = 0;
    private int numeroVeiculo;

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

class Moto extends VeiculoAutoMovel {
    private int cilindradas;
    private int quantidadeMarchas;
    private int capacidadeTanque;
    private String tipoMoto;

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

class Aviao extends VeiculoAutoMovel {
    private int capacidadePassageiros;
    private int quantidadePassageirosAtuais;
    private int quantidadeTripulantes;
    private double altitude;
    private boolean comida;
    private boolean tremPousoAbaixado;
    private boolean pilotoAutomatico;
    private boolean wifi;
    private boolean classeExecutiva;
    private String tipoAviao;

    public Aviao(String _marca, String _modelo, double _preco, double _km, int _anoLancamento, int _velocidade,
                 int capacidadePassageiros, int quantidadePassageirosAtuais, int quantidadeTripulantes,
                 double altitude, boolean comida, boolean tremPousoAbaixado, boolean pilotoAutomatico,
                 boolean wifi, boolean classeExecutiva, String tipoAviao) {
        super(_marca, _modelo, _preco, _km, _anoLancamento, _velocidade);
        this.capacidadePassageiros = capacidadePassageiros;
        this.quantidadePassageirosAtuais = 0;
        this.quantidadeTripulantes = quantidadeTripulantes;
        this.altitude = 0;
        this.comida = false;
        this.tremPousoAbaixado = true;
        this.pilotoAutomatico = pilotoAutomatico;
        this.wifi = wifi;
        this.classeExecutiva = classeExecutiva;
        this.tipoAviao = tipoAviao;
    }

    public void setCapacidadePassageiros(int capacidadePassageiros) {
        if (capacidadePassageiros > 0) {
            this.capacidadePassageiros = capacidadePassageiros;
        }
    }

    public void embarcarPassageiros() {
        if (quantidadePassageirosAtuais < capacidadePassageiros) {
            quantidadePassageirosAtuais++;
        }
        else {
            System.out.println("Avião lotado!");
        }
    }

    public void setQuantidadeTripulantes(int quantidadeTripulantes) {
        if (quantidadeTripulantes > 0) {
            this.quantidadeTripulantes = quantidadeTripulantes;
        }
    }

    public void setTipoAviao(String tipoAviao) {
        this.tipoAviao = tipoAviao;
    }

    public void decolar() {
        if (getVelocidade() >= 250) {
            altitude = 1000;
            tremPousoAbaixado = false;
            System.out.println("Avião decolou!");
        }
        else {
            System.out.println("Velocidade insuficiente para iniciar a decolagem!");
        }
    }

    public void subirAviao(double metros) {
        if (altitude > 0) {
            altitude += metros;
        }
    }

    public void setServirComida() {
        if (altitude >= 10000) {
            this.comida = true;
            System.out.println("Serviço de bordo iniciado!");
        }
        else {
            System.out.println("Altitude insuficiente para iniciar o serviço de bordo!");
        }
    }

    public void desligarTremPouso() {
        if (altitude >= 100) {
            this.tremPousoAbaixado = false;
            System.out.println("Avião decolando! Trem de pouso desligado!");
        }
        else {
            System.out.println("Avião em solo! Trem de pouso ligado!");
        }
    }

    public void setPilotoAutomatico(boolean pilotoAutomatico) {
        if (altitude > 10000) {
            this.pilotoAutomatico = true;
            System.out.println("Piloto automático ligado!");
        }
        else {
            System.out.println("Altitude insuficiente para ligar o piloto automático!");
        }
    }

    public void desligarPilotoAutomatico() {
        pilotoAutomatico = false;
        System.out.println("Piloto automático desligado!");
    }

    public void conectarInternet() {
        if (altitude >= 5000) {
            wifi = true;
            System.out.println("Wi-fi ligado!");
        }
        else {
            System.out.println("Wi-fi não conectado! Só será ativado durante o voo!");
        }
    }

    public void desligarWifi() {
        wifi = false;
        System.out.println("Wi-fi desligado!");
    }

    public void configurarClasseExecutiva(boolean possuiClasseExecutiva) {
        this.classeExecutiva = possuiClasseExecutiva;

        if (classeExecutiva) {
            System.out.println("Avião com classe executiva.");
        }
        else {
            System.out.println("Avião sem classe executiva.");
        }
    }

    @Override
    public void mostrarDados() {
        super.mostrarDados();

        System.out.println("Tipo de avião: " + tipoAviao);
        System.out.println("Capacidade de passageiros: " + capacidadePassageiros);
        System.out.println("Passageiros atuais: " + quantidadePassageirosAtuais);
        System.out.println("Quantidade de tripulantes: " + quantidadeTripulantes);
        System.out.println("Altitude atual: " + altitude + " m");
        System.out.println("Trem de pouso abaixado: " + (tremPousoAbaixado ? "Sim" : "Não"));
        System.out.println("Piloto automático: " + (pilotoAutomatico ? "Ligado" : "Desligado"));
        System.out.println("Serviço de bordo: " + (comida ? "Ativo" : "Inativo"));
        System.out.println("Wi-Fi: " + (wifi ? "Ligado" : "Desligado"));
        System.out.println("Classe executiva: " + (classeExecutiva ? "Sim" : "Não"));
    }

}

class Trem extends VeiculoAutoMovel {
    private int quantidadeVagoes;
    private int capacidadePorVagao;
    private int quantidadePassageiros;
    private int quantidadePassageirosAtuais;
    private boolean portasAbertas;
    private boolean naEstacao;

    public Trem(String _marca, String _modelo, double _preco, double _km, int _anoLancamento, int _velocidade,
                int _quantidadeVagoes, int _capacidadePorVagao, int _quantidadePassageiros, int _quantidadePassageirosAtuais,
                boolean _portaAbertas, boolean _naEstacao) {
        super(_marca, _modelo, _preco, _km, _anoLancamento, _velocidade);
        this.quantidadeVagoes = _quantidadeVagoes;
        this.capacidadePorVagao = _capacidadePorVagao;
        this.quantidadePassageiros = _quantidadePassageiros;
        this.quantidadePassageirosAtuais = 0;
        this.portasAbertas = true;
        this.naEstacao = true;
    }

    public void setQuantidadeVagoes(int quantidadeVagoes) {
        if (quantidadeVagoes > 0) {
            this.quantidadeVagoes = quantidadeVagoes;
        }
    }

    public void setQuantidadePassageiros(int quantidadePassageiros) {
        if (quantidadePassageiros > 0) {
            this.quantidadePassageiros = quantidadePassageiros;
        }
    }

    public void embarcarPassageiros() {
        if (quantidadePassageirosAtuais < quantidadePassageiros) {
            quantidadePassageirosAtuais++;
            System.out.println("Passageiros embarcando!");
        }
        else {
            System.out.println("Trem lotado!");
        }
    }

    public void desembarcarPassageiros() {
        if (getVelocidade() > 0) {
            System.out.println("O trem está em movimento!");
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

    public void abrirPortas() {
        if (getVelocidade() == 0 && naEstacao) {
            portasAbertas = true;
            System.out.println("Portas abertas.");
        }
        else {
            System.out.println("O trem precisa estar parado na estação.");
        }
    }

    public void chegarEstacao() {
        if (getVelocidade() == 0) {
            naEstacao = true;
            System.out.println("Trem chegou à estação!");
        }
    }

    @Override
    public void mostrarDados() {
        super.mostrarDados();
        System.out.println("Quantidade de vagões: " + quantidadeVagoes);
        System.out.println("Passageiros atuais: " + quantidadePassageirosAtuais);
        System.out.println("Na estação: " + naEstacao);
        System.out.println("Portas abertas: " + portasAbertas);
    }

}

public class Veiculo {
    public static void main(String[] args) {
        Carro carro1 = new Carro("Honda",
                "Civic Hybrid 2025",
                265.900,
                0,
                2025,
                180,
                4,
                5,
                3,
                3,
                "Automático/Elétrico",
                "Aditivada",
                495,
                false
        );

        Carro carro2 = new Carro("Porsche",
                "Panamera Turbo S E-Hybrid",
                991.755,
                259456,
                2022,
                315,
                4,
                8,
                5,
                2,
                "Automático",
                "Comum",
                425,
                true
        );

        Carro carro3 = new Carro(
                "Renault",
                "Kwid Desgrama Outsider",
                47.520,
                20.547,
                2019,
                2,
                4,
                5,
                5,
                8,
                "Manual",
                "Comum",
                290,
                false
        );

        Onibus onibus1 = new Onibus
                ("Mercedes-Benz",
                        "OF 1519",
                        165.000,
                        0,
                        2014,
                        80,
                        3,
                        32,
                        85,
                        70,
                        5,
                        true,
                        false,
                        true,
                        false
                );

        Onibus onibus2 = new Onibus(
                "BYD",
                "BYD D9W",
                2800000,
                0,
                2025,
                70,
                2,
                40,
                100,
                140,
                6,
                false,
                true,
                false,
                true
        );

        Onibus onibus3 = new Onibus(
                "Volkswagen",
                "Volksbus 17.230 OD",
                300.000,
                80000,
                2016,
                95,
                3,
                45,
                120,
                10,
                5,
                true,
                false,
                true,
                false
        );

        Moto moto1 = new Moto(
                "Kawasaki",
                "Ninja ZX-10R",
                115000.00,
                0.0,
                2024,
                299,
                5,
                8,
                450,
                "Esportiva"
        );

        Moto moto2 = new Moto(
                "Yamaha",
                "MT-07",
                47500.00,
                0.0,
                2024,
                230,
                2,
                6,
                250,
                "Urbano"
        );

        Moto moto3 = new Moto(
                "BMW",
                "R 1250 GS",
                125000.00,
                5500.0,
                2024,
                200,
                4,
                6,
                320,
                "Trabalho"
        );

        Aviao aviao1 = new Aviao(
                "Embraer",
                "Phenom 300",
                50000000.0,
                500.0,
                2023,
                839,
                10,
                8,
                2,
                9500.0,
                false,
                false,
                true,
                true,
                false,
                "Jato"
        );

        Aviao aviao2 = new Aviao(
                "Cessna",
                "172 Skyhawk",
                2500000.0,
                1500.0,
                2022,
                226,
                3,
                2,
                1,
                15000.0,
                false,
                true,
                false,
                false,
                false,
                "Monomotor"
        );

        Aviao aviao3 = new Aviao(
                "LATAM Airlines Group",
                "A320",
                600000000.0,
                2500.0,
                2021,
                876,
                220,
                210,
                2,
                0,
                true,
                true,
                false,
                true,
                false,
                "Passageiro bimotor de fuselagem estreita"
        );

        Trem trem1 = new Trem("Alstom",
                "TGV Euroduplex",
                180000000.0,
                12000.0,
                2022,
                320,
                10,
                70,
                80,
                80,
                false,
                false
        );

        Trem trem2 = new Trem(
                "Siemens",
                "Velaro",
                150000000.0,
                8500.0,
                2021,
                330,
                8,
                70,
                80,
                50,
                true,
                false
        );

        Trem trem3 = new Trem(
                "Bombardier",
                "Zefiro 380",
                140000000.0,
                5000.0,
                2023,
                380,
                15,
                90,
                150,
                200,
                false,
                true
        );

        VeiculoAutoMovel[] veiculos = {
                carro1, carro2, carro3,
                onibus1, onibus2, onibus3,
                moto1, moto2, moto3,
                aviao1, aviao2, aviao3,
                trem1, trem2, trem3
        };

        for (VeiculoAutoMovel v : veiculos) {
            v.mostrarDados();
            System.out.println("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
        }
    }
}
