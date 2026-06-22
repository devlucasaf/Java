package application.system.animal.zoologico.mamifero.felino;
public class Pantera extends Felino {
    public Pantera(String nome, int idade) {
        super(nome, idade);
    }
    @Override
    public void emitirSom() {
        System.out.println(getNome() + " ruge baixo na escuridao!");
    }
}
