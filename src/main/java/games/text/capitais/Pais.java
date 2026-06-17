package games.text.capitais;

public class Pais {

    private final String nome;
    private final String capital;
    private final String continente;

    public Pais(String nome, String capital, String continente) {
        this.nome = nome;
        this.capital = capital;
        this.continente = continente;
    }

    public String getNome() {
        return nome;
    }

    public String getCapital() {
        return capital;
    }

    public String getContinente() {
        return continente;
    }
}

