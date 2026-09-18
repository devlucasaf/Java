package math.numerico.sistemaslineares;

import java.util.Locale;

public class PrincipalSistemasLineares {

    public static void main(String[] args) {
        double[][] a = {
                {2, 1, -1},
                {-3, -1, 2},
                {-2, 1, 2}
        };
        double[] b = {8, -11, -3};

        SolucionadorGauss solucionador = new SolucionadorGauss();
        ResultadoSistemaLinear resultado = solucionador.resolver(a, b);

        System.out.println("==============================================");
        System.out.println("RESOLUCAO DE SISTEMAS LINEARES (GAUSS)");
        System.out.println("==============================================");
        double[] x = resultado.getSolucao();
        for (int i = 0; i < x.length; i++) {
            System.out.println("x" + (i + 1) + " = " + formatar(x[i]));
        }
        System.out.println("Determinante aproximado de A: " + formatar(resultado.getDeterminanteAproximado()));
    }

    private static String formatar(double valor) {
        return String.format(Locale.US, "%.10f", valor);
    }
}
