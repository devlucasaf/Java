package math.probabilidade.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LeitorCsv {

    // --- LÊ UM ARQUIVO CSV E CRIA UMA TABELA ---
    public TabelaCsv ler(String caminhoArquivo) throws IOException {
        return ler(Path.of(validarCaminho(caminhoArquivo)));
    }

    // --- LÊ UM ARQUIVO CSV E CRIA UMA TABELA ---
    public TabelaCsv ler(Path caminhoArquivo) throws IOException {
        validarArquivo(caminhoArquivo);

        try (BufferedReader leitor = Files.newBufferedReader(caminhoArquivo, StandardCharsets.UTF_8)) {
            String primeiraLinha = leitor.readLine();

            if (primeiraLinha == null || primeiraLinha.trim().isEmpty()) {
                throw new IOException("O arquivo CSV está vazio.");
            }

            primeiraLinha = removerMarcadorUtf8(primeiraLinha);

            char delimitador = detectarDelimitador(primeiraLinha);
            List<String> cabecalhos = separarLinha(primeiraLinha, delimitador);
            TabelaCsv tabela = new TabelaCsv(cabecalhos, delimitador);

            String linha;

            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    tabela.adicionarRegistro(separarLinha(linha, delimitador));
                }
            }

            return tabela;
        }
    }

    // --- DETECTA AUTOMATICAMENTE O DELIMITADOR DO CSV ---
    public char detectarDelimitador(String linha) {
        char[] delimitadores = {',', ';', '\t', '|'};
        char melhorDelimitador = ',';
        int maiorQuantidade = -1;

        for (char delimitador : delimitadores) {
            int quantidade = contarDelimitadoresForaDeAspas(linha, delimitador);

            if (quantidade > maiorQuantidade) {
                maiorQuantidade = quantidade;
                melhorDelimitador = delimitador;
            }
        }

        if (maiorQuantidade <= 0) {
            throw new IllegalArgumentException("Não foi possível identificar o delimitador do arquivo CSV.");
        }

        return melhorDelimitador;
    }

    // --- SEPARA UMA LINHA RESPEITANDO CAMPOS ENTRE ASPAS ---
    public List<String> separarLinha(String linha, char delimitador) {
        List<String> valores = new ArrayList<>();
        StringBuilder valorAtual = new StringBuilder();
        boolean dentroDeAspas = false;

        for (int indice = 0; indice < linha.length(); indice++) {
            char caractere = linha.charAt(indice);

            if (caractere == '"') {
                if (dentroDeAspas && indice + 1 < linha.length() && linha.charAt(indice + 1) == '"') {
                    valorAtual.append('"');
                    indice++;
                } else {
                    dentroDeAspas = !dentroDeAspas;
                }
            } else if (caractere == delimitador && !dentroDeAspas) {
                valores.add(valorAtual.toString().trim());
                valorAtual.setLength(0);
            } else {
                valorAtual.append(caractere);
            }
        }

        if (dentroDeAspas) {
            throw new IllegalArgumentException("A linha CSV possui aspas não finalizadas.");
        }

        valores.add(valorAtual.toString().trim());
        return valores;
    }

    // --- CONTA DELIMITADORES QUE NÃO ESTÃO ENTRE ASPAS ---
    private int contarDelimitadoresForaDeAspas(String linha, char delimitador) {
        int quantidade = 0;
        boolean dentroDeAspas = false;

        for (int indice = 0; indice < linha.length(); indice++) {
            char caractere = linha.charAt(indice);

            if (caractere == '"') {
                if (dentroDeAspas && indice + 1 < linha.length() && linha.charAt(indice + 1) == '"') {
                    indice++;
                } else {
                    dentroDeAspas = !dentroDeAspas;
                }
            } else if (caractere == delimitador && !dentroDeAspas) {
                quantidade++;
            }
        }

        return quantidade;
    }

    // --- REMOVE O MARCADOR UTF-8 DO INÍCIO DO ARQUIVO ---
    private String removerMarcadorUtf8(String linha) {
        return linha.startsWith("\uFEFF") ? linha.substring(1) : linha;
    }

    // --- VALIDA O ARQUIVO INFORMADO ---
    private void validarArquivo(Path caminhoArquivo) throws IOException {
        if (caminhoArquivo == null) {
            throw new IllegalArgumentException("O caminho do arquivo não pode ser nulo.");
        }

        if (!Files.exists(caminhoArquivo)) {
            throw new IOException("O arquivo informado não existe.");
        }

        if (!Files.isRegularFile(caminhoArquivo)) {
            throw new IOException("O caminho informado não representa um arquivo.");
        }

        if (!Files.isReadable(caminhoArquivo)) {
            throw new IOException("O arquivo informado não pode ser lido.");
        }
    }

    // --- VALIDA O CAMINHO RECEBIDO COMO TEXTO ---
    private String validarCaminho(String caminhoArquivo) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo não pode estar vazio.");
        }

        return caminhoArquivo.trim();
    }
}