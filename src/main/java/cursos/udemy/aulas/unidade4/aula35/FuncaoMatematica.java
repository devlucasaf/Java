package cursos.udemy.aulas.unidade4.aula35;

public class FuncaoMatematica {
    private void calculaRaizes(double a, double b, double c) {
        double delta = Math.pow(b, 2.0) - 4.0 * a * c;

        if (delta < 0) {
            System.out.println("A equação não possui raízes reais.");
        } else {
            double x1 = (-b + Math.sqrt(delta)) / (2.0 * a);
            double x2 = (-b - Math.sqrt(delta)) / (2.0 * a);

            System.out.println("Raiz 1 = " + x1);
            System.out.println("Raiz 2 = " + x2);
        }
    }

    private void calculaRaizQuadrada() {
        double x = 3.0;
        double y = 4.0;
        double z = -5.0;
        double a;
        double b;
        double c;

        a = Math.sqrt(x);
        b = Math.sqrt(y);
        c = Math.sqrt(25.0);
        System.out.println("Raiz quadrada de " + x + " = " + a);
        System.out.println("Raiz quadrada de " + y + " = " + b);
        System.out.println("Raiz quadrada de 25 = " + c);

        a = Math.pow(x, y);
        b = Math.pow(y, 2.0);
        c = Math.pow(5.0, 2.0);
        System.out.println(x + " elevado a " + y + " = " + a);
        System.out.println(y + " elevado ao quadrado = " + b);
        System.out.println("5 elevado ao quadrado = " + c);

        a = Math.abs(z);
        b = Math.abs(y);
        System.out.println("Valor absoluto de " + y + " = " + a);
        System.out.println("Valor absoluto de " + z + " = " + b);
    }

    public static void main(String[] args) {
        FuncaoMatematica matematica = new FuncaoMatematica();
        System.out.println("Exemplo 1: ");
        matematica.calculaRaizes(1.0, -3.0, 2.0);
        System.out.println("Exemplo 2: ");
        matematica.calculaRaizQuadrada();
    }
}
