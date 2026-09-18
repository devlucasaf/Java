package math.numerico.integracao;

import java.util.function.DoubleUnaryOperator;

public class IntegradorNumerico {

    // --- CALCULA A INTEGRAL PELO MÉTODO DOS RETÂNGULOS ---
    public ResultadoIntegracao retangulos(DoubleUnaryOperator funcao, double a, double b, int subintervalos) {
        validarEntrada(funcao, a, b, subintervalos);
        long inicio = System.nanoTime();

        double h = (b - a) / subintervalos;
        double soma = 0;

        for (int i = 0; i < subintervalos; i++) {
            double pontoMedio = a + (i + 0.5) * h;
            soma += funcao.applyAsDouble(pontoMedio);
        }

        double resultado = soma * h;
        double tempoMs = (System.nanoTime() - inicio) / 1_000_000.0;
        return new ResultadoIntegracao(MetodoIntegracao.RETANGULOS, a, b, subintervalos, resultado, tempoMs);
    }

    // --- CALCULA A INTEGRAL PELO MÉTODO DO TRAPÉZIO ---
    public ResultadoIntegracao trapezio(DoubleUnaryOperator funcao, double a, double b, int subintervalos) {
        validarEntrada(funcao, a, b, subintervalos);
        long inicio = System.nanoTime();

        double h = (b - a) / subintervalos;
        double soma = 0.5 * (funcao.applyAsDouble(a) + funcao.applyAsDouble(b));

        for (int i = 1; i < subintervalos; i++) {
            double x = a + i * h;
            soma += funcao.applyAsDouble(x);
        }

        double resultado = soma * h;
        double tempoMs = (System.nanoTime() - inicio) / 1_000_000.0;
        return new ResultadoIntegracao(MetodoIntegracao.TRAPEZIO, a, b, subintervalos, resultado, tempoMs);
    }

    // --- CALCULA A INTEGRAL PELO MÉTODO DE SIMPSON 1/3 ---
    public ResultadoIntegracao simpson(DoubleUnaryOperator funcao, double a, double b, int subintervalos) {
        validarEntrada(funcao, a, b, subintervalos);
        if (subintervalos % 2 != 0) {
            throw new IllegalArgumentException("No método de Simpson, o número de subintervalos deve ser par.");
        }

        long inicio = System.nanoTime();
        double h = (b - a) / subintervalos;
        double soma = funcao.applyAsDouble(a) + funcao.applyAsDouble(b);

        for (int i = 1; i < subintervalos; i++) {
            double x = a + i * h;
            soma += (i % 2 == 0 ? 2.0 : 4.0) * funcao.applyAsDouble(x);
        }

        double resultado = (h / 3.0) * soma;
        double tempoMs = (System.nanoTime() - inicio) / 1_000_000.0;
        return new ResultadoIntegracao(MetodoIntegracao.SIMPSON, a, b, subintervalos, resultado, tempoMs);
    }

    // --- VALIDA A ENTRADA COMUM DOS MÉTODOS ---
    private void validarEntrada(DoubleUnaryOperator funcao, double a, double b, int subintervalos) {
        if (funcao == null) {
            throw new IllegalArgumentException("A função não pode ser nula.");
        }
        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            throw new IllegalArgumentException("Os limites da integral devem ser valores numéricos finitos.");
        }
        if (a >= b) {
            throw new IllegalArgumentException("O limite inferior deve ser menor que o limite superior.");
        }
        if (subintervalos <= 0) {
            throw new IllegalArgumentException("A quantidade de subintervalos deve ser maior que zero.");
        }
    }
}
