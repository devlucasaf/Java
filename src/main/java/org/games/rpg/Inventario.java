package org.games.rpg;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private final List<ItemRPG> itens;
    private final int           capacidadeMaxima = 10;

    public Inventario() {
        this.itens = new ArrayList<>();
        itens.add(new ItemRPG("Poção de Vida", "cura", 30, 15));
        itens.add(new ItemRPG("Poção de Vida", "cura", 30, 15));
    }

    public boolean adicionarItem(ItemRPG item) {
        if (itens.size() >= capacidadeMaxima) {
            System.out.println("Inventário cheio! Não é possível adicionar " + item.getNome());
            return false;
        }
        itens.add(item);
        return true;
    }

    public ItemRPG usarItem(int indice) {
        if (indice < 0 || indice >= itens.size()) {
            System.out.println("Item inválido!");
            return null;
        }
        return itens.remove(indice);
    }

    public void mostrar() {
        if (itens.isEmpty()) {
            System.out.println("Inventário vazio!");
            return;
        }
        System.out.println("\n=== INVENTÁRIO ===");
        for (int i = 0; i < itens.size(); i++) {
            System.out.println((i + 1) + ". " + itens.get(i));
        }
        System.out.println("==================");
    }

    public List<ItemRPG> getItens() {
        return itens;
    }

    public boolean temItens() {
        return !itens.isEmpty();
    }
}

