package application.outros.megasena;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MegaSena megaSena = new MegaSena();

        System.out.print("Digite quantos jogos de Mega Sena quer fazer: ");
        int numJogos = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numJogos; i++) {
            System.out.println("\nNovo jogo!");
            System.out.print("Digite a quantidade de dezenas (6-20): ");
            int qtd = Integer.parseInt(scanner.nextLine());

            try {
                List<Integer> jogo = megaSena.gerarJogo(qtd);
                System.out.printf("Seu jogo está pronto! Números: %s\n", jogo);
                System.out.printf("Custo da aposta: R$%.2f\n", megaSena.custoJogoMegaSena(qtd));

                megaSena.gravarJogos();
            }

            catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        scanner.close();
    }
}

