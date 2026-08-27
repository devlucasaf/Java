package games.puzzle.nonogram;

import java.util.List;
import java.util.Scanner;

public class PrincipalNonogram {

    private static final Scanner LEITOR = new Scanner(System.in);

    public static void main(String[] args) {
        exibirTitulo();

        Dificuldade dificuldade = lerDificuldade();
        JogoNonogram jogo = new JogoNonogram(dificuldade);

        jogo.iniciar();
        executarJogo(jogo);

        LEITOR.close();
    }

    // --- EXECUTA O LAÇO PRINCIPAL DO JOGO ---
    private static void executarJogo(JogoNonogram jogo) {
        boolean executando = true;

        while (executando) {
            exibirJogo(jogo);
            exibirMenu();

            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    preencherCelula(jogo);
                    break;
                case 2:
                    marcarCelulaVazia(jogo);
                    break;
                case 3:
                    limparCelula(jogo);
                    break;
                case 4:
                    validarProgresso(jogo);
                    break;
                case 5:
                    jogo.reiniciar();
                    System.out.println("\nTabuleiro reiniciado.");
                    break;
                case 6:
                    exibirSolucao(jogo.getTabuleiro());
                    break;
                case 0:
                    executando = false;
                    break;
                default:
                    System.out.println("\nOpção inválida.");
            }

            if (jogo.isConcluido()) {
                exibirJogo(jogo);
                System.out.println("\nParabéns! Você resolveu o Nonogram.");
                System.out.printf("Total de jogadas: %d%n", jogo.getQuantidadeJogadas());
                executando = false;
            }
        }

