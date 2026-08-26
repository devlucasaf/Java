package math.filas.mm1k;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Simulador de fila M/M/1/K (capacidade finita) ===");
        System.out.println();

        double lambda = 4.0;
        double mu = 6.0;
        int K = 3;

        SimuladorFilaMM1K fila = new SimuladorFilaMM1K(lambda, mu, K);

        System.out.printf("Taxa de chegada (lambda): %.2f clientes/hora%n", lambda);
        System.out.printf("Taxa de atendimento (mu): %.2f clientes/hora%n", mu);
        System.out.printf("Capacidade máxima do sistema (K): %d clientes%n", K);
        System.out.printf("Fator de utilização oferecido (rho = lambda/mu): %.2f%n", fila.getFatorUtilizacao());
        System.out.printf("Probabilidade de sistema cheio (perda): %.4f%n", fila.getProbabilidadeSistemaCheio());
        System.out.printf("Taxa efetiva de entrada (lambda_eff): %.2f clientes/hora%n", fila.getLambdaEfetivo());
        System.out.println();

        System.out.println("--- Métricas teóricas (fórmulas para M/M/1/K) ---");
        System.out.printf("Número médio de clientes no sistema (L): %.3f%n", fila.getNumeroMedioNoSistemaTeorico());
        System.out.printf("Número médio de clientes na fila (Lq): %.3f%n", fila.getNumeroMedioNaFilaTeorico());
        System.out.printf("Tempo médio no sistema (W): %.3f horas (%.1f min)%n",
                fila.getTempoMedioNoSistemaTeorico(),
                fila.getTempoMedioNoSistemaTeorico() * 60);
        System.out.printf("Tempo médio de espera na fila (Wq): %.3f horas (%.1f min)%n",
                fila.getTempoMedioEsperaNaFilaTeorico(),
                fila.getTempoMedioEsperaNaFilaTeorico() * 60);

        System.out.println();
        System.out.println("--- Métricas obtidas por simulação (10.000 tentativas de chegada) ---");
        ResultadoSimulacaoMM1K resultado = fila.simular(10_000);
        System.out.printf("Clientes aceitos: %d%n", resultado.clientesAtendidos);
        System.out.printf("Clientes rejeitados (perdidos): %d%n", resultado.clientesRejeitados);
        System.out.printf("Taxa de rejeição simulada: %.2f%%%n",
                (double) resultado.clientesRejeitados / (resultado.clientesAtendidos + resultado.clientesRejeitados) * 100);
        System.out.printf("Tempo médio de espera na fila (Wq): %.3f horas (%.1f min)%n",
                resultado.tempoMedioEspera, resultado.tempoMedioEspera * 60);
        System.out.printf("Tempo médio no sistema (W): %.3f horas (%.1f min)%n",
                resultado.tempoMedioNoSistema, resultado.tempoMedioNoSistema * 60);
        System.out.printf("Tamanho médio da fila (Lq) por área: %.3f clientes%n", resultado.tamanhoMedioFila);

        System.out.println();
        System.out.println("Observe que a fila não cresce indefinidamente devido ao limite K,");
        System.out.println("e clientes são perdidos quando o sistema está cheio. Isso é útil para");
        System.out.println("modelar buffers de rede, salas de espera com lotação, etc.");
    }
}