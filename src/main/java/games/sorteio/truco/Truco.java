package games.sorteio.truco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Truco {

    public static final String[] ORDEM = {
            "4", "5", "6",
            "7", "Q", "J",
            "K", "A", "2",
            "3"
    };

    private static final int HUMANO = 0;
    private static final int CPU = 1;

    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);
    private final int[] placar = new int[2];
    @SuppressWarnings("unchecked")
    private final List<Carta>[] maos = new List[]{new ArrayList<Carta>(), new ArrayList<Carta>()};

    private Carta vira;
    private int valor;
    private int ultimoPedidor;
    private int vencedorMao;
    private boolean entrada = true;

    public static void main(String[] args) {
        new Truco().jogar();
    }

    // ------------------------------------------------------------------ partida
    private void jogar() {
        System.out.println("=== TRUCO PAULISTA === (primeiro a 12 pontos)");
        int primeiro = HUMANO;

        while (placar[0] < 12 && placar[1] < 12 && entrada) {
            System.out.printf("%n---------- Placar: Voce %d x %d CPU ----------%n", placar[0], placar[1]);
            int vencedor = jogarMao(primeiro);
            if (!entrada) {
                break;
            }

            if (vencedor >= 0) {
                placar[vencedor] += valor;
                System.out.println((vencedor == HUMANO ? "Voce faz " : "CPU faz ") + valor + " ponto(s).");
            } else {
                System.out.println("Mao empatada: ninguem pontua.");
            }
            primeiro = 1 - primeiro;
        }

        if (!entrada) {
            return;
        }
        System.out.println(placar[0] >= 12 ? "\nVOCE VENCEU A PARTIDA!" : "\nA CPU VENCEU A PARTIDA.");
    }

    private int jogarMao(int primeiro) {
        List<Carta> baralho = new ArrayList<>();
        for (Naipe n : Naipe.values()) {
            for (int i = 0; i < ORDEM.length; i++) {
                baralho.add(new Carta(i, n));
            }
        }

        Collections.shuffle(baralho, random);
        for (int j = 0; j < 2; j++) {
            maos[j].clear();
            for (int k = 0; k < 3; k++) {
                maos[j].add(baralho.remove(baralho.size() - 1));
            }
        }
        vira = baralho.remove(baralho.size() - 1);
        valor = 1;
        ultimoPedidor = -1;
        vencedorMao = -1;

        System.out.println("Vira: " + vira + "  -> manilha: " + ORDEM[(vira.indice() + 1) % ORDEM.length]);

        List<Integer> vazas = new ArrayList<>(); // 0, 1 ou 2 (empate)
        int lider = primeiro;
        while (true) {
            int resultado = decidir(vazas);
            if (resultado != -2) {
                return resultado == -3 ? -1 : resultado;
            }

            Carta carta1 = jogada(lider, null, vazas);
            if (carta1 == null) {
                return vencedorMao;
            }

            Carta carta2 = jogada(1 - lider, carta1, vazas);
            if (carta2 == null) {
                return vencedorMao;
            }

            int f1 = forca(carta1), f2 = forca(carta2);
            if (f1 == f2) {
                System.out.println("Vaza empatada (embuchou).");
                vazas.add(2);
                lider = primeiro;
            } else {
                int ganhador = f1 > f2 ? lider : 1 - lider;
                System.out.println("Vaza para " + (ganhador == HUMANO ? "voce" : "a CPU") + ".");
                vazas.add(ganhador);
                lider = ganhador;
            }
        }
    }

    private int decidir(List<Integer> v) {
        int n = v.size();
        if (n < 2) {
            return -2;
        }

        int a = v.get(0), b = v.get(1);
        if (n == 2) {
            if (a != 2 && a == b) {
                return a;
            }

            if (a == 2 && b != 2) {
                return b;
            }

            if (a != 2 && b == 2) {
                return a;
            }

            return -2;
        }
        int c = v.get(2);
        if (c != 2) {
            return c;
        }

        if (a != 2) {
            return a;
        }

        return -3;
    }

    // ------------------------------------------------------------------- jogadas
    private int forca(Carta c) {
        int manilha = (vira.indice() + 1) % ORDEM.length;
        return c.indice() == manilha ? 100 + c.naipe().ordinal() : c.indice();
    }

    private Carta jogada(int jogador, Carta adversaria, List<Integer> vazas) {
        List<Carta> mao = maos[jogador];
        if (jogador == HUMANO) {
            return jogadaHumano(mao, adversaria);
        }

        if (valor < 12 && ultimoPedidor != CPU && random.nextDouble() < chancePedidoCpu(vazas)) {
            if (!pedir(CPU)) {
                return null;
            }
        }
        Carta escolhida = escolherCpu(mao, adversaria, vazas.size());
        mao.remove(escolhida);
        System.out.println("CPU joga: " + escolhida);
        return escolhida;
    }

    private Carta jogadaHumano(List<Carta> mao, Carta adversaria) {
        while (true) {
            StringBuilder sb = new StringBuilder("Sua mao: ");
            for (int i = 0; i < mao.size(); i++) {
                sb.append('[')
                        .append(i + 1)
                        .append("] ")
                        .append(mao.get(i))
                        .append("   ");
            }

            System.out.println(sb);
            boolean podePedir = valor < 12 && ultimoPedidor != HUMANO;
            System.out.print("Escolha a carta" + (podePedir ? " ou T para pedir " + nomePedido(proximo(valor)) : "") + ": ");

            if (!scanner.hasNextLine()) {
                entrada = false;
                return null;
            }
            String s = scanner.nextLine().trim();

            if (s.equalsIgnoreCase("t")) {
                if (!podePedir) {
                    System.out.println("Voce nao pode pedir agora.");
                    continue;
                }

                if (!pedir(HUMANO)) {
                    return null;
                }
                continue;
            }
            if (s.matches("[1-9]") && Integer.parseInt(s) <= mao.size()) {
                Carta c = mao.remove(Integer.parseInt(s) - 1);
                System.out.println("Voce joga: " + c);
                return c;
            }
            System.out.println("Opcao invalida.");
        }
    }

    // --------------------------------------------------------------------- truco
    private int proximo(int v) {
        return v == 1 ? 3 : v + 3;
    }

    private String nomePedido(int v) {
        return switch (v) {
            case 3 -> "TRUCO";
            case 6 -> "SEIS";
            case 9 -> "NOVE";
            default -> "DOZE";
        };
    }

    private boolean pedir(int quem) {
        int novo = proximo(valor);
        int outro = 1 - quem;
        System.out.println((quem == HUMANO ? "Voce pede " : "CPU pede ") + nomePedido(novo) + "!");

        boolean aceita;
        boolean aumenta = false;

        if (outro == CPU) {
            double s = forcaMaoCpu();
            double limite = limiar(novo) + random.nextDouble() * 0.15 - 0.05;
            aceita = s >= limite;
            aumenta = aceita && novo < 12 && s >= limite + 0.2 && random.nextDouble() < 0.5;
            if (aceita) {
                System.out.println("CPU: aceito!");
            }
        } else {
            String opcoes = "[A]ceitar, [C]orrer" + (novo < 12 ? ", [R]aumentar" : "") + "? ";
            while (true) {
                System.out.print(opcoes);
                if (!scanner.hasNextLine()) {
                    entrada = false;
                    return false;
                }

                String r = scanner.nextLine().trim().toLowerCase();
                if (r.equals("a")) {
                    aceita = true;
                    break;
                }

                if (r.equals("c")) {
                    aceita = false;
                    break;
                }

                if (r.equals("r") && novo < 12) {
                    aceita = true;
                    aumenta = true;
                    break;
                }
            }
        }

        if (!aceita) {
            System.out.println((outro == HUMANO ? "Voce correu." : "CPU correu.") + " " + (quem == HUMANO ? "Voce" : "CPU") + " ganha a mao.");
            vencedorMao = quem;
            return false;
        }
        valor = novo;
        ultimoPedidor = quem;
        return !aumenta || pedir(outro);
    }

    private double limiar(int v) {
        return switch (v) {
            case 3 -> 0.40;
            case 6 -> 0.50;
            case 9 -> 0.58;
            default -> 0.65;
        };
    }

    private double nota(Carta c) {
        int f = forca(c);
        return f >= 100 ? 0.8 + (f - 100) * 0.05 : f / 10.0 * 0.7;
    }

    private double forcaMaoCpu() {
        List<Carta> mao = maos[CPU];
        if (mao.isEmpty()) {
            return 0;
        }

        double soma = 0;
        for (Carta c : mao) {
            soma += nota(c);
        }
        return soma / mao.size();
    }

    private double chancePedidoCpu(List<Integer> vazas) {
        double s = forcaMaoCpu();
        if (s < 0.5) {
            return 0.02;
        }
        return Math.min(0.5, (s - 0.45) * 1.2);
    }

    private Carta escolherCpu(List<Carta> mao, Carta adversaria, int numeroVaza) {
        List<Carta> ordenada = new ArrayList<>(mao);
        ordenada.sort((a, b) -> Integer.compare(forca(a), forca(b)));

        if (adversaria == null) {
            if (numeroVaza == 0 && ordenada.size() == 3) {
                return ordenada.get(1);
            }
            return ordenada.get(ordenada.size() - 1);
        }

        for (Carta c : ordenada) {
            if (forca(c) > forca(adversaria)) {
                return c;
            }
        }
        return ordenada.get(0);
    }
}
