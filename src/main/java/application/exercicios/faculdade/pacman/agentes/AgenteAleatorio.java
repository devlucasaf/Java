package application.exercicios.faculdade.pacman.agentes;

import application.exercicios.faculdade.pacman.Direcao;
import application.exercicios.faculdade.pacman.EstadoJogo;

import java.util.List;
import java.util.Random;

public class AgenteAleatorio implements Agente {

    private final Random random;

    public AgenteAleatorio() {
        this(new Random());
    }

    public AgenteAleatorio(long semente) {
        this(new Random(semente));
    }

    public AgenteAleatorio(Random random) {
        this.random = random;
    }

    @Override
    public Direcao getAcao(EstadoJogo estado, int indice) {
        List<Direcao> acoes = estado.getAcoesLegais(indice);
        if (acoes.isEmpty()) {
            return Direcao.PARADO;
        }
        return acoes.get(random.nextInt(acoes.size()));
    }
}

