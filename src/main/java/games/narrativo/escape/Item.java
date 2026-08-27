package games.narrativo.escape;

import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String    identificador;
    private final String    nome;
    private final String    descricao;
    private final boolean   coletavel;

    public Item(String identificador, String nome, String descricao, boolean coletavel) {
        if (identificador == null || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException("O identificador do item não pode estar vazio.");
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do item não pode estar vazio.");
        }

        this.identificador = identificador;
        this.nome = nome;
        this.descricao = descricao == null ? "" : descricao;
        this.coletavel = coletavel;
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

    public boolean isColetavel() {
        return coletavel;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Item)) {
            return false;
        }

        Item item = (Item) objeto;
        return identificador.equals(item.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return nome;
    }
}

