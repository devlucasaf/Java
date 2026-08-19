package application.utilitarios.conversor.moedas;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("=== CONVERSOR DE MOEDAS (API awesomeapi.com.br) ===\n");

        ConversorMoedas c = new ConversorMoedas();
        Map<String, Double> cotacoes;

        try {
            cotacoes = c.consultar("USD-BRL", "EUR-BRL", "GBP-BRL", "BTC-BRL", "ARS-BRL");
        } catch (Exception e) {
            System.out.println("Falha ao consultar API (offline?). Usando cotacoes fixas de demonstracao.");
            cotacoes = Map.of("USDBRL", 5.10, "EURBRL", 5.55, "GBPBRL", 6.50, "BTCBRL", 320000.0, "ARSBRL", 0.006);
        }

        System.out.println("Cotacoes atuais:");
        cotacoes.forEach((k, v) -> System.out.printf("  %s = R$ %.4f%n", k, v));

        try {
            c.salvarHistorico(cotacoes);
            System.out.println("\nHistorico salvo em: " + c.getHistoricoArquivo());
        } catch (Exception ignored) {}

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== CONVERSAO ===");
        System.out.print("Valor: ");
        double valor = Double.parseDouble(scanner.nextLine().replace(",", "."));
        System.out.print("De (USD, EUR, GBP, BTC, ARS): ");
        String origem = scanner.nextLine().trim().toUpperCase();
        System.out.print("Para (BRL): ");
        String destino = scanner.nextLine().trim().toUpperCase();

        Double taxa = cotacoes.get(origem + destino);
        if (taxa == null) {
            System.out.println("Par nao encontrado. Tente outro.");
        } else {
            System.out.printf("%.4f %s = %.2f %s%n", valor, origem, valor * taxa, destino);
        }
    }
}

