package games.puzzle.nonogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabuleiroNonogram {

    private final boolean[][]           solucao;
    private final EstadoCelula[][]      tabuleiroJogador;
    private final List<List<Integer>>   dicasLinhas;
    private final List<List<Integer>>   dicasColunas;

    public TabuleiroNonogram(boolean[][] solucao) {
        validarSolucao(solucao);

        this.solucao = copiarSolucao(solucao);
        this.tabuleiroJogador = new EstadoCelula[getQuantidadeLinhas()][getQuantidadeColunas()];

        inicializarTabuleiroJogador();

        this.dicasLinhas = calcularDicasLinhas();
        this.dicasColunas = calcularDicasColunas();
    }

    // --- INICIALIZA TODAS AS CÉLULAS COMO DESCONHECIDAS ---
    private void inicializarTabuleiroJogador() {
        for (EstadoCelula[] linha : tabuleiroJogador) {
            Arrays.fill(linha, EstadoCelula.DESCONHECIDA);
        }
    }

    // --- ALTERA O ESTADO DE UMA CÉLULA ---
    public void definirEstadoCelula(int linha, int coluna, EstadoCelula estado) {
        validarPosicao(linha, coluna);

        if (estado == null) {
            throw new IllegalArgumentException("O estado da célula não pode ser nulo.");
        }

        tabuleiroJogador[linha][coluna] = estado;
    }

    // --- ALTERNA A CÉLULA ENTRE DESCONHECIDA, PREENCHIDA E VAZIA ---
    public EstadoCelula alternarCelula(int linha, int coluna) {
        validarPosicao(linha, coluna);

        EstadoCelula estadoAtual = tabuleiroJogador[linha][coluna];
        EstadoCelula proximoEstado;

        switch (estadoAtual) {
            case DESCONHECIDA:
                proximoEstado = EstadoCelula.PREENCHIDA;
                break;
            case PREENCHIDA:
                proximoEstado = EstadoCelula.VAZIA;
                break;
            case VAZIA:
                proximoEstado = EstadoCelula.DESCONHECIDA;
                break;
            default:
                throw new IllegalStateException("Estado de célula desconhecido.");
        }

        tabuleiroJogador[linha][coluna] = proximoEstado;
        return proximoEstado;
    }

    // --- LIMPA TODAS AS MARCAÇÕES DO JOGADOR ---
    public void limparTabuleiroJogador() {
        inicializarTabuleiroJogador();
    }

    // --- CALCULA AS DICAS DE TODAS AS LINHAS ---
    private List<List<Integer>> calcularDicasLinhas() {
        List<List<Integer>> dicas = new ArrayList<>();

        for (boolean[] linha : solucao) {
            dicas.add(calcularDicas(linha));
        }

        return Collections.unmodifiableList(dicas);
    }

    // --- CALCULA AS DICAS DE TODAS AS COLUNAS ---
    private List<List<Integer>> calcularDicasColunas() {
        List<List<Integer>> dicas = new ArrayList<>();

        for (int coluna = 0; coluna < getQuantidadeColunas(); coluna++) {
            boolean[] valoresColuna = new boolean[getQuantidadeLinhas()];

            for (int linha = 0; linha < getQuantidadeLinhas(); linha++) {
                valoresColuna[linha] = solucao[linha][coluna];
            }

            dicas.add(calcularDicas(valoresColuna));
        }

        return Collections.unmodifiableList(dicas);
    }

    // --- CALCULA AS SEQUÊNCIAS DE CÉLULAS PREENCHIDAS ---
    private List<Integer> calcularDicas(boolean[] valores) {
        List<Integer> dicas = new ArrayList<>();
        int tamanhoSequencia = 0;

        for (boolean valor : valores) {
            if (valor) {
                tamanhoSequencia++;
            } else if (tamanhoSequencia > 0) {
                dicas.add(tamanhoSequencia);
                tamanhoSequencia = 0;
            }
        }

        if (tamanhoSequencia > 0) {
            dicas.add(tamanhoSequencia);
        }

        if (dicas.isEmpty()) {
            dicas.add(0);
        }

        return Collections.unmodifiableList(dicas);
    }

    // --- RETORNA UMA CÓPIA DO ESTADO ATUAL DO TABULEIRO DO JOGADOR ---
    public EstadoCelula[][] getCopiaTabuleiroJogador() {
        EstadoCelula[][] copia = new EstadoCelula[getQuantidadeLinhas()][getQuantidadeColunas()];

        for (int linha = 0; linha < getQuantidadeLinhas(); linha++) {
            System.arraycopy(tabuleiroJogador[linha], 0, copia[linha], 0, getQuantidadeColunas());
        }

        return copia;
    }

    // --- RETORNA UMA CÓPIA DA SOLUÇÃO ---
    public boolean[][] getCopiaSolucao() {
        return copiarSolucao(solucao);
    }

    // --- RETORNA O ESTADO DE UMA CÉLULA ---
    public EstadoCelula getEstadoCelula(int linha, int coluna) {
        validarPosicao(linha, coluna);
        return tabuleiroJogador[linha][coluna];
    }

    // --- INFORMA SE UMA CÉLULA FAZ PARTE DA SOLUÇÃO ---
    public boolean isCelulaSolucaoPreenchida(int linha, int coluna) {
        validarPosicao(linha, coluna);
        return solucao[linha][coluna];
    }

    // --- RETORNA AS DICAS DAS LINHAS ---
    public List<List<Integer>> getDicasLinhas() {
        return dicasLinhas;
    }

    // --- RETORNA AS DICAS DAS COLUNAS ---
    public List<List<Integer>> getDicasColunas() {
        return dicasColunas;
    }

    public int getQuantidadeLinhas() {
        return solucao.length;
    }

    public int getQuantidadeColunas() {
        return solucao[0].length;
    }

    // --- VERIFICA SE A POSIÇÃO EXISTE NO TABULEIRO ---
    private void validarPosicao(int linha, int coluna) {
        if (linha < 0 || linha >= getQuantidadeLinhas()) {
            throw new IndexOutOfBoundsException("Linha inválida: " + linha);
        }

        if (coluna < 0 || coluna >= getQuantidadeColunas()) {
            throw new IndexOutOfBoundsException("Coluna inválida: " + coluna);
        }
    }

    // --- VALIDA A MATRIZ RECEBIDA COMO SOLUÇÃO ---
    private void validarSolucao(boolean[][] solucao) {
        if (solucao == null || solucao.length == 0) {
            throw new IllegalArgumentException("A solução deve possuir pelo menos uma linha.");
        }

        if (solucao[0] == null || solucao[0].length == 0) {
            throw new IllegalArgumentException("A solução deve possuir pelo menos uma coluna.");
        }

        int quantidadeColunas = solucao[0].length;

        for (boolean[] linha : solucao) {
            if (linha == null || linha.length != quantidadeColunas) {
                throw new IllegalArgumentException("Todas as linhas devem possuir o mesmo tamanho.");
            }
        }
    }

    // --- CRIA UMA CÓPIA DEFENSIVA DA SOLUÇÃO ---
    private boolean[][] copiarSolucao(boolean[][] original) {
        boolean[][] copia = new boolean[original.length][];

        for (int linha = 0; linha < original.length; linha++) {
            copia[linha] = Arrays.copyOf(original[linha], original[linha].length);
        }

        return copia;
    }
}

