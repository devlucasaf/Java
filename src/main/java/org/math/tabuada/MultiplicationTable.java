package org.math.tabuada;

import java.util.Scanner;

public class MultiplicationTable {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Digite um número: ");
        int number = input.nextInt();

        for (int o = 1; o <= 10; o++) {
            int sum = number + o;
            System.out.printf("%s + %s = %s \n", number, o, sum);
        }

        System.out.print("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");

        for (int a=1; a<=10; a++) {
            int sub = number + a;
            System.out.printf("\n%s - %s = %s\n", sub, number, a);
        }

        System.out.print("+=+=" + 25);

        for (int i = 1; i <= 10; i++) {
            int mult = number * i;
            System.out.printf("\n%s * %s = %s\n", number, i, mult);
        }

        System.out.print("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");

        for (int u = 1; u <= 10; u++) {
            int div = number * u;
            System.out.printf("\n%s / %s = %s\n", div, number, u);
        }

        System.out.print("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
    }
}
