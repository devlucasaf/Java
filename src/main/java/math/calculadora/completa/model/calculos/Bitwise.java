package math.calculadora.completa.model.calculos;

public class Bitwise {

    public static long executar(String operacao, long a, long b) {
        switch (operacao) {
            case "AND":
                return a & b;
            case "OR":
                return a | b;
            case "XOR":
                return a ^ b;
            case "NOT (A)":
                return (~a) & 0xFFFFFFFFL;
            case "A << B":
                return a << b;
            case "A >> B":
                return a >> b;
            default:
                throw new IllegalArgumentException("Operação inválida: " + operacao);
        }
    }
}
