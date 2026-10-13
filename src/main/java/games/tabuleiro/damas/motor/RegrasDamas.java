package games.tabuleiro.damas.motor;

import games.tabuleiro.damas.model.Jogador;
import games.tabuleiro.damas.model.Movimento;
import games.tabuleiro.damas.model.Peca;
import games.tabuleiro.damas.model.Posicao;
import games.tabuleiro.damas.model.Tabuleiro;

import java.util.ArrayList;
import java.util.List;

public final class RegrasDamas {

    private static final int[][] DIRECOES = {
            {-1, -1},
            {-1, 1},
            {1, -1},
            {1, 1}
    };

    private RegrasDamas() {
    }

    public static List<Movimento> gerarMovimentosLegais(Tabuleiro tabuleiro, Jogador jogador) {
        List<Movimento> capturas = gerarCapturas(tabuleiro, jogador);
        if (!capturas.isEmpty()) {
            int maximo = 0;
            for (Movimento captura : capturas) {
                maximo = Math.max(maximo, captura.getQuantidadeCapturas());
            }

            final int maiorCaptura = maximo;
            capturas.removeIf(captura -> captura.getQuantidadeCapturas() < maiorCaptura);
            return capturas;
        }
        return gerarMovimentosSimples(tabuleiro, jogador);
    }

    public static boolean aplicarMovimento(Tabuleiro tabuleiro, Movimento movimento) {
        Peca peca = tabuleiro.getPeca(movimento.getOrigem());
        if (peca == null) {
            throw new IllegalStateException("Não há peça na casa " + movimento.getOrigem());
        }

        tabuleiro.remover(movimento.getOrigem());
        for (Posicao capturada : movimento.getCapturadas()) {
            tabuleiro.remover(capturada);
        }

        boolean promove = deveSerPromovida(peca, movimento.getDestino());
        tabuleiro.colocar(movimento.getDestino(), promove ? peca.promover() : peca);
        return promove;
    }

    public static boolean deveSerPromovida(Peca peca, Posicao destino) {
        return !peca.isDama() && destino.linha() == peca.getJogador().getLinhaPromocao();
    }

    private static List<Movimento> gerarCapturas(Tabuleiro tabuleiro, Jogador jogador) {
        List<Movimento> resultado = new ArrayList<>();
        for (Posicao origem : tabuleiro.posicoesDoJogador(jogador)) {
            Peca peca = tabuleiro.getPeca(origem);
            Tabuleiro tabuleiroSemPeca = tabuleiro.copiar();
            tabuleiroSemPeca.remover(origem);
            buscarCapturas(tabuleiroSemPeca, origem, peca, origem, new ArrayList<>(), new ArrayList<>(), resultado);
        }
        return resultado;
    }

    private static void buscarCapturas(Tabuleiro tabuleiro, Posicao origem, Peca peca, Posicao atual,
                                       List<Posicao> caminho, List<Posicao> capturadas,
                                       List<Movimento> resultado) {
        boolean continuou = false;

        for (int[] direcao : DIRECOES) {
            int dl = direcao[0];
            int dc = direcao[1];

            Posicao alvo;
            if (peca.isDama()) {
                alvo = atual.deslocar(dl, dc);
                while (alvo.dentroDoTabuleiro() && tabuleiro.estaVazia(alvo)) {
                    alvo = alvo.deslocar(dl, dc);
                }
            } else {
                alvo = atual.deslocar(dl, dc);
            }

            if (!podeSerCapturada(tabuleiro, alvo, peca, capturadas)) {
                continue;
            }

            Posicao pouso = alvo.deslocar(dl, dc);
            while (pouso.dentroDoTabuleiro() && tabuleiro.estaVazia(pouso)) {
                continuou = true;
                caminho.add(pouso);
                capturadas.add(alvo);
                buscarCapturas(tabuleiro, origem, peca, pouso, caminho, capturadas, resultado);
                caminho.remove(caminho.size() - 1);
                capturadas.remove(capturadas.size() - 1);

                if (!peca.isDama()) {
                    break;
                }
                pouso = pouso.deslocar(dl, dc);
            }
        }

        if (!continuou && !caminho.isEmpty()) {
            resultado.add(new Movimento(origem, caminho, capturadas));
        }
    }

    private static boolean podeSerCapturada(Tabuleiro tabuleiro, Posicao alvo, Peca atacante, List<Posicao> jaCapturadas) {
        if (!alvo.dentroDoTabuleiro()) {
            return false;
        }
        Peca pecaAlvo = tabuleiro.getPeca(alvo);
        return pecaAlvo != null
                && pecaAlvo.getJogador() != atacante.getJogador()
                && !jaCapturadas.contains(alvo);
    }

    private static List<Movimento> gerarMovimentosSimples(Tabuleiro tabuleiro, Jogador jogador) {
        List<Movimento> resultado = new ArrayList<>();
        for (Posicao origem : tabuleiro.posicoesDoJogador(jogador)) {
            Peca peca = tabuleiro.getPeca(origem);
            if (peca.isDama()) {
                for (int[] direcao : DIRECOES) {
                    Posicao destino = origem.deslocar(direcao[0], direcao[1]);
                    while (destino.dentroDoTabuleiro() && tabuleiro.estaVazia(destino)) {
                        resultado.add(Movimento.simples(origem, destino));
                        destino = destino.deslocar(direcao[0], direcao[1]);
                    }
                }
            } else {
                int frente = jogador.getDirecaoAvanco();
                for (int dc : new int[]{-1, 1}) {
                    Posicao destino = origem.deslocar(frente, dc);
                    if (destino.dentroDoTabuleiro() && tabuleiro.estaVazia(destino)) {
                        resultado.add(Movimento.simples(origem, destino));
                    }
                }
            }
        }
        return resultado;
    }
}
