package application.system.animal.zoologico.mamifero.primata;
public class Gorila extends Primata {
    public Gorila(String nome, int idade) {
        super(nome, idade);
    }
    @Override
    public void emitirSom() {
        System.out.println(getNome() + " bate no peito e grunhe!");
    }
}
