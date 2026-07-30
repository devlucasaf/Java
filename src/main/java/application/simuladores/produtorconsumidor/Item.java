package application.simuladores.produtorconsumidor;

public class Item {

    private final int       id;
    private final String    conteudo;
    private final long      timestamp;

    public Item(int id, String conteudo) {
        this.id = id;
        this.conteudo = conteudo;
        this.timestamp = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Item#" + id + " [" + conteudo + "]";
    }
}

