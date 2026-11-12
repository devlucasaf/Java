package application.estruturas.btree;

import java.util.ArrayList;
import java.util.List;

public class ArvoreB<T extends Comparable<T>> {
    private final int   t;
    private No<T>       raiz;
    private int         tamanho;

    public ArvoreB(int grauMinimo) {
        if (grauMinimo < 2) {
            throw new IllegalArgumentException("Grau minimo deve ser >= 2");
        }

        this.t = grauMinimo;
        this.raiz = new No<>(true);
    }

    public int tamanho() {
        return tamanho;
    }

    public boolean contem(T chave) {
        No<T> n = raiz;
        while (true) {
            int i = 0;
            while (i < n.chaves.size() && chave.compareTo(n.chaves.get(i)) > 0) {
                i++;
            }

            if (i < n.chaves.size() && chave.compareTo(n.chaves.get(i)) == 0) {
                return true;
            }

            if (n.folha) {
                return false;
            }
            n = n.filhos.get(i);
        }
    }

    public boolean inserir(T chave) {
        if (contem(chave)) {
            return false;
        }

        if (raiz.chaves.size() == 2 * t - 1) {
            No<T> nova = new No<>(false);
            nova.filhos.add(raiz);
            dividirFilho(nova, 0);
            raiz = nova;
        }
        inserirNaoCheio(raiz, chave);
        tamanho++;
        return true;
    }

    private void dividirFilho(No<T> pai, int i) {
        No<T> y = pai.filhos.get(i);
        No<T> z = new No<>(y.folha);
        T mediana = y.chaves.get(t - 1);

        z.chaves.addAll(y.chaves.subList(t, 2 * t - 1));
        if (!y.folha) {
            z.filhos.addAll(y.filhos.subList(t, 2 * t));
            y.filhos = new ArrayList<>(y.filhos.subList(0, t));
        }
        List<T> restantes = new ArrayList<>(y.chaves.subList(0, t - 1));
        y.chaves.clear();
        y.chaves.addAll(restantes);

        pai.chaves.add(i, mediana);
        pai.filhos.add(i + 1, z);
    }

    private void inserirNaoCheio(No<T> x, T chave) {
        int i = 0;
        while (i < x.chaves.size() && chave.compareTo(x.chaves.get(i)) > 0) {
            i++;
        }

        if (x.folha) {
            x.chaves.add(i, chave);
            return;
        }

        if (x.filhos.get(i).chaves.size() == 2 * t - 1) {
            dividirFilho(x, i);
            if (chave.compareTo(x.chaves.get(i)) > 0) {
                i++;
            }
        }
        inserirNaoCheio(x.filhos.get(i), chave);
    }

    public int altura() {
        int h = 1;
        No<T> n = raiz;
        while (!n.folha) {
            n = n.filhos.get(0);
            h++;
        }
        return h;
    }

    public List<T> emOrdem() {
        List<T> lista = new ArrayList<>();
        emOrdem(raiz, lista);
        return lista;
    }

    private void emOrdem(No<T> n, List<T> lista) {
        for (int i = 0; i < n.chaves.size(); i++) {
            if (!n.folha) {
                emOrdem(n.filhos.get(i), lista);
            }
            lista.add(n.chaves.get(i));
        }

        if (!n.folha) {
            emOrdem(n.filhos.get(n.chaves.size()), lista);
        }
    }

    public List<T> intervalo(T de, T ate) {
        List<T> lista = new ArrayList<>();
        intervalo(raiz, de, ate, lista);
        return lista;
    }

    private void intervalo(No<T> n, T de, T ate, List<T> lista) {
        for (int i = 0; i < n.chaves.size(); i++) {
            T k = n.chaves.get(i);
            if (!n.folha && k.compareTo(de) >= 0) {
                intervalo(n.filhos.get(i), de, ate, lista);
            }

            if (k.compareTo(de) >= 0 && k.compareTo(ate) <= 0) {
                lista.add(k);
            }

            if (k.compareTo(ate) > 0) {
                return;
            }
        }
        if (!n.folha) {
            intervalo(n.filhos.get(n.chaves.size()), de, ate, lista);
        }
    }

    public boolean verificar() {
        return profundidadeFolhas(raiz, true) != -1;
    }

    private int profundidadeFolhas(No<T> n, boolean ehRaiz) {
        int k = n.chaves.size();
        if (k > 2 * t - 1 || (!ehRaiz && k < t - 1)) {
            return -1;
        }

        for (int i = 1; i < k; i++) {
            if (n.chaves.get(i - 1).compareTo(n.chaves.get(i)) >= 0) {
                return -1;
            }
        }

        if (n.folha) {
            return 1;
        }

        if (n.filhos.size() != k + 1) {
            return -1;
        }

        int p = -2;
        for (No<T> f : n.filhos) {
            int d = profundidadeFolhas(f, false);
            if (d == -1 || (p != -2 && d != p)) {
                return -1;
            }
            p = d;
        }
        return p + 1;
    }

    public void imprimir() {
        imprimir(raiz, 0);
    }

    private void imprimir(No<T> n, int nivel) {
        System.out.println("    ".repeat(nivel) + n.chaves);
        if (!n.folha) {
            for (No<T> f : n.filhos) {
                imprimir(f, nivel + 1);
            }
        }
    }
}
