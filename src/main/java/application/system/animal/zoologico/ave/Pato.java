package application.system.animal.zoologico.ave;

public class Pato extends Ave {

    public Pato(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " faz quack quack!");
    }
}
