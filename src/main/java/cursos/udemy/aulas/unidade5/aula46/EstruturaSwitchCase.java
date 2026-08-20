package cursos.udemy.aulas.unidade5.aula46;

import java.util.Scanner;

public class EstruturaSwitchCase {
    private void exemploIfElse() {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        String dia;

        if (x == 1) {
            dia = "Domingo";
        } else if (x == 2) {
            dia = "Segunda-feira";
        } else if (x == 3) {
            dia = "Terça-feira";
        } else if (x == 4) {
            dia = "Quarta-feira";
        } else if (x == 5) {
            dia = "Quinta-feira";
        } else if (x == 6) {
            dia = "Sexta-feira";
        } else if (x == 7) {
            dia = "Sábado";
        } else {
            dia = "Valor inválido!";
        }

        System.out.println("Dia da semana: " + dia);
        scanner.close();
    }

    private void exemploSwitchCase() {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        String dia;

        switch (x) {
            case 1:
                dia = "Domingo";
                break;
            case 2:
                dia = "Segunda-feira";
                break;
            case 3:
                dia = "Terça-feira";
                break;
            case 4:
                dia = "Quarta-feira";
                break;
            case 5:
                dia = "Quinta-feira";
                break;
            case 6:
                dia = "Sexta-feira";
                break;
            case 7:
                dia = "Sábado";
                break;
            default:
                dia = "Valor inválido!";
                break;
        }

        System.out.println("Dia da semana: " + dia);
        scanner.close();
    }

    public static void main(String[] args) {
        EstruturaSwitchCase switchCase = new EstruturaSwitchCase();
        System.out.println("Exemplo 1 (if, else if e else): ");
        switchCase.exemploIfElse();
        System.out.println("Exemplo 1 (switch case): ");
        switchCase.exemploSwitchCase();
    }
}
