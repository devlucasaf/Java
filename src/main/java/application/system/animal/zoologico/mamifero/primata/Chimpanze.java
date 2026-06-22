package application.system.animal.zoologico.mamifero.primata;

public class Chimpanze extends Primata {

    public Chimpanze(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " grita e pula!");
    }
}
