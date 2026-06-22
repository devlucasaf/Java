package application.system.animal.zoologico.mamifero.primata;
public class MicoLeaoDourado extends Primata {
    public MicoLeaoDourado(String nome, int idade) {
        super(nome, idade);
    }
    @Override
    public void emitirSom() {
        System.out.println(getNome() + " guincha agudo!");
    }
}
