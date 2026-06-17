package games.narrativo.rpg;

public class ItemRPG {
    private String  nome;
    private String  tipo;
    private int     valor;
    private int     preco;

    public ItemRPG(String nome, String tipo, int valor, int preco) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getValor() {
        return valor;
    }

    public int getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        String descricao = switch (tipo) {
            case "cura" -> "Cura " + valor + " HP";
            case "ataque" -> "+" + valor + " Ataque";
            case "defesa" -> "+" + valor + " Defesa";
            default -> "Efeito: " + valor;
        };
        return nome + " [" + descricao + "] (R$ " + preco + ")";
    }
}

