package application.exercicios.faculdade.pacman.agentes;

import application.exercicios.faculdade.pacman.EstadoAgente;
import application.exercicios.faculdade.pacman.EstadoJogo;
import application.exercicios.faculdade.pacman.Posicao;

import java.util.List;

public final class FuncaoAvaliacao {

    private FuncaoAvaliacao() {
    }

    public static double avaliar(EstadoJogo estado) {
        if (estado.venceu()) {
            return 1_000_000.0;
        }

        if (estado.perdeu()) {
            return -1_000_000.0;
        }

        double score = estado.getPontuacao();
        Posicao pacman = estado.getPosicaoPacman();
        boolean[][] comidas = estado.getComidas();
        List<EstadoAgente> fantasmas = estado.getEstadosFantasmas();
        List<Posicao> capsulas = estado.getCapsulas();

        double comidaMaisProxima = Double.POSITIVE_INFINITY;
        int totalComida = 0;
        for (int x = 0; x < comidas.length; x++) {
            for (int y = 0; y < comidas[x].length; y++) {
                if (comidas[x][y]) {
                    totalComida++;
                    double d = pacman.distanciaManhattan(new Posicao(x, y));
                    if (d < comidaMaisProxima) {
                        comidaMaisProxima = d;
                    }
                }
            }
        }

        if (totalComida == 0) {
            return score + 1_000_000.0;
        }

        boolean faseFinal = totalComida <= 3;

        if (faseFinal) {
            score += 500.0 / (comidaMaisProxima + 0.1);
        } else {
            score += 100.0 / (comidaMaisProxima + 0.1);
            score -= 5 * totalComida;
        }

        double distFantasmaAtivo = Double.POSITIVE_INFINITY;
        double distFantasmaAssustado = Double.POSITIVE_INFINITY;
        for (EstadoAgente f : fantasmas) {
            double d = pacman.distanciaManhattan(f.getPosicao());
            if (f.estaAssustado()) {
                if (d < distFantasmaAssustado) {
                    distFantasmaAssustado = d;
                }
            } else {
                if (d < distFantasmaAtivo) {
                    distFantasmaAtivo = d;
                }
            }
        }

        if (distFantasmaAtivo != Double.POSITIVE_INFINITY) {
            if (distFantasmaAtivo <= 1) {
                score -= 5000;
            } else {
                score -= 20.0 / distFantasmaAtivo;
            }
        }

        if (distFantasmaAssustado != Double.POSITIVE_INFINITY) {
            score += 200.0 / (distFantasmaAssustado + 0.1);
        }

        if (!capsulas.isEmpty() && distFantasmaAtivo < 5) {
            double capMaisProx = Double.POSITIVE_INFINITY;
            for (Posicao c : capsulas) {
                double d = pacman.distanciaManhattan(c);
                if (d < capMaisProx) {
                    capMaisProx = d;
                }
            }
            score += 50.0 / (capMaisProx + 0.1);
        }
        score -= 30 * capsulas.size();

        return score;
    }
}

