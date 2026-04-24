package org.application.zoologico;

import java.util.ArrayList;
import java.util.List;

public class Zoologico {

    private List<Animal> animais = new ArrayList<>();

    public void adicionarAnimal(Animal animal) {
        animais.add(animal);
    }

    public void listarAnimais() {
        for (Animal animal : animais) {
            animal.emitirSom();
            animal.dormir();
            System.out.println("-----");
        }
    }
}
