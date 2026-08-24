package math.calculadora.binario;

import java.util.Scanner;

public class CalculadoraBinariaTerminal {

    private static Scanner scanner = new Scanner(System.in);

    public static String converterParaBinario(int numero) {
        if (numero == 0) {
            return "0";
        }
        boolean negativo = numero < 0;
        int valorAbsoluto = Math.abs(numero);
        StringBuilder binario = new StringBuilder();
        while (valorAbsoluto > 0) {
            int resto = valorAbsoluto % 2;
            binario.insert(0, resto);
            valorAbsoluto /= 2;
        }

        if (negativo) {
            binario.insert(0, "-");
        }
        return binario.toString();
    }

    public static void exibirMenu() {
        System.out.println("\n========== CALCULADORA BINÁRIA ==========");
        System.out.println("1 - Somar dois números");
        System.out.println("2 - Subtrair dois números");
        System.out.println("3 - Multiplicar dois números");
        System.out.println("4 - Dividir dois números");
        System.out.println("5 - Converter um número decimal para binário");
        System.out.println("6 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static int lerNumero(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Por favor, insira um número inteiro.");
            }
        }
    }

    public static void realizarOperacao(int opcao) {
        if (opcao >= 1 && opcao <= 4) {
            int num1 = lerNumero("Digite o primeiro número: ");
            int num2 = lerNumero("Digite o segundo número: ");
            int resultado = 0;
            String nomeOperacao = "";

            switch (opcao) {
                case 1:
                    resultado = num1 + num2;
                    nomeOperacao = "Soma";
                    break;
                case 2:
                    resultado = num1 - num2;
                    nomeOperacao = "Subtração";
                    break;
                case 3:
                    resultado = num1 * num2;
                    nomeOperacao = "Multiplicação";
                    break;
                case 4:
                    if (num2 == 0) {
                        System.out.println("Erro: Divisão por zero não é permitida.");
                        return;
                    }
                    resultado = num1 / num2;
                    nomeOperacao = "Divisão (inteira)";
                    break;
            }

            String binario = converterParaBinario(resultado);
            System.out.println("\n" + nomeOperacao + " - Resultado:");
            System.out.println("  Decimal: " + resultado);
            System.out.println("  Binário: " + binario);
        } else if (opcao == 5) {
            int numero = lerNumero("Digite o número decimal para converter: ");
            String binario = converterParaBinario(numero);
            System.out.println("\nConversão:");
            System.out.println("  Decimal: " + numero);
            System.out.println("  Binário: " + binario);
        } else {
            System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
                if (opcao == 6) {
                    System.out.println("Saindo...");
                    break;
                }
                realizarOperacao(opcao);
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite um número de 1 a 6.");
                opcao = 0;
            }
        }
        while (opcao != 6);

        scanner.close();
    }
}

