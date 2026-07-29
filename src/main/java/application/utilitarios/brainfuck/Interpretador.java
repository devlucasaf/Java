package application.utilitarios.brainfuck;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Interpretador {

    private static final int TAMANHO_FITA = 30_000;

    private final byte[]                fita = new byte[TAMANHO_FITA];
    private int                         ponteiro = 0;
    private final Map<Integer, Integer> pares = new HashMap<>();

    public String executar(String programa) throws IOException {
        mapearColchetes(programa);
        StringBuilder saida = new StringBuilder();
        int i = 0;
        while (i < programa.length()) {
            char c = programa.charAt(i);
            switch (c) {
                case '>':
                    ponteiro = (ponteiro + 1) % TAMANHO_FITA;
                    break;
                case '<':
                    ponteiro = (ponteiro - 1 + TAMANHO_FITA) % TAMANHO_FITA;
                    break;
                case '+':
                    fita[ponteiro]++;
                    break;
                case '-':
                    fita[ponteiro]--;
                    break;
                case '.':
                    saida.append((char) (fita[ponteiro] & 0xFF));
                    break;
                case ',':
                    int lido = System.in.read();
                    fita[ponteiro] = (byte) (lido == -1 ? 0 : lido);
                    break;
                case '[':
                    if (fita[ponteiro] == 0) {
                        i = pares.get(i);
                    }
                    break;
                case ']':
                    if (fita[ponteiro] != 0) {
                        i = pares.get(i);
                    }
                    break;
                default:
                    break;
            }
            i++;
        }
        return saida.toString();
    }

    private void mapearColchetes(String programa) {
        pares.clear();
        Deque<Integer> pilha = new ArrayDeque<>();
        for (int i = 0; i < programa.length(); i++) {
            char c = programa.charAt(i);
            if (c == '[') {
                pilha.push(i);
            } else if (c == ']') {
                if (pilha.isEmpty()) {
                    throw new IllegalArgumentException("] sem [ correspondente na posicao " + i);
                }
                int inicio = pilha.pop();
                pares.put(inicio, i);
                pares.put(i, inicio);
            }
        }

        if (!pilha.isEmpty()) {
            throw new IllegalArgumentException("[ sem ] correspondente na posicao " + pilha.pop());
        }
    }

    public void reset() {
        for (int i = 0; i < fita.length; i++) {
            fita[i] = 0;
        }
        ponteiro = 0;
        pares.clear();
    }
}

