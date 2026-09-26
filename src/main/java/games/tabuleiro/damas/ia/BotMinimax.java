package games.tabuleiro.damas.ia;

import games.tabuleiro.damas.modelo.Jogador;
import games.tabuleiro.damas.modelo.Movimento;
import games.tabuleiro.damas.modelo.NivelDificuldade;
import games.tabuleiro.damas.modelo.Tabuleiro;
import games.tabuleiro.damas.motor.RegrasDamas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CancellationException;

/**
 * BOT baseado no algoritmo Minimax com poda alfa-beta.
 * <p>
 * Ao atingir a profundidade limite, a busca é estendida enquanto houver capturas obrigatórias
 * pendentes (busca de quiescência), evitando que o BOT avalie posições no meio de uma troca.
 * Entre jogadas igualmente boas, a escolha é aleatória para variar as partidas.
 */
public class BotMinimax implements JogadorArtificial {

    private static final int INFINITO = Integer.MAX_VALUE / 2;
    private static final int VITORIA = 100_000;
    private static final int LIMITE_EXTENSAO_CAPTURAS = 6;

    private final NivelDificuldade nivel;
    private final AvaliadorPosicao avaliador;
    private final Random aleatorio;

    public BotMinimax(NivelDificuldade nivel) {
        this(nivel, new AvaliadorPosicao(), new Random());
    }

    public BotMinimax(NivelDificuldade nivel, AvaliadorPosicao avaliador, Random aleatorio) {
        this.nivel = nivel;
        this.avaliador = avaliador;
        this.aleatorio = aleatorio;
    }

    @Override
    public Movimento escolherMovimento(Tabuleiro tabuleiro, Jogador bot) {
        List<Movimento> movimentos = RegrasDamas.gerarMovimentosLegais(tabuleiro, bot);
        if (movimentos.isEmpty()) {
            return null;
        }
        if (movimentos.size() == 1) {
            return movimentos.get(0);
        }
        if (aleatorio.nextDouble() < nivel.getChanceErro()) {
            return movimentos.get(aleatorio.nextInt(movimentos.size()));
        }

        int melhorValor = -INFINITO;
        List<Movimento> melhores = new ArrayList<>();
        for (Movimento movimento : ordenar(tabuleiro, movimentos)) {
            Tabuleiro filho = tabuleiro.copiar();
            RegrasDamas.aplicarMovimento(filho, movimento);
            // Janela com alfa = melhor - 1 garante valores exatos para jogadas empatadas com a melhor.
            int alfa = melhorValor == -INFINITO ? -INFINITO : melhorValor - 1;
            int valor = minimax(filho, bot.getAdversario(), bot, nivel.getProfundidade() - 1, alfa, INFINITO);
            if (valor > melhorValor) {
                melhorValor = valor;
                melhores.clear();
                melhores.add(movimento);
            } else if (valor == melhorValor) {
                melhores.add(movimento);
            }
        }
        return melhores.get(aleatorio.nextInt(melhores.size()));
    }

    private int minimax(Tabuleiro tabuleiro, Jogador vez, Jogador bot, int profundidade, int alfa, int beta) {
        if (Thread.currentThread().isInterrupted()) {
            throw new CancellationException("Busca do BOT cancelada.");
        }

        List<Movimento> movimentos = RegrasDamas.gerarMovimentosLegais(tabuleiro, vez);
        if (movimentos.isEmpty()) {
            // Quem não tem jogadas perde. A profundidade restante premia vitórias mais rápidas.
            return vez == bot ? -(VITORIA + profundidade) : VITORIA + profundidade;
        }

        boolean capturasPendentes = movimentos.get(0).isCaptura();
        if (profundidade <= 0 && (!capturasPendentes || profundidade <= -LIMITE_EXTENSAO_CAPTURAS)) {
            return avaliador.avaliar(tabuleiro, bot);
        }

        boolean maximizando = vez == bot;
        int melhor = maximizando ? -INFINITO : INFINITO;
        for (Movimento movimento : ordenar(tabuleiro, movimentos)) {
            Tabuleiro filho = tabuleiro.copiar();
            RegrasDamas.aplicarMovimento(filho, movimento);
            int valor = minimax(filho, vez.getAdversario(), bot, profundidade - 1, alfa, beta);
            if (maximizando) {
                melhor = Math.max(melhor, valor);
                alfa = Math.max(alfa, melhor);
            } else {
                melhor = Math.min(melhor, valor);
                beta = Math.min(beta, melhor);
            }
            if (alfa >= beta) {
                break; // poda alfa-beta
            }
        }
        return melhor;
    }

    /** Examina primeiro as jogadas que promovem peças, o que melhora as podas. */
    private List<Movimento> ordenar(Tabuleiro tabuleiro, List<Movimento> movimentos) {
        List<Movimento> ordenados = new ArrayList<>(movimentos.size());
        for (Movimento movimento : movimentos) {
            if (RegrasDamas.deveSerPromovida(tabuleiro.getPeca(movimento.getOrigem()), movimento.getDestino())) {
                ordenados.add(0, movimento);
            } else {
                ordenados.add(movimento);
            }
        }
        return ordenados;
    }
}
