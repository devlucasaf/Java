package application.system.animal.petshop.model.animal;

import application.system.animal.petshop.model.enums.PorteAnimal;
import application.system.animal.petshop.model.enums.TipoAnimal;

import java.time.LocalDate;

public class Hamster extends Animal {
    private boolean gostaDeRoda;
    private String  corPelagem;
    private int     nivelAtividade;

    public Hamster(String nome, String raca, LocalDate dataNascimento, PorteAnimal porte,
                   double peso, String cor, boolean gostaDeRoda, String corPelagem, int nivelAtividade) {
        super(nome, raca, dataNascimento, TipoAnimal.HAMSTER, porte, peso, cor);
        this.gostaDeRoda = gostaDeRoda;
        this.corPelagem = corPelagem;
        this.nivelAtividade = Math.max(1, Math.min(10, nivelAtividade));
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " faz: Squeeak squeeak!");
    }

    public void correrNaRoda() {
        if (gostaDeRoda) {
            System.out.println(nome + " está correndo feliz na roda!");
        } else {
            System.out.println(nome + " não gosta muito da roda... prefere escavar.");
        }
    }

    public void escavar() {
        System.out.println(nome + " está cavando a serragem.");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Gosta de roda: " + (gostaDeRoda ? "Sim" : "Não"));
        System.out.println("Cor da pelagem: " + corPelagem);
        System.out.println("Nível de atividade (1-10): " + nivelAtividade);
    }

    // Getters adicionais se necessário
    public boolean isGostaDeRoda() {
        return gostaDeRoda;
    }

    public String getCorPelagem() {
        return corPelagem;
    }

    public int getNivelAtividade() {
        return nivelAtividade;
    }
}
