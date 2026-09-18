package math.numerico.integracao;

import java.util.Locale;
import java.util.function.DoubleUnaryOperator;

public class PrincipalIntegracao {

    public static void main(String[] args) {
        IntegradorNumerico integrador = new IntegradorNumerico();

        DoubleUnaryOperator funcao = x -> x * x;
        double limiteInferior = 0.0;
        double limiteSuperior = 2.0;
        int subintervalos = 100;
        double valorExato = 8.0 / 3.0;

        ResultadoIntegracao rRetangulos = integrador.retangulos(funcao, limiteInferior, limiteSuperior, subintervalos);
        ResultadoIntegracao rTrapezio = integrador.trapezio(funcao, limiteInferior, limiteSuperior, subintervalos);
        ResultadoIntegracao rSimpson = integrador.simpson(funcao, limiteInferior, limiteSuperior, subintervalos);

        exibir(rRetangulos, valorExato);
        exibir(rTrapezio, valorExato);
        exibir(rSimpson, valorExato);
    }

    private static void exibir(ResultadoIntegracao resultado, double valorExato) {
        System.out.println("\n==============================================");
        System.out.println("METODO: " + resultado.getMetodo());
        System.out.println("Integral aproximada: " + formatar(resultado.getValorIntegral()));
        System.out.println("Erro absoluto: " + formatar(Math.abs(resultado.getValorIntegral() - valorExato)));
        System.out.println("Tempo (ms): " + formatar(resultado.getTempoExecucaoMilissegundos()));
    }

    private static String formatar(double valor) {
        return String.format(Locale.US, "%.12f", valor);
    }
}
