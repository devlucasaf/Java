package org.application.system.esporte.cbf;

import java.time.LocalDateTime;

public class Partida {
    private static int contador = 1;
    private int                 id;
    private Clube               mandante;
    private Clube               visitante;
    private LocalDateTime       dataHora;
    private Estadio             estadio;
    private Arbitro             arbitroPrincipal;
    private Arbitro             assistente1;
    private Arbitro             assistente2;
    private int                 golsMandante;
    private int                 golsVisitante;
    private boolean             realizada;
    private ResultadoPartida    resultado;

    public Partida(Clube mandante, Clube visitante, LocalDateTime dataHora, Estadio estadio,
                   Arbitro arbitroPrincipal, Arbitro assistente1, Arbitro assistente2) {
        this.id = contador++;
        this.mandante = mandante;
        this.visitante = visitante;
        this.dataHora = dataHora;
        this.estadio = estadio;
        this.arbitroPrincipal = arbitroPrincipal;
        this.assistente1 = assistente1;
        this.assistente2 = assistente2;
        this.realizada = false;
    }

    public void registrarResultado(int golsMandante, int golsVisitante) {
        if (realizada) {
            System.out.println("Partida já foi realizada!");
            return;
        }

        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;
        this.realizada = true;

        if (golsMandante > golsVisitante) {
            resultado = ResultadoPartida.VITORIA_MANDANTE;
            mandante.registrarPartida(golsMandante, golsVisitante);
            visitante.registrarPartida(golsVisitante, golsMandante);
        } else if (golsVisitante > golsMandante) {
            resultado = ResultadoPartida.VITORIA_VISITANTE;
            mandante.registrarPartida(golsMandante, golsVisitante);
            visitante.registrarPartida(golsVisitante, golsMandante);
        } else {
            resultado = ResultadoPartida.EMPATE;
            mandante.registrarPartida(golsMandante, golsVisitante);
            visitante.registrarPartida(golsVisitante, golsMandante);
        }

        System.out.println("\n=== RESULTADO DA PARTIDA ===");
        System.out.println(mandante.getNome() + " " + golsMandante + " x " + golsVisitante + " " + visitante.getNome());
        System.out.println("Resultado: " + resultado);
    }

    public void exibirSumario() {
        System.out.println("Partida " + id + ": " + mandante.getNome() + " vs " + visitante.getNome());
        System.out.println("Data: " + dataHora);
        System.out.println("Estádio: " + estadio.getNome());

        if (realizada) {
            System.out.println("Placar: " + golsMandante + " - " + golsVisitante);
        } else {
            System.out.println("Status: A ser realizada");
        }
    }

    public int getId() {
        return id;
    }

    public Clube getMandante() {
        return mandante;
    }

    public Clube getVisitante() {
        return visitante;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public int getGolsMandante() {
        return golsMandante;
    }

    public int getGolsVisitante() {
        return golsVisitante;
    }
}
