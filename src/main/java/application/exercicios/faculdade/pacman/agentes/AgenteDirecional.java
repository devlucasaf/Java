package application.exercicios.faculdade.pacman.agentes;

import application.exercicios.faculdade.pacman.Direcao;
import application.exercicios.faculdade.pacman.EstadoAgente;
import application.exercicios.faculdade.pacman.EstadoJogo;
import application.exercicios.faculdade.pacman.Posicao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AgenteDirecional implements Agente {

    private final double probabilidadeFantasmaPerseguir;
    private final Random random;

    public AgenteDirecional() {
        this(0.8, new Random());
    }

    public AgenteDirecional(double probabilidadeFantasmaPerseguir, Random random) {
        this.probabilidadeFantasmaPerseguir = probabilidadeFantasmaPerseguir;
        this.random = random;
    }

    @Override
    public Direcao getAcao(EstadoJogo estado, int indice) {
        List<Direcao> acoes = estado.getAcoesLegais(indice);
        if (acoes.isEmpty()) {
            return Direcao.PARADO;
        }

        EstadoAgente agente = estado.getAgente(indice);
        Posicao pacman = estado.getPosicaoPacman();
        boolean assustado = agente.estaAssustado();

        double melhor = assustado ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        List<Direcao> melhores = new ArrayList<>();
        for (Direcao direcao : acoes) {
            Posicao novaPosicao = agente.getPosicao().mover(direcao);
            double distancia = novaPosicao.distanciaManhattan(pacman);
            if (assustado ? distancia > melhor : distancia < melhor) {
                melhor = distancia;
                melhores.clear();
                melhores.add(direcao);
            } else if (distancia == melhor) {
                melhores.add(direcao);
            }
        }

        if (random.nextDouble() < probabilidadeFantasmaPerseguir) {
            return melhores.get(random.nextInt(melhores.size()));
        }
        return acoes.get(random.nextInt(acoes.size()));
    }
}

