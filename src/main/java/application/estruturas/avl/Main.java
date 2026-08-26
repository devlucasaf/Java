package application.estruturas.avl;

public class Main {

    public static void main(String[] args) {
        ArvoreAVL arvore = new ArvoreAVL();

        int[] valores = {10, 20, 30, 40, 50, 25, 5, 15};

        System.out.println("=== Inserindo valores na arvore AVL ===");
        for (int v : valores) {
            arvore.inserir(v);
            System.out.println("Inserido " + v + " -> altura da arvore: " + arvore.altura());
        }

        System.out.println();
        System.out.println("=== Estrutura da arvore (rotacionada 90 graus, raiz a esquerda) ===");
        arvore.imprimir();

        System.out.println();
        System.out.println("=== Percurso em ordem (deve estar ordenado) ===");
        System.out.println(arvore.emOrdem());

        System.out.println();
        System.out.println("=== Testando buscas ===");
        System.out.println("Contem 25? " + arvore.contem(25));
        System.out.println("Contem 99? " + arvore.contem(99));

        System.out.println();
        System.out.println("=== Removendo o valor 20 (tem dois filhos) ===");
        arvore.remover(20);
        arvore.imprimir();
        System.out.println("Altura apos remocao: " + arvore.altura());

        System.out.println();
        System.out.println("Mesmo inserindo valores em sequencia crescente (que numa BST");
        System.out.println("comum viraria uma lista encadeada), a AVL mantem a altura proxima");
        System.out.println("de log2(n), graças as rotacoes automaticas.");
    }
}
