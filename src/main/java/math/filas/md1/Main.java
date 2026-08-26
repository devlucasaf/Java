package math.filas.md1;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Simulador de fila M/D/1 (atendimento determinístico) ===");
        System.out.println();

        double lambda = 4.0;
        double mu = 6.0;

        SimuladorFilaMD1 fila = new SimuladorFilaMD1(lambda, mu);

        System.out.printf("Taxa de chegada (lambda): %.2f clientes/hora%n", lambda);
        System.out.printf("Taxa de atendimento (mu): %.2f clientes/hora%n", mu);
        System.out.printf("Tempo de serviço fixo: %.2f horas (%.1f min)%n", 1.0/mu, (1.0/mu)*60);
        System.out.printf("Fator de utilização (rho = lambda/mu): %.2f%n", fila.getFatorUtilizacao());
        System.out.println();

        System.out.println("--- Métricas teóricas (fórmulas para M/D/1) ---");
        System.out.printf("Número médio de clientes na fila (Lq): %.3f%n", fila.getNumeroMedioNaFilaTeorico());
        System.out.printf("Número médio de clientes no sistema (L): %.3f%n", fila.getNumeroMedioNoSistemaTeorico());
        System.out.printf("Tempo médio de espera na fila (Wq): %.3f horas (%.1f min)%n",
                fila.getTempoMedioEsperaNaFilaTeorico(),
                fila.getTempoMedioEsperaNaFilaTeorico() * 60);
        System.out.printf("Tempo médio no sistema (W): %.3f horas (%.1f min)%n",
                fila.getTempoMedioNoSistemaTeorico(),
                fila.getTempoMedioNoSistemaTeorico() * 60);

        System.out.println();
        System.out.println("--- Métricas obtidas por simulação (10.000 clientes) ---");
        ResultadoSimulacao resultado = fila.simular(10_000);
        System.out.printf("Tempo médio de espera na fila (Wq): %.3f horas (%.1f min)%n",
                resultado.tempoMedioEspera, resultado.tempoMedioEspera * 60);
        System.out.printf("Tempo médio no sistema (W): %.3f horas (%.1f min)%n",
                resultado.tempoMedioNoSistema, resultado.tempoMedioNoSistema * 60);
        System.out.printf("Tamanho médio da fila (Lq) por área: %.3f clientes%n", resultado.tamanhoMedioFila);

        System.out.println();
        System.out.println("Comparando com o M/M/1 (mesmo lambda e mu), o M/D/1 apresenta");
        System.out.println("menores filas e tempos de espera, pois a variabilidade do serviço");
        System.out.println("é eliminada. Isso mostra o impacto da dispersão do tempo de");
        System.out.println("atendimento no desempenho da fila.");
    }
}
