package cursos.udemy.aulas.unidade7.aula65;

import java.util.Scanner;

public class Bitwise {
    private void exemplo1Bitwise() {
        int numero1 = 89;
        int numero2 = 60;

        System.out.println(numero1 & numero2); // AND
        System.out.println(numero1 | numero2); // OR
        System.out.println(numero1 ^ numero2); // XOR
    }

    private void exemplo2Bitwise() {
        Scanner scanner = new Scanner(System.in);

        int mask = 0b100000;
        int number = scanner.nextInt();

        if ((number & mask) != 0) {
            System.out.println("6th bit is true!");
        } else {
            System.out.println("6th bit is false!");
        }
    }
    public static void main(String[] args) {
        Bitwise bitwise = new Bitwise();
        System.out.println("Exemplo 1 - Operadores Bitwise");
        bitwise.exemplo1Bitwise();
        System.out.println("Exemplo 2 - Operadores Bitwise");
        bitwise.exemplo2Bitwise();
    }
}
