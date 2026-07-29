package application.exercicios.faculdade.pacman.agentes;

import application.exercicios.faculdade.pacman.Direcao;
import application.exercicios.faculdade.pacman.EstadoJogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AgenteMinimax implements Agente {

    private final int       profundidade;
    private final Random    random;

    public AgenteMinimax() {
        this(2);
    }

    public AgenteMinimax(int profundidade) {
        this(profundidade, new Random());
    }

    public AgenteMinimax(int profundidade, Random random) {
        this.profundidade = profundidade;
        this.random = random;
    }

    @Override
    public Direcao getAcao(EstadoJogo estado, int indice) {
        List<Direcao> acoes = estado.getAcoesLegais(indice);
        if (acoes.isEmpty()) {
            return Direcao.PARADO;
        }

        double melhorValor = Double.NEGATIVE_INFINITY;
        List<Direcao> melhores = new ArrayList<>();
        for (Direcao a : acoes) {
            EstadoJogo sucessor = estado.gerarSucessor(indice, a);
            double valor = minimax(sucessor, 0, 1);
            if (valor > melhorValor) {
                melhorValor = valor;
                melhores.clear();
                melhores.add(a);
            } else if (valor == melhorValor) {
                melhores.add(a);
            }
        }
        return melhores.get(random.nextInt(melhores.size()));
    }

    private double minimax(EstadoJogo estado, int profundidadeAtual, int indiceAgente) {
        if (estado.terminou() || profundidadeAtual == profundidade) {
            return FuncaoAvaliacao.avaliar(estado);
        }

        List<Direcao> acoes = estado.getAcoesLegais(indiceAgente);
        if (acoes.isEmpty()) {
            return FuncaoAvaliacao.avaliar(estado);
        }

        int numAgentes = estado.getNumAgentes();
        int proxAgente = (indiceAgente + 1) % numAgentes;
        int proxProfundidade = proxAgente == 0 ? profundidadeAtual + 1 : profundidadeAtual;

        boolean ehMax = indiceAgente == 0;
        double melhor = ehMax ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        for (Direcao a : acoes) {
            EstadoJogo sucessor = estado.gerarSucessor(indiceAgente, a);
            double v = minimax(sucessor, proxProfundidade, proxAgente);
            if (ehMax) {
                if (v > melhor) {
                    melhor = v;
                }
            } else {
                if (v < melhor) {
                    melhor = v;
                }
            }
        }
        return melhor;
    }
}

