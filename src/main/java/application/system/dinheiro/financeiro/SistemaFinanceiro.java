package application.system.dinheiro.financeiro;

import java.time.LocalDate;
import java.time.YearMonth;

public class SistemaFinanceiro {
    public static void main(String[] args) {
        // Criar usuário
        Usuario usuario = new Usuario("João Silva", "123.456.789-00", "joao@email.com", "senha123");

        // Criar contas
        Conta contaCorrente = new Conta("Banco do Brasil", TipoConta.CONTA_CORRENTE, 2500.0);
        Conta carteira = new Conta("Carteira", TipoConta.CARTEIRA_DINHEIRO, 500.0);
        usuario.adicionarConta(contaCorrente);
        usuario.adicionarConta(carteira);

        // Criar categorias padrão
        Categoria alimentacao = new Categoria("Alimentação", TipoTransacao.DESPESA);
        Categoria transporte = new Categoria("Transporte", TipoTransacao.DESPESA);
        Categoria lazer = new Categoria("Lazer", TipoTransacao.DESPESA);
        Categoria salario = new Categoria("Salário", TipoTransacao.RECEITA);

        // Subcategorias
        Categoria mercado = new Categoria("Mercado", TipoTransacao.DESPESA);
        Categoria restaurante = new Categoria("Restaurante", TipoTransacao.DESPESA);
        alimentacao.adicionarSubcategoria(mercado);
        alimentacao.adicionarSubcategoria(restaurante);

        // Adicionar transações (Receitas)
        Transacao salarioJan = new Transacao(5200.0, LocalDate.of(2025, 1, 10), "Salário Janeiro",
                TipoTransacao.RECEITA, FormaPagamento.TRANSFERENCIA, salario, contaCorrente);
        contaCorrente.adicionarTransacao(salarioJan);

        Transacao freela = new Transacao(800.0, LocalDate.of(2025, 1, 15), "Freelance",
                TipoTransacao.RECEITA, FormaPagamento.PIX, salario, carteira);
        carteira.adicionarTransacao(freela);

        // Despesas
        Transacao supermercado = new Transacao(450.0, LocalDate.of(2025, 1, 5), "Supermercado",
                TipoTransacao.DESPESA, FormaPagamento.CARTAO_DEBITO, mercado, contaCorrente);
        contaCorrente.adicionarTransacao(supermercado);

        Transacao ifood = new Transacao(120.0, LocalDate.of(2025, 1, 12), "Ifood",
                TipoTransacao.DESPESA, FormaPagamento.CARTAO_CREDITO, restaurante, contaCorrente);
        contaCorrente.adicionarTransacao(ifood);

        Transacao uber = new Transacao(65.0, LocalDate.of(2025, 1, 18), "Uber",
                TipoTransacao.DESPESA, FormaPagamento.CARTAO_CREDITO, transporte, contaCorrente);
        contaCorrente.adicionarTransacao(uber);

        Transacao cinema = new Transacao(80.0, LocalDate.of(2025, 1, 20), "Cinema",
                TipoTransacao.DESPESA, FormaPagamento.DINHEIRO, lazer, carteira);
        carteira.adicionarTransacao(cinema);

        // Criar e verificar orçamento
        Orcamento orcAlimentacao = new Orcamento(alimentacao, 800.0, Periodicidade.MENSAL, YearMonth.of(2025, 1));
        // Atualizar gasto acumulado (somando despesas da categoria)
        for (Transacao t : contaCorrente.getTransacoes()) {
            if (t.isDespesa() && t.getCategoria().getNome().equals("Mercado") || t.getCategoria().getNome().equals("Restaurante")) {
                orcAlimentacao.adicionarGasto(t.getValor());
            }
        }
        for (Transacao t : carteira.getTransacoes()) {
            if (t.isDespesa() && t.getCategoria().getNome().equals("Lazer")) {
                // não afeta alimentação
            }
        }
        // Para simplificar, adicionamos manualmente os gastos de mercado e restaurante
        // Já foram somados no loop acima.

        System.out.println("\n=== STATUS DO ORÇAMENTO ===");
        orcAlimentacao.exibirStatus();

        // Extrato de contas
        contaCorrente.exibirExtrato();
        carteira.exibirExtrato();

        // Relatórios
        RelatorioFinanceiro.gerarRelatorioMensal(usuario, YearMonth.of(2025, 1));
        RelatorioFinanceiro.exibirSaldoGeral(usuario);

        // Polimorfismo com Pessoa (usuário)
        System.out.println("\n=== DADOS DO USUÁRIO ===");
        usuario.exibirInformacoes();

        // Exibir categorias criadas
        System.out.println("\n=== CATEGORIAS CADASTRADAS ===");
        System.out.println(alimentacao);
        System.out.println(transporte);
        System.out.println(lazer);
        System.out.println(salario);
    }
}
