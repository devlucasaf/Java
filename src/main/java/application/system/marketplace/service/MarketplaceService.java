package application.system.marketplace.service;

import application.system.marketplace.model.*;

import java.util.*;

public class MarketplaceService {

    private final Map<String, Produto>  produtos = new LinkedHashMap<>();
    private final Map<String, Vendedor> vendedores = new LinkedHashMap<>();
    private final Map<String, Cupom>    cupons = new HashMap<>();
    private final List<Pedido>          pedidos = new ArrayList<>();

    public void cadastrarVendedor(Vendedor vendedor) {
        vendedores.put(vendedor.getId(), vendedor);
    }

    public void cadastrarProduto(Produto produto) {
        produtos.put(produto.getId(), produto);
    }

    public void cadastrarCupom(Cupom cupom) {
        cupons.put(cupom.getCodigo(), cupom);
    }

    public List<Produto> buscarPorCategoria(String categoria) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto p : produtos.values()) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public Produto buscarProdutoPorId(String id) {
        Produto produto = produtos.get(id);
        if (produto == null) {
            throw new NoSuchElementException("Produto nao encontrado: " + id);
        }
        return produto;
    }

    public Cupom buscarCupom(String codigo) {
        return cupons.get(codigo);
    }

    public Pedido fecharPedido(Carrinho carrinho) {
        if (carrinho.isVazio()) {
            throw new IllegalStateException("Não é possível fechar um pedido com carrinho vazio.");
        }

        for (ItemCarrinho item : carrinho.getItens()) {
            if (!item.getProduto().temEstoque(item.getQuantidade())) {
                throw new IllegalStateException("Estoque insuficiente para " + item.getProduto().getNome());
            }
        }

        for (ItemCarrinho item : carrinho.getItens()) {
            item.getProduto().reduzirEstoque(item.getQuantidade());
        }

        Pedido pedido = new Pedido(carrinho);

        if (carrinho.getCupomAplicado() != null) {
            carrinho.getCupomAplicado().marcarComoUsado();
        }

        pedidos.add(pedido);
        return pedido;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public Collection<Produto> listarTodosProdutos() {
        return produtos.values();
    }
}
