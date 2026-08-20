package cursos.udemy.aulas.unidade6.aula62;

import java.util.Locale;
import java.util.Scanner;

public class EstruturaRepeticaoDoWhile {
    private void exemplo1DoWhile() {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a temperatura em Celcius: ");
        double numeroCelsius = scanner.nextDouble();
        double numeroFahrenheit = ((9.0 * numeroCelsius) / 5.0) + 32.0;

        System.out.printf("Digite a temperatura em Fahrenheit: %.2f%n", numeroFahrenheit);
        System.out.println("Deseja continuar? (s/n)");
        char resposta = scanner.next().charAt(0);

        while (resposta != 'n') {
            System.out.println("Digite a temperatura em Celcius: ");
            numeroCelsius = scanner.nextDouble();
            numeroFahrenheit = ((9.0 * numeroCelsius) / 5.0) + 32.0;

            System.out.printf("Digite a temperatura em Fahrenheit: %.2f%n", numeroFahrenheit);
            System.out.println("Deseja continuar? (s/n)");
            resposta = scanner.next().charAt(0);
        }
        scanner.close();
    }

    private void exemplo2DoWhile() {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a temperatura em Celcius: ");
        double numeroCelsius = scanner.nextDouble();
        double numeroFahrenheit = ((9.0 * numeroCelsius) / 5.0) + 32.0;

        System.out.printf("Digite a temperatura em Fahrenheit: %.2f%n", numeroFahrenheit);
        System.out.println("Deseja continuar? (s/n)");

        char resposta;

        do {
            System.out.println("Digite a temperatura em Celcius: ");
            numeroCelsius = scanner.nextDouble();
            numeroFahrenheit = ((9.0 * numeroCelsius) / 5.0) + 32.0;

            System.out.printf("Digite a temperatura em Fahrenheit: %.2f%n", numeroFahrenheit);
            System.out.println("Deseja continuar? (s/n)");
            resposta = scanner.next().charAt(0);
        } while (resposta != 'n');

        scanner.close();
    }

    public static void main(String[] args) {
        EstruturaRepeticaoDoWhile repeticaoDoWhile = new EstruturaRepeticaoDoWhile();

        System.out.println("Exemplo 1 - Estrutura de Repetição While");
        repeticaoDoWhile.exemplo1DoWhile();
        System.out.print("Exemplo 2 - Estrutura de Repetição Do-While");
        repeticaoDoWhile.exemplo2DoWhile();
    }
}
