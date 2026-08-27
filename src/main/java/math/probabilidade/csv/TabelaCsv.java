package math.probabilidade.csv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabelaCsv {

    private final List<ColunaCsv>   colunas;
    private final char              delimitador;
    private int                     quantidadeRegistros;

    public TabelaCsv(List<String> cabecalhos, char delimitador) {
        if (cabecalhos == null || cabecalhos.isEmpty()) {
            throw new IllegalArgumentException("O arquivo CSV deve possuir cabeçalhos.");
        }

        this.colunas = new ArrayList<>();
        this.delimitador = delimitador;
        this.quantidadeRegistros = 0;

        for (int indice = 0; indice < cabecalhos.size(); indice++) {
            String cabecalho = cabecalhos.get(indice);
            String nomeColuna = cabecalho == null || cabecalho.trim().isEmpty() ? "Coluna " + (indice + 1) : cabecalho.trim();
            colunas.add(new ColunaCsv(criarNomeUnico(nomeColuna)));
        }
    }

    // --- ADICIONA UM REGISTRO À TABELA ---
    public void adicionarRegistro(List<String> valores) {
        for (int indice = 0; indice < colunas.size(); indice++) {
            String valor = valores != null && indice < valores.size() ? valores.get(indice) : "";
            colunas.get(indice).adicionarValor(valor);
        }

        quantidadeRegistros++;
    }

    // --- RETORNA AS COLUNAS NUMÉRICAS ---
    public List<ColunaCsv> getColunasNumericas() {
        List<ColunaCsv> colunasNumericas = new ArrayList<>();

        for (ColunaCsv coluna : colunas) {
            if (coluna.isNumerica()) {
                colunasNumericas.add(coluna);
            }
        }

        return colunasNumericas;
    }

    // --- BUSCA UMA COLUNA PELO NOME ---
    public ColunaCsv buscarColuna(String nome) {
        if (nome == null) {
            return null;
        }

        for (ColunaCsv coluna : colunas) {
            if (coluna.getNome().equalsIgnoreCase(nome.trim())) {
                return coluna;
            }
        }

        return null;
    }

    // --- CRIA UM NOME ÚNICO PARA A COLUNA ---
    private String criarNomeUnico(String nomeOriginal) {
        String nome = nomeOriginal;
        int sufixo = 2;

        while (nomeJaExiste(nome)) {
            nome = nomeOriginal + " " + sufixo;
            sufixo++;
        }

        return nome;
    }

    // --- VERIFICA SE O NOME DE UMA COLUNA JÁ EXISTE ---
    private boolean nomeJaExiste(String nome) {
        for (ColunaCsv coluna : colunas) {
            if (coluna.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }

        return false;
    }

    public List<ColunaCsv> getColunas() {
        return Collections.unmodifiableList(colunas);
    }

    public int getQuantidadeColunas() {
        return colunas.size();
    }

    public int getQuantidadeRegistros() {
        return quantidadeRegistros;
    }

    public char getDelimitador() {
        return delimitador;
    }
}

