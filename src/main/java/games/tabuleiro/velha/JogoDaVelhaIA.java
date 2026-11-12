package games.tabuleiro.velha;

import java.util.Scanner;

public class JogoDaVelhaIA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int vitorias = 0;
        int empates = 0;
        int derrotas = 0;
        boolean humanoComeca = true;

        System.out.println("=== JOGO DA VELHA vs IA INVENCIVEL ===");
        do {
            TabuleiroVelha t = new TabuleiroVelha();
            boolean vezHumano = humanoComeca;
            System.out.println("\nNova partida. " + (humanoComeca ? "Voce" : "A IA") + " comeca. Voce e o X.\n");

            while (!t.terminou()) {
                if (vezHumano) {
                    System.out.println(t);
                    System.out.print("Sua jogada (1-9): ");
                    if (!scanner.hasNextLine()) {
                        return;
                    }

                    String linha = scanner.nextLine().trim();
                    if (!linha.matches("[1-9]") || !t.jogar(Integer.parseInt(linha) - 1, TabuleiroVelha.X)) {
                        System.out.println("Jogada invalida.");
                        continue;
                    }
                } else {
                    int pos = t.melhorJogada(TabuleiroVelha.O);
                    t.jogar(pos, TabuleiroVelha.O);
                    System.out.println("IA jogou na posicao " + (pos + 1));
                }
                vezHumano = !vezHumano;
            }

            System.out.println(t);
            char v = t.vencedor();
            if (v == TabuleiroVelha.X) {
                vitorias++;
                System.out.println("Voce venceu! (isso nao deveria acontecer...)");
            } else if (v == TabuleiroVelha.O) {
                derrotas++;
                System.out.println("A IA venceu.");
            } else {
                empates++;
                System.out.println("Empate.");
            }

            System.out.printf("Placar -> Vitorias: %d | Empates: %d | Derrotas: %d%n", vitorias, empates, derrotas);

            humanoComeca = !humanoComeca;
            System.out.print("Jogar de novo? (s/n): ");
        } while (scanner.hasNextLine() && scanner.nextLine().trim().equalsIgnoreCase("s"));
    }
}
