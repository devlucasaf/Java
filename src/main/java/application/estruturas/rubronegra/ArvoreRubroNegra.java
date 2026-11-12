package application.estruturas.rubronegra;

import java.util.ArrayList;
import java.util.List;

public class ArvoreRubroNegra<T extends Comparable<T>> {

    private No<T>   raiz;
    private int     tamanho;

    public int tamanho() {
        return tamanho;
    }

    public boolean contem(T chave) {
        No<T> n = raiz;
        while (n != null) {
            int c = chave.compareTo(n.chave);
            if (c == 0) {
                return true;
            }
            n = c < 0 ? n.esquerda : n.direita;
        }
        return false;
    }

    public boolean inserir(T chave) {
        if (contem(chave)) {
            return false;
        }
        raiz = inserir(raiz, chave);
        raiz.cor = No.PRETO;
        tamanho++;
        return true;
    }

    private No<T> inserir(No<T> h, T chave) {
        if (h == null) {
            return new No<>(chave, No.VERMELHO);
        }

        if (chave.compareTo(h.chave) < 0) {
            h.esquerda = inserir(h.esquerda, chave);
        } else {
            h.direita = inserir(h.direita, chave);
        }
        return balancear(h);
    }

    public boolean remover(T chave) {
        if (!contem(chave)) {
            return false;
        }

        if (!vermelho(raiz.esquerda) && !vermelho(raiz.direita)) {
            raiz.cor = No.VERMELHO;
        }

        raiz = remover(raiz, chave);
        if (raiz != null) {
            raiz.cor = No.PRETO;
        }
        tamanho--;
        return true;
    }

    private No<T> remover(No<T> h, T chave) {
        if (chave.compareTo(h.chave) < 0) {
            if (!vermelho(h.esquerda) && !vermelho(h.esquerda.esquerda)) {
                h = moverVermelhoEsq(h);
            }
            h.esquerda = remover(h.esquerda, chave);
        } else {
            if (vermelho(h.esquerda)) {
                h = rotacionarDir(h);
            }

            if (chave.compareTo(h.chave) == 0 && h.direita == null) {
                return null;
            }

            if (!vermelho(h.direita) && !vermelho(h.direita.esquerda)) {
                h = moverVermelhoDir(h);
            }

            if (chave.compareTo(h.chave) == 0) {
                h.chave = minimo(h.direita).chave;
                h.direita = removerMinimo(h.direita);
            } else {
                h.direita = remover(h.direita, chave);
            }
        }
        return balancear(h);
    }

    private No<T> removerMinimo(No<T> h) {
        if (h.esquerda == null) {
            return null;
        }

        if (!vermelho(h.esquerda) && !vermelho(h.esquerda.esquerda)) {
            h = moverVermelhoEsq(h);
        }
        h.esquerda = removerMinimo(h.esquerda);
        return balancear(h);
    }

    private No<T> minimo(No<T> n) {
        while (n.esquerda != null) {
            n = n.esquerda;
        }
        return n;
    }

    private boolean vermelho(No<T> n) {
        return n != null && n.cor == No.VERMELHO;
    }

    private No<T> rotacionarEsq(No<T> h) {
        No<T> x = h.direita;
        h.direita = x.esquerda;
        x.esquerda = h;
        x.cor = h.cor;
        h.cor = No.VERMELHO;
        return x;
    }

    private No<T> rotacionarDir(No<T> h) {
        No<T> x = h.esquerda;
        h.esquerda = x.direita;
        x.direita = h;
        x.cor = h.cor;
        h.cor = No.VERMELHO;
        return x;
    }

    private void inverterCores(No<T> h) {
        h.cor = !h.cor;
        h.esquerda.cor = !h.esquerda.cor;
        h.direita.cor = !h.direita.cor;
    }

    private No<T> moverVermelhoEsq(No<T> h) {
        inverterCores(h);
        if (vermelho(h.direita.esquerda)) {
            h.direita = rotacionarDir(h.direita);
            h = rotacionarEsq(h);
            inverterCores(h);
        }
        return h;
    }

    private No<T> moverVermelhoDir(No<T> h) {
        inverterCores(h);
        if (vermelho(h.esquerda.esquerda)) {
            h = rotacionarDir(h);
            inverterCores(h);
        }
        return h;
    }

    private No<T> balancear(No<T> h) {
        if (vermelho(h.direita) && !vermelho(h.esquerda)) {
            h = rotacionarEsq(h);
        }

        if (vermelho(h.esquerda) && vermelho(h.esquerda.esquerda)) {
            h = rotacionarDir(h);
        }

        if (vermelho(h.esquerda) && vermelho(h.direita)) {
            inverterCores(h);
        }
        return h;
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(No<T> n) {
        return n == null ? 0 : 1 + Math.max(altura(n.esquerda), altura(n.direita));
    }

    public List<T> emOrdem() {
        List<T> lista = new ArrayList<>();
        emOrdem(raiz, lista);
        return lista;
    }

    private void emOrdem(No<T> n, List<T> lista) {
        if (n == null) {
            return;
        }
        emOrdem(n.esquerda, lista);
        lista.add(n.chave);
        emOrdem(n.direita, lista);
    }

    public boolean verificar() {
        if (vermelho(raiz)) {
            return false;
        }
        return alturaNegra(raiz) != -1;
    }

    private int alturaNegra(No<T> n) {
        if (n == null) {
            return 0;
        }

        if (vermelho(n.direita)) {
            return -1;
        }

        if (vermelho(n) && vermelho(n.esquerda)) {
            return -1;
        }

        int e = alturaNegra(n.esquerda);
        int d = alturaNegra(n.direita);

        if (e == -1 || d == -1 || e != d) {
            return -1;
        }
        return e + (n.cor == No.PRETO ? 1 : 0);
    }

    public void imprimir() {
        imprimir(raiz, 0);
    }

    private void imprimir(No<T> n, int nivel) {
        if (n == null) {
            return;
        }
        imprimir(n.direita, nivel + 1);
        System.out.println("    ".repeat(nivel) + n.chave + (n.cor == No.VERMELHO ? " (R)" : " (P)"));
        imprimir(n.esquerda, nivel + 1);
    }
}
