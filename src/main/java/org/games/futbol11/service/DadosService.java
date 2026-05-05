package org.games.futbol11.service;

import org.games.futbol11.model.Jogador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DadosService {

    public List<Jogador> carregarJogadores() {
        List<Jogador> jogadores = new ArrayList<>();

        jogadores.add(new Jogador("Ronaldo", "Brasil", Arrays.asList("Real Madrid", "Barcelona", "Inter")));
        jogadores.add(new Jogador("Kaka", "Brasil", Arrays.asList("Real Madrid", "Milan")));
        jogadores.add(new Jogador("Casemiro", "Brasil", Arrays.asList("Real Madrid")));
        jogadores.add(new Jogador("Benzema", "França", Arrays.asList("Real Madrid")));
        jogadores.add(new Jogador("Giroud", "França", Arrays.asList("Chelsea", "Milan")));
        jogadores.add(new Jogador("Mbappé", "França", Arrays.asList("PSG")));
        jogadores.add(new Jogador("Messi", "Argentina", Arrays.asList("Barcelona", "PSG")));
        jogadores.add(new Jogador("Di María", "Argentina", Arrays.asList("Real Madrid", "PSG")));
        jogadores.add(new Jogador("Paredes", "Argentina", Arrays.asList("PSG", "Chelsea")));

        return jogadores;
    }

    public String[] paises() {
        return new String[]{"Brasil", "França", "Argentina"};
    }

    public String[] clubes() {
        return new String[]{"Real Madrid", "Milan", "Chelsea"};
    }

}
