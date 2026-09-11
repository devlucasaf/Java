package games.strategy.cardbattle;

import java.util.List;
import java.util.Scanner;

public class PrincipalBatalhaCartas {

    private static final Scanner LEITOR = new Scanner(System.in);

    public static void main(String[] args) {
        exibirTitulo();

        String nomeJogador = lerTextoObrigatorio("Informe o nome do jogador: ");
        JogoBatalhaCartas jogo = new JogoBatalhaCartas(nomeJogador);

        executarPartida(jogo);
        LEITOR.close();
    }

    // --- EXECUTA A PARTIDA ATÉ QUE EXISTA UM VENCEDOR ---
    private static void executarPartida(JogoBatalhaCartas jogo) {
        while (!jogo.isPartidaEncerrada()) {
            exibirAcontecimentos(jogo.iniciarTurnoJogador());

            if (jogo.isPartidaEncerrada()) {
                break;
            }

            executarTurnoJogador(jogo);

            if (!jogo.isPartidaEncerrada()) {
                System.out.println("\nTURNO DA INTELIGÊNCIA ARTIFICIAL");
                exibirAcontecimentos(jogo.finalizarTurnoJogador());
            }
        }

        exibirResultadoFinal(jogo);
    }

    // --- EXECUTA AS AÇÕES DO JOGADOR DURANTE O TURNO ---
    private static void executarTurnoJogador(JogoBatalhaCartas jogo) {
        boolean finalizarTurno = false;

        while (!finalizarTurno && !jogo.isPartidaEncerrada()) {
            exibirEstadoPartida(jogo);
            exibirMao(jogo.getJogador());
            exibirMenuTurno();

            int opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1:
                        utilizarCarta(jogo);
                        break;
                    case 2:
                        exibirEfeitos(jogo.getJogador(), jogo.getJogadorInteligenciaArtificial());
                        break;
                    case 3:
                        finalizarTurno = true;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException | IllegalStateException | IndexOutOfBoundsException excecao) {
                System.out.println("Não foi possível realizar a ação: " + excecao.getMessage());
            }

            if (!jogo.getJogador().possuiCartaJogavel() && !jogo.isPartidaEncerrada()) {
                System.out.println("Você não possui mais cartas que possam ser utilizadas neste turno.");
                finalizarTurno = true;
            }
        }
    }

    // --- UTILIZA UMA CARTA ESCOLHIDA PELO JOGADOR ---
    private static void utilizarCarta(JogoBatalhaCartas jogo) {
        if (jogo.getJogador().getMao().isEmpty()) {
            System.out.println("Sua mão está vazia.");
            return;
        }

        int posicao = lerInteiro("Número da carta: ");
        System.out.println(jogo.jogarCarta(posicao - 1));
    }

    // --- EXIBE O ESTADO ATUAL DA PARTIDA ---
    private static void exibirEstadoPartida(JogoBatalhaCartas jogo) {
        Jogador jogador = jogo.getJogador();
        Jogador ia = jogo.getJogadorInteligenciaArtificial();

        System.out.println("\n==================================================");
        System.out.println("TURNO " + jogo.getNumeroTurno());
        System.out.println("==================================================");
        System.out.println(jogador.getNome() + ": vida " + jogador.getVidaAtual() + "/" + jogador.getVidaMaxima() + " | defesa " + jogador.getDefesaAtual() + " | energia " + jogador.getEnergiaAtual() + "/" + jogador.getEnergiaMaxima());
        System.out.println(ia.getNome() + ": vida " + ia.getVidaAtual() + "/" + ia.getVidaMaxima() + " | defesa " + ia.getDefesaAtual());
    }

    // --- EXIBE AS CARTAS DA MÃO DO JOGADOR ---
    private static void exibirMao(Jogador jogador) {
        System.out.println("\nSUA MÃO");

        if (jogador.getMao().isEmpty()) {
            System.out.println("Nenhuma carta disponível.");
            return;
        }

        for (int indice = 0; indice < jogador.getMao().size(); indice++) {
            Carta carta = jogador.getMao().get(indice);
            String disponibilidade = carta.podeSerJogada(jogador.getEnergiaAtual()) ? "" : " [ENERGIA INSUFICIENTE]";

            System.out.println((indice + 1) + ". " + carta + disponibilidade);

            if (!carta.getDescricao().isEmpty()) {
                System.out.println("   " + carta.getDescricao());
            }
        }

        System.out.println("Baralho: " + jogador.getQuantidadeCartasBaralho() + " | Descarte: " + jogador.getQuantidadeCartasDescarte());
    }

    // --- EXIBE OS EFEITOS ATIVOS DOS PARTICIPANTES ---
    private static void exibirEfeitos(Jogador jogador, Jogador ia) {
        exibirEfeitosJogador(jogador);
        exibirEfeitosJogador(ia);
    }

    // --- EXIBE OS EFEITOS DE UM JOGADOR ---
    private static void exibirEfeitosJogador(Jogador jogador) {
        System.out.println("\nEfeitos de " + jogador.getNome() + ":");

        if (jogador.getEfeitosAtivos().isEmpty()) {
            System.out.println("Nenhum efeito ativo.");
            return;
        }

        for (EfeitoStatus efeito : jogador.getEfeitosAtivos()) {
            System.out.println("- " + efeito);
        }
    }

    // --- EXIBE OS ACONTECIMENTOS DE UM TURNO ---
    private static void exibirAcontecimentos(List<String> acontecimentos) {
        for (String acontecimento : acontecimentos) {
            System.out.println(acontecimento);
        }
    }

    // --- EXIBE O RESULTADO FINAL DA PARTIDA ---
    private static void exibirResultadoFinal(JogoBatalhaCartas jogo) {
        System.out.println("\n==================================================");
        System.out.println("                 FIM DA PARTIDA");
        System.out.println("==================================================");

        switch (jogo.getResultado()) {
            case VITORIA_JOGADOR:
                System.out.println("Vitória de " + jogo.getJogador().getNome() + "!");
                break;
            case VITORIA_IA:
                System.out.println(jogo.getJogadorInteligenciaArtificial().getNome() + " venceu a batalha.");
                break;
            case EMPATE:
                System.out.println("A batalha terminou empatada.");
                break;
            default:
                System.out.println("A partida foi interrompida.");
        }

        System.out.println("Turnos disputados: " + jogo.getNumeroTurno());
        System.out.println("Vida final do jogador: " + jogo.getJogador().getVidaAtual());
        System.out.println("Vida final da IA: " + jogo.getJogadorInteligenciaArtificial().getVidaAtual());
    }

    // --- EXIBE O MENU DO TURNO ---
    private static void exibirMenuTurno() {
        System.out.println("\n1. Utilizar carta");
        System.out.println("2. Visualizar efeitos ativos");
        System.out.println("3. Finalizar turno");
    }

    // --- LÊ UM TEXTO OBRIGATÓRIO ---
    private static String lerTextoObrigatorio(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = LEITOR.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("O valor não pode estar vazio.");
        }
    }

    // --- LÊ UM NÚMERO INTEIRO ---
    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = LEITOR.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException excecao) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    // --- EXIBE O TÍTULO DO JOGO ---
    private static void exibirTitulo() {
        System.out.println("==================================================");
        System.out.println("             BATALHA ESTRATÉGICA");
        System.out.println("==================================================");
        System.out.println("Use cartas, administre sua energia e derrote a inteligência artificial.");
        System.out.println();
    }
}

