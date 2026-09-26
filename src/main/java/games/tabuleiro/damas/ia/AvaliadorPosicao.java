package games.tabuleiro.damas.ia;

import games.tabuleiro.damas.modelo.Jogador;
import games.tabuleiro.damas.modelo.Peca;
import games.tabuleiro.damas.modelo.Tabuleiro;

/**
 * Função heurística que estima o quão boa uma posição é para um jogador.
 * Valores positivos favorecem o jogador informado; negativos, o adversário.
 */
public class AvaliadorPosicao {

    private static final int VALOR_PECA = 100;
    private static final int VALOR_DAMA = 330;
    private static final int BONUS_AVANCO = 4;
    private static final int BONUS_PROXIMA_PROMOCAO = 6;
    private static final int BONUS_RETAGUARDA = 10;
    private static final int BONUS_CENTRO = 6;
    private static final int BONUS_BORDA = 3;
    private static final int BONUS_DAMA_DIAGONAL_PRINCIPAL = 12;
    private static final int TOTAL_PECAS_INICIAL = 24;

    public int avaliar(Tabuleiro tabuleiro, Jogador perspectiva) {
        int pontuacao = 0;
        int materialProprio = 0;
        int materialAdversario = 0;
        int totalPecas = 0;

        for (int linha = 0; linha < Tabuleiro.TAMANHO; linha++) {
            for (int coluna = 0; coluna < Tabuleiro.TAMANHO; coluna++) {
                Peca peca = tabuleiro.getPeca(linha, coluna);
                if (peca == null) {
                    continue;
                }
                totalPecas++;
                int material = peca.isDama() ? VALOR_DAMA : VALOR_PECA;
                int valor = material + bonusPosicional(peca, linha, coluna);
                if (peca.getJogador() == perspectiva) {
                    pontuacao += valor;
                    materialProprio += material;
                } else {
                    pontuacao -= valor;
                    materialAdversario += material;
                }
            }
        }

        // Quem está em vantagem se beneficia de trocas: a vantagem pesa mais com menos peças na mesa.
        int diferenca = materialProprio - materialAdversario;
        pontuacao += diferenca * (TOTAL_PECAS_INICIAL - totalPecas) / (TOTAL_PECAS_INICIAL * 2);

        return pontuacao;
    }

    private int bonusPosicional(Peca peca, int linha, int coluna) {
        int bonus = 0;
        if (peca.isDama()) {
            // A grande diagonal (a1–h8) é a mais valiosa para uma dama.
            if (linha + coluna == Tabuleiro.TAMANHO - 1) {
                bonus += BONUS_DAMA_DIAGONAL_PRINCIPAL;
            }
        } else {
            int avanco = peca.getJogador() == Jogador.JOGADOR_1 ? (Tabuleiro.TAMANHO - 1 - linha) : linha;
            bonus += avanco * BONUS_AVANCO;
            if (avanco >= 5) {
                bonus += BONUS_PROXIMA_PROMOCAO * (avanco - 4);
            }
            if (avanco == 0) {
                bonus += BONUS_RETAGUARDA; // protege a linha de promoção do adversário
            }
            if (coluna == 0 || coluna == Tabuleiro.TAMANHO - 1) {
                bonus += BONUS_BORDA; // peças na borda não podem ser capturadas lateralmente
            }
        }
        if (linha >= 2 && linha <= 5 && coluna >= 2 && coluna <= 5) {
            bonus += BONUS_CENTRO;
        }
        return bonus;
    }
}
