package math.estatistica.regressaolinear;

import java.util.Arrays;

public class DadosRegressao {

    private final double[][]    variaveisIndependentes;
    private final double[]      variavelDependente;
    private final String[]      nomesVariaveis;

    public DadosRegressao(double[][] variaveisIndependentes, double[] variavelDependente, String[] nomesVariaveis) {
        validarDados(variaveisIndependentes, variavelDependente, nomesVariaveis);

        this.variaveisIndependentes = copiarMatriz(variaveisIndependentes);
        this.variavelDependente = Arrays.copyOf(variavelDependente, variavelDependente.length);
        this.nomesVariaveis = Arrays.copyOf(nomesVariaveis, nomesVariaveis.length);
    }

    // --- VALIDA OS DADOS UTILIZADOS NA REGRESSÃO ---
    private void validarDados(double[][] variaveisIndependentes, double[] variavelDependente, String[] nomesVariaveis) {
        if (variaveisIndependentes == null || variaveisIndependentes.length == 0) {
            throw new IllegalArgumentException("A matriz de variáveis independentes deve possuir pelo menos uma observação.");
        }

        if (variavelDependente == null || variavelDependente.length != variaveisIndependentes.length) {
            throw new IllegalArgumentException("A variável dependente deve possuir a mesma quantidade de observações da matriz.");
        }

        if (variaveisIndependentes[0] == null || variaveisIndependentes[0].length == 0) {
            throw new IllegalArgumentException("A matriz deve possuir pelo menos uma variável independente.");
        }

        int quantidadeVariaveis = variaveisIndependentes[0].length;

        if (nomesVariaveis == null || nomesVariaveis.length != quantidadeVariaveis) {
            throw new IllegalArgumentException("Deve ser informado um nome para cada variável independente.");
        }

        for (int linha = 0; linha < variaveisIndependentes.length; linha++) {
            if (variaveisIndependentes[linha] == null || variaveisIndependentes[linha].length != quantidadeVariaveis) {
                throw new IllegalArgumentException("Todas as observações devem possuir a mesma quantidade de variáveis.");
            }

            for (double valor : variaveisIndependentes[linha]) {
                if (!Double.isFinite(valor)) {
                    throw new IllegalArgumentException("As variáveis independentes devem possuir apenas valores finitos.");
                }
            }

            if (!Double.isFinite(variavelDependente[linha])) {
                throw new IllegalArgumentException("A variável dependente deve possuir apenas valores finitos.");
            }
        }

        for (String nome : nomesVariaveis) {
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("Os nomes das variáveis não podem estar vazios.");
            }
        }
    }

    // --- CRIA UMA CÓPIA DEFENSIVA DA MATRIZ ---
    private double[][] copiarMatriz(double[][] matriz) {
        double[][] copia = new double[matriz.length][];

        for (int linha = 0; linha < matriz.length; linha++) {
            copia[linha] = Arrays.copyOf(matriz[linha], matriz[linha].length);
        }

        return copia;
    }

    public double[][] getVariaveisIndependentes() {
        return copiarMatriz(variaveisIndependentes);
    }

    public double[] getVariavelDependente() {
        return Arrays.copyOf(variavelDependente, variavelDependente.length);
    }

    public String[] getNomesVariaveis() {
        return Arrays.copyOf(nomesVariaveis, nomesVariaveis.length);
    }

    public int getQuantidadeObservacoes() {
        return variaveisIndependentes.length;
    }

    public int getQuantidadeVariaveis() {
        return variaveisIndependentes[0].length;
    }
}

