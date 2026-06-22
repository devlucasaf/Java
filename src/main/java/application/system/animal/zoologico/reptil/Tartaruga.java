package application.system.animal.zoologico.reptil;

public class Tartaruga extends Reptil {

    public Tartaruga(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " faz sons quase imperceptíveis.");
    }
}

