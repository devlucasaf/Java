package application.system.animal.zoologico.mamifero.felino;

public class Onca extends Felino {

    public Onca(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " ruge fortemente!");
    }
}
