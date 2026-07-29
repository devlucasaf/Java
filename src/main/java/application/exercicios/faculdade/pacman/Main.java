package application.exercicios.faculdade.pacman;

import application.exercicios.faculdade.pacman.agentes.Agente;
import application.exercicios.faculdade.pacman.agentes.AgenteDirecional;
import application.exercicios.faculdade.pacman.agentes.AgenteMinimax;
import application.exercicios.faculdade.pacman.display.Display;
import application.exercicios.faculdade.pacman.display.DisplayGrafico;
import application.exercicios.faculdade.pacman.display.DisplayTexto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String nomeLayout = args.length > 0 ? args[0] : "mediumClassic";
        int profundidade = args.length > 1 ? Integer.parseInt(args[1]) : 2;
        boolean grafico = args.length > 2 ? Boolean.parseBoolean(args[2]) : true;
        int atrasoMs = args.length > 3 ? Integer.parseInt(args[3]) : 150;

        Layout layout = Layout.carregar(nomeLayout);
        EstadoJogo estado = EstadoJogo.inicial(layout);

        List<Agente> agentes = new ArrayList<>();
        agentes.add(new AgenteMinimax(profundidade));
        for (int i = 0; i < layout.getPosicoesFantasmas().size(); i++) {
            agentes.add(new AgenteDirecional());
        }

        Display display = grafico ? new DisplayGrafico() : new DisplayTexto();
        Jogo jogo = new Jogo(agentes, display, atrasoMs);
        jogo.executar(estado);
    }
}

