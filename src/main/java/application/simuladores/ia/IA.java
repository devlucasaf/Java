package application.simuladores.ia;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IA {

    // --- VALIDA A CHAVE DA API E REALIZA A ANALISE DO ARQUIVO PDF ---
    public static AnaliseCronologica analise(Path caminhoPdf) throws IOException {
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Defina a variavel de ambiente GEMINI_API_KEY.");
        }

        return new AnaliseCronologica(new ArrayList<>(), "", "");
    }

    // --- RECEBE OS DADOS DA ANALISE PARA GERAR UM GRAFICO DE BARRAS ---
    public static void graficoBarras(AnaliseCronologica dados, Path caminhoSaida) {
        List<AnaliseValores> historico = dados.getHistorico();
        System.out.println("Itens recebidos para o grafico de barras: " + historico.size());
    }

    // --- RECEBE OS DADOS DA ANALISE PARA GERAR UM GRAFICO DE PIZZA ---
    public static void graficoPizza(AnaliseCronologica dados, Path caminhoSaida) {
        List<AnaliseValores> historico = dados.getHistorico();
        System.out.println("Itens recebidos para o grafico de pizza: " + historico.size());
    }

    // --- GERA O RELATORIO PDF COM OS DADOS E O GRAFICO INFORMADO ---
    public static void gerarPdf(AnaliseCronologica dados, Path graficoPath) throws IOException {
        RelatorioPDF pdf = new RelatorioPDF();
        pdf.gerar(dados, graficoPath, Path.of("Relatorio_Final.pdf"));
    }

    // --- EXECUTA A ANALISE, GERA O GRAFICO E CRIA O RELATORIO FINAL ---
    public static void main(String[] args) {
        Path caminhoPdf = Path.of("C:", "VSCode", "GitHub", "IA", "leitor-de-documentos-ia", "teste_pizza.pdf");

        try {
            AnaliseCronologica dados = analise(caminhoPdf);
            Path grafico = Path.of("Grafico_Pizza.png");
            graficoPizza(dados, grafico);
            gerarPdf(dados, grafico);
        } catch (IOException | IllegalStateException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
