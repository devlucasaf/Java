package games.puzzle.nonogram;

public class JogoNonogram {

    private final Dificuldade dificuldade;
    private final TabuleiroNonogram tabuleiro;
    private final ValidadorNonogram validador;
    private EstadoPartida estado;
    private int quantidadeJogadas;

    public JogoNonogram(Dificuldade dificuldade) {
        validarDificuldade(dificuldade);

        this.dificuldade = dificuldade;

        GeradorNonogram gerador = new GeradorNonogram();

        this.tabuleiro = new TabuleiroNonogram(gerador.gerar(dificuldade));
        this.validador = new ValidadorNonogram();
        this.estado = EstadoPartida.NAO_INICIADA;
        this.quantidadeJogadas = 0;
    }

    public JogoNonogram(Dificuldade dificuldade, long semente) {
        validarDificuldade(dificuldade);

        this.dificuldade = dificuldade;

        GeradorNonogram gerador = new GeradorNonogram(semente);

        this.tabuleiro = new TabuleiroNonogram(gerador.gerar(dificuldade));
        this.validador = new ValidadorNonogram();
        this.estado = EstadoPartida.NAO_INICIADA;
        this.quantidadeJogadas = 0;
    }

    // --- INICIA A PARTIDA ---
    public void iniciar() {
        if (estado == EstadoPartida.NAO_INICIADA) {
            estado = EstadoPartida.EM_ANDAMENTO;
        }
    }

    // --- ALTERNA O ESTADO DE UMA CÉLULA ---
    public EstadoCelula alternarCelula(int linha, int coluna) {
        garantirPartidaJogavel();

        EstadoCelula estadoCelula = tabuleiro.alternarCelula(linha, coluna);
        quantidadeJogadas++;

        atualizarEstado();

        return estadoCelula;
    }

    // --- DEFINE DIRETAMENTE O ESTADO DE UMA CÉLULA ---
    public void definirEstadoCelula(int linha, int coluna, EstadoCelula estadoCelula) {
        garantirPartidaJogavel();

        tabuleiro.definirEstadoCelula(linha, coluna, estadoCelula);
        quantidadeJogadas++;

        atualizarEstado();
    }

    // --- REINICIA O TABULEIRO ATUAL ---
    public void reiniciar() {
        tabuleiro.limparTabuleiroJogador();
        quantidadeJogadas = 0;
        estado = EstadoPartida.EM_ANDAMENTO;
    }

    // --- ATUALIZA O ESTADO DA PARTIDA DEPOIS DE CADA JOGADA ---
    private void atualizarEstado() {
        if (validador.isConcluido(tabuleiro)) {
            estado = EstadoPartida.CONCLUIDA;
        }
    }

    // --- IMPEDE JOGADAS FORA DE UMA PARTIDA ATIVA ---
    private void garantirPartidaJogavel() {
        if (estado == EstadoPartida.NAO_INICIADA) {
            throw new IllegalStateException("A partida ainda não foi iniciada.");
        }

        if (estado == EstadoPartida.CONCLUIDA) {
            throw new IllegalStateException("A partida já foi concluída.");
        }
    }

    // --- VALIDA A DIFICULDADE INFORMADA ---
    private void validarDificuldade(Dificuldade dificuldade) {
        if (dificuldade == null) {
            throw new IllegalArgumentException("A dificuldade não pode ser nula.");
        }
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public TabuleiroNonogram getTabuleiro() {
        return tabuleiro;
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    public int getQuantidadeJogadas() {
        return quantidadeJogadas;
    }

    public boolean isProgressoValido() {
        return validador.isProgressoValido(tabuleiro);
    }

    public boolean isConcluido() {
        return validador.isConcluido(tabuleiro);
    }

    public int getQuantidadeErros() {
        return validador.contarErros(tabuleiro);
    }

    public double getPorcentagemProgresso() {
        return validador.calcularProgresso(tabuleiro);
    }
}

