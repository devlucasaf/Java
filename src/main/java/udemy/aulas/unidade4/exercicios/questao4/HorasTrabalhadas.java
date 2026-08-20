package udemy.aulas.unidade4.exercicios.questao4;

import java.util.Locale;
import java.util.Scanner;

public class HorasTrabalhadas {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        int     numero;
        int     horas;
        double  valorHora;
        double  salario;

        numero = scanner.nextInt();
        horas = scanner.nextInt();
        valorHora = scanner.nextDouble();

        salario = valorHora * horas;

        System.out.println("Número = " + numero);
        System.out.printf("Salário = U$ %.2f%n", salario);

        scanner.close();
    }
}
