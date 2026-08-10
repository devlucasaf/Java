package math.calculadora.completa.model.calculos;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {

    public static List<Long> gerarSequencia(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N deve ser >= 0");
        }

        List<Long> sequencia = new ArrayList<>(n+1);
        if (n == 0) {
            sequencia.add(0L);
            return sequencia;
        }

        sequencia.add(0L);
        sequencia.add(1L);
        for (int i = 2; i <= n; i++) {
            sequencia.add(sequencia.get(i-1) + sequencia.get(i-2));
        }
        return sequencia;
    }

    public static boolean isFibonacci(long num) {
        if (num < 0) {
            return false;
        }

        long a = 0;
        long b = 1;
        if (num == a || num == b) {
            return true;
        }

        long c = a + b;
        while (c <= num) {
            if (c == num) {
                return true;
            }
            a = b;
            b = c;
            c = a + b;
        }
        return false;
    }
}
