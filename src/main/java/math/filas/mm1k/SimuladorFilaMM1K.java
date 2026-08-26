package math.filas.mm1k;

import java.util.*;

public class SimuladorFilaMM1K {

    private final double lambda;
    private final double mu;
    private final int K;
    private final Random random = new Random();

    public SimuladorFilaMM1K(double lambda, double mu, int K) {
        if (lambda <= 0 || mu <= 0 || K < 1) {
            throw new IllegalArgumentException("Lambda e mu devem ser positivos, e K >= 1.");
        }
        this.lambda = lambda;
        this.mu = mu;
        this.K = K;
    }

    public double getFatorUtilizacao() {
        return lambda / mu;
    }

    public double getProbabilidadeSistemaCheio() {
        double rho = getFatorUtilizacao();
        if (Math.abs(rho - 1.0) < 1e-9) {
            return 1.0 / (K + 1);
        }
        double P0 = (1 - rho) / (1 - Math.pow(rho, K + 1));
        return P0 * Math.pow(rho, K);
    }

    public double getLambdaEfetivo() {
        return lambda * (1 - getProbabilidadeSistemaCheio());
    }

    public double getNumeroMedioNoSistemaTeorico() {
        double rho = getFatorUtilizacao();
        if (Math.abs(rho - 1.0) < 1e-9) {
            return K / 2.0;
        }

        double P0 = (1 - rho) / (1 - Math.pow(rho, K + 1));
        double L = 0;
        for (int n = 0; n <= K; n++) {
            L += n * P0 * Math.pow(rho, n);
        }
        return L;
    }

    public double getNumeroMedioNaFilaTeorico() {
        double L = getNumeroMedioNoSistemaTeorico();
        double rho_eff = getLambdaEfetivo() / mu;
        return L - rho_eff;
    }

    public double getTempoMedioNoSistemaTeorico() {
        double lambdaEff = getLambdaEfetivo();
        if (lambdaEff == 0) {
            return 0;
        }
        return getNumeroMedioNoSistemaTeorico() / lambdaEff;
    }

    public double getTempoMedioEsperaNaFilaTeorico() {
        double lambdaEff = getLambdaEfetivo();
        if (lambdaEff == 0) {
            return 0;
        }
        return getNumeroMedioNaFilaTeorico() / lambdaEff;
    }

    private double gerarExponencial(double taxa) {
        return -Math.log(1 - random.nextDouble()) / taxa;
    }

    public ResultadoSimulacaoMM1K simular(int totalTentativas) {
        if (totalTentativas <= 0) {
            ResultadoSimulacaoMM1K vazio = new ResultadoSimulacaoMM1K();
            vazio.clientesAtendidos = 0;
            vazio.clientesRejeitados = 0;
            vazio.tempoMedioEspera = 0;
            vazio.tempoMedioNoSistema = 0;
            vazio.tamanhoMedioFila = 0;
            return vazio;
        }

        double tempoAtual = 0;
        double proxChegada = gerarExponencial(lambda);
        double proxFimServico = Double.POSITIVE_INFINITY;
        Queue<Double> fila = new LinkedList<>();
        boolean servidorOcupado = false;
        double clienteEmServicoChegada = 0;

        int aceitos = 0;
        int rejeitados = 0;
        double somaEsperas = 0;
        double somaTemposSistema = 0;

        int tentativasRestantes = totalTentativas - 1;

        double areaFila = 0;
        double ultimoTempo = 0;
        int currentFilaSize = 0;

        while (tentativasRestantes > 0 || servidorOcupado || !fila.isEmpty()) {
            double proximoEvento;
            if (tentativasRestantes > 0) {
                proximoEvento = proxChegada;
            } else {
                proximoEvento = Double.POSITIVE_INFINITY;
            }

            if (servidorOcupado && proxFimServico < proximoEvento) {
                proximoEvento = proxFimServico;
            }

            areaFila += currentFilaSize * (proximoEvento - ultimoTempo);
            ultimoTempo = proximoEvento;
            tempoAtual = proximoEvento;

            if (servidorOcupado && proximoEvento == proxFimServico) {
                double tempoSistema = tempoAtual - clienteEmServicoChegada;
                somaTemposSistema += tempoSistema;

                if (!fila.isEmpty()) {
                    double chegadaCliente = fila.remove();
                    currentFilaSize--;
                    double espera = tempoAtual - chegadaCliente;
                    somaEsperas += espera;
                    clienteEmServicoChegada = chegadaCliente;
                    proxFimServico = tempoAtual + gerarExponencial(mu);
                } else {
                    servidorOcupado = false;
                    proxFimServico = Double.POSITIVE_INFINITY;
                }
            } else {
                if (servidorOcupado) {
                    if (currentFilaSize < K - 1) {
                        fila.add(proxChegada);
                        currentFilaSize++;
                        aceitos++;
                    } else {
                        rejeitados++;
                    }
                } else {
                    servidorOcupado = true;
                    clienteEmServicoChegada = proxChegada;
                    proxFimServico = tempoAtual + gerarExponencial(mu);
                    aceitos++;
                }

                if (tentativasRestantes > 0) {
                    proxChegada += gerarExponencial(lambda);
                    tentativasRestantes--;
                }
            }
        }

        double tempoTotal = ultimoTempo;

        ResultadoSimulacaoMM1K res = new ResultadoSimulacaoMM1K();
        res.clientesAtendidos = aceitos;
        res.clientesRejeitados = rejeitados;
        res.tempoMedioEspera = (aceitos > 0) ? somaEsperas / aceitos : 0;
        res.tempoMedioNoSistema = (aceitos > 0) ? somaTemposSistema / aceitos : 0;
        res.tamanhoMedioFila = (tempoTotal > 0) ? areaFila / tempoTotal : 0;
        return res;
    }

    public double getLambda() {
        return lambda;
    }

    public double getMu() {
        return mu;
    }

    public int getK() {
        return K;
    }
}

