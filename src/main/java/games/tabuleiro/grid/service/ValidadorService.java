package games.tabuleiro.grid.service;

import games.tabuleiro.grid.model.Jogador;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidadorService {

    private Set<String> jogadoresUsados = new HashSet<>();

    public boolean validarResposta(String resposta, String pais, String clube, List<Jogador> jogadores) {
        if (jogadoresUsados.contains(resposta.toLowerCase())) {
            System.out.println("Jogador já utilizado!");
            return false;
        }

        for (Jogador j : jogadores) {
            if (j.getNome().equalsIgnoreCase(resposta)
            && j.getPais().equalsIgnoreCase(pais)
            && j.getClubes().contains(clube)) {

                jogadoresUsados.add(resposta.toLowerCase());
                return true;
            }
        }
        return false;
    }
}
