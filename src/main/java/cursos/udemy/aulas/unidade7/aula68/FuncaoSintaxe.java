package cursos.udemy.aulas.unidade7.aula68;

import java.util.Scanner;

public class FuncaoSintaxe {
    private void exemplo1FuncaoMostrarRaizQuadrada() {
        double y = 25.0;
        double x = Math.sqrt(y);
        System.out.println("A raiz quadrada de " + y + " é: " + x);
    }

    private void exemplo2FuncaoMostrarMaiorNumero1() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite 3 números: ");

        int numero1 = scanner.nextInt();
        int numero2 = scanner.nextInt();
        int numero3 = scanner.nextInt();

        int maiorNumero = Math.max(numero1, Math.max(numero2, numero3));
        System.out.println("O maior número é: " + maiorNumero);

        scanner.close();
    }

    private void exemplo3FuncaoMostrarMaiorNumero2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite 3 números");

        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();

        if (a > b && a > c) {
            System.out.println("Maior número: " + a);
        } else if (b > c) {
            System.out.println("Maior número: " + b);
        } else {
            System.out.println("Maior número: " + c);
        }
        scanner.close();
    }

    private static void mostrarResultados() {
        FuncaoSintaxe sintaxe = new FuncaoSintaxe();
        System.out.print("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
        System.out.println("\nExemplo 1: (raiz quadrada)");
        sintaxe.exemplo1FuncaoMostrarRaizQuadrada();
        System.out.print("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
        System.out.println("\nExemplo 2: (maior número)\n");
        sintaxe.exemplo2FuncaoMostrarMaiorNumero1();
        System.out.print("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
        System.out.println("\nExemplo 3: (maior número)\n");
        sintaxe.exemplo3FuncaoMostrarMaiorNumero2();
    }

    public static void main(String[] args) {
        mostrarResultados();
    }
}
