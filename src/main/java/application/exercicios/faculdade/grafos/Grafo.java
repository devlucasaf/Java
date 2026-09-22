package application.exercicios.faculdade.grafos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class Grafo {
    private static final int MAX_VERTICES = 100;

    private int quantidadeVertices;
    private int quantidadeArestas;

    private final String[]      rotulos;
    private final boolean[][]   matrizAdjacencia;

    public Grafo() {
        this.quantidadeVertices = 0;
        this.quantidadeArestas = 0;
        this.rotulos = new String[MAX_VERTICES];
        this.matrizAdjacencia = new boolean[MAX_VERTICES][MAX_VERTICES];
    }

    // --- ADICIONA UM NOVO VÉRTICE AO GRAFO ---
    public String adicionarVertice() {
        if (quantidadeVertices >= MAX_VERTICES) {
            throw new IllegalStateException("Limite de " + MAX_VERTICES + " vértices atingido.");
        }

        int novoIndice = quantidadeVertices;
        String novoRotulo = gerarRotulo(novoIndice);

        rotulos[novoIndice] = novoRotulo;

        for (int i = 0; i <= novoIndice; i++) {
            matrizAdjacencia[novoIndice][i] = false;
            matrizAdjacencia[i][novoIndice] = false;
        }

        quantidadeVertices++;

        return novoRotulo;
    }

    // --- ADICIONA UMA ARESTA NÃO DIRECIONADA ENTRE DOIS VÉRTICES ---
    public void adicionarAresta(String primeiroRotulo, String segundoRotulo) {
        if (quantidadeVertices < 2) {
            throw new IllegalStateException("É necessário ter pelo menos 2 vértices.");
        }

        int primeiroIndice = obterIndiceDoRotulo(primeiroRotulo);
        int segundoIndice = obterIndiceDoRotulo(segundoRotulo);

        validarVertice(primeiroIndice, primeiroRotulo);
        validarVertice(segundoIndice, segundoRotulo);

        if (primeiroIndice == segundoIndice) {
            throw new IllegalArgumentException("Não é permitido laço, isto é, uma aresta de um vértice nele mesmo.");
        }

        if (matrizAdjacencia[primeiroIndice][segundoIndice]) {
            throw new IllegalArgumentException("A aresta " + rotulos[primeiroIndice] + "-" + rotulos[segundoIndice] + " já existe.");
        }

        matrizAdjacencia[primeiroIndice][segundoIndice] = true;
        matrizAdjacencia[segundoIndice][primeiroIndice] = true;

        quantidadeArestas++;
    }

    // --- REMOVE UM VERTICE E TODAS AS SUAS ARESTAS ---
    public String removerVertice(String rotulo) {
        if (quantidadeVertices == 0) {
            throw new IllegalStateException("O grafo está vazio.");
        }

        int indiceVertice = obterIndiceDoRotulo(rotulo);
        validarVertice(indiceVertice, rotulo);

        String rotuloRemovido = rotulos[indiceVertice];

        for (int outroVertice = 0; outroVertice < quantidadeVertices; outroVertice++) {
            if (matrizAdjacencia[indiceVertice][outroVertice]) {
                quantidadeArestas--;
            }
        }

        // --- DESLOCA AS LINHAS DA MATRIZ PARA REMOVER O VERTICE ---
        for (int linha = indiceVertice; linha < quantidadeVertices - 1; linha++) {
            for (int coluna = 0; coluna < quantidadeVertices; coluna++) {
                matrizAdjacencia[linha][coluna] = matrizAdjacencia[linha + 1][coluna];
            }
        }

        // --- DESLOCA AS COLUNAS DA MATRIZ PARA REMOVER O VERTICE ---
        for (int linha = 0; linha < quantidadeVertices - 1; linha++) {
            for (int coluna = indiceVertice; coluna < quantidadeVertices - 1; coluna++) {
                matrizAdjacencia[linha][coluna] = matrizAdjacencia[linha][coluna + 1];
            }
        }

        quantidadeVertices--;

        // --- LIMPA A ÚLTIMA LINHA E A ÚLTIMA COLUNA DA MATRIZ ---
        for (int indice = 0; indice <= quantidadeVertices; indice++) {
            matrizAdjacencia[quantidadeVertices][indice] = false;
            matrizAdjacencia[indice][quantidadeVertices] = false;
        }

        rotulos[quantidadeVertices] = null;

        reordenarRotulos();

        return rotuloRemovido;
    }

    // --- REMOVE UMA ARESTA ENTRE DOIS VÉRTICES ---
    public void removerAresta(String primeiroRotulo, String segundoRotulo) {
        if (quantidadeArestas == 0) {
            throw new IllegalStateException("Não há arestas para excluir.");
        }

        int primeiroIndice = obterIndiceDoRotulo(primeiroRotulo);
        int segundoIndice = obterIndiceDoRotulo(segundoRotulo);

        validarVertice(primeiroIndice, primeiroRotulo);
        validarVertice(segundoIndice, segundoRotulo);

        if (!matrizAdjacencia[primeiroIndice][segundoIndice]) {
            throw new IllegalArgumentException("A aresta " + rotulos[primeiroIndice] + "-" + rotulos[segundoIndice] + " não existe.");
        }

        matrizAdjacencia[primeiroIndice][segundoIndice] = false;
        matrizAdjacencia[segundoIndice][primeiroIndice] = false;

        quantidadeArestas--;
    }

    // --- CALCULA O GRAU DE UM VÉRTICE ---
    public int obterGrauDoVertice(int indiceVertice) {
        if (indiceVertice < 0 || indiceVertice >= quantidadeVertices) {
            throw new IndexOutOfBoundsException("Índice de vértice inválido: " + indiceVertice);
        }

        int grau = 0;

        for (int outroVertice = 0; outroVertice < quantidadeVertices; outroVertice++) {
            if (matrizAdjacencia[indiceVertice][outroVertice]) {
                grau++;
            }
        }

        return grau;
    }

    // --- VERIFICA SE TODOS OS VÉRTICES SÃO ALCANÇÁVEIS ----
    public boolean estaConexo() {
        if (quantidadeVertices <= 1) {
            return true;
        }

        boolean[] visitados = new boolean[quantidadeVertices];
        Queue<Integer> fila = new ArrayDeque<>();

        int quantidadeAlcancados = 0;

        visitados[0] = true;
        fila.offer(0);

        while (!fila.isEmpty()) {
            int verticeAtual = fila.poll();

            quantidadeAlcancados++;

            for (int outroVertice = 0; outroVertice < quantidadeVertices; outroVertice++) {
                if (matrizAdjacencia[verticeAtual][outroVertice] && !visitados[outroVertice]) {
                    visitados[outroVertice] = true;
                    fila.offer(outroVertice);
                }
            }
        }

        return quantidadeAlcancados == quantidadeVertices;
    }

    // --- VERIFICA SE O GRAFO POSSUI EXATAMENTE UM VÉRTICE E NENHUMA ARESTA ---
    public boolean ehTrivial() {
        return quantidadeVertices == 1 && quantidadeArestas == 0;
    }

    // --- VERIFICA SE O GRAFO COMPLETO FORMA UM ÚNICO CICLO ---
    public boolean ehCiclo() {
        if (quantidadeVertices < 3) {
            return false;
        }

        if (quantidadeArestas != quantidadeVertices) {
            return false;
        }

        for (int indice = 0; indice < quantidadeVertices; indice++) {
            if (obterGrauDoVertice(indice) != 2) {
                return false;
            }
        }

        return estaConexo();
    }

    public int obterMaiorGrau() {
        int maiorGrau = 0;

        for (int indice = 0; indice < quantidadeVertices; indice++) {
            int grauAtual = obterGrauDoVertice(indice);

            if (grauAtual > maiorGrau) {
                maiorGrau = grauAtual;
            }
        }

        return maiorGrau;
    }

    public int obterSomaDosGraus() {
        int somaDosGraus = 0;

        for (int indice = 0; indice < quantidadeVertices; indice++) {
            somaDosGraus += obterGrauDoVertice(indice);
        }

        return somaDosGraus;
    }

    public int obterQuantidadeVerticesIsolados() {
        int quantidadeIsolados = 0;

        for (int indice = 0; indice < quantidadeVertices; indice++) {
            if (obterGrauDoVertice(indice) == 0) {
                quantidadeIsolados++;
            }
        }

        return quantidadeIsolados;
    }

    // --- RETORNA AS ARESTAS SEM REPETIR AS LIGAÇÕES ---
    public List<String> obterArestas() {
        if (quantidadeArestas == 0) {
            return Collections.emptyList();
        }

        List<String> arestas = new ArrayList<>();

        for (int primeiroVertice = 0; primeiroVertice < quantidadeVertices; primeiroVertice++) {
            for (int segundoVertice = primeiroVertice + 1; segundoVertice < quantidadeVertices; segundoVertice++) {
                if (matrizAdjacencia[primeiroVertice][segundoVertice]) {
                    arestas.add(rotulos[primeiroVertice] + " - " + rotulos[segundoVertice]);
                }
            }
        }

        return arestas;
    }

    public String obterRotulo(int indiceVertice) {
        if (indiceVertice < 0 || indiceVertice >= quantidadeVertices) {
            throw new IndexOutOfBoundsException("Índice de vértice inválido: " + indiceVertice);
        }

        return rotulos[indiceVertice];
    }

    public boolean existeVertice(String rotulo) {
        return obterIndiceDoRotulo(rotulo) >= 0;
    }

    public int getQuantidadeVertices() {
        return quantidadeVertices;
    }

    public int getQuantidadeArestas() {
        return quantidadeArestas;
    }

    public int getLimiteVertices() {
        return MAX_VERTICES;
    }

    private void validarVertice(int indiceVertice, String rotuloInformado) {
        if (indiceVertice < 0) {
            throw new IllegalArgumentException("O vértice '" + rotuloInformado + "' não existe.");
        }
    }

    private int obterIndiceDoRotulo(String rotulo) {
        if (rotulo == null || rotulo.isBlank()) {
            return -1;
        }

        String rotuloNormalizado = Entrada.normalizar(rotulo);

        for (int indice = 0; indice < quantidadeVertices; indice++) {
            if (rotulos[indice].equals(rotuloNormalizado)) {
                return indice;
            }
        }

        return -1;
    }

    private void reordenarRotulos() {
        for (int indice = 0; indice < quantidadeVertices; indice++) {
            rotulos[indice] = gerarRotulo(indice);
        }
    }

    private String gerarRotulo(int indice) {
        if (indice < 26) {
            return String.valueOf((char) ('A' + indice));
        }

        return "V" + (indice + 1);
    }
}
