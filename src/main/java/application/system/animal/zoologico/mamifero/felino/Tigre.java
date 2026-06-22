package application.system.animal.zoologico.mamifero.felino;

public class Tigre extends Felino {

    public Tigre(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " rosna!");
    }
}

