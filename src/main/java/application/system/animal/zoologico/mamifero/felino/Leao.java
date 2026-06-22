package application.system.animal.zoologico.mamifero.felino;

public class Leao extends Felino {

    public Leao(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " ruge!");
    }
}
