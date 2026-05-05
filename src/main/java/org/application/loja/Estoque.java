package org.application.loja;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Estoque {
    private final Map<Integer, Item> inventario = new LinkedHashMap<>();

    public void registrarItem(Item item) {
        if (inventario.containsKey(item.getId())) {
            throw new IllegalArgumentException("Id do item já registrado: " + item.getId());
        }
        inventario.put(item.getId(), item);
    }

    public Item buscarPorId(int id) {
        Item item = inventario.get(id);
        if (item == null) {
            throw new IllegalArgumentException("Id do item não encontrado: " + id);
        }
        return item;
    }

    public boolean temEstoqueSuficiente(Item item, int quantidade) {
        return item.getQuantidadeEstoque() >= quantidade;
    }

    public void deduzirEstoque(Item item, int quantidade) {
        if (!temEstoqueSuficiente(item, quantidade)) {
            throw new IllegalStateException("Estoque insuficiente para o item id " + item.getId());
        }
        item.diminuirEstoque(quantidade);
    }

    public Collection<Item> getTodosItens() {
        return inventario.values();
    }
}
