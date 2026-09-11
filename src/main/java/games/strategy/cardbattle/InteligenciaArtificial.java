package games.strategy.cardbattle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InteligenciaArtificial {

    private final Random aleatorio;

    public InteligenciaArtificial() {
        this.aleatorio = new Random();
    }

    // --- EXECUTA TODO O TURNO DA INTELIGÊNCIA ARTIFICIAL ---
    public List<String> executarTurno(Jogador jogadorIa, Jogador adversario) {
        if (jogadorIa == null || adversario == null) {
            throw new IllegalArgumentException("Os dois jogadores devem ser informados.");
        }

        List<String> acontecimentos = new ArrayList<>();

        while (jogadorIa.isVivo() && adversario.isVivo() && jogadorIa.possuiCartaJogavel()) {
            int indiceCarta = escolherCarta(jogadorIa, adversario);

            if (indiceCarta < 0) {
                break;
            }

            Carta carta = jogadorIa.getMao().get(indiceCarta);
            acontecimentos.add(jogadorIa.jogarCarta(indiceCarta, adversario) + " " + descreverEscolha(carta));
        }

        return acontecimentos;
    }

    // --- ESCOLHE A MELHOR CARTA PARA A SITUAÇÃO ATUAL ---
    public int escolherCarta(Jogador jogadorIa, Jogador adversario) {
        int melhorIndice = -1;
        int melhorPontuacao = Integer.MIN_VALUE;

        for (int indice = 0; indice < jogadorIa.getMao().size(); indice++) {
            Carta carta = jogadorIa.getMao().get(indice);

            if (!carta.podeSerJogada(jogadorIa.getEnergiaAtual())) {
                continue;
            }

            int pontuacao = calcularPontuacao(carta, jogadorIa, adversario);
            pontuacao += aleatorio.nextInt(4);

            if (pontuacao > melhorPontuacao) {
                melhorPontuacao = pontuacao;
                melhorIndice = indice;
            }
        }

        return melhorIndice;
    }

    // --- CALCULA A PONTUAÇÃO ESTRATÉGICA DE UMA CARTA ---
    private int calcularPontuacao(Carta carta, Jogador jogadorIa, Jogador adversario) {
        int pontuacao = 0;
        double percentualVidaIa = jogadorIa.getVidaAtual() / (double) jogadorIa.getVidaMaxima();

        if (carta.getDano() > 0) {
            pontuacao += carta.getDano() * 3;

            if (carta.getDano() + jogadorIa.calcularModificadorAtaque() >= adversario.getVidaAtual() + adversario.getDefesaAtual()) {
                pontuacao += 100;
            }
        }

        if (carta.getDefesa() > 0) {
            pontuacao += carta.getDefesa() * 2;

            if (percentualVidaIa <= 0.5) {
                pontuacao += 20;
            }
        }

        if (carta.getCura() > 0) {
            int vidaFaltante = jogadorIa.getVidaMaxima() - jogadorIa.getVidaAtual();
            pontuacao += Math.min(carta.getCura(), vidaFaltante) * 3;

            if (percentualVidaIa <= 0.35) {
                pontuacao += 40;
            }

            if (vidaFaltante == 0) {
                pontuacao -= 100;
            }
        }

        if (carta.getEfeito() != null) {
            pontuacao += carta.getEfeito().getIntensidade() * carta.getEfeito().getTurnosRestantes() * 2;

            if (carta.getEfeito().getTipo() == TipoEfeito.VENENO && adversario.getVidaAtual() > 10) {
                pontuacao += 12;
            }

            if (carta.getEfeito().getTipo() == TipoEfeito.REGENERACAO && percentualVidaIa < 0.7) {
                pontuacao += 15;
            }
        }

        pontuacao -= carta.getCustoEnergia();
        return pontuacao;
    }

    // --- DESCREVE RESUMIDAMENTE A DECISÃO DA IA ---
    private String descreverEscolha(Carta carta) {
        return switch (carta.getTipo()) {
            case ATAQUE -> "A IA decidiu atacar.";
            case DEFESA -> "A IA decidiu se defender.";
            case CURA -> "A IA decidiu recuperar vida.";
            case SUPORTE -> "A IA decidiu aplicar um efeito.";
        };
    }
}

