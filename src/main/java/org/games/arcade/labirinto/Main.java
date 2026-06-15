package org.games.arcade.labirinto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("       LABIRINTO COM BACKTRACKING   ");

        System.out.println("\nEscolha o tamanho do labirinto:");
        System.out.println("1. Pequeno (11x11)");
        System.out.println("2. Médio (21x21)");
        System.out.println("3. Grande (31x31)");
        System.out.print("Escolha: ");

        int tamanho = switch (scanner.nextLine()) {
            case "2" -> 21;
            case "3" -> 31;
            default -> 11;
        };

        Labirinto labirinto = new Labirinto(tamanho, tamanho);

        while (true) {
            System.out.println("\nO que deseja fazer?");
            System.out.println("1. Jogar (resolver manualmente)");
            System.out.println("2. Ver solução automática (Backtracking)");
            System.out.println("3. Gerar novo labirinto");
            System.out.println("4. Sair");
            System.out.print("Escolha: ");

            switch (scanner.nextLine()) {
                case "1" -> jogar(labirinto, scanner);
                case "2" -> {
                    System.out.println("\nLabirinto original:");
                    labirinto.imprimir();
                    if (labirinto.resolver()) {
                        System.out.println("\n Solução encontrada (caminho marcado com '·'):");
                        labirinto.imprimir();
                    } else {
                        System.out.println(" Nenhuma solução encontrada!");
                    }
                }
                case "3" -> {
                    labirinto = new Labirinto(tamanho, tamanho);
                    System.out.println("Novo labirinto gerado!");
                    labirinto.imprimir();
                }
                case "4" -> {
                    System.out.println("Até mais!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void jogar(Labirinto labirinto, Scanner scanner) {
        int jogadorL = labirinto.getInicioLinha();
        int jogadorC = labirinto.getInicioColuna();
        int movimentos = 0;

        System.out.println("\nControles: W (cima), S (baixo), A (esquerda), D (direita), Q (sair)");
        System.out.println("Objetivo: Chegue ao 'E' (saída)!");

        labirinto.imprimirComJogador(jogadorL, jogadorC);

        while (true) {
            System.out.print("Movimento: ");
            String input = scanner.nextLine().toUpperCase();

            int novoL = jogadorL, novoC = jogadorC;
            switch (input) {
                case "W" -> novoL--;
                case "S" -> novoL++;
                case "A" -> novoC--;
                case "D" -> novoC++;
                case "Q" -> {
                    System.out.println("Você desistiu após " + movimentos + " movimentos.");
                    return;
                }
                default -> {
                    System.out.println("Comando inválido!");
                    continue;
                }
            }

            if (labirinto.podeMover(novoL, novoC)) {
                jogadorL = novoL;
                jogadorC = novoC;
                movimentos++;
                labirinto.imprimirComJogador(jogadorL, jogadorC);

                if (jogadorL == labirinto.getFimLinha() && jogadorC == labirinto.getFimColuna()) {
                    System.out.println(" Parabéns! Você escapou do labirinto em " + movimentos + " movimentos!");
                    return;
                }
            } else {
                System.out.println("Não pode ir para lá! (parede)");
            }
        }
    }
}

