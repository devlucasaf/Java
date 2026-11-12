package application.estruturas.rubronegra;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        ArvoreRubroNegra<Integer> arvore = new ArvoreRubroNegra<>();

        System.out.println("=== Inserindo 1..15 em ordem crescente (pior caso de uma BST comum) ===");
        for (int i = 1; i <= 15; i++) {
            arvore.inserir(i);
        }
        arvore.imprimir();
        System.out.println("Altura: " + arvore.altura() + " | tamanho: " + arvore.tamanho() + " | valida: " + arvore.verificar());

        System.out.println("\n=== Removendo 4, 8 e 1 ===");
        arvore.remover(4);
        arvore.remover(8);
        arvore.remover(1);
        arvore.imprimir();
        System.out.println("Em ordem: " + arvore.emOrdem());

        System.out.println("\n=== Teste de estresse: 100.000 operacoes aleatorias ===");
        ArvoreRubroNegra<Integer> grande = new ArvoreRubroNegra<>();
        Random rnd = new Random(42);
        for (int i = 0; i < 100_000; i++) {
            int v = rnd.nextInt(5_000);
            if (rnd.nextBoolean()) {
                grande.inserir(v);
            } else {
                grande.remover(v);
            }

            if (i % 10_000 == 0 && !grande.verificar()) {
                System.out.println("Invariante violada na operacao " + i);
                return;
            }
        }
        System.out.println("Tamanho: " + grande.tamanho() + " | altura: " + grande.altura() + " | valida: " + grande.verificar());
    }
}
