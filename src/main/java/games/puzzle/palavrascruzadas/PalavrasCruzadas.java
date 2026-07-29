package games.puzzle.palavrascruzadas;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PalavrasCruzadas {

    private static final int    LINHAS = 7;
    private static final int    COLUNAS = 9;
    private static final char   BLOQUEADO = '#';
    private static final char   VAZIO = '.';

    private final char[][] solucao = {
            {BLOQUEADO, 'J', BLOQUEADO, BLOQUEADO, BLOQUEADO, 'C', BLOQUEADO, BLOQUEADO, BLOQUEADO},
            {BLOQUEADO, 'A', BLOQUEADO, BLOQUEADO, BLOQUEADO, 'L', BLOQUEADO, BLOQUEADO, BLOQUEADO},
            {'C', 'V', 'A', 'R', 'I', 'A', 'V', 'E', 'L'},
            {BLOQUEADO, 'A', BLOQUEADO, BLOQUEADO, BLOQUEADO, 'S', BLOQUEADO, BLOQUEADO, BLOQUEADO},
            {BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO, 'S', BLOQUEADO, BLOQUEADO, BLOQUEADO},
            {BLOQUEADO, 'M', 'E', 'T', 'O', 'E', 'D', 'O', BLOQUEADO},
            {BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO}
    };

    private char[][] tabuleiro;
    private final Scanner entrada = new Scanner(System.in);

    private final List<Pista> pistas = Arrays.asList(
            new Pista(1, "VERTICAL",   "Linguagem de programacao (4 letras)",       2, 1, 4),
            new Pista(2, "VERTICAL",   "Tipo abstrato em Java (6 letras)",          0, 5, 6),
            new Pista(3, "HORIZONTAL", "Armazena um valor que pode mudar (8 letras)", 2, 1, 8),
            new Pista(4, "HORIZONTAL", "Funcao dentro de uma classe (7 letras)",    5, 1, 7)
    );

    public static void main(String[] args) {
        new PalavrasCruzadas().iniciar();
    }

    public void iniciar() {
        System.out.println("=== PALAVRAS CRUZADAS ===");
        System.out.println("Preencha conforme as dicas. Comando: linha coluna LETRA");
        System.out.println("Ex: 2 1 V  (coloca V na linha 2 coluna 1). Use 'q' para sair.");
        inicializarTabuleiro();
        executar();
    }

    private void inicializarTabuleiro() {
        tabuleiro = new char[LINHAS][COLUNAS];
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                tabuleiro[i][j] = (solucao[i][j] == BLOQUEADO) ? BLOQUEADO : VAZIO;
            }
        }
    }

    private void executar() {
        while (true) {
            desenhar();
            mostrarPistas();
            if (resolvido()) {
                System.out.println("PARABENS! Voce completou as palavras cruzadas!");
                return;
            }
            
            System.out.print("Jogada: ");
            String linha = entrada.nextLine().trim();
            if (linha.equalsIgnoreCase("q")) {
                return;
            }
            
            String[] partes = linha.split("\\s+");
            if (partes.length != 3) {
                System.out.println("Formato invalido.");
                continue;
            }
            try {
                int l = Integer.parseInt(partes[0]);
                int c = Integer.parseInt(partes[1]);
                char letra = Character.toUpperCase(partes[2].charAt(0));
                if (l < 0 || l >= LINHAS || c < 0 || c >= COLUNAS) {
                    System.out.println("Posicao fora do tabuleiro.");
                    continue;
                }
                
                if (tabuleiro[l][c] == BLOQUEADO) {
                    System.out.println("Celula bloqueada.");
                    continue;
                }
                tabuleiro[l][c] = letra;
            } catch (NumberFormatException e) {
                System.out.println("Linha e coluna devem ser numeros.");
            }
        }
    }

    private boolean resolvido() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                if (tabuleiro[i][j] != solucao[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void desenhar() {
        System.out.println();
        System.out.print("   ");
        for (int j = 0; j < COLUNAS; j++) {
            System.out.printf(" %d ", j);
        }
        
        System.out.println();
        for (int i = 0; i < LINHAS; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < COLUNAS; j++) {
                System.out.print(" " + tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void mostrarPistas() {
        System.out.println("\nPistas:");
        for (Pista p : pistas) {
            System.out.printf("  %d (%s) [%d,%d, %d letras]: %s%n",
                    p.getNumero(),
                    p.getDirecao(),
                    p.getLinhaInicial(),
                    p.getColunaInicial(),
                    p.getTamanho(),
                    p.getDica()
            );
        }
    }
}

