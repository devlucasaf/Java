package application.system.animal.zoologico.mamifero;

public class Tigre extends Mamifero {

    public Tigre(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " rosna!");
    }
}

