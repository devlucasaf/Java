package application.utilitarios.trie;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private final No raiz = new No();

    public void inserir(String palavra) {
        No atual = raiz;
        for (char c : palavra.toLowerCase().toCharArray()) {
            atual = atual.filhos.computeIfAbsent(c, k -> new No());
        }
        atual.fimDePalavra = true;
        atual.frequencia++;
    }

    public boolean contem(String palavra) {
        No no = buscarNo(palavra.toLowerCase());
        return no != null && no.fimDePalavra;
    }

    public boolean comecaCom(String prefixo) {
        return buscarNo(prefixo.toLowerCase()) != null;
    }

    private No buscarNo(String s) {
        No atual = raiz;
        for (char c : s.toCharArray()) {
            atual = atual.filhos.get(c);
            if (atual == null) {
                return null;
            }
        }
        return atual;
    }

    public List<String> autocomplete(String prefixo, int limite) {
        List<String> resultado = new ArrayList<>();
        No no = buscarNo(prefixo.toLowerCase());
        if (no == null) {
            return resultado;
        }
        
        coletar(no, new StringBuilder(prefixo.toLowerCase()), resultado, limite);
        resultado.sort((a, b) -> Integer.compare(freq(b), freq(a)));
        return resultado;
    }

    private void coletar(No no, StringBuilder atual, List<String> saida, int limite) {
        if (saida.size() >= limite) {
            return;
        }
        
        if (no.fimDePalavra) {
            saida.add(atual.toString());
        }
        
        for (var e : no.filhos.entrySet()) {
            atual.append(e.getKey());
            coletar(e.getValue(), atual, saida, limite);
            atual.deleteCharAt(atual.length() - 1);
        }
    }

    private int freq(String palavra) {
        No no = buscarNo(palavra);
        return no == null ? 0 : no.frequencia;
    }

    public int contarPalavras() {
        return contar(raiz);
    }

    private int contar(No no) {
        int c = no.fimDePalavra ? 1 : 0;
        for (No f : no.filhos.values()) {
            c += contar(f);
        }
        return c;
    }
}

