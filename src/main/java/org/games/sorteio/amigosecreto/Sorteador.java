package org.games.sorteio.amigosecreto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sorteador {

    private final List<Participante> participantes = new ArrayList<>();
    private final Map<String, List<String>> restricoes = new HashMap<>();

    public void adicionar(Participante p) {
        participantes.add(p);
    }

    public List<Participante> getParticipantes() {
        return Collections.unmodifiableList(participantes);
    }

    public void adicionarRestricao(String nomeA, String nomeB) {
        restricoes.computeIfAbsent(nomeA, k -> new ArrayList<>()).add(nomeB);
        restricoes.computeIfAbsent(nomeB, k -> new ArrayList<>()).add(nomeA);
    }

    public Map<Participante, Participante> sortear() {
        if (participantes.size() < 2) {
            throw new IllegalStateException("É necessário pelo menos 2 participantes.");
        }

        int maxTentativas = 1000;
        for (int tentativa = 0; tentativa < maxTentativas; tentativa++) {
            List<Participante> embaralhado = new ArrayList<>(participantes);
            Collections.shuffle(embaralhado);

            Map<Participante, Participante> resultado = new HashMap<>();
            boolean valido = true;

            for (int i = 0; i < participantes.size(); i++) {
                Participante tira = participantes.get(i);
                Participante tirado = embaralhado.get(i);

                if (tira.getNome().equals(tirado.getNome()) || temRestricao(tira, tirado)) {
                    valido = false;
                    break;
                }
                resultado.put(tira, tirado);
            }

            if (valido) {
                return resultado;
            }
        }
        throw new IllegalStateException("Não foi possível realizar o sorteio (restrições muito apertadas).");
    }

    private boolean temRestricao(Participante a, Participante b) {
        List<String> bloqueados = restricoes.get(a.getNome());
        return bloqueados != null && bloqueados.contains(b.getNome());
    }
}

