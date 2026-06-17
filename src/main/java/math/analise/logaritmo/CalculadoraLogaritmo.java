package math.analise.logaritmo;

import java.util.Scanner;
import java.text.DecimalFormat;

public class CalculadoraLogaritmo {
    public static double calcularLogaritmo(double numero, double base) {
        if (numero <= 0) {
            throw new IllegalArgumentException("O número deve ser maior que zero.");
        }

        if (base <= 0) {
            throw new IllegalArgumentException("A base deve ser maior que zero.");
        }

        if (base == 1) {
            throw new IllegalArgumentException("A base não pode ser igual a 1.");
        }
        return Math.log(numero) / Math.log(base);
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        DecimalFormat formatador = new DecimalFormat("#0.0000");

        System.out.println("=== CALCULADORA DE LOGARITMOS ===");

        try {
            System.out.print("Digite o número (positivo): ");
            double numero = entrada.nextDouble();

            System.out.print("Digite a base (positiva e diferente de 1): ");
            double base = entrada.nextDouble();

            double resultado = calcularLogaritmo(numero, base);

            System.out.println("\nRESULTADO:");
            System.out.println("log_" + base + "(" + numero + ") = " + formatador.format(resultado));

        } catch (IllegalArgumentException erro) {
            System.err.println("Erro: " + erro.getMessage());
        } catch (Exception erro) {
            System.err.println("Erro: Entrada inválida. Certifique-se de digitar números válidos.");
        } finally {
            entrada.close();
        }
    }
}