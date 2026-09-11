package games.strategy.cardbattle;

import java.util.ArrayList;
import java.util.List;

public class JogoBatalhaCartas {

    private final Jogador                   jogador;
    private final Jogador                   jogadorInteligenciaArtificial;
    private final InteligenciaArtificial    inteligenciaArtificial;
    private ResultadoPartida                resultado;
    private int                             numeroTurno;
    private boolean                         turnoEmAndamento;

    public JogoBatalhaCartas(String nomeJogador) {
        this.jogador = new Jogador(nomeJogador, 100, 5, criarBaralhoJogador());
        this.jogadorInteligenciaArtificial = new Jogador("Guardião Sombrio", 100, 5, criarBaralhoIa());
        this.inteligenciaArtificial = new InteligenciaArtificial();
        this.resultado = ResultadoPartida.EM_ANDAMENTO;
        this.numeroTurno = 0;
        this.turnoEmAndamento = false;

        jogador.comprarMaoInicial(5);
        jogadorInteligenciaArtificial.comprarMaoInicial(5);
    }

    // --- INICIA O TURNO DO JOGADOR ---
    public List<String> iniciarTurnoJogador() {
        verificarPartidaEmAndamento();

        if (turnoEmAndamento) {
            throw new IllegalStateException("O turno atual já foi iniciado.");
        }

        numeroTurno++;
        turnoEmAndamento = true;

        List<String> acontecimentos = jogador.iniciarTurno();
        atualizarResultado();
        return acontecimentos;
    }

    // --- JOGA UMA CARTA DA MÃO DO JOGADOR ---
    public String jogarCarta(int indiceCarta) {
        verificarPartidaEmAndamento();

        if (!turnoEmAndamento) {
            throw new IllegalStateException("O turno do jogador ainda não foi iniciado.");
        }

        String mensagem = jogador.jogarCarta(indiceCarta, jogadorInteligenciaArtificial);
        atualizarResultado();
        return mensagem;
    }

    // --- FINALIZA O TURNO DO JOGADOR E EXECUTA O TURNO DA IA ---
    public List<String> finalizarTurnoJogador() {
        verificarPartidaEmAndamento();

        if (!turnoEmAndamento) {
            throw new IllegalStateException("O turno do jogador ainda não foi iniciado.");
        }

        turnoEmAndamento = false;
        atualizarResultado();

        List<String> acontecimentos = new ArrayList<>();

        if (resultado != ResultadoPartida.EM_ANDAMENTO) {
            return acontecimentos;
        }

        acontecimentos.addAll(jogadorInteligenciaArtificial.iniciarTurno());
        atualizarResultado();

        if (resultado == ResultadoPartida.EM_ANDAMENTO) {
            acontecimentos.addAll(inteligenciaArtificial.executarTurno(jogadorInteligenciaArtificial, jogador));
            atualizarResultado();
        }

        return acontecimentos;
    }

    // --- ATUALIZA O RESULTADO DA PARTIDA ---
    private void atualizarResultado() {
        if (!jogador.isVivo() && !jogadorInteligenciaArtificial.isVivo()) {
            resultado = ResultadoPartida.EMPATE;
        } else if (!jogadorInteligenciaArtificial.isVivo()) {
            resultado = ResultadoPartida.VITORIA_JOGADOR;
        } else if (!jogador.isVivo()) {
            resultado = ResultadoPartida.VITORIA_IA;
        }
    }

    // --- VERIFICA SE A PARTIDA AINDA ESTÁ EM ANDAMENTO ---
    private void verificarPartidaEmAndamento() {
        if (resultado != ResultadoPartida.EM_ANDAMENTO) {
            throw new IllegalStateException("A partida já foi encerrada.");
        }
    }

