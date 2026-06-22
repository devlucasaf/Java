package application.system.animal.zoologico.mamifero;

public class Ariranha extends Mamifero {

    public Ariranha(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " emite sons agudos!");
    }
}
