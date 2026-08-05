package math.calculadora.completa.model.calculos;

import java.util.ArrayList;
import java.util.List;

public class NumerosPrimos {

    public static boolean isPrime(long n) {
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
            if (isPrime(i)) {
                lista.add(i);
            }
        }
        return lista;
    }

    public static String fatorar(long n) {
        if (n < 2) {
            return "Número deve ser >= 2";
        }
        StringBuilder sb = new StringBuilder("Fatoração de " + n + ":\n");
        long temp = n;
        for (long i = 2; i * i <= temp; i++) {
            int count = 0;
            while (temp % i == 0) {
                temp /= i;
                count++;
            }

            if (count > 0) {
                sb.append(i).append("^").append(count).append("  ");
            }
        }

        if (temp > 1) {
            sb.append(temp).append("^1");
        }
        return sb.toString().trim();
    }
}
