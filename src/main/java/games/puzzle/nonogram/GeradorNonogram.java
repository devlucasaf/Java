package games.puzzle.nonogram;

import java.util.Random;

public class GeradorNonogram {

    private final Random aleatorio;

    public GeradorNonogram() {
        this.aleatorio = new Random();
    }

    public GeradorNonogram(long semente) {
        this.aleatorio = new Random(semente);
    }

    // --- GERA UMA SOLUÇÃO ALEATÓRIA COM BASE NA DIFICULDADE ---
    public boolean[][] gerar(Dificuldade dificuldade) {
        if (dificuldade == null) {
            throw new IllegalArgumentException("A dificuldade não pode ser nula.");
        }

        return gerar(dificuldade.getQuantidadeLinhas(), dificuldade.getQuantidadeColunas(), dificuldade.getProbabilidadePreenchimento());
    }

    // --- GERA UMA SOLUÇÃO COM TAMANHO E DENSIDADE PERSONALIZADOS ---
    public boolean[][] gerar(int quantidadeLinhas, int quantidadeColunas, double probabilidadePreenchimento) {
        validarParametros(quantidadeLinhas, quantidadeColunas, probabilidadePreenchimento);

        boolean[][] solucao = new boolean[quantidadeLinhas][quantidadeColunas];

        do {
            preencherAleatoriamente(solucao, probabilidadePreenchimento);
        } while (!possuiCelulaPreenchida(solucao) || !possuiCelulaVazia(solucao));

        return solucao;
    }

    // --- PREENCHE A MATRIZ ALEATORIAMENTE ---
    private void preencherAleatoriamente(boolean[][] solucao, double probabilidadePreenchimento) {
        for (int linha = 0; linha < solucao.length; linha++) {
            for (int coluna = 0; coluna < solucao[linha].length; coluna++) {
                solucao[linha][coluna] = aleatorio.nextDouble() < probabilidadePreenchimento;
            }
        }
    }

    // --- VERIFICA SE EXISTE PELO MENOS UMA CÉLULA PREENCHIDA ---
    private boolean possuiCelulaPreenchida(boolean[][] solucao) {
        for (boolean[] linha : solucao) {
            for (boolean celula : linha) {
                if (celula) {
                    return true;
                }
            }
        }

        return false;
    }

    // --- VERIFICA SE EXISTE PELO MENOS UMA CÉLULA VAZIA ---
    private boolean possuiCelulaVazia(boolean[][] solucao) {
        for (boolean[] linha : solucao) {
            for (boolean celula : linha) {
                if (!celula) {
                    return true;
                }
            }
        }

        return false;
    }

    // --- VALIDA OS PARÂMETROS UTILIZADOS NA GERAÇÃO ---
    private void validarParametros(int quantidadeLinhas, int quantidadeColunas, double probabilidadePreenchimento) {
        if (quantidadeLinhas <= 0) {
            throw new IllegalArgumentException("A quantidade de linhas deve ser maior que zero.");
        }

        if (quantidadeColunas <= 0) {
            throw new IllegalArgumentException("A quantidade de colunas deve ser maior que zero.");
        }

        if (probabilidadePreenchimento <= 0.0 || probabilidadePreenchimento >= 1.0) {
            throw new IllegalArgumentException("A probabilidade de preenchimento deve estar entre 0 e 1.");
        }
    }
}

