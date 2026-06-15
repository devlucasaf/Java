package org.application.system.restaurante;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        Restaurante restaurante = new Restaurante(
                "Sabor & Arte",
                "Rua das Flores, 123 - Centro",
                "(11) 99999-1234"
        );

        restaurante.adicionarMesa(new Mesa(1, 2));
        restaurante.adicionarMesa(new Mesa(2, 4));
        restaurante.adicionarMesa(new Mesa(3, 4));
        restaurante.adicionarMesa(new Mesa(4, 6));
        restaurante.adicionarMesa(new Mesa(5, 8));
        restaurante.adicionarMesa(new Mesa(6, 2));
        restaurante.adicionarMesa(new Mesa(7, 4));
        restaurante.adicionarMesa(new Mesa(8, 10));

        Garcom garcom1 = new Garcom("Pedro Silva", "G001", 2200.00, 0.10);
        Garcom garcom2 = new Garcom("Ana Souza", "G002", 2200.00, 0.10);
        Garcom garcom3 = new Garcom("Carlos Lima", "G003", 2200.00, 0.10);

        Cozinheiro chef = new Cozinheiro("Roberto Nakamura", "C001", Cargo.CHEF, 8500.00, "Culinária Japonesa");
        Cozinheiro cozinheiro1 = new Cozinheiro("Maria Oliveira", "C002", Cargo.COZINHEIRO, 4200.00, "Massas");
        Cozinheiro cozinheiro2 = new Cozinheiro("João Santos", "C003", Cargo.AUXILIAR_COZINHA, 2800.00, "Grelhados");

        restaurante.adicionarFuncionario(garcom1);
        restaurante.adicionarFuncionario(garcom2);
        restaurante.adicionarFuncionario(garcom3);
        restaurante.adicionarFuncionario(chef);
        restaurante.adicionarFuncionario(cozinheiro1);
        restaurante.adicionarFuncionario(cozinheiro2);

        // Entradas
        Entrada entrada1 = new Entrada(1, "Bruschetta", "Pão italiano com tomate e manjericão", 28.90, 10, true, 2);
        Entrada entrada2 = new Entrada(2, "Carpaccio", "Finas fatias de carne com rúcula e parmesão", 42.00, 8, false, 1);
        Entrada entrada3 = new Entrada(3, "Bolinho de Bacalhau", "6 unidades crocantes", 35.50, 12, true, 3);
        Entrada entrada4 = new Entrada(4, "Ceviche", "Peixe branco marinado com limão e coentro", 38.00, 5, false, 1);

        // Pratos Principais
        Prato prato1 = new Prato(5, "Filé Mignon ao Molho Madeira", "Com arroz e batatas", 89.90, 25,
                "Filé Mignon", false, true, TamanhoPorcao.MEDIA);
        Prato prato2 = new Prato(6, "Risoto de Funghi", "Arroz arbóreo com mix de cogumelos", 72.00, 20,
                "Cogumelos", true, true, TamanhoPorcao.MEDIA);
        Prato prato3 = new Prato(7, "Salmão Grelhado", "Com legumes na manteiga", 95.00, 18,
                "Salmão", false, true, TamanhoPorcao.MEDIA);
        Prato prato4 = new Prato(8, "Lasanha Bolonhesa", "Massa fresca com molho artesanal", 62.00, 30,
                "Carne Moída", false, false, TamanhoPorcao.MEDIA);
        Prato prato5 = new Prato(9, "Feijoada Completa", "Com todos os acompanhamentos", 78.00, 35,
                "Feijão Preto", false, false, TamanhoPorcao.GRANDE);
        Prato prato6 = new Prato(10, "Pad Thai Vegetariano", "Macarrão de arroz com vegetais e amendoim", 55.00, 15,
                "Macarrão de Arroz", true, true, TamanhoPorcao.MEDIA);

        // Sobremesas
        Sobremesa sobremesa1 = new Sobremesa(11, "Petit Gâteau", "Bolo de chocolate com sorvete", 32.00, 12,
                450, true, false, "Chocolate");
        Sobremesa sobremesa2 = new Sobremesa(12, "Cheesecake de Frutas Vermelhas", "Com calda artesanal", 28.00, 5,
                380, true, false, "Frutas Vermelhas");
        Sobremesa sobremesa3 = new Sobremesa(13, "Pudim de Leite", "Receita tradicional", 18.00, 3,
                290, true, false, "Leite Condensado");
        Sobremesa sobremesa4 = new Sobremesa(14, "Açaí na Tigela", "Com granola, banana e mel", 25.00, 5,
                320, false, true, "Açaí");

        // Bebidas
        Bebida bebida1 = new Bebida(15, "Suco Natural de Laranja", "Feito na hora", 12.00, 3,
                300, true, false, 0);
        Bebida bebida2 = new Bebida(16, "Caipirinha", "Limão, açúcar e cachaça artesanal", 22.00, 5,
                350, true, true, 15.0);
        Bebida bebida3 = new Bebida(17, "Refrigerante", "Lata 350ml", 8.00, 1,
                350, true, false, 0);
        Bebida bebida4 = new Bebida(18, "Vinho Tinto Reserva", "Taça - Cabernet Sauvignon", 35.00, 2,
                150, false, true, 13.5);
        Bebida bebida5 = new Bebida(19, "Água Mineral", "Com ou sem gás", 6.00, 1,
                500, true, false, 0);
        Bebida bebida6 = new Bebida(20, "Chopp Artesanal", "IPA - 500ml", 18.00, 2,
                500, true, true, 6.5);

        // Acompanhamentos
        Acompanhamento acomp1 = new Acompanhamento(21, "Arroz Branco", "Porção individual", 8.00, 5, "Arroz", true);
        Acompanhamento acomp2 = new Acompanhamento(22, "Farofa Crocante", "Com bacon e ovos", 12.00, 5, "Farofa", false);
        Acompanhamento acomp3 = new Acompanhamento(23, "Salada Caesar", "Alface, croutons e parmesão", 22.00, 5, "Salada", false);

        // Registrando tudo no cardápio
        restaurante.getCardapio().adicionarItem(entrada1);
        restaurante.getCardapio().adicionarItem(entrada2);
        restaurante.getCardapio().adicionarItem(entrada3);
        restaurante.getCardapio().adicionarItem(entrada4);
        restaurante.getCardapio().adicionarItem(prato1);
        restaurante.getCardapio().adicionarItem(prato2);
        restaurante.getCardapio().adicionarItem(prato3);
        restaurante.getCardapio().adicionarItem(prato4);
        restaurante.getCardapio().adicionarItem(prato5);
        restaurante.getCardapio().adicionarItem(prato6);
        restaurante.getCardapio().adicionarItem(sobremesa1);
        restaurante.getCardapio().adicionarItem(sobremesa2);
        restaurante.getCardapio().adicionarItem(sobremesa3);
        restaurante.getCardapio().adicionarItem(sobremesa4);
        restaurante.getCardapio().adicionarItem(bebida1);
        restaurante.getCardapio().adicionarItem(bebida2);
        restaurante.getCardapio().adicionarItem(bebida3);
        restaurante.getCardapio().adicionarItem(bebida4);
        restaurante.getCardapio().adicionarItem(bebida5);
        restaurante.getCardapio().adicionarItem(bebida6);
        restaurante.getCardapio().adicionarItem(acomp1);
        restaurante.getCardapio().adicionarItem(acomp2);
        restaurante.getCardapio().adicionarItem(acomp3);

        // Combo especial
        Combo combo1 = new Combo(24, "Combo Executivo",
                "Entrada + Prato + Bebida com desconto", 20, 0.15,
                entrada1, prato4, bebida3);
        restaurante.getCardapio().adicionarItem(combo1);

        // Pratos assinatura do chef
        chef.adicionarPratoAssinatura(prato1);
        chef.adicionarPratoAssinatura(prato3);

        restaurante.getCardapio().exibirCardapio();

        Cliente cliente1 = new Cliente("Lucas Freitas", "(11) 98765-4321", "123.456.789-00");
        Cliente cliente2 = new Cliente("Mariana Costa", "(11) 91234-5678", "987.654.321-00");
        Cliente cliente3 = new Cliente("Fernando Alves", "(11) 95555-1234", "456.789.123-00");

        restaurante.cadastrarCliente(cliente1);
        restaurante.cadastrarCliente(cliente2);
        restaurante.cadastrarCliente(cliente3);

        System.out.println("\n\n========================================");
        System.out.println("   SIMULAÇÃO DE ATENDIMENTO - MESA 2");
        System.out.println("========================================");

        Mesa mesa2 = restaurante.buscarMesa(2);
        mesa2.ocupar();
        garcom1.atribuirMesa(mesa2);

        Pedido pedido1 = new Pedido(cliente1, garcom1, mesa2);
        pedido1.adicionarItem(entrada1, 1);
        pedido1.adicionarItem(prato1, 1, "Bem passado");
        pedido1.adicionarItem(prato3, 1, "Sem pele");
        pedido1.adicionarItem(bebida4, 2);
        pedido1.adicionarItem(sobremesa1, 2);
        pedido1.adicionarItem(acomp3, 1);
        pedido1.setObservacaoGeral("Aniversário - trazer vela na sobremesa");

        mesa2.adicionarPedido(pedido1);
        pedido1.exibirResumo();

        // Fila da cozinha
        FilaCozinha filaCozinha = new FilaCozinha();
        filaCozinha.adicionarPedido(pedido1);
        chef.prepararPrato();
        chef.prepararPrato();
        cozinheiro1.prepararPrato();

        filaCozinha.prepararProximo();
        filaCozinha.entregarPedido();

        // Finalizando com pagamento
        Pagamento pagamento1 = new PagamentoCartao(0, "4532", true, 2);
        String resultado1 = pedido1.finalizarPedido(restaurante, pagamento1);
        System.out.println("\n" + resultado1);

        System.out.println("\n\n========================================");
        System.out.println("   SIMULAÇÃO DE ATENDIMENTO - MESA 4");
        System.out.println("========================================");

        Mesa mesa4 = restaurante.buscarMesa(4);
        mesa4.ocupar();
        garcom2.atribuirMesa(mesa4);

        Pedido pedido2 = new Pedido(cliente2, garcom2, mesa4);
        pedido2.adicionarItem(entrada3, 2);
        pedido2.adicionarItem(prato5, 1);
        pedido2.adicionarItem(prato6, 1, "Sem amendoim");
        pedido2.adicionarItem(bebida2, 2);
        pedido2.adicionarItem(bebida1, 1);
        pedido2.adicionarItem(acomp2, 1);
        pedido2.adicionarItem(sobremesa3, 2);

        mesa4.adicionarPedido(pedido2);
        pedido2.exibirResumo();

        // Processando na cozinha
        filaCozinha.adicionarPedido(pedido2);
        cozinheiro2.prepararPrato();
        cozinheiro2.prepararPrato();
        filaCozinha.prepararProximo();
        filaCozinha.entregarPedido();

        // Pagamento PIX
        Pagamento pagamento2 = new PagamentoPix(0, "mariana@email.com");
        String resultado2 = pedido2.finalizarPedido(restaurante, pagamento2);
        System.out.println("\n" + resultado2);

        System.out.println("\n\n========================================");
        System.out.println("   SIMULAÇÃO DE ATENDIMENTO - MESA 1");
        System.out.println("========================================");

        Mesa mesa1 = restaurante.buscarMesa(1);
        mesa1.ocupar();
        garcom3.atribuirMesa(mesa1);

        Pedido pedido3 = new Pedido(cliente3, garcom3, mesa1);
        pedido3.adicionarItem(combo1, 1);
        pedido3.adicionarItem(bebida6, 2);
        pedido3.adicionarItem(sobremesa4, 1);

        mesa1.adicionarPedido(pedido3);
        pedido3.exibirResumo();

        filaCozinha.adicionarPedido(pedido3);
        cozinheiro1.prepararPrato();
        filaCozinha.prepararProximo();
        filaCozinha.entregarPedido();

        // Pagamento em dinheiro
        double totalPedido3 = pedido3.calcularTotal();
        Pagamento pagamento3 = new PagamentoDinheiro(0, totalPedido3 + 20); // dá R$20 a mais
        String resultado3 = pedido3.finalizarPedido(restaurante, pagamento3);
        System.out.println("\n" + resultado3);

        System.out.println("\n\n========================================");
        System.out.println("   RESERVAS");
        System.out.println("========================================");

        Reserva reserva = restaurante.fazerReserva(cliente1, 5,
                LocalDateTime.now().plusDays(1).withHour(20).withMinute(0), 6);
        reserva.setObservacao("Jantar de negócios - precisa de cardápio em inglês");
        System.out.println(reserva);

        Reserva reserva2 = restaurante.fazerReserva(cliente2, 7,
                LocalDateTime.now().plusDays(2).withHour(19).withMinute(30), 3);
        System.out.println(reserva2);

        System.out.println("\n\n========================================");
        System.out.println("   AVALIAÇÕES");
        System.out.println("========================================");

        Avaliacao avaliacao1 = new Avaliacao(cliente1, pedido1, 5, 5, 4, "Excelente! Comida incrível e atendimento impecável.");
        Avaliacao avaliacao2 = new Avaliacao(cliente2, pedido2, 4, 5, 4, "Muito bom, voltarei com certeza!");
        Avaliacao avaliacao3 = new Avaliacao(cliente3, pedido3, 5, 4, 5, "Combo com ótimo custo-benefício.");

        restaurante.adicionarAvaliacao(avaliacao1);
        restaurante.adicionarAvaliacao(avaliacao2);
        restaurante.adicionarAvaliacao(avaliacao3);

        for (Avaliacao av : restaurante.getAvaliacoes()) {
            System.out.println(av);
        }

        restaurante.exibirStatusMesas();

        System.out.println("\n\n========================================");
        System.out.println("   PROGRAMA DE FIDELIDADE");
        System.out.println("========================================");

        for (Cliente cliente : restaurante.getClientes()) {
            System.out.printf("Cliente: %s | Pontos: %d | Total gasto: R$%.2f | Pedidos: %d%n",
                    cliente.getNome(),
                    cliente.getPontosFidelidade(),
                    cliente.getTotalGasto(),
                    cliente.getHistoricoPedidos().size()
            );

            System.out.println("\n\n========================================");
            System.out.println("   FUNCIONÁRIOS");
            System.out.println("========================================");

            for (Funcionario funcionario : restaurante.getFuncionarios()) {
                System.out.printf("%s | Remuneração total: R$%.2f%n",
                        funcionario,
                        funcionario.calcularRemuneracaoTotal()
                );
            }

            restaurante.exibirRelatorio();

            filaCozinha.exibirStatus();
        }
    }
}