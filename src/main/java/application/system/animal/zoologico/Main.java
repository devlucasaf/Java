package application.system.animal.zoologico;

import application.system.animal.zoologico.ave.Arara;
import application.system.animal.zoologico.ave.Ema;
import application.system.animal.zoologico.ave.Flamingo;
import application.system.animal.zoologico.ave.Ganso;
import application.system.animal.zoologico.ave.Tucano;
import application.system.animal.zoologico.mamifero.Elefante;
import application.system.animal.zoologico.mamifero.Girafa;
import application.system.animal.zoologico.mamifero.Zebra;
import application.system.animal.zoologico.mamifero.felino.Leao;
import application.system.animal.zoologico.mamifero.felino.Onca;
import application.system.animal.zoologico.mamifero.felino.Tigre;
import application.system.animal.zoologico.mamifero.primata.Chimpanze;
import application.system.animal.zoologico.mamifero.primata.Gorila;
import application.system.animal.zoologico.mamifero.primata.MicoLeaoDourado;
import application.system.animal.zoologico.reptil.Cobra;
import application.system.animal.zoologico.reptil.Jacare;
import application.system.animal.zoologico.reptil.Tartaruga;

public class Main {
    public static void main(String[] args) {

        Zoologico zoo = new Zoologico("Zoologico Municipal", 20);

        zoo.adicionarAnimal(new Ema("Perna", 5));
        zoo.adicionarAnimal(new Flamingo("Cheirinho", 2));
        zoo.adicionarAnimal(new Ganso("Paulo Henrique", 4));
        zoo.adicionarAnimal(new Arara("Azul", 3));
        zoo.adicionarAnimal(new Tucano("Tuquinha", 6));

        zoo.adicionarAnimal(new Elefante("Dumbo", 10));
        zoo.adicionarAnimal(new Girafa("Melman", 8));
        zoo.adicionarAnimal(new Zebra("Marty", 7));

        zoo.adicionarAnimal(new Leao("Simba", 5));
        zoo.adicionarAnimal(new Tigre("Sher Khan", 12));
        zoo.adicionarAnimal(new Onca("Onix", 4));

        zoo.adicionarAnimal(new Chimpanze("Cesar", 9));
        zoo.adicionarAnimal(new Gorila("King", 15));
        zoo.adicionarAnimal(new MicoLeaoDourado("Tico", 3));

        zoo.adicionarAnimal(new Cobra("Kaa", 6));
        zoo.adicionarAnimal(new Jacare("Tic Tac", 20));
        zoo.adicionarAnimal(new Tartaruga("Donatello", 50));

        zoo.exibirTodos();
        zoo.exibirContagemPorCategoria();

        System.out.println("\n=== APRESENTACAO DOS ANIMAIS ===");
        zoo.getAnimais().forEach(Animal::apresentar);

        zoo.coroDosAnimais();
        zoo.alimentarTodos();
        zoo.colocarParaDormir();

        System.out.println("\n=== BUSCA ===");
        zoo.buscarPorNome("Simba").ifPresent(a -> System.out.println("Encontrado: " + a));

        System.out.println("\n=== REMOCAO ===");
        zoo.removerAnimal("Marty");

        zoo.exibirContagemPorCategoria();
    }
}
