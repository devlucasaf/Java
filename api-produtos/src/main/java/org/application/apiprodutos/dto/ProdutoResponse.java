package org.application.apiprodutos.dto;

import org.application.apiprodutos.model.Categoria;
import org.application.apiprodutos.model.Produto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponse(
        Long            id,
        String          nome,
        String          descricao,
        BigDecimal      preco,
        Integer         quantidadeEstoque,
        Categoria       categoria,
        Boolean         ativo,
        LocalDateTime   criadoEm,
        LocalDateTime   atualizadoEm
) {
    public static ProdutoResponse fromEntity(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeEstoque(),
                produto.getCategoria(),
                produto.getAtivo(),
                produto.getCriadoEm(),
                produto.getAtualizadoEm()
        );
    }
}

