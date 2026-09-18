package math.estatistica.regressaolinear;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LeitorCsvRegressao {

    // --- LÊ OS DADOS DE REGRESSÃO DE UM ARQUIVO CSV ---
    public DadosRegressao ler(String caminhoArquivo, String nomeVariavelDependente) throws IOException {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo não pode estar vazio.");
        }

        if (nomeVariavelDependente == null || nomeVariavelDependente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da variável dependente não pode estar vazio.");
        }

        Path caminho = Path.of(caminhoArquivo.trim());

        if (!Files.isRegularFile(caminho)) {
            throw new IOException("O arquivo CSV informado não existe.");
        }

        try (BufferedReader leitor = Files.newBufferedReader(caminho, StandardCharsets.UTF_8)) {
            String primeiraLinha = leitor.readLine();

            if (primeiraLinha == null || primeiraLinha.isBlank()) {
                throw new IOException("O arquivo CSV está vazio.");
            }

            primeiraLinha = removerMarcadorUtf8(primeiraLinha);
            char delimitador = detectarDelimitador(primeiraLinha);
            List<String> cabecalhos = separarLinha(primeiraLinha, delimitador);
            int indiceDependente = localizarColuna(cabecalhos, nomeVariavelDependente);

            if (indiceDependente < 0) {
                throw new IllegalArgumentException("A variável dependente não foi encontrada no cabeçalho.");
            }

            String[] nomesIndependentes = criarNomesIndependentes(cabecalhos, indiceDependente);
            List<double[]> linhasIndependentes = new ArrayList<>();
            List<Double> valoresDependentes = new ArrayList<>();
            String linha;
            int numeroLinha = 1;

            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;

                if (linha.isBlank()) {
                    continue;
                }

                List<String> valores = separarLinha(linha, delimitador);

                if (valores.size() != cabecalhos.size()) {
                    throw new IOException("A linha " + numeroLinha + " possui uma quantidade incorreta de colunas.");
                }

                double[] variaveis = new double[nomesIndependentes.length];
                int indiceVariavel = 0;

                for (int coluna = 0; coluna < valores.size(); coluna++) {
                    double valor = converterNumero(valores.get(coluna), numeroLinha, cabecalhos.get(coluna));

                    if (coluna == indiceDependente) {
                        valoresDependentes.add(valor);
                    } else {
                        variaveis[indiceVariavel++] = valor;
                    }
                }

                linhasIndependentes.add(variaveis);
            }

            if (linhasIndependentes.isEmpty()) {
                throw new IOException("O arquivo não possui observações.");
            }

            double[][] matriz = linhasIndependentes.toArray(double[][]::new);
            double[] vetorDependente = valoresDependentes.stream().mapToDouble(Double::doubleValue).toArray();

            return new DadosRegressao(matriz, vetorDependente, nomesIndependentes);
        }
    }

    // --- DETECTA O DELIMITADOR UTILIZADO NO ARQUIVO ---
    private char detectarDelimitador(String linha) {
        char[] delimitadores = {';', ',', '\t', '|'};
        char melhorDelimitador = ';';
        int maiorQuantidade = -1;

        for (char delimitador : delimitadores) {
            int quantidade = contarDelimitadores(linha, delimitador);

            if (quantidade > maiorQuantidade) {
                maiorQuantidade = quantidade;
                melhorDelimitador = delimitador;
            }
        }

        if (maiorQuantidade <= 0) {
            throw new IllegalArgumentException("Não foi possível identificar o delimitador do arquivo.");
        }

        return melhorDelimitador;
    }

    // --- CONTA DELIMITADORES FORA DE CAMPOS ENTRE ASPAS ---
    private int contarDelimitadores(String linha, char delimitador) {
        int quantidade = 0;
        boolean dentroDeAspas = false;

        for (int indice = 0; indice < linha.length(); indice++) {
            char caractere = linha.charAt(indice);

            if (caractere == '"') {
                dentroDeAspas = !dentroDeAspas;
            } else if (caractere == delimitador && !dentroDeAspas) {
                quantidade++;
            }
        }

        return quantidade;
    }

    // --- SEPARA UMA LINHA RESPEITANDO CAMPOS ENTRE ASPAS ---
    private List<String> separarLinha(String linha, char delimitador) {
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
            throw new IllegalArgumentException("Existe um campo com aspas não finalizadas.");
        }

        valores.add(valorAtual.toString().trim());
        return valores;
    }

    // --- LOCALIZA A COLUNA DEPENDENTE ---
    private int localizarColuna(List<String> cabecalhos, String nome) {
        for (int indice = 0; indice < cabecalhos.size(); indice++) {
            if (cabecalhos.get(indice).trim().equalsIgnoreCase(nome.trim())) {
                return indice;
            }
        }

        return -1;
    }

    // --- CRIA OS NOMES DAS VARIÁVEIS INDEPENDENTES ---
    private String[] criarNomesIndependentes(List<String> cabecalhos, int indiceDependente) {
        String[] nomes = new String[cabecalhos.size() - 1];
        int indiceDestino = 0;

        for (int indice = 0; indice < cabecalhos.size(); indice++) {
            if (indice != indiceDependente) {
                nomes[indiceDestino++] = cabecalhos.get(indice).trim();
            }
        }

        return nomes;
    }

    // --- CONVERTE UM TEXTO PARA NÚMERO ---
    private double converterNumero(String texto, int numeroLinha, String nomeColuna) throws IOException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IOException("A linha " + numeroLinha + " possui um valor vazio na coluna " + nomeColuna + ".");
        }

        String valorNormalizado = normalizarNumero(texto.trim());

        try {
            double valor = Double.parseDouble(valorNormalizado);

            if (!Double.isFinite(valor)) {
                throw new NumberFormatException();
            }

            return valor;
        } catch (NumberFormatException excecao) {
            throw new IOException("O valor da linha " + numeroLinha + " e coluna " + nomeColuna + " não é numérico.");
        }
    }

    // --- NORMALIZA PONTO E VÍRGULA DECIMAL ---
    private String normalizarNumero(String texto) {
        String valor = texto.replace(" ", "");

        if (valor.contains(",") && valor.contains(".")) {
            if (valor.lastIndexOf(',') > valor.lastIndexOf('.')) {
                return valor.replace(".", "").replace(",", ".");
            }

            return valor.replace(",", "");
        }

        return valor.replace(",", ".");
    }

    // --- REMOVE O MARCADOR UTF-8 ---
    private String removerMarcadorUtf8(String linha) {
        return linha.startsWith("\uFEFF") ? linha.substring(1) : linha;
    }
}

