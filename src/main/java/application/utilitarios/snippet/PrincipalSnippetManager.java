package application.utilitarios.snippet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PrincipalSnippetManager {

    private static final Scanner LEITOR = new Scanner(System.in);
    private static final String ARQUIVO_PADRAO = "snippets.json";

    public static void main(String[] args) {
        SnippetsService servico = new SnippetsService(ARQUIVO_PADRAO);

        exibirTitulo();
        System.out.println(servico.carregarDados());

        executarSistema(servico);
        LEITOR.close();
    }

    // --- EXECUTA O LAÇO PRINCIPAL DO SISTEMA ---
    private static void executarSistema(SnippetsService servico) {
        boolean executando = true;

        while (executando) {
            exibirMenu();
            int opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1:
                        cadastrarSnippet(servico);
                        break;
                    case 2:
                        listarSnippets(servico.listarTodos());
                        break;
                    case 3:
                        visualizarSnippet(servico);
                        break;
                    case 4:
                        pesquisarPorPalavraChave(servico);
                        break;
                    case 5:
                        pesquisarPorLinguagem(servico);
                        break;
                    case 6:
                        pesquisarPorTag(servico);
                        break;
                    case 7:
                        atualizarSnippet(servico);
                        break;
                    case 8:
                        removerSnippet(servico);
                        break;
                    case 9:
                        exportarSnippets(servico);
                        break;
                    case 10:
                        importarSnippets(servico);
                        break;
                    case 0:
                        executando = false;
                        break;
                    default:
                        System.out.println("\nOpção inválida.");
                }
            } catch (IllegalArgumentException excecao) {
                System.out.println("\nDados inválidos: " + excecao.getMessage());
            } catch (IOException excecao) {
                System.out.println("\nErro ao acessar o arquivo: " + excecao.getMessage());
            }
        }

        System.out.println("\nGerenciador de snippets encerrado.");
    }

    // --- CADASTRA UM NOVO SNIPPET ---
    private static void cadastrarSnippet(SnippetsService servico) throws IOException {
        System.out.println("\nCADASTRO DE SNIPPET");

        String titulo = lerTextoObrigatorio("Título: ");
        String descricao = lerTexto("Descrição: ");
        String linguagem = lerTextoObrigatorio("Linguagem: ");
        List<String> tags = lerTags();
        String codigo = lerCodigo();

        Snippet snippet = servico.cadastrar(titulo, descricao, linguagem, codigo, tags);
        System.out.println("\nSnippet cadastrado com o identificador " + snippet.getIdentificador() + ".");
    }

    // --- VISUALIZA TODOS OS DADOS DE UM SNIPPET ---
    private static void visualizarSnippet(SnippetsService servico) {
        long identificador = lerLong("Identificador do snippet: ");
        Snippet snippet = servico.buscarPorIdentificador(identificador);

        if (snippet == null) {
            System.out.println("\nSnippet não encontrado.");
            return;
        }

        exibirDetalhesSnippet(snippet);
    }

    // --- PESQUISA SNIPPETS POR PALAVRA-CHAVE ---
    private static void pesquisarPorPalavraChave(SnippetsService servico) {
        String palavraChave = lerTextoObrigatorio("Palavra-chave: ");
        List<Snippet> resultados = servico.pesquisarPorPalavraChave(palavraChave);
        listarResultados(resultados);
    }

    // --- PESQUISA SNIPPETS POR LINGUAGEM ---
    private static void pesquisarPorLinguagem(SnippetsService servico) {
        String linguagem = lerTextoObrigatorio("Linguagem: ");
        List<Snippet> resultados = servico.pesquisarPorLinguagem(linguagem);
        listarResultados(resultados);
    }

    // --- PESQUISA SNIPPETS POR TAG ---
    private static void pesquisarPorTag(SnippetsService servico) {
        String tag = lerTextoObrigatorio("Tag: ");
        List<Snippet> resultados = servico.pesquisarPorTag(tag);
        listarResultados(resultados);
    }

    // --- ATUALIZA UM SNIPPET EXISTENTE ---
    private static void atualizarSnippet(SnippetsService servico) throws IOException {
        long identificador = lerLong("Identificador do snippet: ");
        Snippet snippet = servico.buscarPorIdentificador(identificador);

        if (snippet == null) {
            System.out.println("\nSnippet não encontrado.");
            return;
        }

        System.out.println("\nInforme os novos dados do snippet.");
        String titulo = lerTextoComValorPadrao("Título", snippet.getTitulo());
        String descricao = lerTextoComValorPadrao("Descrição", snippet.getDescricao());
        String linguagem = lerTextoComValorPadrao("Linguagem", snippet.getLinguagem());

        System.out.print("Tags atuais: ");
        System.out.println(snippet.getTags().isEmpty() ? "nenhuma" : String.join(", ", snippet.getTags()));
        System.out.print("Novas tags separadas por vírgula ou pressione Enter para manter: ");
        String entradaTags = LEITOR.nextLine().trim();
        List<String> tags = entradaTags.isEmpty() ? new ArrayList<>(snippet.getTags()) : converterTags(entradaTags);

        System.out.println("Digite o novo código. Digite FIM em uma linha separada para finalizar.");
        System.out.println("Digite MANTER em uma linha separada para conservar o código atual.");
        String codigo = lerCodigoAtualizacao(snippet.getCodigo());

        boolean atualizado = servico.atualizar(identificador, titulo, descricao, linguagem, codigo, tags);
        System.out.println(atualizado ? "\nSnippet atualizado com sucesso." : "\nNão foi possível atualizar o snippet.");
    }

    // --- REMOVE UM SNIPPET ---
    private static void removerSnippet(SnippetsService servico) throws IOException {
        long identificador = lerLong("Identificador do snippet: ");
        Snippet snippet = servico.buscarPorIdentificador(identificador);

        if (snippet == null) {
            System.out.println("\nSnippet não encontrado.");
            return;
        }

        System.out.println("Snippet: " + snippet.getTitulo());
        String resposta = lerTextoObrigatorio("Confirma a exclusão? Digite sim para confirmar: ");

        if (!resposta.equalsIgnoreCase("sim")) {
            System.out.println("\nExclusão cancelada.");
            return;
        }

        boolean removido = servico.remover(identificador);
        System.out.println(removido ? "\nSnippet removido com sucesso." : "\nNão foi possível remover o snippet.");
    }

    // --- EXPORTA TODOS OS SNIPPETS PARA UM ARQUIVO JSON ---
    private static void exportarSnippets(SnippetsService servico) throws IOException {
        String caminhoArquivo = lerTextoObrigatorio("Caminho do arquivo JSON: ");
        servico.exportar(caminhoArquivo);
        System.out.println("\nSnippets exportados com sucesso para " + caminhoArquivo + ".");
    }

    // --- IMPORTA SNIPPETS DE UM ARQUIVO JSON ---
    private static void importarSnippets(SnippetsService servico) throws IOException {
        String caminhoArquivo = lerTextoObrigatorio("Caminho do arquivo JSON: ");

        System.out.println("\n1. Adicionar aos snippets existentes");
        System.out.println("2. Substituir todos os snippets existentes");

        int opcao = lerInteiro("Escolha o modo de importação: ");

        if (opcao == 1) {
            int quantidade = servico.importarEAdicionar(caminhoArquivo);
            System.out.println("\n" + quantidade + " snippet(s) importado(s).");
        } else if (opcao == 2) {
            String confirmacao = lerTextoObrigatorio("Digite substituir para confirmar: ");

            if (!confirmacao.equalsIgnoreCase("substituir")) {
                System.out.println("\nImportação cancelada.");
                return;
            }

            int quantidade = servico.importarESubstituir(caminhoArquivo);
            System.out.println("\nDados substituídos. " + quantidade + " snippet(s) importado(s).");
        } else {
            System.out.println("\nModo de importação inválido.");
        }
    }

    // --- EXIBE UMA LISTA DE RESULTADOS ---
    private static void listarResultados(List<Snippet> resultados) {
        System.out.println("\nRESULTADOS DA PESQUISA");
        listarSnippets(resultados);
    }

    // --- EXIBE UMA LISTA RESUMIDA DE SNIPPETS ---
    private static void listarSnippets(List<Snippet> snippets) {
        if (snippets.isEmpty()) {
            System.out.println("\nNenhum snippet encontrado.");
            return;
        }

        System.out.println("\nSNIPPETS ENCONTRADOS");

        for (Snippet snippet : snippets) {
            String tags = snippet.getTags().isEmpty() ? "sem tags" : String.join(", ", snippet.getTags());
            System.out.println("----------------------------------------");
            System.out.println("Identificador: " + snippet.getIdentificador());
            System.out.println("Título: " + snippet.getTitulo());
            System.out.println("Linguagem: " + snippet.getLinguagem());
            System.out.println("Tags: " + tags);
            System.out.println("Atualizado em: " + snippet.getDataAtualizacaoFormatada());
        }

        System.out.println("----------------------------------------");
        System.out.println("Quantidade: " + snippets.size());
    }

    // --- EXIBE TODOS OS DETALHES DE UM SNIPPET ---
    private static void exibirDetalhesSnippet(Snippet snippet) {
        System.out.println("\nDETALHES DO SNIPPET");
        System.out.println("----------------------------------------");
        System.out.println("Identificador: " + snippet.getIdentificador());
        System.out.println("Título: " + snippet.getTitulo());
        System.out.println("Descrição: " + snippet.getDescricao());
        System.out.println("Linguagem: " + snippet.getLinguagem());
        System.out.println("Tags: " + (snippet.getTags().isEmpty() ? "sem tags" : String.join(", ", snippet.getTags())));
        System.out.println("Criado em: " + snippet.getDataCriacaoFormatada());
        System.out.println("Atualizado em: " + snippet.getDataAtualizacaoFormatada());
        System.out.println("----------------------------------------");
        System.out.println("CÓDIGO");
        System.out.println("----------------------------------------");
        System.out.println(snippet.getCodigo());
        System.out.println("----------------------------------------");
    }

    // --- LÊ AS TAGS SEPARADAS POR VÍRGULA ---
    private static List<String> lerTags() {
        System.out.print("Tags separadas por vírgula: ");
        return converterTags(LEITOR.nextLine());
    }

    // --- CONVERTE UM TEXTO EM UMA LISTA DE TAGS ---
    private static List<String> converterTags(String entrada) {
        List<String> tags = new ArrayList<>();

        if (entrada == null || entrada.trim().isEmpty()) {
            return tags;
        }

        tags.addAll(Arrays.asList(entrada.split(",")));
        return tags;
    }

    // --- LÊ UM BLOCO DE CÓDIGO ATÉ ENCONTRAR A PALAVRA FIM ---
    private static String lerCodigo() {
        System.out.println("Digite o código. Digite FIM em uma linha separada para finalizar.");

        StringBuilder codigo = new StringBuilder();

        while (true) {
            String linha = LEITOR.nextLine();

            if (linha.equals("FIM")) {
                break;
            }

            if (codigo.length() > 0) {
                codigo.append(System.lineSeparator());
            }

            codigo.append(linha);
        }

        if (codigo.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("O código não pode estar vazio.");
        }

        return codigo.toString();
    }

    // --- LÊ O CÓDIGO DURANTE A ATUALIZAÇÃO ---
    private static String lerCodigoAtualizacao(String codigoAtual) {
        StringBuilder codigo = new StringBuilder();
        boolean primeiraLinha = true;

        while (true) {
            String linha = LEITOR.nextLine();

            if (primeiraLinha && linha.equals("MANTER")) {
                return codigoAtual;
            }

            if (linha.equals("FIM")) {
                break;
            }

            if (codigo.length() > 0) {
                codigo.append(System.lineSeparator());
            }

            codigo.append(linha);
            primeiraLinha = false;
        }

        if (codigo.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("O código não pode estar vazio.");
        }

        return codigo.toString();
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

    // --- LÊ UM TEXTO QUE PODE ESTAR VAZIO ---
    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return LEITOR.nextLine().trim();
    }

    // --- LÊ UM TEXTO E MANTÉM O VALOR ATUAL QUANDO A ENTRADA ESTÁ VAZIA ---
    private static String lerTextoComValorPadrao(String nomeCampo, String valorAtual) {
        System.out.print(nomeCampo + " [" + valorAtual + "]: ");
        String novoValor = LEITOR.nextLine().trim();
        return novoValor.isEmpty() ? valorAtual : novoValor;
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

    // --- LÊ UM NÚMERO DO TIPO LONG ---
    private static long lerLong(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = LEITOR.nextLine().trim();

            try {
                return Long.parseLong(entrada);
            } catch (NumberFormatException excecao) {
                System.out.println("Digite um identificador válido.");
            }
        }
    }

    // --- EXIBE O MENU PRINCIPAL ---
    private static void exibirMenu() {
        System.out.println("\n========================================");
        System.out.println("1. Cadastrar snippet");
        System.out.println("2. Listar snippets");
        System.out.println("3. Visualizar snippet");
        System.out.println("4. Pesquisar por palavra-chave");
        System.out.println("5. Pesquisar por linguagem");
        System.out.println("6. Pesquisar por tag");
        System.out.println("7. Atualizar snippet");
        System.out.println("8. Remover snippet");
        System.out.println("9. Exportar para JSON");
        System.out.println("10. Importar de JSON");
        System.out.println("0. Sair");
        System.out.println("========================================");
    }

    // --- EXIBE O TÍTULO DO SISTEMA ---
    private static void exibirTitulo() {
        System.out.println("========================================");
        System.out.println("      GERENCIADOR DE SNIPPETS");
        System.out.println("========================================");
    }
}

