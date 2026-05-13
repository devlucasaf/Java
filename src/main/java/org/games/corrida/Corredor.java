package org.games.corrida;

import java.util.Random;

public class Corredor implements Runnable {
    private final String nome;
    private final String emoji;

    private int             posicao = 0;
    private final int       distanciaTotal;
    private boolean         chegou = false;
    private final Random    random = new Random();
    private final Corrida   corrida;

    public Corredor(String nome, String emoji, int distanciaTotal, Corrida corrida) {
        this.nome = nome;
        this.emoji = emoji;
        this.distanciaTotal = distanciaTotal;
        this.corrida = corrida;
    }

    @Override
    public void run() {
        while (posicao < distanciaTotal && !corrida.temVencedor()) {
            int avanco = 1 + random.nextInt(3); // Avança 1 a 3 posições
            posicao = Math.min(posicao + avanco, distanciaTotal);

            int evento = random.nextInt(100);
            if (evento < 5) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    return;
                }
            } else if (evento < 10) {
                posicao = Math.min(posicao + 2, distanciaTotal);
            }

            try {
                Thread.sleep(100 + random.nextInt(200));
            } catch (InterruptedException e) {
                return;
            }
        }

        if (posicao >= distanciaTotal) {
            chegou = true;
            corrida.registrarChegada(this);
        }
    }

    public String getPista() {
        int pistaTamanho = 40;
        int pos = (int) ((double) posicao / distanciaTotal * pistaTamanho);
        pos = Math.min(pos, pistaTamanho);
        String trilha = "░".repeat(pos) + emoji + "░".repeat(pistaTamanho - pos);
        return String.format("%-12s |%s| %d%%", nome, trilha, (posicao * 100 / distanciaTotal));
    }

    public String getNome() {
        return nome;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getPosicao() {
        return posicao;
    }

    public boolean isChegou() {
        return chegou;
    }

}

