package math.aritmetica.parimpar;

public class NumerosParImpar {

    public static void main(String[] args) {

        System.out.println("Números de 1 a 10:");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Números pares de 1 a 50:\n");
        int number = 1;
        while (number <= 50) {
            if (number % 2 == 0) {
                System.out.print(number + " ");
            }
            number++;
        }

        System.out.println("Números pares de 1 a 50:\n");
        int impar = 1;
        while (impar <= 50) {
            if (impar % 2 != 0) {
                System.out.print(impar + " ");
            }
            impar++;
        }
    }
}