package application.calculadoras.investimentos;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final NumberFormat BRL = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== CALCULADORA DE INVESTIMENTOS ===");
        System.out.print("Capital investido (R$): ");
        double capital = lerDouble(sc, 10000);
        System.out.print("Dias corridos: ");
        int dias = lerInt(sc, 365);
        System.out.print("CDI anual atual (%) [ex: 10.5]: ");
        double cdi = lerDouble(sc, 10.5);
        System.out.print("Selic anual atual (%) [ex: 10.5]: ");
        double selic = lerDouble(sc, 10.5);

        System.out.println();
        Investimentos.Resultado cdb = Investimentos.calcularCDB(capital, 100, cdi, dias);
        Investimentos.Resultado lci = Investimentos.calcularLCI(capital, 95, cdi, dias);
        Investimentos.Resultado tsl = Investimentos.calcularTesouroSelic(capital, selic, dias);

        imprimir("CDB (100% CDI)", cdb);
        imprimir("LCI (95% CDI, isenta de IR)", lci);
        imprimir("Tesouro Selic", tsl);

        System.out.println("\n=== MELHOR OPCAO LIQUIDA ===");
        double melhor = Math.max(cdb.montanteLiquido, Math.max(lci.montanteLiquido, tsl.montanteLiquido));
        if (melhor == cdb.montanteLiquido) System.out.println("-> CDB");
        else if (melhor == lci.montanteLiquido) System.out.println("-> LCI");
        else System.out.println("-> Tesouro Selic");
    }

    private static void imprimir(String nome, Investimentos.Resultado r) {
        System.out.println("---- " + nome + " ----");
        System.out.println("  Montante bruto:      " + BRL.format(r.montanteBruto));
        System.out.println("  Lucro:               " + BRL.format(r.lucro));
        System.out.println("  IOF:                 " + BRL.format(r.iof));
        System.out.println("  IR:                  " + BRL.format(r.ir));
        System.out.println("  Montante liquido:    " + BRL.format(r.montanteLiquido));
        System.out.printf ("  Rentabilidade liq.:  %.2f%%%n", r.rentabilidadeLiquida);
    }

    private static double lerDouble(Scanner sc, double padrao) {
        String s = sc.nextLine().replace(",", ".").trim();
        if (s.isEmpty()) return padrao;
        try { return Double.parseDouble(s); } catch (Exception e) { return padrao; }
    }

    private static int lerInt(Scanner sc, int padrao) {
        String s = sc.nextLine().trim();
        if (s.isEmpty()) return padrao;
        try { return Integer.parseInt(s); } catch (Exception e) { return padrao; }
    }
}

