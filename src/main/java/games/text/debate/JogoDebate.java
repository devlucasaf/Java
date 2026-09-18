package games.text.debate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JogoDebate {

    private final String                        nomeJogador;
    private final TemaDebate                    tema;
    private final LadoDebate                    ladoJogador;
    private final LadoDebate                    ladoIa;
    private final int                           quantidadeMaximaRodadas;
    private final AvaliadorArgumentos           avaliador;
    private final InteligenciaArtificialDebate  inteligenciaArtificialDebate;
    private final List<RodadaDebate>            rodadas;
    private long                                proximoIdentificadorArgumento;

    public JogoDebate(String nomeJogador, TemaDebate tema, LadoDebate ladoJogador, int quantidadeMaximaRodadas) {
        if (nomeJogador == null || nomeJogador.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do jogador não pode estar vazio.");
        }

        if (tema == null || ladoJogador == null) {
            throw new IllegalArgumentException("O tema e o lado do jogador devem ser informados.");
        }

        if (quantidadeMaximaRodadas <= 0 || quantidadeMaximaRodadas > 20) {
            throw new IllegalArgumentException("A quantidade de rodadas deve estar entre 1 e 20.");
        }

        this.nomeJogador = nomeJogador.trim();
        this.tema = tema;
        this.ladoJogador = ladoJogador;
        this.ladoIa = ladoJogador.getOposto();
        this.quantidadeMaximaRodadas = quantidadeMaximaRodadas;
        this.avaliador = new AvaliadorArgumentos();
        this.inteligenciaArtificialDebate = new InteligenciaArtificialDebate();
        this.rodadas = new ArrayList<>();
        this.proximoIdentificadorArgumento = 1;
    }

    // --- EXECUTA UMA RODADA COMPLETA DO DEBATE ---
    public RodadaDebate executarRodada(String textoJogador, EstrategiaArgumentacao estrategiaJogador) {
        if (isFinalizado()) {
            throw new IllegalStateException("O debate já foi finalizado.");
        }

        if (textoJogador == null || textoJogador.trim().isEmpty()) {
            throw new IllegalArgumentException("O argumento do jogador não pode estar vazio.");
        }

        if (textoJogador.trim().length() < 20) {
            throw new IllegalArgumentException("O argumento deve possuir pelo menos vinte caracteres.");
        }

        if (estrategiaJogador == null) {
            throw new IllegalArgumentException("A estratégia do jogador deve ser informada.");
        }

        String argumentoAnteriorIa = rodadas.isEmpty() ? null : rodadas.get(rodadas.size() - 1).getArgumentoInteligenciaArtificial().getTexto();
        Argumento argumentoJogador = new Argumento(proximoIdentificadorArgumento++, nomeJogador, ladoJogador, estrategiaJogador, textoJogador);
        AvaliacaoArgumento avaliacaoJogador = avaliador.avaliar(argumentoJogador, tema, argumentoAnteriorIa);

        EstrategiaArgumentacao estrategiaIa = escolherEstrategiaIa();
        String textoIa = inteligenciaArtificialDebate.criarArgumento(tema, ladoIa, estrategiaIa, textoJogador);
        Argumento argumentoIa = new Argumento(proximoIdentificadorArgumento++, "Argumentador virtual", ladoIa, estrategiaIa, textoIa);
        AvaliacaoArgumento avaliacaoIa = avaliador.avaliar(argumentoIa, tema, textoJogador);

        RodadaDebate rodada = new RodadaDebate(rodadas.size() + 1, argumentoJogador, avaliacaoJogador, argumentoIa, avaliacaoIa);
        rodadas.add(rodada);
        return rodada;
    }

    // --- ESCOLHE UMA ESTRATÉGIA PARA O OPONENTE ---
    private EstrategiaArgumentacao escolherEstrategiaIa() {
        EstrategiaArgumentacao[] estrategias = EstrategiaArgumentacao.values();

        if (!rodadas.isEmpty() && new Random().nextBoolean()) {
            return EstrategiaArgumentacao.REFUTACAO;
        }

        return estrategias[new Random().nextInt(estrategias.length)];
    }

    // --- CALCULA A PONTUAÇÃO TOTAL DO JOGADOR ---
    public double calcularPontuacaoJogador() {
        double total = 0;

        for (RodadaDebate rodada : rodadas) {
            total += rodada.getAvaliacaoJogador().getPontuacaoTotal();
        }

        return total;
    }

    // --- CALCULA A PONTUAÇÃO TOTAL DA INTELIGÊNCIA ARTIFICIAL ---
    public double calcularPontuacaoIa() {
        double total = 0;

        for (RodadaDebate rodada : rodadas) {
            total += rodada.getAvaliacaoInteligenciaArtificial().getPontuacaoTotal();
        }

        return total;
    }

    // --- RETORNA A QUANTIDADE DE RODADAS VENCIDAS PELO JOGADOR ---
    public int contarVitoriasJogador() {
        int quantidade = 0;

        for (RodadaDebate rodada : rodadas) {
            if (rodada.isVitoriaJogador()) {
                quantidade++;
            }
        }

        return quantidade;
    }

    // --- RETORNA A QUANTIDADE DE RODADAS VENCIDAS PELA IA ---
    public int contarVitoriasIa() {
        int quantidade = 0;

        for (RodadaDebate rodada : rodadas) {
            if (!rodada.isVitoriaJogador() && !rodada.isEmpate()) {
                quantidade++;
            }
        }

        return quantidade;
    }

    // --- RETORNA O RESULTADO FINAL DO DEBATE ---
    public String getResultadoFinal() {
        if (!isFinalizado()) {
            return "O debate ainda está em andamento.";
        }

        double pontuacaoJogador = calcularPontuacaoJogador();
        double pontuacaoIa = calcularPontuacaoIa();

        if (Math.abs(pontuacaoJogador - pontuacaoIa) < 0.0001) {
            return "O debate terminou empatado.";
        }

        return pontuacaoJogador > pontuacaoIa ? nomeJogador + " venceu o debate." : "O argumentador virtual venceu o debate.";
    }

    // --- VERIFICA SE TODAS AS RODADAS FORAM EXECUTADAS ---
    public boolean isFinalizado() {
        return rodadas.size() >= quantidadeMaximaRodadas;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public TemaDebate getTema() {
        return tema;
    }

    public LadoDebate getLadoJogador() {
        return ladoJogador;
    }

    public LadoDebate getLadoIa() {
        return ladoIa;
    }

    public int getQuantidadeMaximaRodadas() {
        return quantidadeMaximaRodadas;
    }

    public int getRodadaAtual() {
        return rodadas.size();
    }

    public List<RodadaDebate> getRodadas() {
        return Collections.unmodifiableList(rodadas);
    }
}
