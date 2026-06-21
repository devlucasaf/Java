package application.system.animal.zoologico;

import application.system.animal.zoologico.ave.*;
import application.system.animal.zoologico.mamifero.Elefante;
import application.system.animal.zoologico.mamifero.felino.Leao;

public class Main {
    public static void main(String[] args) {

        Zoologico zoologico = new Zoologico();

        zoologico.adicionarAnimal(new Ema("Perna", 5));
        zoologico.adicionarAnimal(new Flamingo("Cheirinho", 2));
        zoologico.adicionarAnimal(new Ganso("Paulo Henrique", 4));
        zoologico.adicionarAnimal(new Elefante("Dumbo", 10));
        zoologico.adicionarAnimal(new Arara("Azul", 3));
        zoologico.adicionarAnimal(new Leao("Simba", 5));
        zoologico.adicionarAnimal(new Elefante("Dumbo", 10));
        zoologico.adicionarAnimal(new Arara("Azul", 3));

        zoologico.listarAnimais();
    }
}

