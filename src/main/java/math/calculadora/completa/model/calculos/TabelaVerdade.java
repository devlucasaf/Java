package math.calculadora.completa.model.calculos;

public class TabelaVerdade {

    public static String gerarTabela(String operacao) {
        StringBuilder sb = new StringBuilder();
        boolean usaC = operacao.contains("C");

        sb.append("A\tB");
        if (usaC) {
            sb.append("\tC");
        }
        sb.append("\t| Resultado\n");
        sb.append("----------------------------\n");

        int max = usaC ? 8 : 4;
        for (int i = 0; i < max; i++) {
            boolean a = (i & 4) != 0;
            boolean b = (i & 2) != 0;
            boolean c = (i & 1) != 0;
            if (!usaC && (i & 1) != 0) {
                continue;
            }
            boolean resultado = avaliar(operacao, a, b, c);
            sb.append((a ? 1 : 0)).append("\t").append((b ? 1 : 0));
            if (usaC) {
                sb.append("\t").append((c ? 1 : 0));
            }
            sb.append("\t| ").append(resultado ? 1 : 0).append("\n");
        }
        return sb.toString();
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
