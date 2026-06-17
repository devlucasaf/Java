package math.sequencia.fatorial;

import java.math.BigInteger;
import java.util.Scanner;

public class CalculadoraFatorial {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("Digite um número inteiro não negativo: ");
        int numero = entrada.nextInt();

        if (numero < 0) {
            System.out.println("Erro: O fatorial não está definido para números negativos.");
        } else {
            BigInteger numeroBig = BigInteger.valueOf(numero);
            BigInteger fatorialIterativo = calcularFatorialIterativo(numeroBig);
            System.out.println("Resultado iterativo: " + numero + "! = " + fatorialIterativo);

            BigInteger fatorialRecursivo = calcularFatorialRecursivo(numeroBig);
            System.out.println("Resultado recursivo: " + numero + "! = " + fatorialRecursivo);
        }

        entrada.close();
    }

    public static BigInteger calcularFatorialIterativo(BigInteger n) {
        BigInteger resultado = BigInteger.ONE;
        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(n) <= 0; i = i.add(BigInteger.ONE)) {
            resultado = resultado.multiply(i);
        }
        return resultado;
    }

    public static BigInteger calcularFatorialRecursivo(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) <= 0) {
            return BigInteger.ONE;
        }
        return n.multiply(calcularFatorialRecursivo(n.subtract(BigInteger.ONE)));
    }
}