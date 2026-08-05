package math.calculadora.completa.model.calculos;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {

    public static List<Long> gerarSequencia(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N deve ser >= 0");
        }

        List<Long> seq = new ArrayList<>(n+1);
        if (n == 0) {
            seq.add(0L);
            return seq;
        }

        seq.add(0L);
        seq.add(1L);
        for (int i = 2; i <= n; i++) {
            seq.add(seq.get(i-1) + seq.get(i-2));
        }
        return seq;
    }

    public static boolean isFibonacci(long num) {
        if (num < 0) {
            return false;
        }
        long a = 0, b = 1;
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
