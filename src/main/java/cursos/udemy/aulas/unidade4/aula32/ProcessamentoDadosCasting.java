package cursos.udemy.aulas.unidade4.aula32;

public class ProcessamentoDadosCasting {
    private void primeiroTeste() {
        int x;
        int y;

        x = 5;
        y = 2 * x;

        System.out.println(x);
        System.out.println(y);
    }

    private void exemplo2() {
        int x;
        double y;

        x = 5;
        y = 2 * x;

        System.out.println(x);
        System.out.println(y);
    }

    private void exemplo3() {
        double b; // base menor
        double h; // altura
        double B; // base maior
        double area;

        b = 6.0;
        h = 5.0;
        B = 8.0;

        area = (b + B) * h / 2.0;

        System.out.println(area);
    }

    private void exemplo4() {
        int     a;
        int     b;
        double  resultado;

        a = 5;
        b = 2;

        resultado = (double) a / b;

        System.out.println(resultado);
    }

    private void exemplo5() {
        double  a;
        int     b;

        a = 5.0;
        b = (int) a;

        System.out.println(b);
    }

    public static void main(String[] args) {
        ProcessamentoDadosCasting processamento = new ProcessamentoDadosCasting();
        System.out.println("1. Exemplo 1 (int): ");
        processamento.primeiroTeste();
        System.out.println("2. Exemplo 2 (int e double): ");
        processamento.exemplo2();
        System.out.println("3. Exemplo 3 (área do trapézio): ");
        processamento.exemplo3();
        System.out.println("4. Exemplo 4 (divisão de inteiros): ");
        processamento.exemplo4();
        System.out.println("5. Exemplo 5 (casting de double para int): ");
        processamento.exemplo5();
    }
}
