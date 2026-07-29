package application.exercicios.faculdade.pacman;

public final class EstadoAgente {

    private final Posicao   posicao;
    private final Direcao   direcao;
    private final int       tempoAssustado;

    public EstadoAgente(Posicao posicao, Direcao direcao, int tempoAssustado) {
        this.posicao = posicao;
        this.direcao = direcao;
        this.tempoAssustado = tempoAssustado;
    }

    public EstadoAgente(Posicao posicao, Direcao direcao) {
        this(posicao, direcao, 0);
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public Direcao getDirecao() {
        return direcao;
    }

    public int getTempoAssustado() {
        return tempoAssustado;
    }

    public boolean estaAssustado() {
        return tempoAssustado > 0;
    }

    public EstadoAgente comPosicao(Posicao novaPosicao, Direcao novaDirecao) {
        return new EstadoAgente(novaPosicao, novaDirecao, tempoAssustado);
    }

    public EstadoAgente comTempoAssustado(int novoTempo) {
        return new EstadoAgente(posicao, direcao, novoTempo);
    }
}

