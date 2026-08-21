package cursos.udemy.aulas.unidade8.exercicios.questao1;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        Funcionario funcionario = new Funcionario();

        System.out.print("Nome: ");
        funcionario.nome = scanner.nextLine();

        System.out.print("Salário bruto: R$ ");
        funcionario.salarioBruto = scanner.nextDouble();

        System.out.print("Imposto: R$ ");
        funcionario.imposto = scanner.nextDouble();

        System.out.println();
        System.out.println("Funcionário: " + funcionario);

        System.out.println();
        System.out.print("Digite a porcentagem para aumentar o salário: ");
        double porcentagem = scanner.nextDouble();

        funcionario.aumentarSalario(porcentagem);

        System.out.println();
        System.out.println("Dados atualizados: " + funcionario);

        scanner.close();
    }
}

