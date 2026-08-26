package application.simuladores.trafego;

public class Semaforo {
    private Estado      estado;
    private final int   duracaoVerde;
    private final int   duracaoAmarelo;
    private final int   duracaoVermelho;
    private int         contador;

    public Semaforo(int duracaoVerde, int duracaoAmarelo, int duracaoVermelho, Estado estadoInicial) {
        this.duracaoVerde = duracaoVerde;
        this.duracaoAmarelo = duracaoAmarelo;
        this.duracaoVermelho = duracaoVermelho;
        this.estado = estadoInicial;
        this.contador = 0;
    }

    public void atualizar() {
        contador++;
        switch (estado) {
            case VERDE:
                if (contador >= duracaoVerde) {
                    estado = Estado.AMARELO;
                    contador = 0;
                }
                break;
            case AMARELO:
                if (contador >= duracaoAmarelo) {
                    estado = Estado.VERMELHO;
                    contador = 0;
                }
                break;
            case VERMELHO:
                if (contador >= duracaoVermelho) {
                    estado = Estado.VERDE;
                    contador = 0;
                }
                break;
        }
    }

    public boolean podePassar() {
        return estado == Estado.VERDE;
    }

    public Estado getEstado() {
        return estado;
    }
}
