package math.numerico.sistemaslineares;

public class SolucionadorGauss {

    // --- RESOLVE O SISTEMA LINEAR A * x = b POR ELIMINACAO DE GAUSS COM PIVOTEAMENTO ---
    public ResultadoSistemaLinear resolver(double[][] matrizA, double[] vetorB) {
        validarEntrada(matrizA, vetorB);

        int n = vetorB.length;
        double[][] a = copiarMatriz(matrizA);
        double[] b = vetorB.clone();
        int trocas = 0;

        for (int coluna = 0; coluna < n; coluna++) {
            int linhaPivo = coluna;
            double maior = Math.abs(a[coluna][coluna]);

            for (int linha = coluna + 1; linha < n; linha++) {
                double valor = Math.abs(a[linha][coluna]);
                if (valor > maior) {
                    maior = valor;
                    linhaPivo = linha;
                }
            }

            if (Math.abs(a[linhaPivo][coluna]) < 1.0E-12) {
                throw new IllegalArgumentException("O sistema possui matriz singular ou quase singular.");
            }

            if (linhaPivo != coluna) {
                trocarLinhas(a, coluna, linhaPivo);
                double tmp = b[coluna];
                b[coluna] = b[linhaPivo];
                b[linhaPivo] = tmp;
                trocas++;
            }

            for (int linha = coluna + 1; linha < n; linha++) {
                double fator = a[linha][coluna] / a[coluna][coluna];
                a[linha][coluna] = 0;

                for (int j = coluna + 1; j < n; j++) {
                    a[linha][j] -= fator * a[coluna][j];
                }
                b[linha] -= fator * b[coluna];
            }
        }

        double[] x = retrosubstituicao(a, b);
        double determinante = calcularDeterminanteTriangular(a, trocas);
        return new ResultadoSistemaLinear(x, determinante);
    }

    private double[] retrosubstituicao(double[][] a, double[] b) {
        int n = b.length;
        double[] x = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            double soma = b[i];
            for (int j = i + 1; j < n; j++) {
                soma -= a[i][j] * x[j];
            }
            x[i] = soma / a[i][i];
        }
        return x;
    }

    private double calcularDeterminanteTriangular(double[][] a, int trocas) {
        double det = trocas % 2 == 0 ? 1.0 : -1.0;
        for (int i = 0; i < a.length; i++) {
            det *= a[i][i];
        }
        return det;
    }

    private double[][] copiarMatriz(double[][] original) {
        double[][] copia = new double[original.length][];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i].clone();
        }
        return copia;
    }

    private void trocarLinhas(double[][] matriz, int linhaA, int linhaB) {
        double[] tmp = matriz[linhaA];
        matriz[linhaA] = matriz[linhaB];
        matriz[linhaB] = tmp;
    }

    // --- VALIDA MATRIZ E VETOR ---
    private void validarEntrada(double[][] matrizA, double[] vetorB) {
        if (matrizA == null || vetorB == null) {
            throw new IllegalArgumentException("A matriz A e o vetor b devem ser informados.");
        }

        if (matrizA.length == 0 || matrizA.length != vetorB.length) {
            throw new IllegalArgumentException("A matriz A deve ser quadrada e compatível com o vetor b.");
        }
        int n = matrizA.length;
        for (int i = 0; i < n; i++) {
            if (matrizA[i] == null || matrizA[i].length != n) {
                throw new IllegalArgumentException("A matriz A deve ser quadrada.");
            }
        }
    }
}
