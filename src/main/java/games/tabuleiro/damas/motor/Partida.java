package games.tabuleiro.damas.motor;

import games.tabuleiro.damas.modelo.EstadoPartida;
import games.tabuleiro.damas.modelo.Jogador;
import games.tabuleiro.damas.modelo.Movimento;
import games.tabuleiro.damas.modelo.Peca;
import games.tabuleiro.damas.modelo.Posicao;
import games.tabuleiro.damas.modelo.Tabuleiro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Partida {

    public static final int LIMITE_LANCES_SEM_PROGRESSO = 40;

    private Tabuleiro tabuleiro;
    private Jogador jogadorAtual;
    private EstadoPartida estado;
    private List<Movimento> movimentosLegais;
    private Movimento ultimoMovimento;
    private int lancesSemProgresso;
    private int jogadasRealizadas;
    private String motivoFim;

    public Partida() {
        reiniciar();
    }

    public final void reiniciar() {
        tabuleiro = Tabuleiro.criarPosicaoInicial();
        jogadorAtual = Jogador.JOGADOR_1;
        estado = EstadoPartida.EM_ANDAMENTO;
        ultimoMovimento = null;
        lancesSemProgresso = 0;
        jogadasRealizadas = 0;
        motivoFim = "";
        atualizarMovimentosLegais();
    }

    public ResultadoJogada executarMovimento(Movimento movimento) {
        if (estado.isEncerrada()) {
            throw new IllegalStateException("A partida já foi encerrada.");
        }
        if (!movimentosLegais.contains(movimento)) {
            throw new IllegalArgumentException("Movimento inválido: " + movimento);
        }

        Jogador autor = jogadorAtual;
        Peca peca = tabuleiro.getPeca(movimento.getOrigem());
        boolean promoveu = RegrasDamas.aplicarMovimento(tabuleiro, movimento);

        if (movimento.isCaptura() || !peca.isDama()) {
            lancesSemProgresso = 0;
        } else {
            lancesSemProgresso++;
        }

        ultimoMovimento = movimento;
        jogadasRealizadas++;
        jogadorAtual = jogadorAtual.getAdversario();
        atualizarMovimentosLegais();
        verificarFimDeJogo();

        return new ResultadoJogada(autor, movimento, promoveu, estado);
    }

    private void atualizarMovimentosLegais() {
        movimentosLegais = RegrasDamas.gerarMovimentosLegais(tabuleiro, jogadorAtual);
    }

    private void verificarFimDeJogo() {
        if (movimentosLegais.isEmpty()) {
            Jogador perdedor = jogadorAtual;
            estado = EstadoPartida.vitoriaDe(perdedor.getAdversario());
            motivoFim = tabuleiro.contarPecas(perdedor) == 0
                    ? "Todas as peças do " + perdedor.getNome() + " foram capturadas."
                    : "O " + perdedor.getNome() + " ficou sem movimentos válidos.";
        } else if (lancesSemProgresso >= LIMITE_LANCES_SEM_PROGRESSO) {
            estado = EstadoPartida.EMPATE;
            motivoFim = "Foram realizados 20 lances seguidos de cada jogador apenas com damas, sem capturas.";
        }
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro.copiar();
    }

    public Peca getPeca(Posicao posicao) {
        return tabuleiro.getPeca(posicao);
    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    public boolean isEncerrada() {
        return estado.isEncerrada();
    }

    public Jogador getVencedor() {
        return estado.getVencedor();
    }

    public String getMotivoFim() {
        return motivoFim;
    }

    public List<Movimento> getMovimentosLegais() {
        return Collections.unmodifiableList(movimentosLegais);
    }

    public List<Movimento> getMovimentosDaPeca(Posicao origem) {
        List<Movimento> resultado = new ArrayList<>();
        for (Movimento movimento : movimentosLegais) {
            if (movimento.getOrigem().equals(origem)) {
                resultado.add(movimento);
            }
        }
        return resultado;
    }

    /** Casas das peças que podem se mover neste turno. */
    public Set<Posicao> getOrigensJogaveis() {
        Set<Posicao> origens = new LinkedHashSet<>();
        for (Movimento movimento : movimentosLegais) {
            origens.add(movimento.getOrigem());
        }
        return origens;
    }

    public boolean existeCapturaObrigatoria() {
        return !movimentosLegais.isEmpty() && movimentosLegais.get(0).isCaptura();
    }

    public Movimento getUltimoMovimento() {
        return ultimoMovimento;
    }

    public int getJogadasRealizadas() {
        return jogadasRealizadas;
    }

    public int contarPecas(Jogador jogador) {
        return tabuleiro.contarPecas(jogador);
    }

    public int contarDamas(Jogador jogador) {
        return tabuleiro.contarDamas(jogador);
    }
}
