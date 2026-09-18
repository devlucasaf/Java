package application.system.inventario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AlertaReposicao {

    private final Produto       produto;
    private final int           quantidadeAtual;
    private final int           estoqueMinimo;
    private final int           quantidadeSugerida;
    private final LocalDateTime dataGeracao;

    public AlertaReposicao(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }

        if (!produto.precisaReposicao()) {
            throw new IllegalArgumentException("O produto informado não precisa de reposição.");
        }

        this.produto = produto;
        this.quantidadeAtual = produto.getQuantidadeEstoque();
        this.estoqueMinimo = produto.getEstoqueMinimo();
        this.quantidadeSugerida = produto.calcularQuantidadeReposicao();
        this.dataGeracao = LocalDateTime.now();
    }

    // --- RETORNA A MENSAGEM COMPLETA DO ALERTA ---
    public String criarMensagem() {
        return "Reposição necessária para " + produto.getNome() + ". Estoque atual: " + quantidadeAtual + ", estoque mínimo: " + estoqueMinimo + ", reposição sugerida: " + quantidadeSugerida + ".";
    }

    // --- RETORNA A DATA DE GERAÇÃO FORMATADA ---
    public String getDataGeracaoFormatada() {
        return dataGeracao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public int getQuantidadeSugerida() {
        return quantidadeSugerida;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    @Override
    public String toString() {
        return criarMensagem();
    }
}

