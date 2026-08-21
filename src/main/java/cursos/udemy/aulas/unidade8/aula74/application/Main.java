package cursos.udemy.aulas.unidade8.aula74.application;

import cursos.udemy.aulas.unidade8.aula74.entities.Produto;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        Produto produto = new Produto();
        System.out.println("Entre os dados do produto:");
        System.out.print("Nome: ");
        produto.nome = scanner.nextLine();
        System.out.print("Preço: ");
        produto.preco = scanner.nextDouble();
        System.out.print("Quantidade no estoque: ");
        produto.quantidade = scanner.nextInt();

        System.out.println("Dados do produto: " + produto.nome);
        System.out.println("Preço do produto: " + produto.preco);
        System.out.println("Quantidade no estoque: " + produto.quantidade);
        scanner.close();
    }
}
