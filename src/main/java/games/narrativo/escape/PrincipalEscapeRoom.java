package games.narrativo.escape;

import java.util.Scanner;

public class PrincipalEscapeRoom {

    private static final Scanner LEITOR = new Scanner(System.in);
    private static final String ARQUIVO_SALVAMENTO = "escape_room.salvo";

    public static void main(String[] args) {
        JogoEscapeRoom jogo = new JogoEscapeRoom();

        exibirIntroducao();
        System.out.println(jogo.descreverSalaAtual());

        while (!jogo.getEstado().isJogoEncerrado()) {
            System.out.print("\n> ");
            String comando = LEITOR.nextLine().trim();

            if (!comando.isEmpty()) {
                executarComando(jogo, comando);
            }
        }

        System.out.println("\nAções realizadas: " + jogo.getEstado().getQuantidadeAcoes());
        LEITOR.close();
    }

    // --- INTERPRETA E EXECUTA O COMANDO INFORMADO PELO JOGADOR ---
    private static void executarComando(JogoEscapeRoom jogo, String comandoCompleto) {
        String[] partes = comandoCompleto.split("\\s+", 2);
        String comando = partes[0].toLowerCase();
        String argumento = partes.length > 1 ? partes[1].trim() : "";

        switch (comando) {
            case "ajuda":
                exibirAjuda();
                break;
            case "olhar":
                System.out.println(jogo.descreverSalaAtual());
                break;
            case "examinar":
                System.out.println(jogo.examinar(argumento));
                break;
            case "ir":
            case "mover":
                moverJogador(jogo, argumento);
                break;
            case "pegar":
                System.out.println(jogo.pegarItem(argumento));
                break;
            case "inventario":
            case "inventário":
                System.out.println(jogo.exibirInventario());
                break;
            case "combinar":
                combinarItens(jogo, argumento);
                break;
            case "resolver":
                resolverEnigma(jogo, argumento);
                break;
            case "abrir":
                abrirElemento(jogo, argumento);
                break;
            case "salvar":
                System.out.println(jogo.salvar(ARQUIVO_SALVAMENTO));
                break;
            case "carregar":
                System.out.println(jogo.carregar(ARQUIVO_SALVAMENTO));
                System.out.println(jogo.descreverSalaAtual());
                break;
            case "desistir":
                System.out.println(jogo.desistir());
                break;
            default:
                System.out.println("Comando desconhecido. Digite ajuda para visualizar os comandos.");
        }
    }

    // --- MOVIMENTA O JOGADOR NA DIREÇÃO INFORMADA ---
    private static void moverJogador(JogoEscapeRoom jogo, String argumento) {
        Direcao direcao = Direcao.converter(argumento);
        String resultado = jogo.mover(direcao);

        System.out.println(resultado);

        if (direcao != null && resultado.startsWith("Você seguiu")) {
            System.out.println(jogo.descreverSalaAtual());
        }
    }

    // --- SEPARA OS NOMES DOS ITENS E REALIZA A COMBINAÇÃO ---
    private static void combinarItens(JogoEscapeRoom jogo, String argumento) {
        String[] itens = argumento.split("\\s+com\\s+", 2);

        if (itens.length != 2) {
            System.out.println("Utilize: combinar nome do item com nome do item.");
            return;
        }

        System.out.println(jogo.combinarItens(itens[0].trim(), itens[1].trim()));
    }

    // --- SEPARA O ENIGMA E A RESPOSTA INFORMADA PELO JOGADOR ---
    private static void resolverEnigma(JogoEscapeRoom jogo, String argumento) {
        String[] dados = argumento.split("\\s+com\\s+", 2);

        if (dados.length != 2) {
            System.out.println("Utilize: resolver nome do enigma com resposta.");
            return;
        }

        System.out.println(jogo.resolverEnigma(dados[0].trim(), dados[1].trim()));
    }

    // --- TENTA ABRIR UM ELEMENTO DO CENÁRIO ---
    private static void abrirElemento(JogoEscapeRoom jogo, String argumento) {
        if (argumento.equalsIgnoreCase("porta") || argumento.equalsIgnoreCase("saida")
                || argumento.equalsIgnoreCase("saída")) {
            System.out.println(jogo.abrirSaida());
        } else {
            System.out.println("Não foi possível abrir esse elemento.");
        }
    }

    // --- EXIBE A INTRODUÇÃO DA HISTÓRIA ---
    private static void exibirIntroducao() {
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
        System.out.println("              O SEGREDO DA MANSÃO");
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
        System.out.println();
        System.out.println("Você desperta em uma mansão abandonada sem saber como chegou até ali.");
        System.out.println("Para escapar, será necessário encontrar pistas, combinar itens e resolver enigmas.");
        System.out.println("Algumas decisões podem revelar finais diferentes.");
        System.out.println();
        System.out.println("Digite ajuda para visualizar os comandos disponíveis.");
    }

    // --- EXIBE OS COMANDOS DISPONÍVEIS ---
    private static void exibirAjuda() {
        System.out.println("\nComandos disponíveis:");
        System.out.println("olhar");
        System.out.println("examinar sala");
        System.out.println("examinar nome");
        System.out.println("ir norte");
        System.out.println("ir sul");
        System.out.println("ir leste");
        System.out.println("ir oeste");
        System.out.println("pegar nome do item");
        System.out.println("inventario");
        System.out.println("combinar item com item");
        System.out.println("resolver enigma com resposta");
        System.out.println("abrir porta");
        System.out.println("salvar");
        System.out.println("carregar");
        System.out.println("desistir");
    }
}

