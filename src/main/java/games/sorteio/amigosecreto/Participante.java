package games.sorteio.amigosecreto;

public class Participante {
    private final String nome;
    private final String email;

    public Participante(String nome, String email) {
        this.nome = nome;
        this.email = email == null ? "" : email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return email.isEmpty() ? nome : nome + " <" + email + ">";
    }
}

