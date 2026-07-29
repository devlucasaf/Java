package application.exercicios.faculdade.pacman.agentes;

import application.exercicios.faculdade.pacman.Direcao;
import application.exercicios.faculdade.pacman.EstadoJogo;

public interface Agente {
    Direcao getAcao(EstadoJogo estado, int indice);
}

