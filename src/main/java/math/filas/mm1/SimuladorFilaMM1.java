package math.filas.mm1;

import java.util.*;

public class SimuladorFilaMM1 {

    private final double lambda;
    private final double mu;
    private final Random random = new Random();

    public SimuladorFilaMM1(double lambda, double mu) {
        if (lambda >= mu) {
            throw new IllegalArgumentException("Sistema instavel: lambda deve ser menor que mu (fila cresce sem limite).");
        }
        this.lambda = lambda;
        this.mu = mu;
    }

    public double getFatorUtilizacao() {
        return lambda / mu; // rho
    }

    public double getNumeroMedioNoSistemaTeorico() {
        double rho = getFatorUtilizacao();
        return rho / (1 - rho); // L
    }

    public double getTempoMedioNoSistemaTeorico() {
        return 1.0 / (mu - lambda); // W
    }

    public double getNumeroMedioNaFilaTeorico() {
        double rho = getFatorUtilizacao();
        return (rho * rho) / (1 - rho); // Lq
    }

    public double getTempoMedioEsperaNaFilaTeorico() {
        double rho = getFatorUtilizacao();
        return rho / (mu - lambda); // Wq
    }

    private double gerarTempoExponencial(double taxa) {
        return -Math.log(1 - random.nextDouble()) / taxa;
    }

    public ResultadoSimulacao simular(int totalClientes) {
        double somaEsperas = 0;
        double somaTemposNoSistema = 0;

        List<Double> chegadas = new ArrayList<>(totalClientes);
        List<Double> iniciosAtendimento = new ArrayList<>(totalClientes);
        List<Double> finsAtendimento = new ArrayList<>(totalClientes);

        double proximaChegada = 0;
        double servidorLivreEm = 0;

        for (int i = 0; i < totalClientes; i++) {
            proximaChegada += gerarTempoExponencial(lambda);
            double inicioAtendimento = Math.max(proximaChegada, servidorLivreEm);
            double duracaoAtendimento = gerarTempoExponencial(mu);
            double fimAtendimento = inicioAtendimento + duracaoAtendimento;

            double espera = inicioAtendimento - proximaChegada;
            somaEsperas += espera;
            somaTemposNoSistema += (fimAtendimento - proximaChegada);

            chegadas.add(proximaChegada);
            iniciosAtendimento.add(inicioAtendimento);
            finsAtendimento.add(fimAtendimento);

            servidorLivreEm = fimAtendimento;
        }

        List<double[]> eventos = new ArrayList<>();
        for (int i = 0; i < totalClientes; i++) {
            eventos.add(new double[]{chegadas.get(i), +1});
            eventos.add(new double[]{iniciosAtendimento.get(i), -1});
        }
        eventos.sort(Comparator.comparingDouble(e -> e[0]));

        double areaFila = 0;
        double instanteAnterior = 0;
        int clientesNaFilaAtual = 0;
        for (double[] evento : eventos) {
            areaFila += clientesNaFilaAtual * (evento[0] - instanteAnterior);
            instanteAnterior = evento[0];
            clientesNaFilaAtual += (int) evento[1];
        }

        double tempoTotalSimulado = finsAtendimento.get(finsAtendimento.size() - 1);

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        resultado.clientesAtendidos = totalClientes;
        resultado.tempoMedioEspera = somaEsperas / totalClientes;
        resultado.tempoMedioNoSistema = somaTemposNoSistema / totalClientes;
        resultado.tamanhoMedioFila = areaFila / instanteAnterior;
        return resultado;
    }

    public double getLambda() {
        return lambda;
    }

    public double getMu() {
        return mu;
    }
}

