package org.application.apiprodutos.dto;

import jakarta.validation.constraints.*;
import org.application.apiprodutos.model.Categoria;
import java.math.BigDecimal;

public record ProdutoRequest(

        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
        String nome,

        @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
        String descricao,

        @NotNull(message = "Preço é obrigatório")
        @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
        BigDecimal preco,

        @Min(value = 0, message = "Quantidade não pode ser negativa")
        Integer quantidadeEstoque,

        Categoria categoria
) {
    public ProdutoRequest {
        if (quantidadeEstoque == null) {
            quantidadeEstoque = 0;
        }

        if (categoria == null) {
            categoria = Categoria.OUTROS;
        }
    }
}

