package application.utilitarios.markdown;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServicoMarkdown {

    private final Parser        parser;
    private final HtmlRenderer  renderizadorHtml;

    public ServicoMarkdown() {
        this.parser = Parser.builder().build();
        this.renderizadorHtml = HtmlRenderer.builder().build();
    }

    // --- CONVERTE MARKDOWN PARA HTML ---
    public String converterParaHtml(String markdown) {
        String conteudoMarkdown = markdown == null ? "" : markdown;
        Node documento = parser.parse(conteudoMarkdown);
        return renderizadorHtml.render(documento);
    }

    // --- MONTA O HTML COMPLETO COM ESTILOS BÁSICOS ---
    public String criarDocumentoHtml(String markdown) {
        String htmlCorpo = converterParaHtml(markdown);

        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <title>Preview Markdown</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            margin: 24px;
                            line-height: 1.6;
                            color: #1f2937;
                        }
                        
                        pre {
                            background: #f3f4f6;
                            padding: 12px;
                            border-radius: 6px;
                            overflow-x: auto;
                        }
                        
                        code {
                            font-family: Consolas, monospace;
                        }
                        
                        blockquote {
                            border-left: 4px solid #d1d5db;
                            margin: 0;
                            padding-left: 12px;
                            color: #4b5563;
                        }
                        
                        table {
                            border-collapse: collapse;
                            width: 100%;
                        }
                        
                        th, 
                        td {
                            border: 1px solid #d1d5db;
                            padding: 8px;
                            text-align: left;
                        }
                    </style>
                </head>
                <body>
                """ + htmlCorpo + """
                </body>
                </html>
                """;
    }

    // --- EXPORTA O MARKDOWN CONVERTIDO PARA HTML ---
    public void exportarHtml(String markdown, Path arquivoDestino) throws IOException {
        validarArquivoDestino(arquivoDestino);
        criarDiretorioPaiSeNecessario(arquivoDestino);

        String documento = criarDocumentoHtml(markdown);
        Files.writeString(arquivoDestino, documento, StandardCharsets.UTF_8);
    }

    // --- EXPORTA O MARKDOWN CONVERTIDO PARA PDF ---
    public void exportarPdf(String markdown, Path arquivoDestino) throws IOException {
        validarArquivoDestino(arquivoDestino);
        criarDiretorioPaiSeNecessario(arquivoDestino);

        String documento = criarDocumentoHtml(markdown);
        String uriBase = obterUriBase(arquivoDestino);

        try (OutputStream saida = Files.newOutputStream(arquivoDestino)) {
            PdfRendererBuilder construtor = new PdfRendererBuilder();
            construtor.useFastMode();
            construtor.withHtmlContent(documento, uriBase);
            construtor.toStream(saida);
            construtor.run();
        } catch (Exception excecao) {
            throw new IOException("Não foi possível exportar o arquivo PDF.", excecao);
        }
    }

    // --- VALIDA O CAMINHO DE DESTINO ---
    private void validarArquivoDestino(Path arquivoDestino) {
        if (arquivoDestino == null) {
            throw new IllegalArgumentException("O arquivo de destino não pode ser nulo.");
        }
    }

    // --- CRIA O DIRETÓRIO PAI, SE NECESSÁRIO ---
    private void criarDiretorioPaiSeNecessario(Path arquivoDestino) throws IOException {
        Path diretorioPai = arquivoDestino.toAbsolutePath().getParent();

        if (diretorioPai != null) {
            Files.createDirectories(diretorioPai);
        }
    }

    // --- MONTA A URI BASE PARA RENDERIZAÇÃO ---
    private String obterUriBase(Path arquivoDestino) {
        Path diretorioPai = arquivoDestino.toAbsolutePath().getParent();

        if (diretorioPai == null) {
            return Path.of(".").toAbsolutePath().normalize().toUri().toString();
        }

        return diretorioPai.toUri().toString();
    }
}
