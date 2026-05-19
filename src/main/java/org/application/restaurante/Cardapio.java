package org.application.restaurante;

import java.util.*;
import java.util.stream.Collectors;

public class Cardapio {
    private final Map<Integer, ItemCardapio> itens = new LinkedHashMap<>();

    public void adicionarItem(ItemCardapio item) {
        if (itens.containsKey(item.getId())) {
            throw new IllegalArgumentException("Item com id " + item.getId() + " já existe no cardápio");
        }
        itens.put(item.getId(), item);
    }

    public void removerItem(int id) {
        if (!itens.containsKey(id)) {
            throw new IllegalArgumentException("Item com id " + id + " não encontrado no cardápio");
        }
        itens.remove(id);
    }

    public ItemCardapio buscarPorId(int id) {
        ItemCardapio item = itens.get(id);
        if (item == null) {
            throw new IllegalArgumentException("Item não encontrado: " + id);
        }
        return item;
    }

    public List<ItemCardapio> buscarPorCategoria(CategoriaItem categoria) {
        return itens.values().stream()
                .filter(item -> item.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    public List<ItemCardapio> buscarDisponiveis() {
        return itens.values().stream()
                .filter(ItemCardapio::isDisponivel)
                .collect(Collectors.toList());
    }

    public List<ItemCardapio> buscarPorNome(String nome) {
        return itens.values().stream()
                .filter(item -> item.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Collection<ItemCardapio> getTodosItens() {
        return Collections.unmodifiableCollection(itens.values());
    }

    public void exibirCardapio() {
        System.out.println("                   C A R D Á P I O                    ");

        for (CategoriaItem categoria : CategoriaItem.values()) {
            List<ItemCardapio> itensDaCategoria = buscarPorCategoria(categoria);
            if (!itensDaCategoria.isEmpty()) {
                System.out.println("\n--- " + categoria.getDescricao().toUpperCase() + " ---");
                for (ItemCardapio item : itensDaCategoria) {
                    if (item.isDisponivel()) {
                        item.exibirDetalhes();
                    }
                }
            }
        }
    }
}

