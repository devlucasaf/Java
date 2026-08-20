package udemy.aulas.unidade4.aula34;

import java.util.Scanner;

public class EntradaDados {
    private void exemplo1InformarDados() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do primeiro usuário: ");
        System.out.print("Digite o nome do segundo usuário: ");
        System.out.print("Digite o nome do terceiro usuário: ");

        String nome1;
        String nome2;
        String nome3;

        nome1 = scanner.nextLine();
        nome2 = scanner.nextLine();
        nome3 = scanner.nextLine();

        System.out.println(" --- DADOS DIGITADOS ---");
        System.out.println("Nome 1: " + nome1);
        System.out.println("Nome 2: " + nome2);
        System.out.println("Nome 3: " + nome3);

        scanner.close();
    }

    private void exemplo2QuebrarLinhaPendente() {
        Scanner scanner = new Scanner(System.in);

        int     x;
        String  nome1;
        String  nome2;
        String  nome3;

        x = scanner.nextInt();
        scanner.nextLine(); // Quebrar a linha pendente do nextInt()
        nome1 = scanner.nextLine();
        nome2 = scanner.nextLine();
        nome3 = scanner.nextLine();

        System.out.println(" --- DADOS DIGITADOS ---");
        System.out.println("Número: " + x);
        System.out.println("Nome 1: " + nome1);
        System.out.println("Nome 2: " + nome2);
        System.out.println("Nome 3: " + nome3);
    }

    public static void main(String[] args) {
        EntradaDados entradaDados = new EntradaDados();
        entradaDados.exemplo1InformarDados();
    }
}
