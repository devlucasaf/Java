package application.system.animal.zoologico.reptil;

public class Camaleao extends Reptil {

    public Camaleao(String nome, int idade) {
        super(nome, idade);
    }

    public void mudarCor() {
        System.out.println(getNome() + " está mudando de cor para se camuflar.");
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " emite um som sutil.");
    }
}

