package org.games.bestminds.pedrapapeltesoura;

import java.util.Random;
import java.util.Scanner;

public class PedraPapelTesoura {

    public static void main(String[] args) {

        String[] options = {"Pedra", "Papel", "Tesoura"};

        Random choice = new Random();

        int indexNumber = choice.nextInt(options.length);

        String choicePC = options[indexNumber];

        System.out.print("Escolha uma opção:\n");
        System.out.print("Digite [0] para sair\n");
        System.out.print("Pedra [1]\n");
        System.out.print("Papel [2]\n");
        System.out.print("Tesoura [3]\n");

        while (true){
            Scanner input = new Scanner(System.in);
            System.out.print("Digite a sua escolha: \n");
            int num = input.nextInt();

            if (num == 0){
                break;
            }

            if (num < 1 || num > 3){
                System.out.print("Número inválido!\n");
            }

            String userChoice = options[num-1];

            if (userChoice.equals(choicePC)) {
                System.out.printf("O pc escolheu %s! Vocês empataram\n", choicePC);
                break;
            } else {
                if (userChoice.equals("Papel") && choicePC.equals("Pedra")) {
                    System.out.printf("Você ganhou! O PC escolheu: %s\n", choicePC);
                    break;
                } else if (userChoice.equals("Tesoura") && choicePC.equals("Papel")) {
                    System.out.printf("Você ganhou! O PC escolheu: %s\n", choicePC);
                    break;
                } else if (userChoice.equals("Pedra") && choicePC.equals("Tesoura")) {
                    System.out.printf("Você ganhou! O PC escolheu: %s\n", choicePC);
                    break;
                } else {
                    System.out.printf("Você perdeu! O PC escolheu %s\n", choicePC);
                    break;
                }
            }
        }
    }
}

