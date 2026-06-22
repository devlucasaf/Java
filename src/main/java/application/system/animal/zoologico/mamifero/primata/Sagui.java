package application.system.animal.zoologico.mamifero.primata;
public class Sagui extends Primata {
    public Sagui(String nome, int idade) {
        super(nome, idade);
    }
    @Override
    public void emitirSom() {
        System.out.println(getNome() + " pia rapidamente!");
    }
}
