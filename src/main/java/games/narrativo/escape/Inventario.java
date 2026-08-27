package games.narrativo.escape;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    // --- ADICIONA UM ITEM AO INVENTÁRIO ---
    public boolean adicionarItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo.");
        }

        if (!item.isColetavel() || possuiItem(item.getIdentificador())) {
            return false;
        }

        itens.add(item);
        return true;
    }

    // --- REMOVE UM ITEM DO INVENTÁRIO PELO IDENTIFICADOR ---
    public Item removerItem(String identificador) {
        Item item = buscarItem(identificador);

        if (item != null) {
            itens.remove(item);
        }

        return item;
    }

    // --- PROCURA UM ITEM PELO IDENTIFICADOR OU NOME ---
    public Item buscarItem(String texto) {
        if (texto == null) {
            return null;
        }

        for (Item item : itens) {
            if (item.getIdentificador().equalsIgnoreCase(texto.trim()) || item.getNome().equalsIgnoreCase(texto.trim())) {
                return item;
            }
        }

        return null;
    }

    // --- VERIFICA SE O INVENTÁRIO POSSUI DETERMINADO ITEM ---
    public boolean possuiItem(String identificador) {
        return buscarItem(identificador) != null;
    }

    // --- RETORNA UMA LISTA NÃO MODIFICÁVEL DOS ITENS ---
    public List<Item> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public boolean isVazio() {
        return itens.isEmpty();
    }
}

