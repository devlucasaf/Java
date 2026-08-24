package cursos.udemy.aulas.unidade9.exercicios.entities;

import cursos.udemy.aulas.unidade9.exercicios.application.ContaBancaria;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    // --- METODO PRINCIPAL DO PROGRAMA ---
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o número da conta: ");
        int numeroConta = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Digite o nome do titular: ");
        String nomeTitular = scanner.nextLine();

        System.out.print("Haverá depósito inicial (s/n)? ");
        char resposta = scanner.next().toLowerCase().charAt(0);

        ContaBancaria conta;

        if (resposta == 's') {
            System.out.print("Digite o valor do depósito inicial: R$ ");
            double depositoInicial = scanner.nextDouble();

            conta = new ContaBancaria(numeroConta, nomeTitular, depositoInicial);
        } else {
            conta = new ContaBancaria(numeroConta, nomeTitular);
        }

        System.out.println();
        System.out.println("Dados da conta:");
        System.out.println(conta);

        System.out.println();
        System.out.print("Digite um valor para depósito: R$ ");
        double valorDeposito = scanner.nextDouble();
        conta.depositar(valorDeposito);

        System.out.println("Dados atualizados da conta:");
        System.out.println(conta);

        System.out.println();
        System.out.print("Digite um valor para saque: R$ ");
        double valorSaque = scanner.nextDouble();
        conta.sacar(valorSaque);

        System.out.println("Dados atualizados da conta:");
        System.out.println(conta);

        scanner.close();
    }
}
