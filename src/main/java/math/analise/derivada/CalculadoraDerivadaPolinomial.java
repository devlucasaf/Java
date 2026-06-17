package math.analise.derivada;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculadoraDerivadaPolinomial {
    public static void main(String[] argumentos) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("--- CÁLCULO DA DERIVADA DE UM POLINÔMIO ---");

        System.out.print("Digite o grau do polinômio: ");
        int grau = entrada.nextInt();

        List<Double> coeficientes = new ArrayList<>(grau + 1);

        for (int i = grau; i >= 0; i--) {
            System.out.print("Digite o coeficiente para x^" + i + ": ");
            double coeficiente = entrada.nextDouble();
            coeficientes.add(coeficiente);
        }

        System.out.println("\nPolinômio original:");
        System.out.println(formatarPolinomio(coeficientes, grau));

        List<Double> coeficientesDerivada = calcularDerivada(coeficientes, grau);
        int novoGrau = grau - 1;

        System.out.println("\nDerivada do polinômio:");
        if (novoGrau < 0) {
            System.out.println("0");
        } else {
            System.out.println(formatarPolinomio(coeficientesDerivada, novoGrau));
        }

        entrada.close();
    }

    public static List<Double> calcularDerivada(List<Double> coeficientes, int grau) {
        List<Double> derivada = new ArrayList<>();
        for (int i = 0; i <= grau; i++) {
            double coefOriginal = coeficientes.get(i);
            int expoente = grau - i; // expoente real do termo
            if (expoente > 0) {
                double novoCoeficiente = expoente * coefOriginal;
                derivada.add(novoCoeficiente);
            }
        }
        return derivada;
    }

    public static String formatarPolinomio(List<Double> coeficientes, int grau) {
        StringBuilder polinomioStr = new StringBuilder();
        boolean primeiroTermo = true;

        for (int i = 0; i <= grau; i++) {
            double coeficiente = coeficientes.get(i);
            int expoente = grau - i;

            if (Math.abs(coeficiente) < 1e-10) {
                continue;
            }

            if (!primeiroTermo) {
                polinomioStr.append(coeficiente > 0 ? " + " : " - ");
            } else if (coeficiente < 0) {
                polinomioStr.append("-");
            }

            double valorAbsoluto = Math.abs(coeficiente);

            if (expoente == 0) {
                polinomioStr.append(formatarNumero(valorAbsoluto));
            } else if (expoente == 1) {
                if (Math.abs(valorAbsoluto - 1.0) < 1e-10) {
                    polinomioStr.append("x");
                } else {
                    polinomioStr.append(formatarNumero(valorAbsoluto)).append("x");
                }
            } else {
                if (Math.abs(valorAbsoluto - 1.0) < 1e-10) {
                    polinomioStr.append("x^").append(expoente);
                } else {
                    polinomioStr.append(formatarNumero(valorAbsoluto)).append("x^").append(expoente);
                }
            }

            primeiroTermo = false;
        }

        if (primeiroTermo) {
            return "0";
        }
        return polinomioStr.toString();
    }

    public static String formatarNumero(double valor) {
        if (valor == (long) valor) {
            return String.valueOf((long) valor);
        } else {
            return String.valueOf(valor);
        }
    }
}