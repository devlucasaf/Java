package application.system.inventario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SistemaInventario {

    public static void main(String[] args) {
        Inventario inventario = new Inventario("Estoque principal");

        Produto notebook = inventario.cadastrarProduto("PROD-001", "Notebook profissional", "Notebook com 16 GB de memória e armazenamento SSD.", CategoriaProduto.ELETRONICOS, new BigDecimal("3500.00"), 10, 3, 12);
        Produto teclado = inventario.cadastrarProduto("PROD-002", "Teclado mecânico", "Teclado mecânico para escritório.", CategoriaProduto.ELETRONICOS, new BigDecimal("250.00"), 20, 5, 25);
        Produto papel = inventario.cadastrarProduto("PROD-003", "Papel A4", "Pacote com quinhentas folhas.", CategoriaProduto.ESCRITORIO, new BigDecimal("32.50"), 30, 10, 50);
        Produto detergente = inventario.cadastrarProduto("PROD-004", "Detergente", "Detergente neutro de quinhentos mililitros.", CategoriaProduto.LIMPEZA, new BigDecimal("3.80"), 15, 8, 30);
        Produto agua = inventario.cadastrarProduto("PROD-005", "Água mineral", "Garrafa de água mineral.", CategoriaProduto.BEBIDAS, new BigDecimal("2.50"), 40, 15, 60);

        inventario.registrarSaida(notebook, 8, "Lucas", "Entrega de equipamentos aos funcionários");
        inventario.registrarSaida(teclado, 12, "Lucas", "Distribuição para o novo escritório");
        inventario.registrarSaida(papel, 25, "Mariana", "Consumo mensal");
        inventario.registrarSaida(detergente, 10, "Carlos", "Utilização pela equipe de limpeza");
        inventario.registrarSaida(agua, 30, "Ana", "Consumo em evento interno");

        inventario.registrarEntrada(papel, 20, "Mariana", "Reposição do fornecedor");
        inventario.ajustarEstoque(detergente, 4, "Carlos", "Ajuste após contagem física");

        exibirProdutos(inventario);
        exibirAlertas(inventario);
        exibirCurvaABC(inventario);
        exibirMovimentacoes(inventario);
    }

    // --- EXIBE OS PRODUTOS DO INVENTÁRIO ---
    private static void exibirProdutos(Inventario inventario) {
        System.out.println("============================================================");
        System.out.println("                    CONTROLE DE ESTOQUE");
        System.out.println("============================================================");
        System.out.println("Inventário: " + inventario.getNome());
        System.out.println("Produtos cadastrados: " + inventario.getProdutos().size());
        System.out.println("Valor total em estoque: R$ " + inventario.calcularValorTotalEstoque());

        System.out.println("\nPRODUTOS");

        for (Produto produto : inventario.listarProdutosAtivos()) {
            System.out.println("------------------------------------------------------------");
            System.out.println("Código: " + produto.getCodigo());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Categoria: " + produto.getCategoria().getNomeFormatado());
            System.out.println("Custo unitário: R$ " + produto.getCustoUnitario());
            System.out.println("Quantidade: " + produto.getQuantidadeEstoque());
            System.out.println("Estoque mínimo: " + produto.getEstoqueMinimo());
            System.out.println("Estoque ideal: " + produto.getEstoqueIdeal());
            System.out.println("Valor armazenado: R$ " + produto.calcularValorEmEstoque());
        }
    }

    // --- EXIBE OS ALERTAS DE REPOSIÇÃO ---
    private static void exibirAlertas(Inventario inventario) {
        List<AlertaReposicao> alertas = inventario.gerarAlertasReposicao();

        System.out.println("\n============================================================");
        System.out.println("                    ALERTAS DE REPOSIÇÃO");
        System.out.println("============================================================");

        if (alertas.isEmpty()) {
            System.out.println("Nenhum produto precisa de reposição.");
            return;
        }

        for (AlertaReposicao alerta : alertas) {
            System.out.println("- " + alerta.criarMensagem());
        }
    }

    // --- EXIBE A CLASSIFICAÇÃO DA CURVA ABC ---
    private static void exibirCurvaABC(Inventario inventario) {
        LocalDateTime inicio = LocalDateTime.now().minusMonths(1);
        LocalDateTime fim = LocalDateTime.now();
        List<ItemCurvaABC> curvaABC = inventario.calcularCurvaABC(inicio, fim);

        System.out.println("\n============================================================");
        System.out.println("                       CURVA ABC");
        System.out.println("============================================================");

        if (curvaABC.isEmpty()) {
            System.out.println("Não existem saídas no período informado.");
            return;
        }

        for (ItemCurvaABC item : curvaABC) {
            System.out.println("------------------------------------------------------------");
            System.out.println("Produto: " + item.getProduto().getNome());
            System.out.println("Valor consumido: R$ " + item.getValorConsumo());
            System.out.printf("Percentual individual: %.2f%%%n", item.getPercentualIndividual());
            System.out.printf("Percentual acumulado: %.2f%%%n", item.getPercentualAcumulado());
            System.out.println("Classificação: " + item.getClassificacao() + " - " + item.getClassificacao().getDescricao());
        }
    }

    // --- EXIBE O HISTÓRICO DE MOVIMENTAÇÕES ---
    private static void exibirMovimentacoes(Inventario inventario) {
        System.out.println("\n============================================================");
        System.out.println("                 HISTÓRICO DE MOVIMENTAÇÕES");
        System.out.println("============================================================");

        for (MovimentacaoEstoque movimentacao : inventario.getMovimentacoes()) {
            System.out.println("------------------------------------------------------------");
            System.out.println("Identificador: " + movimentacao.getIdentificador());
            System.out.println("Produto: " + movimentacao.getProduto().getNome());
            System.out.println("Tipo: " + movimentacao.getTipo().getNomeFormatado());
            System.out.println("Quantidade: " + movimentacao.getQuantidade());
            System.out.println("Estoque anterior: " + movimentacao.getQuantidadeAnterior());
            System.out.println("Estoque posterior: " + movimentacao.getQuantidadePosterior());
            System.out.println("Valor: R$ " + movimentacao.calcularValorTotal());
            System.out.println("Responsável: " + movimentacao.getResponsavel());
            System.out.println("Data: " + movimentacao.getDataMovimentacaoFormatada());
        }
    }
}

