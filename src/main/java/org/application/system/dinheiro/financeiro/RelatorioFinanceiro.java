package org.application.system.dinheiro.financeiro;

import java.time.YearMonth;
import java.util.Map;
import java.util.HashMap;

public class RelatorioFinanceiro {

    public static void gerarRelatorioMensal(Usuario usuario, YearMonth mesReferencia) {
        System.out.println("\n========== RELATÓRIO FINANCEIRO - " + mesReferencia + " ==========");
        double totalReceitas = 0;
        double totalDespesas = 0;
        Map<Categoria, Double> gastosPorCategoria = new HashMap<>();

        for (Conta conta : usuario.getContas()) {
            for (Transacao t : conta.getTransacoes()) {
                YearMonth transacaoMes = YearMonth.from(t.getData());
                if (transacaoMes.equals(mesReferencia)) {
                    if (t.isReceita()) {
                        totalReceitas += t.getValor();
                    } else {
                        totalDespesas += t.getValor();
                        gastosPorCategoria.merge(t.getCategoria(), t.getValor(), Double::sum);
                    }
                }
            }
        }

        System.out.println("Total de Receitas: R$" + totalReceitas);
        System.out.println("Total de Despesas: R$" + totalDespesas);
        System.out.println("Saldo do mês: R$" + (totalReceitas - totalDespesas));
        System.out.println("\n--- Gastos por Categoria ---");

        for (Map.Entry<Categoria, Double> entry : gastosPorCategoria.entrySet()) {
            System.out.printf("  %-20s: R$%.2f\n", entry.getKey().getNome(), entry.getValue());
        }
    }

    public static void gerarRelatorioAnual(Usuario usuario, int ano) {
        System.out.println("\n========== RELATÓRIO ANUAL - " + ano + " ==========");
        for (int mes = 1; mes <= 12; mes++) {
            YearMonth ym = YearMonth.of(ano, mes);
            gerarRelatorioMensal(usuario, ym);
        }
    }

    public static void exibirSaldoGeral(Usuario usuario) {
        System.out.println("\n--- SALDO GERAL DO USUÁRIO ---");
        for (Conta c : usuario.getContas()) {
            System.out.println(c);
        }
        System.out.println("Patrimônio líquido total: R$" + usuario.calcularPatrimonioLiquido());
    }
}
