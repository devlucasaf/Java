package games.tabuleiro.damas.model;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

    public static final int TAMANHO = 8;

    public static final int LINHAS_INICIAIS = 3;

    private final Peca[][] casas;

    public Tabuleiro() {
        this.casas = new Peca[TAMANHO][TAMANHO];
    }

    private Tabuleiro(Peca[][] origem) {
        this.casas = new Peca[TAMANHO][];
        for (int linha = 0; linha < TAMANHO; linha++) {
            this.casas[linha] = origem[linha].clone();
        }
    }

    public static Tabuleiro criarPosicaoInicial() {
        Tabuleiro tabuleiro = new Tabuleiro();
        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                Posicao posicao = new Posicao(linha, coluna);
                if (!posicao.isCasaEscura()) {
                    continue;
                }

                if (linha < LINHAS_INICIAIS) {
                    tabuleiro.colocar(posicao, new Peca(Jogador.JOGADOR_2, TipoPeca.COMUM));
                } else if (linha >= TAMANHO - LINHAS_INICIAIS) {
                    tabuleiro.colocar(posicao, new Peca(Jogador.JOGADOR_1, TipoPeca.COMUM));
                }
            }
        }
        return tabuleiro;
    }

    public Tabuleiro copiar() {
        return new Tabuleiro(casas);
    }

    public Peca getPeca(Posicao posicao) {
        return casas[posicao.linha()][posicao.coluna()];
    }

    public Peca getPeca(int linha, int coluna) {
        return casas[linha][coluna];
    }

    public boolean estaVazia(Posicao posicao) {
        return getPeca(posicao) == null;
    }

    public void colocar(Posicao posicao, Peca peca) {
        casas[posicao.linha()][posicao.coluna()] = peca;
    }

    public void remover(Posicao posicao) {
        casas[posicao.linha()][posicao.coluna()] = null;
    }

    public List<Posicao> posicoesDoJogador(Jogador jogador) {
        List<Posicao> posicoes = new ArrayList<>();
        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                Peca peca = casas[linha][coluna];
                if (peca != null && peca.getJogador() == jogador) {
                    posicoes.add(new Posicao(linha, coluna));
                }
            }
        }
        return posicoes;
    }

    public int contarPecas(Jogador jogador) {
        int total = 0;
        for (Peca[] linha : casas) {
            for (Peca peca : linha) {
                if (peca != null && peca.getJogador() == jogador) {
                    total++;
                }
            }
        }
        return total;
    }

    public int contarDamas(Jogador jogador) {
        int total = 0;
        for (Peca[] linha : casas) {
            for (Peca peca : linha) {
                if (peca != null && peca.getJogador() == jogador && peca.isDama()) {
                    total++;
                }
            }
        }
        return total;
    }
}
