package math.filas.mm1;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Simulador de fila M/M/1 (banco com 1 caixa) ===");
        System.out.println();

        double lambda = 4.0;
        double mu = 6.0;

        SimuladorFilaMM1 fila = new SimuladorFilaMM1(lambda, mu);

        System.out.printf("Taxa de chegada (lambda): %.2f clientes/hora%n", lambda);
        System.out.printf("Taxa de atendimento (mu): %.2f clientes/hora%n", mu);
        System.out.printf("Fator de utilizacao (rho = lambda/mu): %.2f%n", fila.getFatorUtilizacao());
        System.out.println();

        System.out.println("--- Metricas teoricas (formulas de Teoria das Filas) ---");
        System.out.printf("Numero medio de clientes no sistema (L): %.2f%n", fila.getNumeroMedioNoSistemaTeorico());
        System.out.printf("Numero medio de clientes na fila (Lq): %.2f%n", fila.getNumeroMedioNaFilaTeorico());
        System.out.printf("Tempo medio no sistema (W): %.2f horas (%.1f min)%n",
                fila.getTempoMedioNoSistemaTeorico(), fila.getTempoMedioNoSistemaTeorico() * 60);
        System.out.printf("Tempo medio de espera na fila (Wq): %.2f horas (%.1f min)%n",
                fila.getTempoMedioEsperaNaFilaTeorico(), fila.getTempoMedioEsperaNaFilaTeorico() * 60);

        System.out.println();
        System.out.println("--- Metricas obtidas por simulacao (10.000 clientes) ---");
        ResultadoSimulacao resultado = fila.simular(10_000);
        System.out.printf("Tempo medio de espera na fila: %.2f horas (%.1f min)%n",
                resultado.tempoMedioEspera, resultado.tempoMedioEspera * 60);
        System.out.printf("Tempo medio no sistema: %.2f horas (%.1f min)%n",
                resultado.tempoMedioNoSistema, resultado.tempoMedioNoSistema * 60);
        System.out.printf("Tamanho medio da fila (aproximado): %.2f clientes%n", resultado.tamanhoMedioFila);

        System.out.println();
        System.out.println("Repare que os valores simulados ficam proximos dos valores teoricos,");
        System.out.println("validando as formulas classicas de Teoria das Filas para o modelo M/M/1.");

        System.out.println();
        System.out.println("=== Testando um cenario de alta utilizacao (rho = 0.9) ===");
        SimuladorFilaMM1 filaCongestionada = new SimuladorFilaMM1(5.4, 6.0);
        System.out.printf("Tempo medio de espera teorico: %.2f horas (%.1f min)%n",
                filaCongestionada.getTempoMedioEsperaNaFilaTeorico(),
                filaCongestionada.getTempoMedioEsperaNaFilaTeorico() * 60);
        System.out.println("Note como a espera cresce rapidamente conforme rho se aproxima de 1 -");
        System.out.println("esse e o fenomeno de saturacao de filas, muito usado em dimensionamento");
        System.out.println("de atendimento (caixas de banco, call centers, servidores web, etc).");
    }
}
