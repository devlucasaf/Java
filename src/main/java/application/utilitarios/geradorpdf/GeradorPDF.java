package application.utilitarios.geradorpdf;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GeradorPDF {

    private final List<String> linhas = new ArrayList<>();
    private String titulo = "Relatorio";

    public GeradorPDF titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public GeradorPDF adicionar(String linha) {
        linhas.add(linha);
        return this;
    }

    public GeradorPDF pular() {
        linhas.add("");
        return this;
    }

    public GeradorPDF cabecalho(String texto) {
        linhas.add(">>> " + texto);
        return this;
    }

    public void salvar(Path arquivo) throws IOException {
        try (OutputStream out = Files.newOutputStream(arquivo)) {
            escreverPDF(out);
        }
    }

    private void escreverPDF(OutputStream out) throws IOException {
        List<Integer> offsets = new ArrayList<>();
        StringBuilder conteudo = new StringBuilder();
        conteudo.append("BT\n/F1 18 Tf 50 780 Td (").append(escapar(titulo)).append(") Tj\n");
        conteudo.append("/F1 11 Tf 0 -30 Td\n");
        for (String linha : linhas) {
            String texto = linha.isEmpty() ? " " : escapar(linha);
            conteudo.append("(").append(texto).append(") Tj 0 -14 Td\n");
        }
        conteudo.append("ET\n");

        StringBuilder pdf = new StringBuilder();
        pdf.append("%PDF-1.4\n%\u00E2\u00E3\u00CF\u00D3\n");

        offsets.add(pdf.length());
        pdf.append("1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");

        offsets.add(pdf.length());
        pdf.append("2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");

        offsets.add(pdf.length());
        pdf.append("3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] ")
                .append("/Contents 4 0 R /Resources << /Font << /F1 5 0 R >> >> >>\nendobj\n");

        offsets.add(pdf.length());
        pdf.append("4 0 obj\n<< /Length ").append(conteudo.length())
                .append(" >>\nstream\n").append(conteudo).append("endstream\nendobj\n");

        offsets.add(pdf.length());
        pdf.append("5 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\nendobj\n");

        int xrefPos = pdf.length();
        pdf.append("xref\n0 6\n0000000000 65535 f \n");

        for (int off : offsets) {
            pdf.append(String.format("%010d 00000 n %n", off));
        }

        pdf.append("trailer\n<< /Size 6 /Root 1 0 R >>\nstartxref\n")
                .append(xrefPos).append("\n%%EOF\n");

        out.write(pdf.toString().getBytes(StandardCharsets.ISO_8859_1));
    }

    private static String escapar(String s) {
        return s.replace("\\", "\\\\")
                .replace("(", "\\(")
                .replace(")", "\\)");
    }
}

