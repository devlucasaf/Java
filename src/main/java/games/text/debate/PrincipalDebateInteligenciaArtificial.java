package games.text.debate;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PrincipalDebateInteligenciaArtificial {

    private static final Scanner LEITOR = new Scanner(System.in);

    public static void main(String[] args) {
        exibirTitulo();

        String nomeJogador = lerTextoObrigatorio("Informe seu nome: ");
        TemaDebate tema = selecionarTema();
        LadoDebate ladoJogador = selecionarLado();
        int quantidadeRodadas = lerInteiroNoIntervalo("Quantidade de rodadas, entre 1 e 10: ", 1, 10);

        JogoDebate jogo = new JogoDebate(nomeJogador, tema, ladoJogador, quantidadeRodadas);

        exibirIntroducao(jogo);
        executarDebate(jogo);
        exibirResultadoFinal(jogo);

        LEITOR.close();
    }

    // --- EXECUTA TODAS AS RODADAS DO DEBATE ---
    private static void executarDebate(JogoDebate jogo) {
        while (!jogo.isFinalizado()) {
            System.out.println("\n============================================================");
            System.out.println("RODADA " + (jogo.getRodadaAtual() + 1) + " DE " + jogo.getQuantidadeMaximaRodadas());
            System.out.println("============================================================");

            if (!jogo.getRodadas().isEmpty()) {
                String argumentoAnterior = jogo.getRodadas().get(jogo.getRodadas().size() - 1).getArgumentoInteligenciaArtificial().getTexto();
                System.out.println("Último argumento do oponente:");
                System.out.println(argumentoAnterior);
                System.out.println();
            }

            EstrategiaArgumentacao estrategia = selecionarEstrategia();
            System.out.println("Orientação: " + estrategia.getOrientacao());
            String argumento = lerArgumento();

            RodadaDebate rodada = jogo.executarRodada(argumento, estrategia);
            exibirRodada(rodada);
        }
    }

    // --- EXIBE OS ARGUMENTOS E AS NOTAS DA RODADA ---
    private static void exibirRodada(RodadaDebate rodada) {
        System.out.println("\nARGUMENTO DO OPONENTE");
        System.out.println(rodada.getArgumentoInteligenciaArtificial().getTexto());
        System.out.println("Estratégia: " + rodada.getArgumentoInteligenciaArtificial().getEstrategia().getNomeFormatado());

        exibirAvaliacao("Sua avaliação", rodada.getAvaliacaoJogador());
        exibirAvaliacao("Avaliação do oponente", rodada.getAvaliacaoInteligenciaArtificial());

        if (rodada.isEmpate()) {
            System.out.println("\nResultado da rodada: empate.");
        } else if (rodada.isVitoriaJogador()) {
            System.out.println("\nResultado da rodada: você venceu.");
        } else {
            System.out.println("\nResultado da rodada: o oponente venceu.");
        }
    }

    // --- EXIBE UMA AVALIAÇÃO COMPLETA ---
    private static void exibirAvaliacao(String titulo, AvaliacaoArgumento avaliacao) {
        System.out.println("\n" + titulo.toUpperCase());

        for (CriterioAvaliacao criterio : CriterioAvaliacao.values()) {
            System.out.println(criterio.getNomeFormatado() + ": " + formatarNota(avaliacao.getNota(criterio)));
        }

        System.out.println("Pontuação ponderada: " + formatarNota(avaliacao.getPontuacaoTotal()));
        System.out.println("Comentário: " + avaliacao.getObservacao());
    }

    // --- EXIBE O RESULTADO FINAL DO JOGO ---
    private static void exibirResultadoFinal(JogoDebate jogo) {
        System.out.println("\n============================================================");
        System.out.println("                    RESULTADO FINAL");
        System.out.println("============================================================");
        System.out.println(jogo.getResultadoFinal());
        System.out.println("Pontuação de " + jogo.getNomeJogador() + ": " + formatarNota(jogo.calcularPontuacaoJogador()));
        System.out.println("Pontuação do argumentador virtual: " + formatarNota(jogo.calcularPontuacaoIa()));
        System.out.println("Rodadas vencidas pelo jogador: " + jogo.contarVitoriasJogador());
        System.out.println("Rodadas vencidas pelo oponente: " + jogo.contarVitoriasIa());
    }

    // --- PERMITE QUE O JOGADOR SELECIONE UM TEMA ---
    private static TemaDebate selecionarTema() {
        List<TemaDebate> temas = criarTemas();

        System.out.println("\nTEMAS DISPONÍVEIS");

        for (int indice = 0; indice < temas.size(); indice++) {
            System.out.println((indice + 1) + ". " + temas.get(indice).getTitulo());
            System.out.println("   " + temas.get(indice).getDescricao());
        }

        int opcao = lerInteiroNoIntervalo("Escolha um tema: ", 1, temas.size());
        return temas.get(opcao - 1);
    }

    // --- PERMITE QUE O JOGADOR SELECIONE UM LADO ---
    private static LadoDebate selecionarLado() {
        System.out.println("\n1. Favorável");
        System.out.println("2. Contrário");

        int opcao = lerInteiroNoIntervalo("Escolha seu lado: ", 1, 2);
        return opcao == 1 ? LadoDebate.FAVORAVEL : LadoDebate.CONTRARIO;
    }

    // --- PERMITE QUE O JOGADOR SELECIONE UMA ESTRATÉGIA ---
    private static EstrategiaArgumentacao selecionarEstrategia() {
        EstrategiaArgumentacao[] estrategias = EstrategiaArgumentacao.values();

        System.out.println("\nESTRATÉGIAS");

        for (int indice = 0; indice < estrategias.length; indice++) {
            System.out.println((indice + 1) + ". " + estrategias[indice].getNomeFormatado());
        }

        int opcao = lerInteiroNoIntervalo("Escolha sua estratégia: ", 1, estrategias.length);
        return estrategias[opcao - 1];
    }

    // --- CRIA OS TEMAS DISPONÍVEIS NO JOGO ---
    private static List<TemaDebate> criarTemas() {
        TemaDebate tecnologiaEducacao = new TemaDebate(
                1,
                "Uso de tecnologia na educação",
                "Debata se ferramentas digitais devem ter maior presença no processo educacional.",
                Arrays.asList("tecnologia", "educação", "aprendizado", "alunos", "professores", "digital"),
                Arrays.asList(
                        "A tecnologia pode ampliar o acesso a materiais educacionais e permitir novas formas de aprendizado.",
                        "Ferramentas digitais podem apoiar professores na personalização de atividades e no acompanhamento dos alunos.",
                        "Recursos interativos podem aumentar a participação dos estudantes quando são utilizados com planejamento."
                ),
                Arrays.asList(
                        "O uso excessivo de ferramentas digitais pode aumentar distrações e prejudicar a concentração dos alunos.",
                        "A desigualdade de acesso a equipamentos pode ampliar diferenças entre estudantes.",
                        "A tecnologia não substitui o acompanhamento pedagógico e pode ser ineficiente quando aplicada sem preparo."
                )
        );

        TemaDebate trabalhoRemoto = new TemaDebate(
                2,
                "Trabalho remoto",
                "Debata se o trabalho remoto deve ser adotado como modelo principal pelas empresas.",
                Arrays.asList("trabalho", "remoto", "empresas", "produtividade", "funcionários", "comunicação"),
                Arrays.asList(
                        "O trabalho remoto pode reduzir deslocamentos e oferecer maior flexibilidade aos funcionários.",
                        "Empresas podem reduzir custos físicos e contratar profissionais de diferentes localidades.",
                        "Com metas claras e ferramentas adequadas, equipes remotas podem manter bons níveis de produtividade."
                ),
                Arrays.asList(
                        "A distância pode dificultar a comunicação e reduzir a integração entre os membros da equipe.",
                        "Nem todos os profissionais possuem um ambiente doméstico adequado para trabalhar.",
                        "Algumas atividades dependem de colaboração presencial e acesso a estruturas específicas."
                )
        );

        TemaDebate transportePublico = new TemaDebate(
                3,
                "Investimento em transporte coletivo",
                "Debata se as cidades devem priorizar investimentos em transporte coletivo.",
                Arrays.asList("transporte", "coletivo", "cidades", "mobilidade", "trânsito", "investimento"),
                Arrays.asList(
                        "O transporte coletivo eficiente pode reduzir congestionamentos e melhorar a mobilidade nas cidades.",
                        "Investimentos em redes integradas podem ampliar o acesso da população a serviços e oportunidades.",
                        "A redução do número de automóveis nas vias pode diminuir impactos ambientais e melhorar o espaço urbano."
                ),
                Arrays.asList(
                        "Grandes projetos de transporte exigem recursos elevados e podem demorar para produzir resultados.",
                        "Regiões com baixa densidade populacional podem não apresentar demanda suficiente para determinadas linhas.",
                        "Investimentos exclusivos no transporte coletivo podem deixar de atender outras necessidades importantes de mobilidade."
                )
        );

        return List.of(tecnologiaEducacao, trabalhoRemoto, transportePublico);
    }

    // --- LÊ UM ARGUMENTO COM VÁRIAS LINHAS ---
    private static String lerArgumento() {
        System.out.println("Digite seu argumento. Digite FIM em uma linha separada para concluir:");

        StringBuilder argumento = new StringBuilder();

        while (true) {
            String linha = LEITOR.nextLine();

            if (linha.equalsIgnoreCase("FIM")) {
                break;
            }

            if (argumento.length() > 0) {
                argumento.append(System.lineSeparator());
            }

            argumento.append(linha);
        }

        if (argumento.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("O argumento não pode estar vazio.");
        }

        return argumento.toString();
    }

    // --- EXIBE A INTRODUÇÃO DO DEBATE ---
    private static void exibirIntroducao(JogoDebate jogo) {
        System.out.println("\n============================================================");
        System.out.println("Tema: " + jogo.getTema().getTitulo());
        System.out.println("Posição do jogador: " + jogo.getLadoJogador().getNomeFormatado());
        System.out.println("Posição do oponente: " + jogo.getLadoIa().getNomeFormatado());
        System.out.println("Rodadas: " + jogo.getQuantidadeMaximaRodadas());
        System.out.println("============================================================");
        System.out.println("A avaliação considera a construção textual, não a correção da opinião apresentada.");
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

    // --- LÊ UM NÚMERO DENTRO DE UM INTERVALO ---
    private static int lerInteiroNoIntervalo(String mensagem, int minimo, int maximo) {
        while (true) {
            System.out.print(mensagem);
            String entrada = LEITOR.nextLine().trim();

            try {
                int valor = Integer.parseInt(entrada);

                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }

                System.out.println("Digite um número entre " + minimo + " e " + maximo + ".");
            } catch (NumberFormatException excecao) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    // --- FORMATA UMA NOTA ---
    private static String formatarNota(double nota) {
        return String.format(Locale.of("pt", "BR"), "%.2f", nota);
    }

    // --- EXIBE O TÍTULO DO JOGO ---
    private static void exibirTitulo() {
        System.out.println("============================================================");
        System.out.println("                   DEBATE AI");
        System.out.println("============================================================");
        System.out.println("Apresente argumentos claros, coerentes e relacionados ao tema.");
    }
}

