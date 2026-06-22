package application.system.animal.zoologico.ave;

public class Tucano extends Ave {

    public Tucano(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " faz um som alto com o bico!");
    }
}