        System.out.println("\nJogo finalizado.");
    }

    // --- PREENCHE UMA CÉLULA ESCOLHIDA PELO JOGADOR ---
    private static void preencherCelula(JogoNonogram jogo) {
        int[] posicao = lerPosicao(jogo.getTabuleiro());
        jogo.definirEstadoCelula(posicao[0], posicao[1], EstadoCelula.PREENCHIDA);
    }

    // --- MARCA UMA CÉLULA COMO VAZIA ---
    private static void marcarCelulaVazia(JogoNonogram jogo) {
        int[] posicao = lerPosicao(jogo.getTabuleiro());
        jogo.definirEstadoCelula(posicao[0], posicao[1], EstadoCelula.VAZIA);
    }

    // --- LIMPA A MARCAÇÃO DE UMA CÉLULA ---
    private static void limparCelula(JogoNonogram jogo) {
        int[] posicao = lerPosicao(jogo.getTabuleiro());
        jogo.definirEstadoCelula(posicao[0], posicao[1], EstadoCelula.DESCONHECIDA);
    }

    // --- LÊ UMA POSIÇÃO UTILIZANDO NÚMEROS INICIADOS EM 1 ---
    private static int[] lerPosicao(TabuleiroNonogram tabuleiro) {
        int linha;

        do {
            linha = lerInteiro("Informe a linha de 1 até " + tabuleiro.getQuantidadeLinhas() + ": ");

            if (linha < 1 || linha > tabuleiro.getQuantidadeLinhas()) {
                System.out.println("Linha inválida.");
            }
        } while (linha < 1 || linha > tabuleiro.getQuantidadeLinhas());

        int coluna;

        do {
            coluna = lerInteiro("Informe a coluna de 1 até " + tabuleiro.getQuantidadeColunas() + ": ");

            if (coluna < 1 || coluna > tabuleiro.getQuantidadeColunas()) {
                System.out.println("Coluna inválida.");
            }
        } while (coluna < 1 || coluna > tabuleiro.getQuantidadeColunas());

        return new int[]{linha - 1, coluna - 1};
    }

    // --- EXIBE O RESULTADO DA VALIDAÇÃO DO PROGRESSO ---
    private static void validarProgresso(JogoNonogram jogo) {
        if (jogo.isProgressoValido()) {
            System.out.println("\nTodas as marcações atuais estão corretas.");
        } else {
            System.out.printf("%nExistem %d marcações incorretas.%n", jogo.getQuantidadeErros());
        }

        System.out.printf("Progresso correto: %.2f%%%n", jogo.getPorcentagemProgresso());
    }

    // --- EXIBE O TABULEIRO E AS DICAS ---
    private static void exibirJogo(JogoNonogram jogo) {
        TabuleiroNonogram tabuleiro = jogo.getTabuleiro();

        System.out.println();
        System.out.println("Dificuldade: " + jogo.getDificuldade().getNomeFormatado());
        System.out.println("Jogadas: " + jogo.getQuantidadeJogadas());
        System.out.printf("Progresso: %.2f%%%n%n", jogo.getPorcentagemProgresso());

        exibirDicasColunas(tabuleiro);
        exibirNumerosColunas(tabuleiro);
        exibirLinhas(tabuleiro);

        System.out.println();
        System.out.println("Legenda: # preenchida | X vazia | . desconhecida");
    }

    // --- EXIBE AS DICAS DAS COLUNAS ALINHADAS VERTICALMENTE ---
    private static void exibirDicasColunas(TabuleiroNonogram tabuleiro) {
        List<List<Integer>> dicas = tabuleiro.getDicasColunas();
        int maiorQuantidadeDicas = 0;

        for (List<Integer> dicasColuna : dicas) {
            maiorQuantidadeDicas = Math.max(maiorQuantidadeDicas, dicasColuna.size());
        }

        for (int nivel = 0; nivel < maiorQuantidadeDicas; nivel++) {
            System.out.print("          ");

            for (int coluna = 0; coluna < tabuleiro.getQuantidadeColunas(); coluna++) {
                List<Integer> dicasColuna = dicas.get(coluna);
                int niveisVazios = maiorQuantidadeDicas - dicasColuna.size();

                if (nivel < niveisVazios) {
                    System.out.print("   ");
                } else {
                    int indiceDica = nivel - niveisVazios;
                    System.out.printf("%2d ", dicasColuna.get(indiceDica));
                }
            }

            System.out.println();
        }
    }

    // --- EXIBE OS NÚMEROS DAS COLUNAS ---
    private static void exibirNumerosColunas(TabuleiroNonogram tabuleiro) {
        System.out.print("          ");

        for (int coluna = 1; coluna <= tabuleiro.getQuantidadeColunas(); coluna++) {
            System.out.printf("%2d ", coluna);
        }

        System.out.println();
    }

    // --- EXIBE AS LINHAS COM SUAS RESPECTIVAS DICAS ---
    private static void exibirLinhas(TabuleiroNonogram tabuleiro) {
        for (int linha = 0; linha < tabuleiro.getQuantidadeLinhas(); linha++) {
            String dicaLinha = formatarDica(tabuleiro.getDicasLinhas().get(linha));

            System.out.printf("%2d %-6s ", linha + 1, dicaLinha);

            for (int coluna = 0; coluna < tabuleiro.getQuantidadeColunas(); coluna++) {
                System.out.printf(" %c ", tabuleiro.getEstadoCelula(linha, coluna).getSimbolo());
            }

            System.out.println();
        }
    }

    // --- CONVERTE UMA LISTA DE DICAS EM TEXTO ---
    private static String formatarDica(List<Integer> dicas) {
        StringBuilder resultado = new StringBuilder();

        for (int indice = 0; indice < dicas.size(); indice++) {
            if (indice > 0) {
                resultado.append(",");
            }

            resultado.append(dicas.get(indice));
        }

        return resultado.toString();
    }

    // --- EXIBE A SOLUÇÃO DO TABULEIRO ---
    private static void exibirSolucao(TabuleiroNonogram tabuleiro) {
        System.out.println("\nSolução:");

        boolean[][] solucao = tabuleiro.getCopiaSolucao();

        for (boolean[] linha : solucao) {
            for (boolean celula : linha) {
                System.out.print(celula ? " # " : " . ");
            }

            System.out.println();
        }
    }

    // --- SOLICITA O NÍVEL DE DIFICULDADE ---
    private static Dificuldade lerDificuldade() {
        System.out.println("1. Fácil         - 5x5");
        System.out.println("2. Médio         - 10x10");
        System.out.println("3. Difícil       - 15x15");
        System.out.println("4. Especialista  - 20x20");

        while (true) {
            int opcao = lerInteiro("\nEscolha a dificuldade: ");

            switch (opcao) {
                case 1:
                    return Dificuldade.FACIL;
                case 2:
                    return Dificuldade.MEDIO;
                case 3:
                    return Dificuldade.DIFICIL;
                case 4:
                    return Dificuldade.ESPECIALISTA;
                default:
                    System.out.println("Dificuldade inválida.");
            }
        }
    }

    // --- LÊ UM NÚMERO INTEIRO SEM ENCERRAR O PROGRAMA EM CASO DE ERRO ---
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

    // --- EXIBE O MENU DE AÇÕES ---
    private static void exibirMenu() {
        System.out.println();
        System.out.println("1. Preencher célula");
        System.out.println("2. Marcar célula como vazia");
        System.out.println("3. Limpar célula");
        System.out.println("4. Validar progresso");
        System.out.println("5. Reiniciar tabuleiro");
        System.out.println("6. Mostrar solução");
        System.out.println("0. Sair");
        System.out.println();
    }

    // --- EXIBE O TÍTULO DO JOGO ---
    private static void exibirTitulo() {
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
        System.out.println("        JAVA NONOGRAM");
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
        System.out.println();
    }
}

