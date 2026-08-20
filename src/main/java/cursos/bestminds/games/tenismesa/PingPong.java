package games.bestminds.tenismesa;

import java.util.Objects;
import java.util.Scanner;

public class PingPong {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int userPointSetOne = 0;
        int userPointSettwo = 0;
        int userpointOne = 0;
        int userpointTwo = 0;

        System.out.print("Digite o nome do participante: ");
        String userNameOne = input.nextLine();
        System.out.print("Digite o nome do participante: ");
        String userNameTwo = input.nextLine();

        System.out.print("Digite o total de pontos da partida: ");
        String totalPoints = input.nextLine();

        System.out.print("Digite o total de Sets da partida: ");
        String totalSets = input.nextLine();

        while (true) {
            while (true) {
                System.out.print("Digite o nome de quem fez o ponto: ");
                String user = input.nextLine();

                if (Objects.equals(user, userNameOne)) {
                    userpointOne++;
                    System.out.printf("Ponto do %s\n", userNameOne);
                    System.out.println(userNameOne + ":" + userpointOne);
                    System.out.println(userNameTwo + ":" + userpointTwo);
                } else {
                    userpointTwo++;
                    System.out.printf("Ponto do %s\n", userNameTwo);
                    System.out.println(userNameOne + ":" + userpointOne);
                    System.out.println(userNameTwo + ":" + userpointTwo);
                }

                if (userpointOne == Integer.parseInt(totalPoints) || userpointTwo == Integer.parseInt(totalPoints)) {
                    break;
                }
            }

            if (userpointOne == Integer.parseInt(totalPoints)) {
                userPointSetOne++;
                userpointOne = 0;
                userpointTwo = 0;
                System.out.printf("setpoint do %s \n", userNameOne);
            } else {
                userPointSettwo++;
                userpointOne = 0;
                userpointTwo = 0;
                System.out.printf("setpoint do %s \n", userNameTwo);
            }

            System.out.println("sets: \n");
            System.out.println(userNameOne + " " + userPointSetOne + "\n");
            System.out.println(userNameTwo + " " + userPointSettwo + "\n");

            if (userPointSetOne == Integer.parseInt(totalSets) || userPointSettwo == Integer.parseInt(totalSets)) {
                break; // Sai do loop principal do jogo
            }
        }

        System.out.println("sets: \n");
        System.out.println(userNameOne + " " + userPointSetOne + "\n");
        System.out.println(userNameTwo + " " + userPointSettwo + "\n");
    }
}
