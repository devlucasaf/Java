package org.games.tabuleiro.batalhanaval;

import java.util.Scanner;

public class JogadorHumano extends Jogador {
    private final Scanner leitor;

    public JogadorHumano(String nome, Scanner leitor) {
        super(nome);
        this.leitor = leitor;
    }

    @Override
    public void posicionarNavios() {
        System.out.println("\nAlmirante " + nome + ", posicione sua frota!");
        for (Navio navio : frota) {
            boolean posicionado = false;

            while (!posicionado) {
                tabuleiro.imprimirTabuleiro(true, "Seu Tabuleiro");
                System.out.println("Posicionando " + navio.getNome() + " (Tamanho: " + navio.getTamanho() + ")");
                System.out.print("Digite a coordenada inicial (ex: A1): ");
                String strCoord = leitor.nextLine().trim();

                System.out.print("Orientação (H para Horizontal, V para Vertical): ");
                String strOri = leitor.nextLine().trim().toUpperCase();
                boolean horizontal = strOri.equals("H");

                try {
                    Coordenada inicio = Coordenada.interpretar(strCoord);
                    posicionado = tabuleiro.posicionarNavio(navio, inicio, horizontal);
                    if (!posicionado) {
                        System.out.println("Posição inválida (sobreposição ou fora dos limites). Tente novamente.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
        System.out.println("\nFrota posicionada com sucesso!");
    }

    @Override
    public Coordenada escolherAtaque() {
        while (true) {
            System.out.print("\n" + nome + ", informe a coordenada para ataque (ex: B7): ");
            String strCoord = leitor.nextLine().trim();

            try {
                return Coordenada.interpretar(strCoord);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    @Override
    public void notificarResultadoAtaque(Coordenada alvo, String resultado) {}
}
