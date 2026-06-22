package application.system.animal.zoologico;

import application.system.animal.zoologico.ave.Ave;
import application.system.animal.zoologico.mamifero.Mamifero;
import application.system.animal.zoologico.mamifero.felino.Felino;
import application.system.animal.zoologico.mamifero.primata.Primata;
import application.system.animal.zoologico.reptil.Reptil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Zoologico {

    private final String        nome;
    private final int           capacidadeMaxima;
    private final List<Animal>  animais;

    public Zoologico(String nome, int capacidadeMaxima) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do zoologico nao pode ser vazio.");
        }

        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser positiva.");
        }
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.animais = new ArrayList<>();
    }

    public boolean adicionarAnimal(Animal animal) {
        if (animais.size() >= capacidadeMaxima) {
            System.out.println("Zoologico cheio. Nao foi possivel adicionar " + animal.getNome() + ".");
            return false;
        }
        animais.add(animal);
        return true;
    }

    public boolean removerAnimal(String nomeAnimal) {
        Optional<Animal> alvo = buscarPorNome(nomeAnimal);
        if (alvo.isPresent()) {
            animais.remove(alvo.get());
            System.out.println(nomeAnimal + " foi removido do zoologico.");
            return true;
        }
        System.out.println(nomeAnimal + " nao foi encontrado.");
        return false;
    }

    public Optional<Animal> buscarPorNome(String nomeAnimal) {
        return animais.stream()
                .filter(a -> a.getNome().equalsIgnoreCase(nomeAnimal))
                .findFirst();
    }

    public List<Animal> listarPorTipo(Class<? extends Animal> tipo) {
        List<Animal> resultado = new ArrayList<>();
        for (Animal a : animais) {
            if (tipo.isInstance(a)) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    public void exibirTodos() {
        System.out.println("\n=== ANIMAIS DO " + nome.toUpperCase() + " ===");
        if (animais.isEmpty()) {
            System.out.println("(Nenhum animal cadastrado)");
            return;
        }
        for (Animal a : animais) {
            System.out.println("- " + a);
        }
    }

    public void exibirContagemPorCategoria() {
        long aves = listarPorTipo(Ave.class).size();
        long mamiferos = listarPorTipo(Mamifero.class).size();
        long felinos = listarPorTipo(Felino.class).size();
        long primatas = listarPorTipo(Primata.class).size();
        long reptis = listarPorTipo(Reptil.class).size();

        System.out.println("\n=== CONTAGEM POR CATEGORIA ===");
        System.out.println("Total geral: " + animais.size() + " / " + capacidadeMaxima);
        System.out.println("Aves: " + aves);
        System.out.println("Mamiferos: " + mamiferos + " (sendo " + felinos + " felinos e " + primatas + " primatas)");
        System.out.println("Repteis: " + reptis);
    }

    public void alimentarTodos() {
        System.out.println("\n=== HORA DA ALIMENTACAO ===");
        animais.forEach(Animal::alimentar);
    }

    public void colocarParaDormir() {
        System.out.println("\n=== HORA DE DORMIR ===");
        animais.forEach(Animal::dormir);
    }

    public void coroDosAnimais() {
        System.out.println("\n=== CORO DOS ANIMAIS ===");
        animais.forEach(Animal::emitirSom);
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public int getTotalAnimais() {
        return animais.size();
    }

    public List<Animal> getAnimais() {
        return new ArrayList<>(animais);
    }
}
