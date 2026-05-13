package org.games.rpg;

import java.util.Scanner;

public class Loja {
    private static final ItemRPG[] ITENS_DISPONIVEIS = {
        new ItemRPG("Poção de Vida", "cura", 30, 15),
        new ItemRPG("Poção de Vida Grande", "cura", 60, 30),
        new ItemRPG("Elixir de Vida", "cura", 100, 55),
        new ItemRPG("Amuleto de Força", "ataque", 5, 40),
        new ItemRPG("Escudo de Ferro", "defesa", 4, 35),
    };

    public static void abrir(Personagem jogador, Scanner scanner) {
        System.out.println("\n === LOJA DO AVENTUREIRO ===");
        System.out.println("Seu ouro: " + jogador.getOuro());
        System.out.println("0. Sair da loja");
        for (int i = 0; i < ITENS_DISPONIVEIS.length; i++) {
            System.out.println((i + 1) + ". " + ITENS_DISPONIVEIS[i]);
        }

        System.out.print("Escolha um item para comprar: ");
        int escolha;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida!");
            return;
        }

        if (escolha == 0) {
            return;
        }

        if (escolha < 1 || escolha > ITENS_DISPONIVEIS.length) {
            System.out.println("Item inválido!");
            return;
        }

        ItemRPG item = ITENS_DISPONIVEIS[escolha - 1];
        if (jogador.getOuro() < item.getPreco()) {
            System.out.println("Ouro insuficiente! Você precisa de " + item.getPreco() + " de ouro.");
            return;
        }

        if (item.getTipo().equals("ataque") || item.getTipo().equals("defesa")) {
            jogador.setOuro(jogador.getOuro() - item.getPreco());
            System.out.println(" " + item.getNome() + " equipado! (efeito permanente nesta sessão)");
            jogador.getInventario().adicionarItem(
                new ItemRPG(item.getNome(), item.getTipo(), item.getValor(), item.getPreco())
            );
        } else {
            jogador.setOuro(jogador.getOuro() - item.getPreco());
            jogador.getInventario().adicionarItem(
                new ItemRPG(item.getNome(), item.getTipo(), item.getValor(), item.getPreco())
            );
            System.out.println(" " + item.getNome() + " adicionado ao inventário!");
        }
    }
}

