package application.system.animal.zoologico.ave;

public class Ema extends Ave {

    public Ema(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " emite um som grave.");
    }
}
