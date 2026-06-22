package application.system.animal.zoologico.ave;

public class Pavao extends Ave {

    public Pavao(String nome, int idade) {
        super(nome, idade);
    }

    public void abrirCauda() {
        System.out.println(getNome() + " abre sua linda cauda colorida.");
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " emite um chamado alto.");
    }
}