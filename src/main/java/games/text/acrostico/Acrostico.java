package games.text.acrostico;

import java.util.Scanner;

public class Acrostico {

    private final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        new Acrostico().iniciar();
    }

    public void iniciar() {
        System.out.println("=== ACROSTICO ===");
        System.out.println("Digite uma palavra e crie uma frase onde cada palavra comeca");
        System.out.println("com as letras dela na ordem.");
        System.out.print("\nPalavra base: ");
        String palavra = entrada.nextLine().trim().toUpperCase();

        if (palavra.isEmpty() || !palavra.matches("[A-Z]+")) {
            System.out.println("Palavra invalida. Use apenas letras.");
            return;
        }

        String[] resultado = new String[palavra.length()];
        for (int i = 0; i < palavra.length(); i++) {
            char letra = palavra.charAt(i);
            while (true) {
                System.out.printf("Palavra para a letra '%c': ", letra);
                String p = entrada.nextLine().trim();
                if (p.isEmpty()) {
                    System.out.println("Nao pode estar vazia.");
                    continue;
                }

                if (Character.toUpperCase(p.charAt(0)) != letra) {
                    System.out.printf("Deve comecar com '%c'.%n", letra);
                    continue;
                }
                resultado[i] = p;
                break;
            }
        }

        System.out.println("\n===== SEU ACROSTICO =====");
        for (int i = 0; i < palavra.length(); i++) {
            System.out.printf("%c - %s%n", palavra.charAt(i), resultado[i]);
        }

        StringBuilder frase = new StringBuilder();
        for (int i = 0; i < resultado.length; i++) {
            frase.append(resultado[i]);
            if (i < resultado.length - 1) {
                frase.append(' ');
            }
        }
        System.out.println("\nFrase completa: " + frase);
    }
}

