package application.system.animal.zoologico.reptil;

public class Cobra extends Reptil {

    public Cobra(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " sibila!");
    }
}

