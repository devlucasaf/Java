package math.calculadora.completa.model.calculos;

public class Matrizes {

    public static double[][] somar(double[][] a, double[][] b) {
        validarDimensoes(a, b);
        int linhas = a.length;
        int colunas = a[0].length;
        double[][] r = new double[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                r[i][j] = a[i][j] + b[i][j];
            }
        }
        return r;
    }

    public static double[][] subtrair(double[][] a, double[][] b) {
        validarDimensoes(a, b);
        int linhas = a.length;
        int colunas = a[0].length;
        double[][] r = new double[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                r[i][j] = a[i][j] - b[i][j];
            }
        }
        return r;
    }

    public static double[][] multiplicar(double[][] a, double[][] b) {
        if (a[0].length != b.length) {
            throw new IllegalArgumentException("Número de colunas de A deve ser igual ao número de linhas de B");
        }

        int linhasA = a.length;
        int colunasA = a[0].length;
        int colunasB = b[0].length;

        double[][] r = new double[linhasA][colunasB];
        for (int i = 0; i < linhasA; i++) {
            for (int j = 0; j < colunasB; j++) {
                double soma = 0;
                for (int k = 0; k < colunasA; k++) {
                    soma += a[i][k] * b[k][j];
                }
                r[i][j] = soma;
            }
        }
        return r;
    }

    public static double determinante(double[][] matriz) {
        int qntdLinhas = matriz.length;
        if (qntdLinhas != matriz[0].length) {
            throw new IllegalArgumentException("Matriz deve ser quadrada");
        }

        if (qntdLinhas == 2) {
            return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
        } else if (qntdLinhas == 3) {
            return matriz[0][0] * (matriz[1][1] * matriz[2][2] - matriz[1][2] * matriz[2][1])
                    - matriz[0][1] * (matriz[1][0] * matriz[2][2] - matriz[1][2] * matriz[2][0])
                    + matriz[0][2] * (matriz[1][0] * matriz[2][1] - matriz[1][1] * matriz[2][0]);
        } else {
            throw new IllegalArgumentException("Determinante suportado apenas para 2x2 e 3x3");
        }
    }

    private static void validarDimensoes(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrizes devem ter as mesmas dimensões");
        }
    }
}
