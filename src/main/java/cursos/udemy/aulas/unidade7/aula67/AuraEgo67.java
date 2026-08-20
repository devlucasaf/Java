package cursos.udemy.aulas.unidade7.aula67;

import java.util.Scanner;

public class AuraEgo67 {
    /*
    * Este programa calcula as raízes de uma equação do segundo grau
    * Os valores dos coeficientes devem ser digitados um por linha
    * */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double a;
        double b;
        double c;
        double delta;

        System.out.println("Digite os valores dos coeficientes:");
        a = scanner.nextDouble();
        b = scanner.nextDouble();
        c = scanner.nextDouble();

        delta = b * b - 4 * a * c;
        System.out.println(delta); // cálculo do valor de delta
    }
}
