package application.system.animal.zoologico.mamifero;

public class Macaco extends Mamifero {

    public Macaco(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " grita e pula!");
    }
}
