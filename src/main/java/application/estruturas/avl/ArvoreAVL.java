package application.estruturas.avl;

import java.util.ArrayList;
import java.util.List;

public class ArvoreAVL {

    private NoAVL raiz;

    // --- RETORNA A ALTURA DO NÓ INFORMADO OU ZERO CASO O NÓ SEJA NULO ---
    private int altura(NoAVL no) {
        return no == null ? 0 : no.altura;
    }

    // --- CALCULA O FATOR DE BALANCEAMENTO DO NÓ PELA DIFERENÇA ENTRE AS ALTURAS DAS SUBÁRVORES ---
    private int fatorBalanceamento(NoAVL no) {
        return no == null ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    // --- ATUALIZA A ALTURA DO NÓ COM BASE NA MAIOR ALTURA ENTRE SUAS SUBÁRVORES ---
    private void atualizarAltura(NoAVL no) {
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
    }

    // --- REALIZA UMA ROTAÇÃO SIMPLES À DIREITA E RETORNA A NOVA RAIZ DA SUBÁRVORE ---
    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerda;
        NoAVL t2 = x.direita;

        x.direita = y;
        y.esquerda = t2;

        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    // --- REALIZA UMA ROTAÇÃO SIMPLES À ESQUERDA E RETORNA A NOVA RAIZ DA SUBÁRVORE ---
    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direita;
        NoAVL t2 = y.esquerda;

        y.esquerda = x;
        x.direita = t2;

        atualizarAltura(x);
        atualizarAltura(y);
        return y;
    }

    // --- VERIFICA O FATOR DE BALANCEAMENTO E APLICA AS ROTAÇÕES NECESSÁRIAS ---
    private NoAVL balancear(NoAVL no) {
        atualizarAltura(no);
        int fator = fatorBalanceamento(no);

        if (fator > 1 && fatorBalanceamento(no.esquerda) >= 0) {
            return rotacaoDireita(no);
        }

        if (fator > 1 && fatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (fator < -1 && fatorBalanceamento(no.direita) <= 0) {
            return rotacaoEsquerda(no);
        }

        if (fator < -1 && fatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    // --- INSERE UMA NOVA CHAVE NA ÁRVORE A PARTIR DO NÓ RAIZ ---
    public void inserir(int chave) {
        raiz = inserir(raiz, chave);
    }

    // --- INSERE RECURSIVAMENTE UMA CHAVE E BALANCEIA A SUBÁRVORE RESULTANTE ---
    private NoAVL inserir(NoAVL no, int chave) {
        if (no == null) {
            return new NoAVL(chave);
        }

        if (chave < no.chave) {
            no.esquerda = inserir(no.esquerda, chave);
        } else if (chave > no.chave) {
            no.direita = inserir(no.direita, chave);
        } else {
            return no;
        }

        return balancear(no);
    }

    // --- REMOVE A CHAVE INFORMADA DA ÁRVORE A PARTIR DO NÓ RAIZ ---
    public void remover(int chave) {
        raiz = remover(raiz, chave);
    }

    // --- REMOVE RECURSIVAMENTE UMA CHAVE E BALANCEIA A SUBÁRVORE RESULTANTE ---
    private NoAVL remover(NoAVL no, int chave) {
        if (no == null) {
            return null;
        }

        if (chave < no.chave) {
            no.esquerda = remover(no.esquerda, chave);
        } else if (chave > no.chave) {
            no.direita = remover(no.direita, chave);
        } else {
            if (no.esquerda == null || no.direita == null) {
                no = (no.esquerda != null) ? no.esquerda : no.direita;
            } else {
                NoAVL sucessor = menorNo(no.direita);
                no.chave = sucessor.chave;
                no.direita = remover(no.direita, sucessor.chave);
            }
        }

        if (no == null) {
            return null;
        }

        return balancear(no);
    }

    // --- LOCALIZA E RETORNA O NÓ COM A MENOR CHAVE DE UMA SUBÁRVORE ---
    private NoAVL menorNo(NoAVL no) {
        while (no.esquerda != null) {
            no = no.esquerda;
        }

        return no;
    }

    // --- VERIFICA SE A CHAVE INFORMADA ESTÁ PRESENTE NA ÁRVORE ---
    public boolean contem(int chave) {
        NoAVL atual = raiz;

        while (atual != null) {
            if (chave == atual.chave) {
                return true;
            }

            atual = chave < atual.chave ? atual.esquerda : atual.direita;
        }

        return false;
    }

    // --- RETORNA A ALTURA TOTAL DA ÁRVORE A PARTIR DO NÓ RAIZ ---
    public int altura() {
        return altura(raiz);
    }

    // --- INICIA A IMPRESSÃO VISUAL DA ESTRUTURA DA ÁRVORE ---
    public void imprimir() {
        imprimir(raiz, 0);
    }

    // --- IMPRIME RECURSIVAMENTE OS NÓS DA ÁRVORE DE ACORDO COM SEUS NÍVEIS ---
    private void imprimir(NoAVL no, int nivel) {
        if (no == null) {
            return;
        }

        imprimir(no.direita, nivel + 1);
        System.out.println("    ".repeat(nivel) + no.chave + " (h=" + no.altura + ")");
        imprimir(no.esquerda, nivel + 1);
    }

    // --- RETORNA UMA LISTA COM AS CHAVES DA ÁRVORE PERCORRIDAS EM ORDEM CRESCENTE ---
    public List<Integer> emOrdem() {
        List<Integer> resultado = new ArrayList<>();
        emOrdem(raiz, resultado);
        return resultado;
    }

    // --- PERCORRE RECURSIVAMENTE A ÁRVORE EM ORDEM E ADICIONA AS CHAVES À LISTA ---
    private void emOrdem(NoAVL no, List<Integer> resultado) {
        if (no == null) {
            return;
        }

        emOrdem(no.esquerda, resultado);
        resultado.add(no.chave);
        emOrdem(no.direita, resultado);
    }
}
