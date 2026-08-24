package math.calculadora.completa.model.calculos;

import java.util.ArrayList;
import java.util.List;

public class NumerosPrimos {

    public static boolean isNumeroPrimo(long n) {
        if (n < 2) {
            return false;
        }

        if (n % 2 == 0) {
            return n == 2;
        }

        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static List<Long> listarPrimosAte(long n) {
        List<Long> lista = new ArrayList<>();
        for (long i = 2; i <= n; i++) {
            if (isNumeroPrimo(i)) {
                lista.add(i);
            }
        }
        return lista;
    }

    public static String fatorar(long n) {
        if (n < 2) {
            return "Número deve ser >= 2";
        }

        StringBuilder builder = new StringBuilder("Fatoração de " + n + ":\n");
        long temp = n;
        for (long i = 2; i * i <= temp; i++) {
            int count = 0;
            while (temp % i == 0) {
                temp /= i;
                count++;
            }

            if (count > 0) {
                builder.append(i)
                        .append("^")
                        .append(count)
                        .append("  ");
            }
        }

        if (temp > 1) {
            builder.append(temp).append("^1");
        }
        return builder.toString().trim();
    }
}
