package application.exercicios.faculdade.pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EstadoJogo {

    private static final int RECOMPENSA_COMIDA = 10;
    private static final int RECOMPENSA_CAPSULA = 0;
    private static final int RECOMPENSA_FANTASMA = 200;
    private static final int RECOMPENSA_VITORIA = 500;
    private static final int PENALIDADE_DERROTA = -500;
    private static final int PENALIDADE_TEMPO = -1;
    private static final int TEMPO_ASSUSTADO = 40;

    private final Layout                layout;
    private final boolean[][]           comidas;
    private final List<Posicao>         capsulas;
    private final List<EstadoAgente>    agentes;
    private final int                   comidaRestante;
    private final int                   pontuacao;
    private final boolean               venceu;
    private final boolean               perdeu;

    private EstadoJogo(Layout layout, boolean[][] comidas, List<Posicao> capsulas,
                       List<EstadoAgente> agentes, int comidaRestante, int pontuacao,
                       boolean venceu, boolean perdeu) {
        this.layout = layout;
        this.comidas = comidas;
        this.capsulas = capsulas;
        this.agentes = agentes;
        this.comidaRestante = comidaRestante;
        this.pontuacao = pontuacao;
        this.venceu = venceu;
        this.perdeu = perdeu;
    }

    public static EstadoJogo inicial(Layout layout) {
        List<EstadoAgente> agentes = new ArrayList<>();
        agentes.add(new EstadoAgente(layout.getPosicaoPacman(), Direcao.PARADO));
        for (Posicao p : layout.getPosicoesFantasmas()) {
            agentes.add(new EstadoAgente(p, Direcao.PARADO));
        }
        return new EstadoJogo(layout, layout.copiarComidas(),
                new ArrayList<>(layout.getCapsulas()), agentes,
                layout.getTotalComida(), 0, false, false);
    }

    public Layout getLayout() {
        return layout;
    }

    public int getNumAgentes() {
        return agentes.size();
    }

    public EstadoAgente getAgente(int indice) {
        return agentes.get(indice);
    }

    public Posicao getPosicaoPacman() {
        return agentes.get(0).getPosicao();
    }

    public List<Posicao> getPosicoesFantasmas() {
        List<Posicao> lista = new ArrayList<>();
        for (int i = 1; i < agentes.size(); i++) {
            lista.add(agentes.get(i).getPosicao());
        }
        return lista;
    }

    public List<EstadoAgente> getEstadosFantasmas() {
        return Collections.unmodifiableList(agentes.subList(1, agentes.size()));
    }

    public boolean[][] getComidas() {
        return comidas;
    }

    public int getComidaRestante() {
        return comidaRestante;
    }

    public List<Posicao> getCapsulas() {
        return Collections.unmodifiableList(capsulas);
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public boolean venceu() {
        return venceu;
    }

    public boolean perdeu() {
        return perdeu;
    }

    public boolean terminou() {
        return venceu || perdeu;
    }

    public List<Direcao> getAcoesLegais(int indiceAgente) {
        if (terminou()) {
            return Collections.emptyList();
        }

        EstadoAgente agente = agentes.get(indiceAgente);
        Posicao pos = agente.getPosicao();
        List<Direcao> acoes = new ArrayList<>();

        for (Direcao d : new Direcao[]{Direcao.NORTE, Direcao.SUL, Direcao.LESTE, Direcao.OESTE}) {
            Posicao nova = pos.mover(d);
            if (!layout.ehParede(nova.getX(), nova.getY())) {
                acoes.add(d);
            }
        }

        if (indiceAgente == 0) {
            acoes.add(Direcao.PARADO);
        } else if (acoes.isEmpty()) {
            acoes.add(Direcao.PARADO);
        }
        return acoes;
    }

    public EstadoJogo gerarSucessor(int indiceAgente, Direcao acao) {
        if (terminou()) {
            throw new IllegalStateException("Nao ha sucessor para estado terminal.");
        }

        List<EstadoAgente> novosAgentes = new ArrayList<>(agentes);
        boolean[][] novasComidas = comidas;
        List<Posicao> novasCapsulas = capsulas;
        int novaComidaRestante = comidaRestante;
        int novaPontuacao = pontuacao;
        boolean novoVenceu = false;
        boolean novoPerdeu = false;

        EstadoAgente agente = novosAgentes.get(indiceAgente);
        Posicao proxPos = agente.getPosicao().mover(acao);

        if (indiceAgente == 0) {
            novaPontuacao += PENALIDADE_TEMPO;
            EstadoAgente novoPacman = new EstadoAgente(proxPos, acao,
                    agente.getTempoAssustado());
            novosAgentes.set(0, novoPacman);

            int x = proxPos.getX();
            int y = proxPos.getY();
            if (x >= 0 && y >= 0 && x < novasComidas.length && y < novasComidas[0].length && novasComidas[x][y]) {
                novasComidas = copiarMatriz(novasComidas);
                novasComidas[x][y] = false;
                novaComidaRestante--;
                novaPontuacao += RECOMPENSA_COMIDA;
                if (novaComidaRestante == 0) {
                    novoVenceu = true;
                    novaPontuacao += RECOMPENSA_VITORIA;
                }
            }

            if (capsulas.contains(proxPos)) {
                novasCapsulas = new ArrayList<>(capsulas);
                novasCapsulas.remove(proxPos);
                novaPontuacao += RECOMPENSA_CAPSULA;
                for (int i = 1; i < novosAgentes.size(); i++) {
                    novosAgentes.set(i, novosAgentes.get(i).comTempoAssustado(TEMPO_ASSUSTADO));
                }
            }
        } else {
            int novoTempo = Math.max(0, agente.getTempoAssustado() - 1);
            EstadoAgente novoFantasma = new EstadoAgente(proxPos, acao, novoTempo);
            novosAgentes.set(indiceAgente, novoFantasma);
        }

        Posicao pacman = novosAgentes.get(0).getPosicao();
        for (int i = 1; i < novosAgentes.size() && !novoVenceu; i++) {
            EstadoAgente fantasma = novosAgentes.get(i);
            if (fantasma.getPosicao().equals(pacman)) {
                if (fantasma.estaAssustado()) {
                    novaPontuacao += RECOMPENSA_FANTASMA;
                    Posicao inicial = layout.getPosicoesFantasmas().get(i - 1);
                    novosAgentes.set(i, new EstadoAgente(inicial, Direcao.PARADO, 0));
                } else {
                    novoPerdeu = true;
                    novaPontuacao += PENALIDADE_DERROTA;
                    break;
                }
            }
        }

        return new EstadoJogo(layout, novasComidas, novasCapsulas, novosAgentes,
                novaComidaRestante, novaPontuacao, novoVenceu, novoPerdeu);
    }

    private static boolean[][] copiarMatriz(boolean[][] origem) {
        boolean[][] copia = new boolean[origem.length][origem[0].length];
        for (int i = 0; i < origem.length; i++) {
            System.arraycopy(origem[i], 0, copia[i], 0, origem[i].length);
        }
        return copia;
    }
}

