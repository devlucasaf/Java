package application.system.marketplace;

import application.system.marketplace.enums.Status;
import application.system.marketplace.enums.Tipo;
import application.system.marketplace.model.*;
import application.system.marketplace.service.MarketplaceService;

public class Main {

    public static void main(String[] args) {
        MarketplaceService marketplaceService = new MarketplaceService();

        // Cadastro de vendedores
        Vendedor vendedorA = new Vendedor("V1", "TechStore", 12.0);
        Vendedor vendedorB = new Vendedor("V2", "CasaFeliz", 8.0);
        marketplaceService.cadastrarVendedor(vendedorA);
        marketplaceService.cadastrarVendedor(vendedorB);

        // Cadastro de produtos
        marketplaceService.cadastrarProduto(new Produto("P1", "Fone Bluetooth", "Eletronicos", 149.90, 20, vendedorA, 0.3));
        marketplaceService.cadastrarProduto(new Produto("P2", "Teclado Mecanico", "Eletronicos", 259.90, 10, vendedorA, 1.1));
        marketplaceService.cadastrarProduto(new Produto("P3", "Jogo de Panelas", "Casa", 189.90, 15, vendedorB, 4.5));

        // Cadastro de cupom
        marketplaceService.cadastrarCupom(new Cupom("BEMVINDO10", Tipo.PERCENTUAL, 10, 50.0));

        System.out.println("=== Produtos na categoria Eletronicos ===");
        for (Produto p : marketplaceService.buscarPorCategoria("Eletronicos")) {
            System.out.println(p);
        }

        Cliente cliente = new Cliente("C1", "Marina Souza", "70000-000");
        Carrinho carrinho = new Carrinho(cliente);
        carrinho.adicionarProduto(marketplaceService.buscarProdutoPorId("P1"), 1);
        carrinho.adicionarProduto(marketplaceService.buscarProdutoPorId("P2"), 1);
        carrinho.aplicarCupom(marketplaceService.buscarCupom("BEMVINDO10"));

        System.out.println();
        System.out.println("=== Fechando pedido ===");
        Pedido pedido = marketplaceService.fecharPedido(carrinho);
        pedido.imprimirResumo();

        pedido.avancarStatus(Status.PAGO);
        pedido.avancarStatus(Status.ENVIADO);

        System.out.println();
        System.out.println("Status atualizado apos pagamento e envio: " + pedido.getStatus());

        System.out.println();
        System.out.println("=== Estoque restante ===");
        for (Produto p : marketplaceService.listarTodosProdutos()) {
            System.out.println(p);
        }
    }
}
