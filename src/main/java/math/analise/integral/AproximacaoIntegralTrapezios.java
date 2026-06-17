package math.analise.integral;

import java.util.Scanner;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class AproximacaoIntegralTrapezios {
    private static ScriptEngine motorJs;

    static {
        ScriptEngineManager gerenciador = new ScriptEngineManager();
        motorJs = gerenciador.getEngineByName("JavaScript");
    }

    public static double avaliarFuncao(String expressao, double x) throws ScriptException {
        motorJs.put("x", x);
        Object resultado = motorJs.eval(expressao);
        if (resultado instanceof Number) {
            return ((Number) resultado).doubleValue();
        } else {
            throw new ScriptException("Expressão não retornou um número válido.");
        }
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("=== APROXIMAÇÃO DE INTEGRAL DEFINIDA - MÉTODO DOS TRAPÉZIOS ===\n");

        System.out.print("Digite a função polinomial (use 'x' como variável): ");
        System.out.println("Exemplos: 2*x**2 + 3*x - 5   ou   Math.sin(x) + x**3");
        System.out.print("Função: ");
        String funcao = entrada.nextLine();

        funcao = funcao.replace('^', '*').replace("**", "**"); // garante que '^' vire '**'

        System.out.print("\nLimite inferior (a): ");
        double a = entrada.nextDouble();
        System.out.print("Limite superior (b): ");
        double b = entrada.nextDouble();

        System.out.print("Número de subdivisões (n): ");
        int n = entrada.nextInt();

        if (n <= 0) {
            System.out.println("ERRO: O número de subdivisões deve ser positivo.");
            entrada.close();
            return;
        }

        double h = (b - a) / n;
        double soma = 0.0;

        try {
            double fa = avaliarFuncao(funcao, a);
            double fb = avaliarFuncao(funcao, b);
            soma = fa + fb;

            for (int i = 1; i <= n - 1; i++) {
                double xi = a + i * h;
                double fxi = avaliarFuncao(funcao, xi);
                soma += 2 * fxi;
            }

            double resultado = (h / 2.0) * soma;

            System.out.printf("\nRESULTADO:\n");
            System.out.printf("Integral aproximada de %.6f até %.6f = %.8f\n", a, b, resultado);
            System.out.printf("(com %d subdivisão(ões), passo h = %.6f)\n", n, h);

        } catch (ScriptException e) {
            System.out.println("\nERRO ao avaliar a função:");
            System.out.println(e.getMessage());
            System.out.println("Verifique a sintaxe da expressão. Use '**' para potência (ex: x**2)");
            System.out.println("ou funções como Math.sin(x), Math.cos(x), Math.exp(x), etc.");
        }

        entrada.close();
    }
}
