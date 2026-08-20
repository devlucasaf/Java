package cursos.udemy.aulas.unidade5.aula47;

import java.util.Scanner;

public class CondicionalTernaria {
    private void exemploIfElse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um número: ");

        double preco = 34.5;
        double desconto;

        if (preco < 20.0) {
            desconto = preco * 0.1;
        } else {
            desconto = preco * 0.05;
        }

        System.out.print("Desconto: " + desconto);
        scanner.close();
    }

    private void exemploCondicionalTernaria() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um número: ");

        double preco = 34.5;
        double desconto = (preco < 20.0) ? preco * 0.1 : preco * 0.05;

        System.out.print("Desconto: " + desconto);
        scanner.close();
    }

    public static void main(String[] args) {
        CondicionalTernaria ternaria = new CondicionalTernaria();
        System.out.print("Exemplo com if/else:\n");
        ternaria.exemploIfElse();
        System.out.print("\nExemplo com condicional ternária:\n");
        ternaria.exemploCondicionalTernaria();
    }
}
