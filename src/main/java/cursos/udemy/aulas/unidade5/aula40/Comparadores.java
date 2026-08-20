package cursos.udemy.aulas.unidade5.aula40;

public class Comparadores {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;

        System.out.println("a = " + a + ", b = " + b);

        // Maior
        if (a > b) {
            System.out.println("a é maior que b");
        } else {
            System.out.println("a não é maior que b");
        }

        // Menor
        if (a < b) {
            System.out.println("a é menor que b");
        } else {
            System.out.println("a não é menor que b");
        }

        // Maior ou igual
        if (a >= b) {
            System.out.println("a é maior ou igual a b");
        } else {
            System.out.println("a não é maior ou igual a b");
        }

        // Menor ou igual
        if (a <= b) {
            System.out.println("a é menor ou igual a b");
        } else {
            System.out.println("a não é menor ou igual a b");
        }

        // Igual
        if (a == b) {
            System.out.println("a é igual a b");
        } else {
            System.out.println("a não é igual a b");
        }

        // Diferente
        if (a != b) {
            System.out.println("a é diferente de b");
        } else {
            System.out.println("a não é diferente de b");
        }
    }
}
