package math.calculadora.completa.model.calculos;

public class TabelaVerdade {

    public static String gerarTabela(String operacao) {
        StringBuilder builder = new StringBuilder();
        boolean usaC = operacao.contains("C");

        builder.append("A\tB");
        if (usaC) {
            builder.append("\tC");
        }
        builder.append("\t| Resultado\n");
        builder.append("----------------------------\n");

        int max = usaC ? 8 : 4;
        for (int i = 0; i < max; i++) {
            boolean a = (i & 4) != 0;
            boolean b = (i & 2) != 0;
            boolean c = (i & 1) != 0;
            if (!usaC && (i & 1) != 0) {
                continue;
            }
            boolean resultado = avaliar(operacao, a, b, c);
            builder.append((a ? 1 : 0)).append("\t").append((b ? 1 : 0));
            if (usaC) {
                builder.append("\t").append((c ? 1 : 0));
            }
            builder.append("\t| ")
                    .append(resultado ? 1 : 0)
                    .append("\n");
        }
        return builder.toString();
    }

    private static boolean avaliar(String op, boolean a, boolean b, boolean c) {
        switch (op) {
            case "A AND B":
                return a && b;
            case "A OR B":
                return a || b;
            case "A XOR B":
                return a ^ b;
            case "NOT A":
                return !a;
            case "A NAND B":
                return !(a && b);
            case "A NOR B":
                return !(a || b);
            case "A XNOR B":
                return !(a ^ b);
            case "(A AND B) OR C":
                return (a && b) || c;
            case "(A OR B) AND C":
                return (a || b) && c;
            case "A AND B AND C":
                return a && b && c;
            case "A OR B OR C":
                return a || b || c;
            default:
                return false;
        }
    }
}
