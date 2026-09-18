package math.numerico.interpolacao;

import java.util.Locale;

public class PrincipalInterpolacao {

    public static void main(String[] args) {
        double[] x = {0, 1, 2, 3};
        double[] y = {1, 3, 2, 5};
        double ponto = 1.5;

        InterpoladorNewton interpolador = new InterpoladorNewton();
        double[][] tabela = interpolador.construirTabela(x, y);
        double valorInterpolado = interpolador.avaliar(x, y, ponto);

        System.out.println("==============================================");
        System.out.println("INTERPOLACAO POLINOMIAL DE NEWTON");
        System.out.println("==============================================");
        System.out.println("Ponto de avaliacao: " + formatar(ponto));
        System.out.println("Valor interpolado: " + formatar(valorInterpolado));
        System.out.println("\nTabela de diferencas divididas:");
        exibirTabela(tabela);
    }

    private static void exibirTabela(double[][] tabela) {
        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela.length - i; j++) {
                System.out.print(formatar(tabela[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    private static String formatar(double valor) {
        return String.format(Locale.US, "%.10f", valor);
    }
}
