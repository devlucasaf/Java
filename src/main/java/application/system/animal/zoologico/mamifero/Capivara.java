package application.system.animal.zoologico.mamifero;

public class Capivara extends Mamifero {

    public Capivara(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " faz um som baixo de capivara.");
    }
}