package application.estruturas.btree;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        ArvoreB<Integer> arvore = new ArvoreB<>(2);

        System.out.println("=== Arvore B (grau minimo 2) inserindo 1..20 ===");
        for (int i = 1; i <= 20; i++) {
            arvore.inserir(i);
        }
        arvore.imprimir();
        System.out.println("Altura: " + arvore.altura() + " | valida: " + arvore.verificar());
        System.out.println("Contem 13? " + arvore.contem(13) + " | Contem 99? " + arvore.contem(99));
        System.out.println("Intervalo [5, 12]: " + arvore.intervalo(5, 12));

        System.out.println("\n=== 200.000 chaves aleatorias, grau minimo 50 ===");
        ArvoreB<Integer> grande = new ArvoreB<>(50);
        Random rnd = new Random(7);
        for (int i = 0; i < 200_000; i++) {
            grande.inserir(rnd.nextInt(1_000_000));
        }
        System.out.println("Chaves: " + grande.tamanho() + " | altura: " + grande.altura() + " | valida: " + grande.verificar());
    }
}
