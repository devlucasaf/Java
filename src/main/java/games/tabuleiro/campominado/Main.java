package games.tabuleiro.campominado;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println(">>>>> CAMPO MINADO <<<<<");
        System.out.println("1 - Fácil (8x8 | 10 minas)");
        System.out.println("2 - Médio (10x10 | 20 minas)");
        System.out.println("3 - Difícil (12x12 | 35 minas)");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");

        int opcao = sc.nextInt();

        Jogo jogo = null;

        switch (opcao) {
            case 1 -> jogo = new Jogo(8, 8, 10);
            case 2 -> jogo = new Jogo(10, 10, 20);
            case 3 -> jogo = new Jogo(12, 12, 35);
            default -> {
                System.out.println("Saindo...");
                return;
            }
        }

        jogo.iniciar();
    }
}

