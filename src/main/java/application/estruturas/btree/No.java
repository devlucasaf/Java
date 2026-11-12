package application.estruturas.btree;

import java.util.ArrayList;
import java.util.List;

public class No<T> {
    public final List<T> chaves = new ArrayList<>();
    public List<No<T>> filhos = new ArrayList<>();
    public final boolean folha;

    public No(boolean folha) {
        this.folha = folha;
    }
}
