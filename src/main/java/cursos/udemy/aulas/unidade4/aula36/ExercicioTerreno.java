package cursos.udemy.aulas.unidade4.aula36;

import java.util.Locale;
import java.util.Scanner;

public class ExercicioTerreno {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        double largura;
        double comprimento;
        double valorMetro;
        double area;
        double preco;

        System.out.print("Digite a largura do terreno: ");
        largura = scanner.nextDouble();

        System.out.print("Digite o comprimento do terreno: ");
        comprimento = scanner.nextDouble();

        System.out.print("Digite o valor do metro quadrado: ");
        valorMetro = scanner.nextDouble();

        area = largura * comprimento;
        preco = area * valorMetro;

        System.out.printf("AREA = %.2f%n", area);
        System.out.printf("PRECO = %.2f%n", preco);

        scanner.close();
    }
}
