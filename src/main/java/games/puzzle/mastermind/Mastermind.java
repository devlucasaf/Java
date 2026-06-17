package games.puzzle.mastermind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Mastermind {

    private static final String[] CORES = {"V", "A", "M", "P", "B", "C"};
    private static final int TAMANHO_SENHA = 4;
    private static final int MAX_TENTATIVAS = 10;

    private final Random sorteador = new Random();
    private final Scanner entrada = new Scanner(System.in);
    private String[] segredo;

    public static void main(String[] args) {
        new Mastermind().iniciar();
    }

    public void iniciar() {
        System.out.println("=== MASTERMIND ===");
        System.out.println("Cores disponiveis: V=Vermelho A=Amarelo M=Marrom P=Preto B=Branco C=Ciano");
        System.out.println("Adivinhe a sequencia de 4 cores em ate " + MAX_TENTATIVAS + " tentativas.");
        System.out.println("Em cada tentativa, digite 4 letras separadas (ex: V A M P).");
        System.out.println("Resposta: X = cor certa no lugar certo, O = cor certa no lugar errado.");

        boolean jogarNovamente = true;
        while (jogarNovamente) {
            sortearSegredo();
            jogar();
            System.out.print("\nJogar de novo? (s/n): ");
            jogarNovamente = entrada.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    private void sortearSegredo() {
        segredo = new String[TAMANHO_SENHA];
        for (int i = 0; i < TAMANHO_SENHA; i++) {
            segredo[i] = CORES[sorteador.nextInt(CORES.length)];
        }
    }

    private void jogar() {
        List<String[]> historico = new ArrayList<>();
        List<String> pistas = new ArrayList<>();

        for (int tentativa = 1; tentativa <= MAX_TENTATIVAS; tentativa++) {
            String[] palpite = lerPalpite(tentativa);
            if (palpite == null) {
                tentativa--;
                continue;
            }
            String pista = avaliar(palpite);
            historico.add(palpite);
            pistas.add(pista);
            mostrarHistorico(historico, pistas);
            if (pista.equals("XXXX")) {
                System.out.println("PARABENS! Voce acertou em " + tentativa + " tentativas!");
                return;
            }
        }
        System.out.println("DERROTA! A sequencia era: " + Arrays.toString(segredo));
    }

    private String[] lerPalpite(int tentativa) {
        System.out.print("Tentativa " + tentativa + " (ex: V A M P): ");
        String linha = entrada.nextLine().trim().toUpperCase();
        String[] partes = linha.split("\\s+");
        if (partes.length != TAMANHO_SENHA) {
            System.out.println("Digite exatamente " + TAMANHO_SENHA + " cores.");
            return null;
        }
        for (String c : partes) {
            boolean valida = false;
            for (String disponivel : CORES) {
                if (disponivel.equals(c)) {
                    valida = true;
                }
            }

            if (!valida) {
                System.out.println("Cor invalida: " + c);
                return null;
            }
        }
        return partes;
    }

    private String avaliar(String[] palpite) {
        boolean[] usadoSegredo = new boolean[TAMANHO_SENHA];
        boolean[] usadoPalpite = new boolean[TAMANHO_SENHA];
        int certos = 0;
        int parciais = 0;
        for (int i = 0; i < TAMANHO_SENHA; i++) {
            if (palpite[i].equals(segredo[i])) {
                certos++;
                usadoSegredo[i] = true;
                usadoPalpite[i] = true;
            }
        }
        for (int i = 0; i < TAMANHO_SENHA; i++) {
            if (usadoPalpite[i]) {
                continue;
            }
            for (int j = 0; j < TAMANHO_SENHA; j++) {
                if (!usadoSegredo[j] && palpite[i].equals(segredo[j])) {
                    parciais++;
                    usadoSegredo[j] = true;
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < certos; i++) {
            sb.append('X');
        }

        for (int i = 0; i < parciais; i++) {
            sb.append('O');
        }

        for (int i = certos + parciais; i < TAMANHO_SENHA; i++) {
            sb.append('-');
        }
        return sb.toString();
    }

    private void mostrarHistorico(List<String[]> palpites, List<String> pistas) {
        System.out.println("\n--- Historico ---");
        for (int i = 0; i < palpites.size(); i++) {
            System.out.printf("%2d: %s   ->   %s%n", i + 1, String.join(" ", palpites.get(i)), pistas.get(i));
        }
    }
}

