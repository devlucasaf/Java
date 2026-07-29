package application.exercicios.faculdade.pacman;

import application.exercicios.faculdade.pacman.agentes.Agente;
import application.exercicios.faculdade.pacman.display.Display;

import java.util.List;

public class Jogo {

    private final List<Agente> agentes;
    private final Display display;
    private final int atrasoMs;

    public Jogo(List<Agente> agentes, Display display, int atrasoMs) {
        this.agentes = agentes;
        this.display = display;
        this.atrasoMs = atrasoMs;
    }

    public EstadoJogo executar(EstadoJogo inicial) {
        EstadoJogo estado = inicial;
        display.iniciar(estado);
        while (!estado.terminou()) {
            for (int i = 0; i < agentes.size() && !estado.terminou(); i++) {
                Direcao acao = agentes.get(i).getAcao(estado, i);
                if (!estado.getAcoesLegais(i).contains(acao)) {
                    acao = Direcao.PARADO;
                }
                estado = estado.gerarSucessor(i, acao);
            }
            display.atualizar(estado);
            if (atrasoMs > 0) {
                try {
                    Thread.sleep(atrasoMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        display.finalizar(estado);
        return estado;
    }
}

