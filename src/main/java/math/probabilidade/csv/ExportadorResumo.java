package math.probabilidade.csv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ExportadorResumo {

    private final Gson gson;

    public ExportadorResumo() {
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().registerTypeAdapter(LocalDateTime.class, new AdaptadorLocalDateTime()).create();
    }

    // --- EXPORTA O RESULTADO EM FORMATO TXT ---
    public void exportarTxt(ResultadoAnaliseCsv resultado, String caminhoArquivo) throws IOException {
        validarResultado(resultado);
        Path caminho = prepararCaminho(caminhoArquivo);
        Files.writeString(caminho, criarResumoTxt(resultado), StandardCharsets.UTF_8);
    }

    // --- EXPORTA O RESULTADO EM FORMATO JSON ---
    public void exportarJson(ResultadoAnaliseCsv resultado, String caminhoArquivo) throws IOException {
        validarResultado(resultado);
        Path caminho = prepararCaminho(caminhoArquivo);
        Files.writeString(caminho, gson.toJson(resultado), StandardCharsets.UTF_8);
    }

    // --- CRIA O CONTEÚDO TEXTUAL DO RESUMO ---
    public String criarResumoTxt(ResultadoAnaliseCsv resultado) {
        validarResultado(resultado);

        StringBuilder texto = new StringBuilder();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        texto.append("==================================================\n");
        texto.append("          RESUMO ESTATÍSTICO DO ARQUIVO CSV\n");
        texto.append("==================================================\n");
        texto.append("Arquivo: ").append(resultado.getArquivoAnalisado()).append("\n");
        texto.append("Data da análise: ").append(resultado.getDataAnalise().format(formatador)).append("\n");
        texto.append("Registros: ").append(resultado.getQuantidadeRegistros()).append("\n");
        texto.append("Colunas: ").append(resultado.getQuantidadeColunas()).append("\n");
        texto.append("Colunas numéricas: ").append(resultado.getQuantidadeColunasNumericas()).append("\n");
        texto.append("Cabeçalhos: ").append(String.join(", ", resultado.getColunas())).append("\n");

        if (resultado.getResumos().isEmpty()) {
            texto.append("\nNenhuma coluna numérica foi encontrada.\n");
        } else {
            texto.append("\n==================================================\n");
            texto.append("                  ESTATÍSTICAS\n");
            texto.append("==================================================\n");

            for (ResumoEstatistico resumo : resultado.getResumos()) {
                texto.append("\nColuna: ").append(resumo.getNomeColuna()).append("\n");
                texto.append("Valores válidos: ").append(resumo.getQuantidadeValores()).append("\n");
                texto.append("Valores ausentes: ").append(resumo.getQuantidadeAusentes()).append("\n");
                texto.append("Soma: ").append(formatarNumero(resumo.getSoma())).append("\n");
                texto.append("Média: ").append(formatarNumero(resumo.getMedia())).append("\n");
                texto.append("Mediana: ").append(formatarNumero(resumo.getMediana())).append("\n");
                texto.append("Moda: ").append(formatarModas(resumo.getModas())).append("\n");
                texto.append("Variância populacional: ").append(formatarNumero(resumo.getVarianciaPopulacional())).append("\n");
                texto.append("Desvio padrão populacional: ").append(formatarNumero(resumo.getDesvioPadraoPopulacional())).append("\n");
                texto.append("Desvio padrão amostral: ").append(formatarNumero(resumo.getDesvioPadraoAmostral())).append("\n");
                texto.append("Mínimo: ").append(formatarNumero(resumo.getMinimo())).append("\n");
                texto.append("Máximo: ").append(formatarNumero(resumo.getMaximo())).append("\n");
                texto.append("Amplitude: ").append(formatarNumero(resumo.getAmplitude())).append("\n");
            }
        }

        texto.append("\n==================================================\n");
        texto.append("                  CORRELAÇÕES\n");
        texto.append("==================================================\n");

        if (resultado.getCorrelacoes().isEmpty()) {
            texto.append("Não existem colunas numéricas suficientes para calcular correlações.\n");
        } else {
            for (ResultadoCorrelacao correlacao : resultado.getCorrelacoes()) {
                texto.append("\n").append(correlacao.getPrimeiraColuna()).append(" x ").append(correlacao.getSegundaColuna()).append("\n");
                texto.append("Pares válidos: ").append(correlacao.getQuantidadePares()).append("\n");
                texto.append("Coeficiente: ").append(correlacao.getCoeficiente() == null ? "Indefinido" : formatarNumero(correlacao.getCoeficiente())).append("\n");
                texto.append("Interpretação: ").append(correlacao.getInterpretacao()).append("\n");
            }
        }

        return texto.toString();
    }

    // --- PREPARA O CAMINHO DE EXPORTAÇÃO ---
    private Path prepararCaminho(String caminhoArquivo) throws IOException {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho de exportação não pode estar vazio.");
        }

        Path caminho = Path.of(caminhoArquivo.trim()).toAbsolutePath();
        Path diretorio = caminho.getParent();

        if (diretorio != null) {
            Files.createDirectories(diretorio);
        }

        return caminho;
    }

    // --- FORMATA UMA LISTA DE MODAS ---
    private String formatarModas(List<Double> modas) {
        if (modas == null || modas.isEmpty()) {
            return "Amodal";
        }

        StringBuilder resultado = new StringBuilder();

        for (int indice = 0; indice < modas.size(); indice++) {
            if (indice > 0) {
                resultado.append(", ");
            }

            resultado.append(formatarNumero(modas.get(indice)));
        }

        return resultado.toString();
    }

    // --- FORMATA UM NÚMERO PARA EXIBIÇÃO ---
    private String formatarNumero(double numero) {
        return String.format(Locale.of("pt", "BR"), "%.6f", numero);
    }

    // --- VALIDA O RESULTADO DA ANÁLISE ---
    private void validarResultado(ResultadoAnaliseCsv resultado) {
        if (resultado == null) {
            throw new IllegalArgumentException("O resultado da análise não pode ser nulo.");
        }
    }

}

