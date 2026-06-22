package application.system.animal.zoologico.mamifero.primata;
public class Orangotango extends Primata {
    public Orangotango(String nome, int idade) {
        super(nome, idade);
    }
    @Override
    public void emitirSom() {
        System.out.println(getNome() + " emite chamados longos!");
    }
}
