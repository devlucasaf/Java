package games.narrativo.escape;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String                identificador;
    private final String                nome;
    private final String                descricao;
    private final Map<Direcao, String>  saidas;
    private final List<Item>            itens;
    private final List<Enigma>          enigmas;
    private boolean                     visitada;

    public Sala(String identificador, String nome, String descricao) {
        if (identificador == null || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException("O identificador da sala não pode estar vazio.");
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da sala não pode estar vazio.");
        }

        this.identificador = identificador;
        this.nome = nome;
        this.descricao = descricao == null ? "" : descricao;
        this.saidas = new EnumMap<>(Direcao.class);
        this.itens = new ArrayList<>();
        this.enigmas = new ArrayList<>();
        this.visitada = false;
    }

    // --- ADICIONA UMA SAÍDA PARA OUTRA SALA ---
    public void adicionarSaida(Direcao direcao, String identificadorSalaDestino) {
        if (direcao == null || identificadorSalaDestino == null || identificadorSalaDestino.trim().isEmpty()) {
            throw new IllegalArgumentException("A direção e a sala de destino devem ser informadas.");
        }

        saidas.put(direcao, identificadorSalaDestino);
    }

    // --- ADICIONA UM ITEM À SALA ---
    public void adicionarItem(Item item) {
        if (item != null && !itens.contains(item)) {
            itens.add(item);
        }
    }

    // --- REMOVE E RETORNA UM ITEM DA SALA ---
    public Item removerItem(String texto) {
        Item item = buscarItem(texto);

        if (item != null) {
            itens.remove(item);
        }

        return item;
    }

    // --- PROCURA UM ITEM EXISTENTE NA SALA ---
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

    // --- ADICIONA UM ENIGMA À SALA ---
    public void adicionarEnigma(Enigma enigma) {
        if (enigma != null) {
            enigmas.add(enigma);
        }
    }

    // --- PROCURA UM ENIGMA PELO IDENTIFICADOR OU TÍTULO ---
    public Enigma buscarEnigma(String texto) {
        if (texto == null) {
            return null;
        }

        for (Enigma enigma : enigmas) {
            if (enigma.getIdentificador().equalsIgnoreCase(texto.trim()) || enigma.getTitulo().equalsIgnoreCase(texto.trim())) {
                return enigma;
            }
        }

        return null;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getSalaDestino(Direcao direcao) {
        return saidas.get(direcao);
    }

    public Map<Direcao, String> getSaidas() {
        return Collections.unmodifiableMap(saidas);
    }

    public List<Item> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public List<Enigma> getEnigmas() {
        return Collections.unmodifiableList(enigmas);
    }

    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }
}

