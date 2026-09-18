package games.strategy.fronteira;

import java.util.Random;
import java.util.Scanner;

public class PrincipalControleFronteira {

    private static final Scanner LEITOR = new Scanner(System.in);
    private static final Random ALEATORIO = new Random();

    public static void main(String[] args) {
        int defesa = 60;
        int diplomacia = 50;
        int orcamento = 70;
        int estabilidade = 65;

        System.out.println("=== CONTROLE DE FRONTEIRA ===");

        for (int turno = 1; turno <= 12; turno++) {
            System.out.println("\nTurno " + turno);
            System.out.println("Defesa: " + defesa);
            System.out.println("Diplomacia: " + diplomacia);
            System.out.println("Orcamento: " + orcamento);
            System.out.println("Estabilidade: " + estabilidade);
            System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
            System.out.println("1) Reforcar patrulha (-12 Orcamento, +10 Defesa)");
            System.out.println("2) Acordo diplomatico (-8 Orcamento, +11 Diplomacia)");
            System.out.println("3) Incentivo economico (+10 Orcamento, +5 Estabilidade)");
            int acao = lerInteiro("Escolha: ", 1, 3);

            if (acao == 1) {
                orcamento -= 12;
                defesa += 10;
            } else if (acao == 2) {
                orcamento -= 8;
                diplomacia += 11;
            } else {
                orcamento += 10;
                estabilidade += 5;
            }

            int evento = ALEATORIO.nextInt(4);
            if (evento == 0) {
                System.out.println("Evento: tensão militar.");
                defesa -= 12;
                diplomacia -= 6;
                estabilidade -= 5;
            } else if (evento == 1) {
                System.out.println("Evento: fluxo migratório intenso.");
                orcamento -= 7;
                estabilidade -= 8;
                diplomacia += 3;
            } else if (evento == 2) {
                System.out.println("Evento: investimento internacional.");
                orcamento += 12;
                diplomacia += 5;
            } else {
                System.out.println("Evento: cooperação regional.");
                defesa += 4;
                diplomacia += 8;
                estabilidade += 4;
            }

            defesa = limitar(defesa);
            diplomacia = limitar(diplomacia);
            estabilidade = limitar(estabilidade);
            orcamento = limitar(orcamento);

            if (defesa == 0 || estabilidade == 0 || orcamento == 0) {
                System.out.println("Crise fronteirica. Governo perdeu o controle.");
                return;
            }
        }

        int indice = defesa + diplomacia + estabilidade + orcamento;
        System.out.println("\nIndice final de governanca: " + indice);
        if (indice >= 260) {
            System.out.println("Excelente gestao de fronteira.");
        } else if (indice >= 210) {
            System.out.println("Gestao estavel com desafios controlados.");
        } else {
            System.out.println("Gestao fragil. Muitos riscos permanecem.");
        }
    }

    private static int limitar(int valor) {
        if (valor < 0) {
            return 0;
        }
        return Math.min(valor, 100);
    }

    private static int lerInteiro(String mensagem, int minimo, int maximo) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(LEITOR.nextLine().trim());
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Valor invalido.");
        }
    }
}
