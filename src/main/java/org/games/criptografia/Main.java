package org.games.criptografia;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("          CRIPTOGRAFIA EM JAVA       ");

        while (true) {
            System.out.println("\nEscolha o algoritmo:");
            System.out.println("1.   Cifra de César");
            System.out.println("2.   Cifra de Vigenère");
            System.out.println("3.   Força Bruta (quebrar César)");
            System.out.println("4.   Demonstração");
            System.out.println("5.  Sair");
            System.out.print("Escolha: ");

            switch (scanner.nextLine()) {
                case "1" -> menuCesar();
                case "2" -> menuVigenere();
                case "3" -> menuForcaBruta();
                case "4" -> demonstracao();
                case "5" -> {
                    System.out.println("Até mais!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void menuCesar() {
        System.out.println("\n--- CIFRA DE CÉSAR ---");
        System.out.println("1. Criptografar");
        System.out.println("2. Descriptografar");
        System.out.print("Escolha: ");
        String opcao = scanner.nextLine();

        System.out.print("Digite o texto: ");
        String texto = scanner.nextLine();
        System.out.print("Digite a chave (número): ");
        int chave;

        try {
            chave = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Chave inválida!");
            return;
        }

        if (opcao.equals("1")) {
            String resultado = CifraCesar.criptografar(texto, chave);
            System.out.println("\n  Original:      " + texto);
            System.out.println("  Criptografado: " + resultado);
        } else {
            String resultado = CifraCesar.descriptografar(texto, chave);
            System.out.println("\n  Criptografado:    " + texto);
            System.out.println("  Descriptografado: " + resultado);
        }
    }

    private static void menuVigenere() {
        System.out.println("\n--- CIFRA DE VIGENÈRE ---");
        System.out.println("1. Criptografar");
        System.out.println("2. Descriptografar");
        System.out.print("Escolha: ");
        String opcao = scanner.nextLine();

        System.out.print("Digite o texto: ");
        String texto = scanner.nextLine();
        System.out.print("Digite a palavra-chave: ");
        String chave = scanner.nextLine();

        if (chave.isBlank()) {
            System.out.println("Chave inválida!");
            return;
        }

        if (opcao.equals("1")) {
            String resultado = CifraVigenere.criptografar(texto, chave);
            System.out.println("\n  Original:      " + texto);
            System.out.println("  Chave:         " + chave);
            System.out.println("  Criptografado: " + resultado);
        } else {
            String resultado = CifraVigenere.descriptografar(texto, chave);
            System.out.println("\n  Criptografado:    " + texto);
            System.out.println("  Chave:            " + chave);
            System.out.println("  Descriptografado: " + resultado);
        }
    }

    private static void menuForcaBruta() {
        System.out.println("\n--- FORÇA BRUTA (QUEBRAR CIFRA DE CÉSAR) ---");
        System.out.print("Digite o texto criptografado: ");
        String texto = scanner.nextLine();

        System.out.println("\nTentando todas as 26 chaves possíveis:\n");
        String[] tentativas = CifraCesar.forcaBruta(texto);
        for (int i = 0; i < 26; i++) {
            System.out.printf("Chave %2d: %s%n", i, tentativas[i]);
        }
    }

    private static void demonstracao() {
        System.out.println("\n=== DEMONSTRAÇÃO ===\n");

        String original = "Java e muito legal para aprender programacao!";

        // César
        System.out.println("--- Cifra de César (chave = 3) ---");
        String cesarCripto = CifraCesar.criptografar(original, 3);
        String cesarDecripto = CifraCesar.descriptografar(cesarCripto, 3);
        System.out.println("Original:         " + original);
        System.out.println("Criptografado:    " + cesarCripto);
        System.out.println("Descriptografado: " + cesarDecripto);
        System.out.println("Funciona: " + (original.equals(cesarDecripto) ? "SIM" : "NÃO"));

        System.out.println();

        // Vigenère
        String chave = "JAVA";
        System.out.println("--- Cifra de Vigenère (chave = '" + chave + "') ---");
        String vigCripto = CifraVigenere.criptografar(original, chave);
        String vigDecripto = CifraVigenere.descriptografar(vigCripto, chave);
        System.out.println("Original:         " + original);
        System.out.println("Criptografado:    " + vigCripto);
        System.out.println("Descriptografado: " + vigDecripto);
        System.out.println("Funciona: " + (original.equals(vigDecripto) ? "SIM" : "NÃO"));

        System.out.println();
        System.out.println("  Note como Vigenère é mais seguro: a mesma letra pode virar");
        System.out.println("   letras diferentes dependendo da posição na palavra-chave!");
    }
}

