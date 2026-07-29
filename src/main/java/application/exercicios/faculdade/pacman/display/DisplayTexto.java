package application.exercicios.faculdade.pacman.display;

import application.exercicios.faculdade.pacman.EstadoAgente;
import application.exercicios.faculdade.pacman.EstadoJogo;
import application.exercicios.faculdade.pacman.Layout;
import application.exercicios.faculdade.pacman.Posicao;

import java.util.List;

public class DisplayTexto implements Display {

    @Override
    public void iniciar(EstadoJogo estado) {
        System.out.println("=== INICIO DO JOGO ===");
        desenhar(estado);
    }

    @Override
    public void atualizar(EstadoJogo estado) {
        desenhar(estado);
    }

    @Override
    public void finalizar(EstadoJogo estado) {
        desenhar(estado);
        System.out.println("=== FIM DO JOGO ===");
        System.out.println("Pontuacao final: " + estado.getPontuacao());
        if (estado.venceu()) System.out.println("VITORIA!");
        else if (estado.perdeu()) System.out.println("DERROTA");
    }

    private void desenhar(EstadoJogo estado) {
        Layout layout = estado.getLayout();
        boolean[][] comidas = estado.getComidas();
        List<Posicao> capsulas = estado.getCapsulas();
        Posicao pacman = estado.getPosicaoPacman();
        List<EstadoAgente> fantasmas = estado.getEstadosFantasmas();

        StringBuilder sb = new StringBuilder();
        for (int y = layout.getAltura() - 1; y >= 0; y--) {
            for (int x = 0; x < layout.getLargura(); x++) {
                char c = ' ';
                if (layout.ehParede(x, y)) c = '%';
                else if (comidas[x][y]) c = '.';
                Posicao p = new Posicao(x, y);
                if (capsulas.contains(p)) c = 'o';
                if (pacman.equals(p)) c = 'P';
                for (EstadoAgente f : fantasmas) {
                    if (f.getPosicao().equals(p)) {
                        c = f.estaAssustado() ? 'g' : 'G';
                    }
                }
                sb.append(c);
            }
            sb.append('\n');
        }
        sb.append("Pontuacao: ").append(estado.getPontuacao())
                .append(" | Comida restante: ").append(estado.getComidaRestante())
                .append('\n');
        System.out.println(sb);
    }
}

