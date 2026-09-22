package application.exercicios.faculdade.grafos;

import java.util.List;

public class EditorGrafos {

    private final Grafo     grafo;
    private final Entrada   entrada;

    public EditorGrafos() {
        this.grafo = new Grafo();
        this.entrada = new Entrada();
    }

    // --- INICIA O EDITOR E MANTÉM O MENU EM EXECUÇÃO ---
    public void iniciar() {
        exibirCabecalho();
        mostrarAjuda();

        boolean executando = true;

        while (executando) {
            mostrarMenu();

            int opcao = entrada.lerInteiro();

            switch (opcao) {
                case 1:
                    adicionarVertice();
                    mostrarResumo();
                    break;

                case 2:
                    adicionarAresta();
                    mostrarResumo();
                    break;

                case 3:
                    removerVertice();
                    mostrarResumo();
                    break;

                case 4:
                    removerAresta();
                    mostrarResumo();
                    break;

                case 5:
                    listarGrafo();
                    break;

                case 6:
                    analisarGrafo();
                    break;

                case 7:
                    mostrarInformacoesGrafo();
                    break;

                case 8:
                    mostrarAjuda();
                    break;

                case 0:
                    System.out.println("\n  Encerrando o editor de grafos. Até logo!\n");
                    executando = false;
                    break;

                default:
                    System.out.println("\n  Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private void exibirCabecalho() {
        System.out.println("=============================================");
        System.out.println("        EDITOR DE GRAFOS  -  JAVA");
        System.out.println("=============================================");
    }

    private void mostrarMenu() {
        System.out.println("\n----------------- MENU -----------------");
        System.out.println("  1 - Adicionar vértice");
        System.out.println("  2 - Adicionar aresta");
        System.out.println("  3 - Excluir vértice");
        System.out.println("  4 - Excluir aresta");
        System.out.println("  5 - Listar grafo");
        System.out.println("  6 - Análise do grafo");
        System.out.println("  7 - Informações do grafo");
        System.out.println("  8 - Como usar");
        System.out.println("  0 - Sair");
        System.out.println("----------------------------------------");
        System.out.print("  Opção: ");
    }

    private void adicionarVertice() {
        try {
            String novoRotulo = grafo.adicionarVertice();

            System.out.println("\n  Vértice '" + novoRotulo + "' adicionado.");
        } catch (IllegalStateException excecao) {
            System.out.println("\n  " + excecao.getMessage());
        }
    }

    private void adicionarAresta() {
        if (grafo.getQuantidadeVertices() < 2) {
            System.out.println("\n  É necessário ter pelo menos 2 vértices.");
            return;
        }

        String primeiroRotulo = entrada.lerVertice("  Primeiro vértice: ");

        if (!grafo.existeVertice(primeiroRotulo)) {
            System.out.println("\n  Vértice inexistente.");
            return;
        }

        String segundoRotulo = entrada.lerVertice("  Segundo vértice:  ");

        if (!grafo.existeVertice(segundoRotulo)) {
            System.out.println("\n  Vértice inexistente.");
            return;
        }

        try {
            grafo.adicionarAresta(primeiroRotulo, segundoRotulo);
            System.out.println("\n  Aresta " + primeiroRotulo + "-" + segundoRotulo + " criada.");
        } catch (IllegalArgumentException | IllegalStateException excecao) {
            System.out.println("\n  " + excecao.getMessage());
        }
    }

    private void removerVertice() {
        if (grafo.getQuantidadeVertices() == 0) {
            System.out.println("\n  O grafo está vazio.");
            return;
        }

        String rotulo = entrada.lerVertice(
                "  Vértice a excluir: "
        );

        if (!grafo.existeVertice(rotulo)) {
            System.out.println("\n  Vértice inexistente.");
            return;
        }

        try {
            grafo.removerVertice(rotulo);
            System.out.println("\n  Vértice excluído.");
        } catch (IllegalArgumentException | IllegalStateException excecao) {
            System.out.println("\n  " + excecao.getMessage());
        }
    }

    private void removerAresta() {
        if (grafo.getQuantidadeArestas() == 0) {
            System.out.println("\n  Não há arestas para excluir.");
            return;
        }

        String primeiroRotulo = entrada.lerVertice("Primeiro vértice: ");

        if (!grafo.existeVertice(primeiroRotulo)) {
            System.out.println("\n  Vértice inexistente.");
            return;
        }

        String segundoRotulo = entrada.lerVertice("Segundo vértice:  ");

        if (!grafo.existeVertice(segundoRotulo)) {
            System.out.println("\n  Vértice inexistente.");
            return;
        }

        try {
            grafo.removerAresta(primeiroRotulo, segundoRotulo);
            System.out.println("\n  Aresta " + primeiroRotulo + "-" + segundoRotulo + " removida.");
        } catch (IllegalArgumentException | IllegalStateException excecao) {
            System.out.println("\n  " + excecao.getMessage());
        }
    }

    private void listarGrafo() {
        if (grafo.getQuantidadeVertices() == 0) {
            System.out.println("\n  (grafo vazio)");
            return;
        }

        System.out.printf("%n  VÉRTICES (%d):%n", grafo.getQuantidadeVertices());

        for (int indice = 0; indice < grafo.getQuantidadeVertices(); indice++) {
            System.out.printf("%-4s  grau = %d%n", grafo.obterRotulo(indice), grafo.obterGrauDoVertice(indice));
        }

        System.out.printf("%n  ARESTAS (%d):%n", grafo.getQuantidadeArestas());

        List<String> arestas = grafo.obterArestas();

        if (arestas.isEmpty()) {
            System.out.println("    (nenhuma)");
            return;
        }

        for (String aresta : arestas) {
            System.out.println("    " + aresta);
        }
    }

    private void analisarGrafo() {
        System.out.println(
                "\n  ---------- ANÁLISE DO GRAFO ----------"
        );

        System.out.printf(
                "    Vértices ..........: %d%n",
                grafo.getQuantidadeVertices()
        );

        System.out.printf(
                "    Arestas ...........: %d%n",
                grafo.getQuantidadeArestas()
        );

        if (grafo.getQuantidadeVertices() == 0) {
            System.out.println("Conexo ............: -");
        } else {
            System.out.printf("Conexo ............: %s%n", grafo.estaConexo() ? "Sim" : "Não");
        }

        if (grafo.getQuantidadeVertices() == 0) {
            System.out.println("Maior grau ........: -");
        } else {
            System.out.printf("Maior grau ........: %d%n", grafo.obterMaiorGrau());
        }
    }

    private void mostrarInformacoesGrafo() {
        System.out.println("\n  ---------- INFORMAÇÕES DO GRAFO ----------");
        System.out.printf("Grafo trivial .....: %s%n", grafo.ehTrivial() ? "Sim" : "Não");
        System.out.printf("Grafo ciclo .......: %s%n", grafo.ehCiclo() ? "Sim" : "Não");

        if (grafo.getQuantidadeVertices() > 0) {
            System.out.printf("Soma dos graus ....: %d%n", grafo.obterSomaDosGraus());
            System.out.printf("Vértices isolados .: %d%n", grafo.obterQuantidadeVerticesIsolados());
        }
    }

    private void mostrarAjuda() {
        System.out.println("\n+=+=+=+=+=+=+= COMO USAR +=+=+=+=+=+=+=");

        System.out.println("1. Adicione vértices com a opção 1.");

        System.out.println("2. Crie arestas informando dois rótulos, como A e B.");

        System.out.println("3. Use as opções 3 e 4 para excluir vértices ou arestas.");

        System.out.println("4. Consulte as opções 5, 6 e 7 para inspecionar o grafo.");

        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
    }

    private void mostrarResumo() {
        System.out.printf("%n  >> Vértices: %d | Arestas: %d%n", grafo.getQuantidadeVertices(), grafo.getQuantidadeArestas());
    }
}

