package application.system.inventario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

public class Produto {

    private final long          identificador;
    private final String        codigo;
    private String              nome;
    private String              descricao;
    private CategoriaProduto    categoria;
    private BigDecimal          custoUnitario;
    private int                 quantidadeEstoque;
    private int                 estoqueMinimo;
    private int                 estoqueIdeal;
    private boolean             ativo;
    private final LocalDateTime dataCadastro;
    private LocalDateTime       dataAtualizacao;

    public Produto(long identificador, String codigo, String nome, String descricao, CategoriaProduto categoria,
                   BigDecimal custoUnitario, int quantidadeInicial, int estoqueMinimo, int estoqueIdeal) {
        validarIdentificador(identificador);
        validarTextoObrigatorio(codigo, "O código");
        validarTextoObrigatorio(nome, "O nome");
        validarCategoria(categoria);
        validarCusto(custoUnitario);
        validarQuantidades(quantidadeInicial, estoqueMinimo, estoqueIdeal);

        this.identificador = identificador;
        this.codigo = codigo.trim();
        this.nome = nome.trim();
        this.descricao = descricao == null ? "" : descricao.trim();
        this.categoria = categoria;
        this.custoUnitario = custoUnitario.setScale(2, RoundingMode.HALF_UP);
        this.quantidadeEstoque = quantidadeInicial;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueIdeal = estoqueIdeal;
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    // --- ATUALIZA OS DADOS CADASTRAIS DO PRODUTO ---
    public void atualizar(String nome, String descricao, CategoriaProduto categoria,
                          BigDecimal custoUnitario, int estoqueMinimo, int estoqueIdeal) {
        validarTextoObrigatorio(nome, "O nome");
        validarCategoria(categoria);
        validarCusto(custoUnitario);

        if (estoqueMinimo < 0) {
            throw new IllegalArgumentException("O estoque mínimo não pode ser negativo.");
        }

        if (estoqueIdeal < estoqueMinimo) {
            throw new IllegalArgumentException("O estoque ideal não pode ser menor que o estoque mínimo.");
        }

        this.nome = nome.trim();
        this.descricao = descricao == null ? "" : descricao.trim();
        this.categoria = categoria;
        this.custoUnitario = custoUnitario.setScale(2, RoundingMode.HALF_UP);
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueIdeal = estoqueIdeal;
        this.dataAtualizacao = LocalDateTime.now();
    }

    // --- ADICIONA UMA QUANTIDADE AO ESTOQUE ---
    void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de entrada deve ser maior que zero.");
        }

        quantidadeEstoque = Math.addExact(quantidadeEstoque, quantidade);
        dataAtualizacao = LocalDateTime.now();
    }

    // --- REMOVE UMA QUANTIDADE DO ESTOQUE ---
    void removerEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de saída deve ser maior que zero.");
        }

        if (quantidade > quantidadeEstoque) {
            throw new IllegalStateException("O produto não possui estoque suficiente.");
        }

        quantidadeEstoque -= quantidade;
        dataAtualizacao = LocalDateTime.now();
    }

    // --- DEFINE O ESTOQUE DURANTE UM AJUSTE DE INVENTÁRIO ---
    void definirQuantidadeEstoque(int novaQuantidade) {
        if (novaQuantidade < 0) {
            throw new IllegalArgumentException("A quantidade do estoque não pode ser negativa.");
        }

        quantidadeEstoque = novaQuantidade;
        dataAtualizacao = LocalDateTime.now();
    }

    // --- VERIFICA SE O PRODUTO PRECISA DE REPOSIÇÃO ---
    public boolean precisaReposicao() {
        return ativo && quantidadeEstoque <= estoqueMinimo;
    }

    // --- CALCULA A QUANTIDADE SUGERIDA PARA REPOSIÇÃO ---
    public int calcularQuantidadeReposicao() {
        return precisaReposicao() ? Math.max(0, estoqueIdeal - quantidadeEstoque) : 0;
    }

    // --- CALCULA O VALOR ATUAL ARMAZENADO DO PRODUTO ---
    public BigDecimal calcularValorEmEstoque() {
        return custoUnitario.multiply(BigDecimal.valueOf(quantidadeEstoque)).setScale(2, RoundingMode.HALF_UP);
    }

    // --- DESATIVA O PRODUTO ---
    public void desativar() {
        ativo = false;
        dataAtualizacao = LocalDateTime.now();
    }

    // --- REATIVA O PRODUTO ---
    public void reativar() {
        ativo = true;
        dataAtualizacao = LocalDateTime.now();
    }

    // --- VALIDA O IDENTIFICADOR ---
    private void validarIdentificador(long identificador) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
    }

    // --- VALIDA UM TEXTO OBRIGATÓRIO ---
    private void validarTextoObrigatorio(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode estar vazio.");
        }
    }

    // --- VALIDA A CATEGORIA ---
    private void validarCategoria(CategoriaProduto categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria não pode ser nula.");
        }
    }

    // --- VALIDA O CUSTO UNITÁRIO ---
    private void validarCusto(BigDecimal custoUnitario) {
        if (custoUnitario == null || custoUnitario.signum() < 0) {
            throw new IllegalArgumentException("O custo unitário não pode ser negativo.");
        }
    }

    // --- VALIDA AS QUANTIDADES DO PRODUTO ---
    private void validarQuantidades(int quantidadeInicial, int estoqueMinimo, int estoqueIdeal) {
        if (quantidadeInicial < 0) {
            throw new IllegalArgumentException("A quantidade inicial não pode ser negativa.");
        }

        if (estoqueMinimo < 0) {
            throw new IllegalArgumentException("O estoque mínimo não pode ser negativo.");
        }

        if (estoqueIdeal < estoqueMinimo) {
            throw new IllegalArgumentException("O estoque ideal não pode ser menor que o estoque mínimo.");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    public BigDecimal getCustoUnitario() {
        return custoUnitario;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public int getEstoqueIdeal() {
        return estoqueIdeal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Produto outroProduto)) {
            return false;
        }

        return codigo.equalsIgnoreCase(outroProduto.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo.toLowerCase());
    }

    @Override
    public String toString() {
        return identificador + " - " + codigo + " - " + nome + " - estoque: " + quantidadeEstoque;
    }
}

