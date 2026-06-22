package application.system.animal.zoologico.mamifero.felino;

public class Jaguatirica extends Felino {

    public Jaguatirica(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " emite um miado forte!");
    }
}
