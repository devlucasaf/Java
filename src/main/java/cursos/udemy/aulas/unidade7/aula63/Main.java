package cursos.udemy.aulas.unidade7.aula63;

import java.util.Locale;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        double largura = scanner.nextDouble();
        double comprimento = scanner.nextDouble();
        double metroQuadrado = scanner.nextDouble();
        double area = largura * comprimento;
        double preco = area * metroQuadrado;

        System.out.printf("Área = %.2f%n", area);
        System.out.printf("Preço = %.2f%n", preco);

        scanner.close();
    }
}
