package math.estatistica.regressaolinear;

public class RegressaoLinearMultipla {

    private final ConfiguracaoRegressao configuracao;

    public RegressaoLinearMultipla() {
        this(new ConfiguracaoRegressao());
    }

    public RegressaoLinearMultipla(ConfiguracaoRegressao configuracao) {
        if (configuracao == null) {
            throw new IllegalArgumentException("A configuração da regressão não pode ser nula.");
        }

        this.configuracao = configuracao;
    }

    // --- TREINA O MODELO DE REGRESSÃO LINEAR MÚLTIPLA ---
    public ResultadoRegressao treinar(DadosRegressao dados) {
        if (dados == null) {
            throw new IllegalArgumentException("Os dados da regressão não podem ser nulos.");
        }

        int quantidadeParametros = dados.getQuantidadeVariaveis() + (configuracao.isIncluirIntercepto() ? 1 : 0);

        if (dados.getQuantidadeObservacoes() < quantidadeParametros) {
            throw new IllegalArgumentException("A quantidade de observações deve ser igual ou superior à quantidade de parâmetros.");
        }

        double[][] matrizProjeto = criarMatrizProjeto(dados.getVariaveisIndependentes());
        double[] valoresDependentes = dados.getVariavelDependente();
        double[] parametros = resolverPorDecomposicaoQr(matrizProjeto, valoresDependentes);

        int deslocamento = configuracao.isIncluirIntercepto() ? 1 : 0;
        double intercepto = configuracao.isIncluirIntercepto() ? parametros[0] : 0;
        double[] coeficientes = new double[dados.getQuantidadeVariaveis()];

        System.arraycopy(parametros, deslocamento, coeficientes, 0, coeficientes.length);

        double[] previsoes = calcularPrevisoes(matrizProjeto, parametros);

        return new ResultadoRegressao(configuracao.isIncluirIntercepto(), intercepto, coeficientes, dados.getNomesVariaveis(), valoresDependentes, previsoes);
    }

    // --- CRIA A MATRIZ DE PROJETO COM INTERCEPTO OPCIONAL ---
    private double[][] criarMatrizProjeto(double[][] variaveis) {
        int quantidadeLinhas = variaveis.length;
        int quantidadeColunas = variaveis[0].length + (configuracao.isIncluirIntercepto() ? 1 : 0);
        double[][] matriz = new double[quantidadeLinhas][quantidadeColunas];

        for (int linha = 0; linha < quantidadeLinhas; linha++) {
            int colunaDestino = 0;

            if (configuracao.isIncluirIntercepto()) {
                matriz[linha][colunaDestino++] = 1.0;
            }

            for (double valor : variaveis[linha]) {
                matriz[linha][colunaDestino++] = valor;
            }
        }

        return matriz;
    }

    // --- RESOLVE O PROBLEMA DE MÍNIMOS QUADRADOS POR DECOMPOSIÇÃO QR ---
    private double[] resolverPorDecomposicaoQr(double[][] matriz, double[] vetorDependente) {
        int quantidadeLinhas = matriz.length;
        int quantidadeColunas = matriz[0].length;
        double[][] matrizQ = new double[quantidadeLinhas][quantidadeColunas];
        double[][] matrizR = new double[quantidadeColunas][quantidadeColunas];

        for (int coluna = 0; coluna < quantidadeColunas; coluna++) {
            double[] vetor = obterColuna(matriz, coluna);

            for (int colunaAnterior = 0; colunaAnterior < coluna; colunaAnterior++) {
                double produtoInterno = calcularProdutoInternoColuna(matrizQ, colunaAnterior, vetor);
                matrizR[colunaAnterior][coluna] = produtoInterno;

                for (int linha = 0; linha < quantidadeLinhas; linha++) {
                    vetor[linha] -= produtoInterno * matrizQ[linha][colunaAnterior];
                }
            }

            double norma = calcularNorma(vetor);

            if (norma <= configuracao.getToleranciaSingularidade()) {
                throw new IllegalArgumentException("A matriz possui variáveis linearmente dependentes ou sem variação suficiente.");
            }

            matrizR[coluna][coluna] = norma;

            for (int linha = 0; linha < quantidadeLinhas; linha++) {
                matrizQ[linha][coluna] = vetor[linha] / norma;
            }
        }

        double[] produtoQTranspostoY = multiplicarQTranspostoPorVetor(matrizQ, vetorDependente);
        return resolverSistemaTriangularSuperior(matrizR, produtoQTranspostoY);
    }

