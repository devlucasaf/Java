package org.application.zoologico;

import org.application.zoologico.ave.Arara;
import org.application.zoologico.mamifero.Elefante;
import org.application.zoologico.mamifero.Leao;

public class Main {
    public static void main(String[] args) {

        Zoologico zoo = new Zoologico();

        zoo.adicionarAnimal(new Leao("Simba", 5));
        zoo.adicionarAnimal(new Elefante("Dumbo", 10));
        zoo.adicionarAnimal(new Arara("Azul", 3));

        zoo.listarAnimais();
    }
}

