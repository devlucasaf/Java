package org.games.sorteio.probabilidades;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class SimuladorProbabilidades {

    private final Scanner entrada = new Scanner(System.in);
    private final NumberFormat formatador = NumberFormat.getInstance(new Locale("pt", "BR"));

    public static void main(String[] args) {
        new SimuladorProbabilidades().iniciar();
    }

    public void iniciar() {
        System.out.println();
        System.out.println("===================================================");
        System.out.println("            SIMULADOR DE PROBABILIDADES            ");
        System.out.println("          Loterias da Caixa - Probabilidade        ");
        System.out.println("===================================================");

        boolean executando = true;
        while (executando) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Mostrar tabela comparativa de todas as loterias");
            System.out.println("2. Detalhar uma loteria específica");
            System.out.println("3. Simular N apostas e estimar ganhos");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            String op = entrada.nextLine().trim();

            switch (op) {
                case "1" -> exibirTabela();
                case "2" -> detalharLoteria();
                case "3" -> simularApostas();
                case "0" -> executando = false;
                default  -> System.out.println("⚠ Opção inválida.");
            }
        }
        System.out.println("Encerrado.");
    }

    private void exibirTabela() {
        System.out.println("\n┌─────────────┬────────────┬──────────────────────┬──────────────────┐");
        System.out.printf ("│ %-11s │ %-10s │ %-20s │ %-16s │%n",
                "Loteria", "Combinações", "Probabilidade", "1 chance em");
        System.out.println("├─────────────┼────────────┼──────────────────────┼──────────────────┤");
        for (Loteria l : Loteria.values()) {
            BigInteger c = l.combinacoes();
            double p = l.probabilidade();
            System.out.printf("│ %-11s │ %10s │ %18.12f%% │ 1 em %11s │%n",
                    l.getNome(),
                    formatarCompacto(c),
                    p * 100,
                    formatador.format(c));
        }
        System.out.println("└─────────────┴────────────┴──────────────────────┴──────────────────┘");
    }

    private void detalharLoteria() {
        System.out.println("\nEscolha uma loteria:");
        Loteria[] todas = Loteria.values();
        for (int i = 0; i < todas.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, todas[i].getNome());
        }
        System.out.print("Opção: ");
        try {
            int op = Integer.parseInt(entrada.nextLine().trim());
            if (op < 1 || op > todas.length) {
                System.out.println("Opção fora do intervalo.");
                return;
            }
            Loteria l = todas[op - 1];
            BigInteger c = l.combinacoes();

            System.out.printf("%n=== %s ===%n", l.getNome());
            System.out.printf("Universo de números: 1 a %d%n", l.getUniverso());
            System.out.printf("Números marcados na aposta: %d%n", l.getAposta());
            System.out.printf("Preço da aposta: R$ %.2f%n", l.getValorAposta());
            System.out.printf("Combinações possíveis: %s%n", formatador.format(c));
            System.out.printf("Probabilidade de ganhar: %.15f%% (1 em %s)%n",
                    l.probabilidade() * 100, formatador.format(c));
            System.out.printf("Para garantir vitória seria necessário gastar: R$ %s%n",
                    formatador.format((long) l.custoMedioParaGanhar()));

            System.out.println("\nPara contextualizar — você teria mais chance de:");
            comparar("ser atingido por um raio (1 em 700.000)", 700_000, l);
            comparar("ganhar um Oscar (1 em 11.500)", 11_500, l);
            comparar("nascer com gêmeos idênticos (1 em 250)", 250, l);

        } catch (NumberFormatException e) {
            System.out.println("⚠ Entrada inválida.");
        }
    }

    private void comparar(String evento, long umEm, Loteria l) {
        double chanceEvento = 1.0 / umEm;
        double chanceLoteria = l.probabilidade();
        double razao = chanceEvento / chanceLoteria;
        System.out.printf("  • %s → %.0fx mais provável que ganhar na %s%n",
                evento, razao, l.getNome());
    }

    private void simularApostas() {
        System.out.println("\nEscolha uma loteria:");
        Loteria[] todas = Loteria.values();
        for (int i = 0; i < todas.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, todas[i].getNome());
        }
        System.out.print("Opção: ");
        int op;
        try {
            op = Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠ Entrada inválida.");
            return;
        }

        if (op < 1 || op > todas.length) {
            System.out.println("⚠ Opção fora do intervalo.");
            return;
        }
        Loteria l = todas[op - 1];

        System.out.print("Quantas apostas pretende fazer? ");
        long n;
        try {
            n = Long.parseLong(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠ Entrada inválida.");
            return;
        }

        if (n <= 0) {
            System.out.println("⚠ Deve ser positivo.");
            return;
        }

        double probabilidade = l.probabilidade();
        double chanceGanhar = 1 - Math.pow(1 - probabilidade, n);
        double custoTotal = n * l.getValorAposta();

        System.out.printf("%n=== Simulação - %s ===%n", l.getNome());
        System.out.printf("Apostas: %s%n", formatador.format(n));
        System.out.printf("Custo total: R$ %.2f%n", custoTotal);
        System.out.printf("Probabilidade de ganhar pelo menos uma vez: %.10f%% (1 em %.2f)%n",
                chanceGanhar * 100,
                chanceGanhar > 0 ? 1.0 / chanceGanhar : Double.POSITIVE_INFINITY);
    }

    private String formatarCompacto(BigInteger valor) {
        double v = valor.doubleValue();
        if (v >= 1e12) {
            return String.format("%.2f tri", v / 1e12);
        }

        if (v >= 1e9) {
            return String.format("%.2f bi", v / 1e9);
        }

        if (v >= 1e6) {
            return String.format("%.2f mi", v / 1e6);
        }

        if (v >= 1e3) {
            return String.format("%.2f mil", v / 1e3);
        }
        return valor.toString();
    }
}

