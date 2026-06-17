package application.system.loja;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Loja loja = new Loja("Tech Universe");

        // Criando itens (polimorfismo: diferentes subclasses armazenadas como Item)
        Item smartphone = new Smartphone(1, "Galaxy S25", 4599.90, 10, 256, 108);
        Item laptop = new Laptop(2, "ThinkPad X1", 8999.00, 5, 32, "Intel Core Ultra 7");
        Item tv = new Televisao(3, "Smart TV 55", 3299.99, 7, 55, "4K");
        Item acessorio = new Acessorio(4, "Fone Bluetooth", 499.90, 20, "Áudio", true);

        // Registrando itens no estoque
        loja.getEstoque().registrarItem(smartphone);
        loja.getEstoque().registrarItem(laptop);
        loja.getEstoque().registrarItem(tv);
        loja.getEstoque().registrarItem(acessorio);

        // Demonstração de polimorfismo com lista de Item
        List<Item> catalogo = List.of(smartphone, laptop, tv, acessorio);
        System.out.println("=== Catálogo ===");

        for (Item item : catalogo) {
            item.exibirDetalhes();
        }

        // Criando vendedor e cliente
        Vendedor vendedor = new Vendedor("Marina Lima", "V001", 0.06);
        loja.adicionarVendedor(vendedor);

        Cliente cliente = new Cliente("Lucas Freitas", "123.456.789-10");

        // Criando pedido
        Pedido pedido = new Pedido(cliente, vendedor);
        pedido.adicionarItem(smartphone, 1);
        pedido.adicionarItem(acessorio, 2);

        // Finalizando pedido com pagamento PIX
        Pagamento pagamento = new PagamentoPix(0, "lucas@pix");
        String resultadoPagamento = pedido.finalizarPedido(loja, pagamento);

        // Exibindo resultados
        System.out.println("\n=== Resultado do Pedido ===");
        System.out.println("Id do pedido: " + pedido.getId());
        System.out.printf("Valor total do pedido: %.2f%n", pedido.calcularTotal());
        System.out.println(resultadoPagamento);

        System.out.println("\n=== Comissão do Vendedor ===");
        System.out.printf("Vendedor: %s | Total vendas: %.2f | Comissão: %.2f%n",
                vendedor.getNome(), vendedor.getTotalVendas(), vendedor.calcularComissao());

        System.out.println("\n=== Histórico do Cliente ===");
        System.out.println("Cliente: " + cliente.getNome() + " | Pedidos: " + cliente.getHistoricoCompras().size());

        System.out.println("\n=== Estoque Após Pedido ===");

        for (Item item : loja.getEstoque().getTodosItens()) {
            System.out.printf("%s -> estoque=%d%n", item.getNome(), item.getQuantidadeEstoque());
        }
    }
}
