package application.system.animal.zoologico.mamifero.primata;

public class Macaco extends Primata {

    public Macaco(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " grita e pula!");
    }
}
