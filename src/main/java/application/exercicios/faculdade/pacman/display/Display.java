package application.exercicios.faculdade.pacman.display;

import application.exercicios.faculdade.pacman.EstadoJogo;

public interface Display {

    void iniciar(EstadoJogo estado);

    void atualizar(EstadoJogo estado);

    void finalizar(EstadoJogo estado);
}

