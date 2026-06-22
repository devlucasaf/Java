package application.system.animal.zoologico.mamifero.felino;
public class Leopardo extends Felino {
    public Leopardo(String nome, int idade) {
        super(nome, idade);
    }
    @Override
    public void emitirSom() {
        System.out.println(getNome() + " ronrona alto!");
    }
}
