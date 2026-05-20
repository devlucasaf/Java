package org.application.federacaofutebol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Arbitro extends Pessoa {
    private String          registroCBF;
    private TipoArbitro     tipo;
    private int             partidasApitadas;
    private List<Partida>   partidasRealizadas;

    public Arbitro(String nome, String cpf, LocalDate dataNascimento, String nacionalidade,
                   String registroCBF, TipoArbitro tipo) {
        super(nome, cpf, dataNascimento, nacionalidade);
        this.registroCBF = registroCBF;
        this.tipo = tipo;
        this.partidasApitadas = 0;
        this.partidasRealizadas = new ArrayList<>();
    }

    public void apitarPartida(Partida partida) {
        partidasRealizadas.add(partida);
        partidasApitadas++;
        System.out.println("Árbitro " + nome + (" apitou a partida ") + partida.getId());
    }

    public void aplicarPenalidade(Jogador jogador, String motivo, int tempoSuspensao) {
        System.out.println("Penalidade aplicada a " + jogador.getNome() + ": " + motivo +
                " - Suspensão de " + tempoSuspensao + " dias.");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- ÁRBITRO ---");
        System.out.println("Nome: " + nome);
        System.out.println("Registro CBF: " + registroCBF);
        System.out.println("Tipo: " + tipo);
        System.out.println("Partidas apitadas: " + partidasApitadas);
    }
}
