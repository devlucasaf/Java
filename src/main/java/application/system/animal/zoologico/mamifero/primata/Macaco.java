package application.system.animal.zoologico.mamifero.primata;

import application.system.animal.zoologico.mamifero.Mamifero;

public class Macaco extends Mamifero {

    public Macaco(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " grita e pula!");
    }
}
