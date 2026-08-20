package cursos.udemy.aulas.unidade6.exercicios.parte1.questao3;

import java.util.Scanner;

public class PreferenciaCombustivel {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int alcool = 0;
        int gasolina = 0;
        int diesel = 0;
        int codigo;

        do {
            codigo = scanner.nextInt();

            switch (codigo) {
                case 1:
                    alcool++;
                    break;
                case 2:
                    gasolina++;
                    break;
                case 3:
                    diesel++;
                    break;
                case 4:
                    break;
                default:
                    break;
            }

        } while (codigo != 4);

        System.out.println("MUITO OBRIGADO");
        System.out.println("Álcool: " + alcool);
        System.out.println("Gasolina: " + gasolina);
        System.out.println("Diesel: " + diesel);

        scanner.close();
    }
}
