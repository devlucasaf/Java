package math.numerico.raizes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class SolucionadorRaizes {

    // --- CALCULA UMA RAIZ PELO MÉTODO DA BISSEÇÃO ---
    public ResultadoRaiz bissecao(DoubleUnaryOperator funcao, double limiteInferior, double limiteSuperior, ConfiguracaoCalculo configuracao) {
        validarFuncaoEConfiguracao(funcao, configuracao);
        validarNumeroFinito(limiteInferior, "O limite inferior");
        validarNumeroFinito(limiteSuperior, "O limite superior");

        long inicioExecucao = System.nanoTime();
        List<Iteracao> historico = new ArrayList<>();

        if (limiteInferior >= limiteSuperior) {
            return criarResultado(MetodoNumerico.BISSECAO, Double.NaN, Double.NaN, SituacaoResultado.INTERVALO_INVALIDO, "O limite inferior deve ser menor que o limite superior.", inicioExecucao, historico);
        }

        double valorInferior = funcao.applyAsDouble(limiteInferior);
        double valorSuperior = funcao.applyAsDouble(limiteSuperior);

        if (!saoFinitos(valorInferior, valorSuperior)) {
            return criarResultado(MetodoNumerico.BISSECAO, Double.NaN, Double.NaN, SituacaoResultado.VALOR_NAO_FINITO, "A função produziu um valor não finito nos limites do intervalo.", inicioExecucao, historico);
        }

        if (Math.abs(valorInferior) <= configuracao.getTolerancia()) {
            historico.add(criarIteracao(1, limiteInferior, limiteSuperior, limiteInferior, valorInferior, null, configuracao.getRaizExata()));
            return criarResultado(MetodoNumerico.BISSECAO, limiteInferior, valorInferior, SituacaoResultado.CONVERGIU, "O limite inferior já representa uma raiz.", inicioExecucao, historico);
        }

        if (Math.abs(valorSuperior) <= configuracao.getTolerancia()) {
            historico.add(criarIteracao(1, limiteInferior, limiteSuperior, limiteSuperior, valorSuperior, null, configuracao.getRaizExata()));
            return criarResultado(MetodoNumerico.BISSECAO, limiteSuperior, valorSuperior, SituacaoResultado.CONVERGIU, "O limite superior já representa uma raiz.", inicioExecucao, historico);
        }

        if (mesmoSinal(valorInferior, valorSuperior)) {
            return criarResultado(MetodoNumerico.BISSECAO, Double.NaN, Double.NaN, SituacaoResultado.INTERVALO_INVALIDO, "A função deve apresentar sinais opostos nos extremos do intervalo.", inicioExecucao, historico);
        }

        double aproximacaoAnterior = Double.NaN;
        double aproximacaoAtual = Double.NaN;
        double valorAtual = Double.NaN;

        for (int numeroIteracao = 1; numeroIteracao <= configuracao.getMaximoIteracoes(); numeroIteracao++) {
            aproximacaoAtual = limiteInferior + (limiteSuperior - limiteInferior) / 2.0;
            valorAtual = funcao.applyAsDouble(aproximacaoAtual);

            if (!saoFinitos(aproximacaoAtual, valorAtual)) {
                return criarResultado(MetodoNumerico.BISSECAO, aproximacaoAtual, valorAtual, SituacaoResultado.VALOR_NAO_FINITO, "A função produziu um valor não finito.", inicioExecucao, historico);
            }

            Double erroAbsoluto = calcularErroAbsoluto(aproximacaoAtual, aproximacaoAnterior);
            historico.add(criarIteracao(numeroIteracao, limiteInferior, limiteSuperior, aproximacaoAtual, valorAtual, erroAbsoluto, configuracao.getRaizExata()));

            double metadeIntervalo = Math.abs(limiteSuperior - limiteInferior) / 2.0;

            if (Math.abs(valorAtual) <= configuracao.getTolerancia() || metadeIntervalo <= configuracao.getTolerancia()) {
                return criarResultado(MetodoNumerico.BISSECAO, aproximacaoAtual, valorAtual, SituacaoResultado.CONVERGIU, "A tolerância definida foi atingida.", inicioExecucao, historico);
            }

            if (mesmoSinal(valorInferior, valorAtual)) {
                limiteInferior = aproximacaoAtual;
                valorInferior = valorAtual;
            } else {
                limiteSuperior = aproximacaoAtual;
                valorSuperior = valorAtual;
            }

            aproximacaoAnterior = aproximacaoAtual;
        }

        return criarResultado(MetodoNumerico.BISSECAO, aproximacaoAtual, valorAtual, SituacaoResultado.MAXIMO_ITERACOES, "O número máximo de iterações foi atingido.", inicioExecucao, historico);
    }

    // --- CALCULA UMA RAIZ PELO MÉTODO DE NEWTON-RAPHSON ---
    public ResultadoRaiz newtonRaphson(DoubleUnaryOperator funcao, DoubleUnaryOperator derivada, double aproximacaoInicial, ConfiguracaoCalculo configuracao) {
        validarFuncaoEConfiguracao(funcao, configuracao);

        if (derivada == null) {
            throw new IllegalArgumentException("A derivada não pode ser nula.");
        }

        validarNumeroFinito(aproximacaoInicial, "A aproximação inicial");

        long inicioExecucao = System.nanoTime();
        List<Iteracao> historico = new ArrayList<>();
        double aproximacaoAnterior = aproximacaoInicial;
        double aproximacaoAtual = aproximacaoInicial;
        double valorAtual = funcao.applyAsDouble(aproximacaoAtual);

        if (!Double.isFinite(valorAtual)) {
            return criarResultado(MetodoNumerico.NEWTON_RAPHSON, aproximacaoAtual, valorAtual, SituacaoResultado.VALOR_NAO_FINITO, "A função produziu um valor não finito na aproximação inicial.", inicioExecucao, historico);
        }

        if (Math.abs(valorAtual) <= configuracao.getTolerancia()) {
            historico.add(criarIteracao(1, null, null, aproximacaoAtual, valorAtual, 0.0, configuracao.getRaizExata()));
            return criarResultado(MetodoNumerico.NEWTON_RAPHSON, aproximacaoAtual, valorAtual, SituacaoResultado.CONVERGIU, "A aproximação inicial já satisfaz a tolerância.", inicioExecucao, historico);
        }

        for (int numeroIteracao = 1; numeroIteracao <= configuracao.getMaximoIteracoes(); numeroIteracao++) {
            double valorDerivada = derivada.applyAsDouble(aproximacaoAnterior);

            if (!Double.isFinite(valorDerivada)) {
                return criarResultado(MetodoNumerico.NEWTON_RAPHSON, aproximacaoAnterior, valorAtual, SituacaoResultado.VALOR_NAO_FINITO, "A derivada produziu um valor não finito.", inicioExecucao, historico);
            }

            if (Math.abs(valorDerivada) <= configuracao.getLimiteValorProximoZero()) {
                return criarResultado(MetodoNumerico.NEWTON_RAPHSON, aproximacaoAnterior, valorAtual, SituacaoResultado.DERIVADA_PROXIMA_DE_ZERO, "A derivada está próxima de zero e impede a continuação segura do método.", inicioExecucao, historico);
            }

            aproximacaoAtual = aproximacaoAnterior - funcao.applyAsDouble(aproximacaoAnterior) / valorDerivada;
            valorAtual = funcao.applyAsDouble(aproximacaoAtual);

            if (!saoFinitos(aproximacaoAtual, valorAtual)) {
                return criarResultado(MetodoNumerico.NEWTON_RAPHSON, aproximacaoAtual, valorAtual, SituacaoResultado.VALOR_NAO_FINITO, "O cálculo produziu um valor não finito.", inicioExecucao, historico);
            }

            double erroAbsoluto = Math.abs(aproximacaoAtual - aproximacaoAnterior);
            historico.add(criarIteracao(numeroIteracao, null, null, aproximacaoAtual, valorAtual, erroAbsoluto, configuracao.getRaizExata()));

            if (erroAbsoluto <= configuracao.getTolerancia() || Math.abs(valorAtual) <= configuracao.getTolerancia()) {
                return criarResultado(MetodoNumerico.NEWTON_RAPHSON, aproximacaoAtual, valorAtual, SituacaoResultado.CONVERGIU, "A tolerância definida foi atingida.", inicioExecucao, historico);
            }

            aproximacaoAnterior = aproximacaoAtual;
        }

        return criarResultado(MetodoNumerico.NEWTON_RAPHSON, aproximacaoAtual, valorAtual, SituacaoResultado.MAXIMO_ITERACOES, "O número máximo de iterações foi atingido.", inicioExecucao, historico);
    }

    // --- CALCULA UMA RAIZ PELO MÉTODO DA SECANTE ---
    public ResultadoRaiz secante(DoubleUnaryOperator funcao, double primeiraAproximacao, double segundaAproximacao, ConfiguracaoCalculo configuracao) {
        validarFuncaoEConfiguracao(funcao, configuracao);
        validarNumeroFinito(primeiraAproximacao, "A primeira aproximação");
        validarNumeroFinito(segundaAproximacao, "A segunda aproximação");

        if (primeiraAproximacao == segundaAproximacao) {
            throw new IllegalArgumentException("As aproximações iniciais devem ser diferentes.");
        }

        long inicioExecucao = System.nanoTime();
        List<Iteracao> historico = new ArrayList<>();
        double valorPrimeiro = funcao.applyAsDouble(primeiraAproximacao);
        double valorSegundo = funcao.applyAsDouble(segundaAproximacao);

        if (!saoFinitos(valorPrimeiro, valorSegundo)) {
            return criarResultado(MetodoNumerico.SECANTE, Double.NaN, Double.NaN, SituacaoResultado.VALOR_NAO_FINITO, "A função produziu um valor não finito nas aproximações iniciais.", inicioExecucao, historico);
        }

        if (Math.abs(valorPrimeiro) <= configuracao.getTolerancia()) {
            historico.add(criarIteracao(1, primeiraAproximacao, segundaAproximacao, primeiraAproximacao, valorPrimeiro, 0.0, configuracao.getRaizExata()));
            return criarResultado(MetodoNumerico.SECANTE, primeiraAproximacao, valorPrimeiro, SituacaoResultado.CONVERGIU, "A primeira aproximação já satisfaz a tolerância.", inicioExecucao, historico);
        }

        if (Math.abs(valorSegundo) <= configuracao.getTolerancia()) {
            historico.add(criarIteracao(1, primeiraAproximacao, segundaAproximacao, segundaAproximacao, valorSegundo, 0.0, configuracao.getRaizExata()));
            return criarResultado(MetodoNumerico.SECANTE, segundaAproximacao, valorSegundo, SituacaoResultado.CONVERGIU, "A segunda aproximação já satisfaz a tolerância.", inicioExecucao, historico);
        }

        double aproximacaoAtual = segundaAproximacao;
        double valorAtual = valorSegundo;

        for (int numeroIteracao = 1; numeroIteracao <= configuracao.getMaximoIteracoes(); numeroIteracao++) {
            double denominador = valorSegundo - valorPrimeiro;

            if (Math.abs(denominador) <= configuracao.getLimiteValorProximoZero()) {
                return criarResultado(MetodoNumerico.SECANTE, aproximacaoAtual, valorAtual, SituacaoResultado.DENOMINADOR_PROXIMO_DE_ZERO, "A diferença entre os valores da função está próxima de zero.", inicioExecucao, historico);
            }

            aproximacaoAtual = segundaAproximacao - valorSegundo * (segundaAproximacao - primeiraAproximacao) / denominador;
            valorAtual = funcao.applyAsDouble(aproximacaoAtual);

            if (!saoFinitos(aproximacaoAtual, valorAtual)) {
                return criarResultado(MetodoNumerico.SECANTE, aproximacaoAtual, valorAtual, SituacaoResultado.VALOR_NAO_FINITO, "O cálculo produziu um valor não finito.", inicioExecucao, historico);
            }

            double erroAbsoluto = Math.abs(aproximacaoAtual - segundaAproximacao);
            historico.add(criarIteracao(numeroIteracao, primeiraAproximacao, segundaAproximacao, aproximacaoAtual, valorAtual, erroAbsoluto, configuracao.getRaizExata()));

            if (erroAbsoluto <= configuracao.getTolerancia() || Math.abs(valorAtual) <= configuracao.getTolerancia()) {
                return criarResultado(MetodoNumerico.SECANTE, aproximacaoAtual, valorAtual, SituacaoResultado.CONVERGIU, "A tolerância definida foi atingida.", inicioExecucao, historico);
            }

            primeiraAproximacao = segundaAproximacao;
            valorPrimeiro = valorSegundo;
            segundaAproximacao = aproximacaoAtual;
            valorSegundo = valorAtual;
        }

        return criarResultado(MetodoNumerico.SECANTE, aproximacaoAtual, valorAtual, SituacaoResultado.MAXIMO_ITERACOES, "O número máximo de iterações foi atingido.", inicioExecucao, historico);
    }

    // --- CRIA O REGISTRO DE UMA ITERAÇÃO ---
    private Iteracao criarIteracao(int numero, Double limiteInferior, Double limiteSuperior, double aproximacao, double valorFuncao, Double erroAbsoluto, Double raizExata) {
        Double erroRelativo = calcularErroRelativo(aproximacao, erroAbsoluto);
        Double erroVerdadeiro = raizExata == null ? null : Math.abs(raizExata - aproximacao);
        return new Iteracao(numero, limiteInferior, limiteSuperior, aproximacao, valorFuncao, erroAbsoluto, erroRelativo, erroVerdadeiro);
    }

    // --- CALCULA O ERRO ABSOLUTO ENTRE DUAS APROXIMAÇÕES ---
    private Double calcularErroAbsoluto(double aproximacaoAtual, double aproximacaoAnterior) {
        return Double.isFinite(aproximacaoAnterior) ? Math.abs(aproximacaoAtual - aproximacaoAnterior) : null;
    }

    // --- CALCULA O ERRO RELATIVO ---
    private Double calcularErroRelativo(double aproximacaoAtual, Double erroAbsoluto) {
        if (erroAbsoluto == null) {
            return null;
        }

        if (aproximacaoAtual == 0.0) {
            return erroAbsoluto == 0.0 ? 0.0 : null;
        }

        return erroAbsoluto / Math.abs(aproximacaoAtual);
    }

    // --- VERIFICA SE DOIS VALORES POSSUEM O MESMO SINAL ---
    private boolean mesmoSinal(double primeiroValor, double segundoValor) {
        return Math.signum(primeiroValor) == Math.signum(segundoValor);
    }

    // --- VERIFICA SE TODOS OS VALORES SÃO FINITOS ---
    private boolean saoFinitos(double... valores) {
        for (double valor : valores) {
            if (!Double.isFinite(valor)) {
                return false;
            }
        }

        return true;
    }

    // --- VALIDA A FUNÇÃO E A CONFIGURAÇÃO ---
    private void validarFuncaoEConfiguracao(DoubleUnaryOperator funcao, ConfiguracaoCalculo configuracao) {
        if (funcao == null) {
            throw new IllegalArgumentException("A função não pode ser nula.");
        }

        if (configuracao == null) {
            throw new IllegalArgumentException("A configuração do cálculo não pode ser nula.");
        }
    }

    // --- VALIDA UM NÚMERO FINITO ---
    private void validarNumeroFinito(double valor, String campo) {
        if (!Double.isFinite(valor)) {
            throw new IllegalArgumentException(campo + " deve ser um número finito.");
        }
    }

    // --- CRIA O RESULTADO FINAL DO MÉTODO ---
    private ResultadoRaiz criarResultado(MetodoNumerico metodo, double raiz, double valorFuncao, SituacaoResultado situacao, String mensagem, long inicioExecucao, List<Iteracao> historico) {
        long tempoExecucao = System.nanoTime() - inicioExecucao;
        return new ResultadoRaiz(metodo, raiz, valorFuncao, situacao, mensagem, tempoExecucao, historico);
    }
}

