package org.games.corrida;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("         SIMULADOR DE CORRIDA         ");

        System.out.println("\nModo de corrida:");
        System.out.println("1. Corrida rápida (5 corredores padrão)");
        System.out.println("2. Corrida personalizada");
        System.out.print("Escolha: ");

        Corrida corrida = new Corrida(100);

        if (scanner.nextLine().equals("2")) {
            System.out.print("Quantos corredores? (2-8): ");
            int n;
            try {
                n = Math.min(Math.max(Integer.parseInt(scanner.nextLine()), 2), 8);
            } catch (NumberFormatException e) {
                n = 5;
            }

            String[] emojis = {"🚗", "🏎️", "🚕", "🚙", "🏍️", "🚌", "🚐", "🛻"};
            for (int i = 0; i < n; i++) {
                System.out.print("Nome do corredor " + (i + 1) + ": ");
                String nome = scanner.nextLine();
                if (nome.isBlank()) {
                    nome = "Corredor " + (i + 1);
                }
                corrida.adicionarCorredor(nome, emojis[i]);
            }
        } else {
            corrida.adicionarCorredor("Relâmpago", "🚗");
            corrida.adicionarCorredor("Veloz", "🏎️");
            corrida.adicionarCorredor("Turbo", "🚕");
            corrida.adicionarCorredor("Flash", "🚙");
            corrida.adicionarCorredor("Nitro", "🏍️");
        }

        // Imprime espaço para a renderização
        for (int i = 0; i < corrida.getCorredores().size() + 3; i++) {
            System.out.println();
        }

        corrida.iniciar();
        scanner.close();
    }
}

