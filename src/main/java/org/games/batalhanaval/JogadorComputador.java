package org.games.batalhanaval;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class JogadorComputador extends Jogador {
    private final Random            aleatorio = new Random();
    private final boolean[][]       memoria = new boolean[Tabuleiro.TAMANHO][Tabuleiro.TAMANHO];
    private final Queue<Coordenada> filaDeCaca = new LinkedList<>();

    public JogadorComputador(String nome) {
        super(nome);
    }

    @Override
    public void posicionarNavios() {
        for (Navio navio : frota) {
            boolean posicionado = false;
            while (!posicionado) {
                int l = aleatorio.nextInt(Tabuleiro.TAMANHO);
                int c = aleatorio.nextInt(Tabuleiro.TAMANHO);
                boolean horizontal = aleatorio.nextBoolean();
                posicionado = tabuleiro.posicionarNavio(navio, new Coordenada(l, c), horizontal);
            }
        }
    }

    @Override
    public Coordenada escolherAtaque() {
        // Tenta caçar navios atingidos
        while (!filaDeCaca.isEmpty()) {
            Coordenada alvo = filaDeCaca.poll();

            if (!memoria[alvo.getLinha()][alvo.getColuna()]) {
                memoria[alvo.getLinha()][alvo.getColuna()] = true;
                return alvo;
            }
        }

        // Tiro aleatório
        while (true) {
            int l = aleatorio.nextInt(Tabuleiro.TAMANHO);
            int c = aleatorio.nextInt(Tabuleiro.TAMANHO);

            if (!memoria[l][c]) {
                memoria[l][c] = true;
                return new Coordenada(l, c);
            }
        }
    }

    @Override
    public void notificarResultadoAtaque(Coordenada alvo, String resultado) {
        if (resultado.contains("ACERTO")) {
            int l = alvo.getLinha();
            int c = alvo.getColuna();

            if (l > 0) {
                filaDeCaca.add(new Coordenada(l - 1, c)); // Cima
            }

            if (l < Tabuleiro.TAMANHO - 1) {
                filaDeCaca.add(new Coordenada(l + 1, c)); // Baixo
            }

            if (c > 0) {
                filaDeCaca.add(new Coordenada(l, c - 1)); // Esquerda
            }
            if (c < Tabuleiro.TAMANHO - 1) {
                filaDeCaca.add(new Coordenada(l, c + 1)); // Direita
            }
        }
    }
}