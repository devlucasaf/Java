package org.application.veiculo;

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
