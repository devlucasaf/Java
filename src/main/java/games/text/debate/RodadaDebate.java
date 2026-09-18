package games.text.debate;

public class RodadaDebate {

    private final int                   numero;
    private final Argumento             argumentoJogador;
    private final AvaliacaoArgumento    avaliacaoJogador;
    private final Argumento             argumentoInteligenciaArtificial;
    private final AvaliacaoArgumento    avaliacaoInteligenciaArtificial;

    public RodadaDebate(int numero, Argumento argumentoJogador, AvaliacaoArgumento avaliacaoJogador,
                        Argumento argumentoInteligenciaArtificial, AvaliacaoArgumento avaliacaoInteligenciaArtificial) {
        if (numero <= 0) {
            throw new IllegalArgumentException("O número da rodada deve ser maior que zero.");
        }

        if (argumentoJogador == null || avaliacaoJogador == null || argumentoInteligenciaArtificial == null || avaliacaoInteligenciaArtificial == null) {
            throw new IllegalArgumentException("Todos os dados da rodada devem ser informados.");
        }

        this.numero = numero;
        this.argumentoJogador = argumentoJogador;
        this.avaliacaoJogador = avaliacaoJogador;
        this.argumentoInteligenciaArtificial = argumentoInteligenciaArtificial;
        this.avaliacaoInteligenciaArtificial = avaliacaoInteligenciaArtificial;
    }

    // --- RETORNA A DIFERENÇA DE PONTOS DA RODADA ---
    public double calcularDiferencaPontos() {
        return avaliacaoJogador.getPontuacaoTotal() - avaliacaoInteligenciaArtificial.getPontuacaoTotal();
    }

    // --- VERIFICA SE O JOGADOR VENCEU A RODADA ---
    public boolean isVitoriaJogador() {
        return calcularDiferencaPontos() > 0;
    }

    // --- VERIFICA SE A RODADA TERMINOU EMPATADA ---
    public boolean isEmpate() {
        return Math.abs(calcularDiferencaPontos()) < 0.0001;
    }

    public int getNumero() {
        return numero;
    }

    public Argumento getArgumentoJogador() {
        return argumentoJogador;
    }

    public AvaliacaoArgumento getAvaliacaoJogador() {
        return avaliacaoJogador;
    }

    public Argumento getArgumentoInteligenciaArtificial() {
        return argumentoInteligenciaArtificial;
    }

    public AvaliacaoArgumento getAvaliacaoInteligenciaArtificial() {
        return avaliacaoInteligenciaArtificial;
    }
}

