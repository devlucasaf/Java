package math.probabilidade.csv;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PrincipalAnalisadorCsv {

    private static final Scanner LEITOR = new Scanner(System.in);

    public static void main(String[] args) {
        exibirTitulo();

        String caminhoArquivo = lerTextoObrigatorio("Informe o caminho do arquivo CSV: ");
        AnalisadorCsv analisador = new AnalisadorCsv();

        try {
            ResultadoAnaliseCsv resultado = analisador.analisar(caminhoArquivo);
            exibirResultado(resultado);
            executarMenuExportacao(resultado);
        } catch (IOException excecao) {
            System.out.println("\nNão foi possível analisar o arquivo: " + excecao.getMessage());
        } catch (IllegalArgumentException excecao) {
            System.out.println("\nDados inválidos: " + excecao.getMessage());
        } finally {
            LEITOR.close();
        }
    }

    // --- EXIBE O RESULTADO DA ANÁLISE NO TERMINAL ---
    private static void exibirResultado(ResultadoAnaliseCsv resultado) {
        System.out.println("\n==================================================");
        System.out.println("                 RESULTADO GERAL");
        System.out.println("==================================================");
        System.out.println("Arquivo: " + resultado.getArquivoAnalisado());
        System.out.println("Registros: " + resultado.getQuantidadeRegistros());
        System.out.println("Colunas: " + resultado.getQuantidadeColunas());
        System.out.println("Colunas numéricas: " + resultado.getQuantidadeColunasNumericas());

        if (resultado.getResumos().isEmpty()) {
            System.out.println("\nNenhuma coluna totalmente numérica foi encontrada.");
        } else {
            exibirResumos(resultado.getResumos());
        }

        exibirCorrelacoes(resultado.getCorrelacoes());
    }

    // --- EXIBE OS RESUMOS ESTATÍSTICOS ---
    private static void exibirResumos(List<ResumoEstatistico> resumos) {
        System.out.println("\n==================================================");
        System.out.println("                  ESTATÍSTICAS");
        System.out.println("==================================================");

        for (ResumoEstatistico resumo : resumos) {
            System.out.println("\nColuna: " + resumo.getNomeColuna());
            System.out.println("Valores válidos: " + resumo.getQuantidadeValores());
            System.out.println("Valores ausentes: " + resumo.getQuantidadeAusentes());
            System.out.println("Soma: " + formatarNumero(resumo.getSoma()));
            System.out.println("Média: " + formatarNumero(resumo.getMedia()));
            System.out.println("Mediana: " + formatarNumero(resumo.getMediana()));
            System.out.println("Moda: " + formatarModas(resumo.getModas()));
            System.out.println("Desvio padrão populacional: " + formatarNumero(resumo.getDesvioPadraoPopulacional()));
            System.out.println("Desvio padrão amostral: " + formatarNumero(resumo.getDesvioPadraoAmostral()));
            System.out.println("Mínimo: " + formatarNumero(resumo.getMinimo()));
            System.out.println("Máximo: " + formatarNumero(resumo.getMaximo()));
            System.out.println("Amplitude: " + formatarNumero(resumo.getAmplitude()));
        }
    }

    // --- EXIBE AS CORRELAÇÕES CALCULADAS ---
    private static void exibirCorrelacoes(List<ResultadoCorrelacao> correlacoes) {
        System.out.println("\n==================================================");
        System.out.println("                  CORRELAÇÕES");
        System.out.println("==================================================");

        if (correlacoes.isEmpty()) {
            System.out.println("Não existem colunas numéricas suficientes.");
            return;
        }

        for (ResultadoCorrelacao correlacao : correlacoes) {
            String coeficiente = correlacao.getCoeficiente() == null ? "Indefinido" : formatarNumero(correlacao.getCoeficiente());
            System.out.println("\n" + correlacao.getPrimeiraColuna() + " x " + correlacao.getSegundaColuna());
            System.out.println("Pares válidos: " + correlacao.getQuantidadePares());
            System.out.println("Coeficiente: " + coeficiente);
            System.out.println("Interpretação: " + correlacao.getInterpretacao());
        }
    }

    // --- EXECUTA O MENU DE EXPORTAÇÃO ---
    private static void executarMenuExportacao(ResultadoAnaliseCsv resultado) {
        ExportadorResumo exportador = new ExportadorResumo();
        boolean executando = true;

        while (executando) {
            System.out.println("\n1. Exportar resumo em TXT");
            System.out.println("2. Exportar resumo em JSON");
            System.out.println("3. Exportar nos dois formatos");
            System.out.println("0. Encerrar");

            int opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1:
                        exportarTxt(exportador, resultado);
                        break;
                    case 2:
                        exportarJson(exportador, resultado);
                        break;
                    case 3:
                        exportarTodos(exportador, resultado);
                        break;
                    case 0:
                        executando = false;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (IOException excecao) {
                System.out.println("Não foi possível exportar: " + excecao.getMessage());
            }
        }

        System.out.println("\nAnalisador encerrado.");
    }

    // --- EXPORTA O RESUMO EM TXT ---
    private static void exportarTxt(ExportadorResumo exportador, ResultadoAnaliseCsv resultado) throws IOException {
        String caminho = lerTextoObrigatorio("Caminho do arquivo TXT: ");
        exportador.exportarTxt(resultado, caminho);
        System.out.println("Resumo TXT exportado com sucesso.");
    }

    // --- EXPORTA O RESUMO EM JSON ---
    private static void exportarJson(ExportadorResumo exportador, ResultadoAnaliseCsv resultado) throws IOException {
        String caminho = lerTextoObrigatorio("Caminho do arquivo JSON: ");
        exportador.exportarJson(resultado, caminho);
        System.out.println("Resumo JSON exportado com sucesso.");
    }

    // --- EXPORTA O RESUMO NOS DOIS FORMATOS ---
    private static void exportarTodos(ExportadorResumo exportador, ResultadoAnaliseCsv resultado) throws IOException {
        String caminhoBase = lerTextoObrigatorio("Caminho base sem extensão: ");
        exportador.exportarTxt(resultado, caminhoBase + ".txt");
        exportador.exportarJson(resultado, caminhoBase + ".json");
        System.out.println("Resumos TXT e JSON exportados com sucesso.");
    }

    // --- FORMATA UMA LISTA DE MODAS ---
    private static String formatarModas(List<Double> modas) {
        if (modas.isEmpty()) {
            return "Amodal";
        }

        StringBuilder texto = new StringBuilder();

        for (int indice = 0; indice < modas.size(); indice++) {
            if (indice > 0) {
                texto.append(", ");
            }

            texto.append(formatarNumero(modas.get(indice)));
        }

        return texto.toString();
    }

    // --- FORMATA UM NÚMERO ---
    private static String formatarNumero(double numero) {
        return String.format(Locale.of("pt", "BR"), "%.6f", numero);
    }

    // --- LÊ UM TEXTO OBRIGATÓRIO ---
    private static String lerTextoObrigatorio(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = LEITOR.nextLine().trim();

            if (!valor.isEmpty()) {
                return valor;
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

    // --- EXIBE O TÍTULO DO PROGRAMA ---
    private static void exibirTitulo() {
        System.out.println("==================================================");
        System.out.println("          ANALISADOR ESTATÍSTICO DE CSV");
        System.out.println("==================================================");
    }
}

