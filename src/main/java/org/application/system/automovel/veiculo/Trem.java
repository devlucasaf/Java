package org.application.system.automovel.veiculo;

class Trem extends VeiculoAutoMovel {
    private int     quantidadeVagoes;
    private int     capacidadePorVagao;
    private int     quantidadePassageiros;
    private int     quantidadePassageirosAtuais;
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
        } else {
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
        } else {
            System.out.println("Não há passageiros para descer.");
        }
    }

    public void abrirPortas() {
        if (getVelocidade() == 0 && naEstacao) {
            portasAbertas = true;
            System.out.println("Portas abertas.");
        } else {
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
