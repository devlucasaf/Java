package cursos.udemy.aulas.unidade8.exercicios.questao2;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Qual é a cotação do dólar? ");
        double cotacaoDolar = scanner.nextDouble();

        System.out.print("Quantos dólares serão comprados? ");
        double quantidadeDolares = scanner.nextDouble();

        double valorAPagar = ConversorDeMoeda.converterParaReais(cotacaoDolar, quantidadeDolares);

        System.out.printf("Valor a ser pago em reais = R$ %.2f%n", valorAPagar);

        scanner.close();
    }
}

