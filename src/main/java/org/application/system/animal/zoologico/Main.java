package org.application.system.animal.zoologico;

import org.application.system.animal.zoologico.ave.Arara;
import org.application.system.animal.zoologico.mamifero.Elefante;
import org.application.system.animal.zoologico.mamifero.Leao;

public class Main {
    public static void main(String[] args) {

        Zoologico zoo = new Zoologico();

        zoo.adicionarAnimal(new Leao("Simba", 5));
        zoo.adicionarAnimal(new Elefante("Dumbo", 10));
        zoo.adicionarAnimal(new Arara("Azul", 3));

        zoo.listarAnimais();
    }
}