    // --- CRIA O BARALHO DO JOGADOR ---
    private List<Carta> criarBaralhoJogador() {
        List<Carta> cartas = new ArrayList<>();

        cartas.add(Carta.criarAtaque(1, "Golpe Rápido", "Um ataque simples e de baixo custo.", 1, 9));
        cartas.add(Carta.criarAtaque(2, "Golpe Rápido", "Um ataque simples e de baixo custo.", 1, 9));
        cartas.add(Carta.criarAtaque(3, "Lâmina de Fogo", "Um poderoso ataque envolvido em chamas.", 3, 24));
        cartas.add(Carta.criarAtaque(4, "Lâmina de Fogo", "Um poderoso ataque envolvido em chamas.", 3, 24));
        cartas.add(Carta.criarDefesa(5, "Escudo de Ferro", "Cria uma proteção resistente.", 2, 16));
        cartas.add(Carta.criarDefesa(6, "Escudo de Ferro", "Cria uma proteção resistente.", 2, 16));
        cartas.add(Carta.criarCura(7, "Poção de Cura", "Recupera parte da vida.", 2, 18));
        cartas.add(Carta.criarCura(8, "Poção de Cura", "Recupera parte da vida.", 2, 18));
        cartas.add(Carta.criarSuporte(9, "Fúria", "Aumenta o ataque por três turnos.", 2, AlvoCarta.PROPRIO_JOGADOR, new EfeitoStatus(TipoEfeito.AUMENTO_ATAQUE, 5, 3)));
        cartas.add(Carta.criarSuporte(10, "Enfraquecer", "Reduz o ataque do adversário.", 2, AlvoCarta.ADVERSARIO, new EfeitoStatus(TipoEfeito.REDUCAO_ATAQUE, 4, 3)));
        cartas.add(Carta.criarSuporte(11, "Regeneração", "Recupera vida no início dos próximos turnos.", 3, AlvoCarta.PROPRIO_JOGADOR, new EfeitoStatus(TipoEfeito.REGENERACAO, 5, 3)));
        cartas.add(Carta.criarSuporte(12, "Veneno", "Provoca dano no início dos próximos turnos.", 3, AlvoCarta.ADVERSARIO, new EfeitoStatus(TipoEfeito.VENENO, 6, 3)));

        return cartas;
    }

    // --- CRIA O BARALHO DA INTELIGÊNCIA ARTIFICIAL ---
    private List<Carta> criarBaralhoIa() {
        List<Carta> cartas = new ArrayList<>();

        cartas.add(Carta.criarAtaque(101, "Garra Sombria", "Um corte produzido por energia sombria.", 1, 10));
        cartas.add(Carta.criarAtaque(102, "Garra Sombria", "Um corte produzido por energia sombria.", 1, 10));
        cartas.add(Carta.criarAtaque(103, "Explosão Sombria", "Uma explosão de alto poder.", 3, 23));
        cartas.add(Carta.criarAtaque(104, "Explosão Sombria", "Uma explosão de alto poder.", 3, 23));
        cartas.add(Carta.criarDefesa(105, "Barreira Obscura", "Uma barreira que absorve ataques.", 2, 15));
        cartas.add(Carta.criarDefesa(106, "Barreira Obscura", "Uma barreira que absorve ataques.", 2, 15));
        cartas.add(Carta.criarCura(107, "Essência Vital", "Restaura a energia vital.", 2, 17));
        cartas.add(Carta.criarCura(108, "Essência Vital", "Restaura a energia vital.", 2, 17));
        cartas.add(Carta.criarSuporte(109, "Poder Sombrio", "Aumenta o ataque temporariamente.", 2, AlvoCarta.PROPRIO_JOGADOR, new EfeitoStatus(TipoEfeito.AUMENTO_ATAQUE, 5, 3)));
        cartas.add(Carta.criarSuporte(110, "Fragilidade", "Reduz a defesa do adversário.", 2, AlvoCarta.ADVERSARIO, new EfeitoStatus(TipoEfeito.REDUCAO_DEFESA, 5, 3)));
        cartas.add(Carta.criarSuporte(111, "Regeneração Sombria", "Recupera vida por três turnos.", 3, AlvoCarta.PROPRIO_JOGADOR, new EfeitoStatus(TipoEfeito.REGENERACAO, 5, 3)));
        cartas.add(Carta.criarSuporte(112, "Peste", "Aplica veneno ao adversário.", 3, AlvoCarta.ADVERSARIO, new EfeitoStatus(TipoEfeito.VENENO, 6, 3)));

        return cartas;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Jogador getJogadorInteligenciaArtificial() {
        return jogadorInteligenciaArtificial;
    }

    public ResultadoPartida getResultado() {
        return resultado;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public boolean isTurnoEmAndamento() {
        return turnoEmAndamento;
    }

    public boolean isPartidaEncerrada() {
        return resultado != ResultadoPartida.EM_ANDAMENTO;
    }
}

