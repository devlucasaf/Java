package udemy.aulas.unidade4.aula33;

import java.util.Locale;
import java.util.Scanner;

public class EntradaDados {
    private void exemplo1ScannerString() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite seu nome: ");

        String x;
        x = scanner.next();
        System.out.println("Você digitou: " + x);
    }

    private void exemplo2ScannerInt() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um numero: ");

        int x;
        x = scanner.nextInt();
        System.out.println("Você digitou: " + x);
    }

    private void exemplo3ScannerDouble() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um numero decimal: ");
        Locale.setDefault(Locale.US);

        double x;
        x = scanner.nextDouble();
        System.out.println("Você digitou: " + x);
    }

    private void exemplo4ScannerChar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um caractere: ");

        char x;
        x = scanner.next().charAt(0);
        System.out.println("Você digitou: " + x);
    }

    private void exemplo5ScannerDiferentesDados() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um nome: ");
        System.out.print("Digite um número: ");
        System.out.print("Digite um número decimal: ");

        String  x;
        int     y;
        double  z;

        x = scanner.next();
        y = scanner.nextInt();
        z = scanner.nextDouble();

        System.out.println("Dados digitados: ");
        System.out.println("String: " + x);
        System.out.println("Int: " + y);
        System.out.println("Double: " + z);
    }

    private void exemploExtra1ScannerFloat() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um numero decimal (float): ");

        float x;
        x = scanner.nextFloat();
        System.out.println("Você digitou: " + x);
    }

    private void exemploExtra2ScannerBoolean() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite true ou false: ");

        boolean x;
        x = scanner.nextBoolean();
        System.out.println("Você digitou: " + x);
    }

    public static void main(String[] args) {
        EntradaDados leitura = new EntradaDados();
        System.out.println("Exemplo 1 (String): ");
        leitura.exemplo1ScannerString();
        System.out.println("Exemplo 2 (int): ");
        leitura.exemplo2ScannerInt();
        System.out.println("Exemplo 3 (double): ");
        leitura.exemplo3ScannerDouble();
        System.out.println("Exemplo 4 (char): ");
        leitura.exemplo4ScannerChar();
        System.out.println("Exemplo 5 (diferentes dados): ");
        leitura.exemplo5ScannerDiferentesDados();
        System.out.println("Exemplo Extra 1 (float): ");
        leitura.exemploExtra1ScannerFloat();
        System.out.println("Exemplo Extra 2 (boolean): ");
        leitura.exemploExtra2ScannerBoolean();
    }
}
