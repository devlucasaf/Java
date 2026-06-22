package application.system.animal.zoologico.reptil;

public class Calango extends Reptil {

    public Calango(String nome, int idade) {
        super(nome, idade);
    }

    public void correr() {
        System.out.println(getNome() + " corre rapidamente pelas pedras.");
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " faz um leve chiado.");
    }
}
