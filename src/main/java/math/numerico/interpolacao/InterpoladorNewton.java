package math.numerico.interpolacao;

public class InterpoladorNewton {

    // --- CONSTROI A TABELA DE DIFERENCAS DIVIDIDAS ---
    public double[][] construirTabela(double[] x, double[] y) {
        validarEntrada(x, y);
        int n = x.length;
        double[][] tabela = new double[n][n];

        for (int i = 0; i < n; i++) {
            tabela[i][0] = y[i];
        }

        for (int coluna = 1; coluna < n; coluna++) {
            for (int linha = 0; linha < n - coluna; linha++) {
                double denominador = x[linha + coluna] - x[linha];
                if (Math.abs(denominador) < 1.0E-12) {
                    throw new IllegalArgumentException("Os valores de x devem ser distintos.");
                }
                tabela[linha][coluna] = (tabela[linha + 1][coluna - 1] - tabela[linha][coluna - 1]) / denominador;
            }
        }

        return tabela;
    }

    // --- AVALIA O POLINOMIO INTERPOLADOR NO PONTO INFORMADO ---
    public double avaliar(double[] x, double[] y, double ponto) {
        double[][] tabela = construirTabela(x, y);
        int n = x.length;
        double resultado = tabela[0][0];
        double produto = 1.0;

        for (int i = 1; i < n; i++) {
            produto *= (ponto - x[i - 1]);
            resultado += tabela[0][i] * produto;
        }

        return resultado;
    }

    // --- VALIDA A ENTRADA DOS PONTOS ---
    private void validarEntrada(double[] x, double[] y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Os vetores x e y devem ser informados.");
        }
        if (x.length == 0 || x.length != y.length) {
            throw new IllegalArgumentException("Os vetores x e y devem ter o mesmo tamanho e nao podem ser vazios.");
        }
    }
}