    // --- OBTÉM UMA COLUNA DA MATRIZ ---
    private double[] obterColuna(double[][] matriz, int coluna) {
        double[] resultado = new double[matriz.length];

        for (int linha = 0; linha < matriz.length; linha++) {
            resultado[linha] = matriz[linha][coluna];
        }

        return resultado;
    }

    // --- CALCULA O PRODUTO INTERNO ENTRE UMA COLUNA E UM VETOR ---
    private double calcularProdutoInternoColuna(double[][] matrizQ, int coluna, double[] vetor) {
        double produto = 0;

        for (int linha = 0; linha < matrizQ.length; linha++) {
            produto += matrizQ[linha][coluna] * vetor[linha];
        }

        return produto;
    }

    // --- CALCULA A NORMA EUCLIDIANA DE UM VETOR ---
    private double calcularNorma(double[] vetor) {
        double escala = 0;
        double somaNormalizada = 1;

        for (double valor : vetor) {
            double valorAbsoluto = Math.abs(valor);

            if (valorAbsoluto != 0) {
                if (escala < valorAbsoluto) {
                    double proporcao = escala / valorAbsoluto;
                    somaNormalizada = 1 + somaNormalizada * proporcao * proporcao;
                    escala = valorAbsoluto;
                } else {
                    double proporcao = valorAbsoluto / escala;
                    somaNormalizada += proporcao * proporcao;
                }
            }
        }

        return escala == 0 ? 0 : escala * Math.sqrt(somaNormalizada);
    }

    // --- MULTIPLICA A MATRIZ Q TRANSPOSTA PELO VETOR DEPENDENTE ---
    private double[] multiplicarQTranspostoPorVetor(double[][] matrizQ, double[] vetor) {
        int quantidadeColunas = matrizQ[0].length;
        double[] resultado = new double[quantidadeColunas];

        for (int coluna = 0; coluna < quantidadeColunas; coluna++) {
            double soma = 0;

            for (int linha = 0; linha < matrizQ.length; linha++) {
                soma += matrizQ[linha][coluna] * vetor[linha];
            }

            resultado[coluna] = soma;
        }

        return resultado;
    }

    // --- RESOLVE UM SISTEMA TRIANGULAR SUPERIOR ---
    private double[] resolverSistemaTriangularSuperior(double[][] matrizR, double[] vetor) {
        int tamanho = vetor.length;
        double[] resultado = new double[tamanho];

        for (int linha = tamanho - 1; linha >= 0; linha--) {
            double soma = vetor[linha];

            for (int coluna = linha + 1; coluna < tamanho; coluna++) {
                soma -= matrizR[linha][coluna] * resultado[coluna];
            }

            if (Math.abs(matrizR[linha][linha]) <= configuracao.getToleranciaSingularidade()) {
                throw new IllegalArgumentException("Não foi possível calcular os coeficientes porque a matriz é singular.");
            }

            resultado[linha] = soma / matrizR[linha][linha];
        }

        return resultado;
    }

    // --- CALCULA AS PREVISÕES DO CONJUNTO INFORMADO ---
    private double[] calcularPrevisoes(double[][] matrizProjeto, double[] parametros) {
        double[] previsoes = new double[matrizProjeto.length];

        for (int linha = 0; linha < matrizProjeto.length; linha++) {
            double previsao = 0;

            for (int coluna = 0; coluna < parametros.length; coluna++) {
                previsao += matrizProjeto[linha][coluna] * parametros[coluna];
            }

            previsoes[linha] = previsao;
        }

        return previsoes;
    }

    public ConfiguracaoRegressao getConfiguracao() {
        return configuracao;
    }
}

