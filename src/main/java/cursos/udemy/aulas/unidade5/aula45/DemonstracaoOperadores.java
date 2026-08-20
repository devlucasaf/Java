package cursos.udemy.aulas.unidade5.aula45;

import java.util.Scanner;

public class DemonstracaoOperadores {

    public static void main(String[] args) {
        operadoresAtribuicaoCumulativa();
    }

    private static void operadoresAtribuicaoCumulativa() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o valor de A: ");
        double a = scanner.nextDouble();

        System.out.print("Digite o valor de B: ");
        double b = scanner.nextDouble();

        double resultado;

        resultado = a;
        resultado += b;
        System.out.println("A += B: " + resultado);

        resultado = a;
        resultado -= b;
        System.out.println("A -= B: " + resultado);

        resultado = a;
        resultado *= b;
        System.out.println("A *= B: " + resultado);

        if (b != 0) {
            resultado = a;
            resultado /= b;
            System.out.println("A /= B: " + resultado);

            resultado = a;
            resultado %= b;
            System.out.println("A %= B: " + resultado);
        } else {
            System.out.println("A /= B: não é possível dividir por zero.");
            System.out.println("A %= B: não é possível calcular o resto por zero.");
        }

        scanner.close();
    }
}
