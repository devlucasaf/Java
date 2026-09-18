package application.system.inventario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Inventario {

    private final String                    nome;
    private final List<Produto>             produtos;
    private final List<MovimentacaoEstoque> movimentacoes;
    private long                            proximoIdentificadorProduto;
    private long                            proximoIdentificadorMovimentacao;

    public Inventario(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do inventário não pode estar vazio.");
        }

        this.nome = nome.trim();
        this.produtos = new ArrayList<>();
        this.movimentacoes = new ArrayList<>();
        this.proximoIdentificadorProduto = 1;
        this.proximoIdentificadorMovimentacao = 1;
    }

    // --- CADASTRA UM NOVO PRODUTO ---
    public Produto cadastrarProduto(String codigo, String nome, String descricao, CategoriaProduto categoria,
                                    BigDecimal custoUnitario, int quantidadeInicial, int estoqueMinimo, int estoqueIdeal) {
        if (buscarProdutoPorCodigo(codigo) != null) {
            throw new IllegalArgumentException("Já existe um produto cadastrado com esse código.");
        }

        Produto produto = new Produto(proximoIdentificadorProduto++, codigo, nome, descricao, categoria,
                custoUnitario, quantidadeInicial, estoqueMinimo, estoqueIdeal);
        produtos.add(produto);

        if (quantidadeInicial > 0) {
            registrarMovimentacao(produto, TipoMovimentacao.ENTRADA, quantidadeInicial, 0, quantidadeInicial, "Sistema", "Estoque inicial");
        }

        return produto;
    }

    // --- REGISTRA UMA ENTRADA DE ESTOQUE ---
    public MovimentacaoEstoque registrarEntrada(Produto produto, int quantidade, String responsavel, String observacao) {
        validarProduto(produto);
        validarProdutoAtivo(produto);

        int quantidadeAnterior = produto.getQuantidadeEstoque();
        produto.adicionarEstoque(quantidade);

        return registrarMovimentacao(produto, TipoMovimentacao.ENTRADA, quantidade, quantidadeAnterior, produto.getQuantidadeEstoque(), responsavel, observacao);
    }

    // --- REGISTRA UMA SAÍDA DE ESTOQUE ---
    public MovimentacaoEstoque registrarSaida(Produto produto, int quantidade, String responsavel, String observacao) {
        validarProduto(produto);
        validarProdutoAtivo(produto);

        int quantidadeAnterior = produto.getQuantidadeEstoque();
        produto.removerEstoque(quantidade);

        return registrarMovimentacao(produto, TipoMovimentacao.SAIDA, quantidade, quantidadeAnterior, produto.getQuantidadeEstoque(), responsavel, observacao);
    }

    // --- AJUSTA O ESTOQUE PARA A QUANTIDADE CONTADA ---
    public MovimentacaoEstoque ajustarEstoque(Produto produto, int quantidadeContada, String responsavel, String observacao) {
        validarProduto(produto);
        validarProdutoAtivo(produto);

        if (quantidadeContada < 0) {
            throw new IllegalArgumentException("A quantidade contada não pode ser negativa.");
        }

        int quantidadeAnterior = produto.getQuantidadeEstoque();

        if (quantidadeContada == quantidadeAnterior) {
            throw new IllegalArgumentException("A quantidade contada é igual à quantidade existente.");
        }

        int diferenca = Math.abs(quantidadeContada - quantidadeAnterior);
        TipoMovimentacao tipo = quantidadeContada > quantidadeAnterior ? TipoMovimentacao.AJUSTE_POSITIVO : TipoMovimentacao.AJUSTE_NEGATIVO;

        produto.definirQuantidadeEstoque(quantidadeContada);
        return registrarMovimentacao(produto, tipo, diferenca, quantidadeAnterior, quantidadeContada, responsavel, observacao);
    }

    // --- REGISTRA UMA MOVIMENTAÇÃO NO HISTÓRICO ---
    private MovimentacaoEstoque registrarMovimentacao(Produto produto, TipoMovimentacao tipo, int quantidade, int quantidadeAnterior, int quantidadePosterior, String responsavel, String observacao) {
        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque(proximoIdentificadorMovimentacao++, produto, tipo,
                quantidade, quantidadeAnterior, quantidadePosterior, produto.getCustoUnitario(), responsavel, observacao);
        movimentacoes.add(movimentacao);
        return movimentacao;
    }

    // --- BUSCA UM PRODUTO PELO CÓDIGO ---
    public Produto buscarProdutoPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }

        for (Produto produto : produtos) {
            if (produto.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return produto;
            }
        }

        return null;
    }

    // --- BUSCA UM PRODUTO PELO IDENTIFICADOR ---
    public Produto buscarProdutoPorIdentificador(long identificador) {
        for (Produto produto : produtos) {
            if (produto.getIdentificador() == identificador) {
                return produto;
            }
        }

        return null;
    }

    // --- PESQUISA PRODUTOS POR PALAVRA-CHAVE ---
    public List<Produto> pesquisarProdutos(String palavraChave) {
        if (palavraChave == null || palavraChave.trim().isEmpty()) {
            return listarProdutosAtivos();
        }

        String termo = palavraChave.trim().toLowerCase();

        return produtos.stream()
                .filter(Produto::isAtivo)
                .filter(produto -> produto.getCodigo().toLowerCase().contains(termo) ||
                        produto.getNome().toLowerCase().contains(termo) ||
                        produto.getDescricao().toLowerCase().contains(termo) ||
                        produto.getCategoria().getNomeFormatado().toLowerCase().contains(termo))
                .sorted(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    // --- LISTA OS PRODUTOS ATIVOS ---
    public List<Produto> listarProdutosAtivos() {
        return produtos.stream().filter(Produto::isAtivo).sorted(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER)).toList();
    }

    // --- LISTA OS PRODUTOS DE UMA CATEGORIA ---
    public List<Produto> listarProdutosPorCategoria(CategoriaProduto categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria não pode ser nula.");
        }

        return produtos.stream().filter(Produto::isAtivo).filter(produto -> produto.getCategoria() == categoria).sorted(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER)).toList();
    }

    // --- GERA OS ALERTAS DE REPOSIÇÃO ---
    public List<AlertaReposicao> gerarAlertasReposicao() {
        List<AlertaReposicao> alertas = new ArrayList<>();

        for (Produto produto : produtos) {
            if (produto.precisaReposicao()) {
                alertas.add(new AlertaReposicao(produto));
            }
        }

        alertas.sort(Comparator.comparingInt(AlertaReposicao::getQuantidadeAtual).thenComparing(alerta -> alerta.getProduto().getNome(), String.CASE_INSENSITIVE_ORDER));
        return alertas;
    }

    // --- CALCULA O VALOR TOTAL ARMAZENADO ---
    public BigDecimal calcularValorTotalEstoque() {
        BigDecimal total = BigDecimal.ZERO;

        for (Produto produto : produtos) {
            if (produto.isAtivo()) {
                total = total.add(produto.calcularValorEmEstoque());
            }
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    // --- CALCULA A CURVA ABC COM BASE NAS SAÍDAS DO PERÍODO ---
    public List<ItemCurvaABC> calcularCurvaABC(LocalDateTime inicio, LocalDateTime fim) {
        validarPeriodo(inicio, fim);

        Map<Produto, BigDecimal> consumoPorProduto = calcularConsumoPorProduto(inicio, fim);
        List<Map.Entry<Produto, BigDecimal>> itensOrdenados = new ArrayList<>(consumoPorProduto.entrySet());
        itensOrdenados.sort(Map.Entry.<Produto, BigDecimal>comparingByValue().reversed());

        BigDecimal consumoTotal = BigDecimal.ZERO;

        for (Map.Entry<Produto, BigDecimal> item : itensOrdenados) {
            consumoTotal = consumoTotal.add(item.getValue());
        }

        List<ItemCurvaABC> resultado = new ArrayList<>();
        double percentualAcumulado = 0;

        for (Map.Entry<Produto, BigDecimal> item : itensOrdenados) {
            double percentualIndividual = consumoTotal.signum() == 0 ? 0 :
                    item.getValue().multiply(BigDecimal.valueOf(100)).divide(consumoTotal, 8, RoundingMode.HALF_UP).doubleValue();
            double percentualAnterior = percentualAcumulado;
            percentualAcumulado += percentualIndividual;

            ClassificacaoABC classificacao;

            if (percentualAnterior < 80.0) {
                classificacao = ClassificacaoABC.A;
            } else if (percentualAnterior < 95.0) {
                classificacao = ClassificacaoABC.B;
            } else {
                classificacao = ClassificacaoABC.C;
            }

            resultado.add(new ItemCurvaABC(item.getKey(), item.getValue(), percentualIndividual, Math.min(100.0, percentualAcumulado), classificacao));
        }

        return resultado;
    }

    // --- CALCULA O CONSUMO FINANCEIRO DE CADA PRODUTO ---
    private Map<Produto, BigDecimal> calcularConsumoPorProduto(LocalDateTime inicio, LocalDateTime fim) {
        Map<Produto, BigDecimal> consumoPorProduto = new LinkedHashMap<>();

        for (Produto produto : produtos) {
            if (produto.isAtivo()) {
                consumoPorProduto.put(produto, BigDecimal.ZERO);
            }
        }

        for (MovimentacaoEstoque movimentacao : movimentacoes) {
            boolean dentroPeriodo = !movimentacao.getDataMovimentacao().isBefore(inicio) && !movimentacao.getDataMovimentacao().isAfter(fim);
            boolean representaConsumo = movimentacao.getTipo() == TipoMovimentacao.SAIDA;

            if (dentroPeriodo && representaConsumo) {
                BigDecimal valorAtual = consumoPorProduto.getOrDefault(movimentacao.getProduto(), BigDecimal.ZERO);
                consumoPorProduto.put(movimentacao.getProduto(), valorAtual.add(movimentacao.calcularValorTotal()));
            }
        }

        consumoPorProduto.entrySet().removeIf(item -> item.getValue().signum() == 0);
        return consumoPorProduto;
    }

    // --- LISTA AS MOVIMENTAÇÕES DE UM PRODUTO ---
    public List<MovimentacaoEstoque> listarMovimentacoes(Produto produto) {
        validarProduto(produto);

        return movimentacoes.stream()
                .filter(movimentacao -> movimentacao.getProduto().equals(produto))
                .sorted(Comparator.comparing(MovimentacaoEstoque::getDataMovimentacao).reversed())
                .toList();
    }

    // --- LISTA AS MOVIMENTAÇÕES DE UM PERÍODO ---
    public List<MovimentacaoEstoque> listarMovimentacoes(LocalDateTime inicio, LocalDateTime fim) {
        validarPeriodo(inicio, fim);

        return movimentacoes.stream()
                .filter(movimentacao -> !movimentacao.getDataMovimentacao().isBefore(inicio)
                        && !movimentacao.getDataMovimentacao().isAfter(fim))
                .sorted(Comparator.comparing(MovimentacaoEstoque::getDataMovimentacao).reversed())
                .toList();
    }

    // --- VALIDA UM PRODUTO DO INVENTÁRIO ---
    private void validarProduto(Produto produto) {
        if (produto == null || !produtos.contains(produto)) {
            throw new IllegalArgumentException("O produto não pertence a este inventário.");
        }
    }

    // --- VALIDA SE O PRODUTO ESTÁ ATIVO ---
    private void validarProdutoAtivo(Produto produto) {
        if (!produto.isAtivo()) {
            throw new IllegalStateException("O produto está inativo.");
        }
    }

    // --- VALIDA UM PERÍODO DE CONSULTA ---
    private void validarPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("As datas inicial e final devem ser informadas.");
        }

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data inicial não pode ser posterior à data final.");
        }
    }

    public String getNome() {
        return nome;
    }

    public List<Produto> getProdutos() {
        return List.copyOf(produtos);
    }

    public List<MovimentacaoEstoque> getMovimentacoes() {
        return List.copyOf(movimentacoes);
    }
}

