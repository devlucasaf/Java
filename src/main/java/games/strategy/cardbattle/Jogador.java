package games.strategy.cardbattle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Jogador {

    private static final int TAMANHO_MAXIMO_MAO = 7;

    private final String                nome;
    private final int                   vidaMaxima;
    private final int                   energiaMaxima;
    private final List<Carta>           baralho;
    private final List<Carta>           mao;
    private final List<Carta>           descarte;
    private final List<EfeitoStatus>    efeitosAtivos;
    private final Random                aleatorio;
    private int                         vidaAtual;
    private int                         energiaAtual;
    private int                         defesaAtual;

    public Jogador(String nome, int vidaMaxima, int energiaMaxima, List<Carta> cartas) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do jogador não pode estar vazio.");
        }

        if (vidaMaxima <= 0 || energiaMaxima <= 0) {
            throw new IllegalArgumentException("A vida e a energia máximas devem ser maiores que zero.");
        }

        if (cartas == null || cartas.isEmpty()) {
            throw new IllegalArgumentException("O jogador deve possuir pelo menos uma carta.");
        }

        this.nome = nome.trim();
        this.vidaMaxima = vidaMaxima;
        this.energiaMaxima = energiaMaxima;
        this.vidaAtual = vidaMaxima;
        this.energiaAtual = energiaMaxima;
        this.defesaAtual = 0;
        this.baralho = new ArrayList<>(cartas);
        this.mao = new ArrayList<>();
        this.descarte = new ArrayList<>();
        this.efeitosAtivos = new ArrayList<>();
        this.aleatorio = new Random();

        embaralhar();
    }

    // --- INICIA O TURNO DO JOGADOR ---
    public List<String> iniciarTurno() {
        List<String> acontecimentos = new ArrayList<>();

        defesaAtual = 0;
        energiaAtual = energiaMaxima;
        processarEfeitosPeriodicos(acontecimentos);
        removerEfeitosExpirados(acontecimentos);

        if (isVivo()) {
            Carta cartaComprada = comprarCarta();

            if (cartaComprada != null) {
                acontecimentos.add(nome + " comprou uma carta.");
            }
        }

        return acontecimentos;
    }

    // --- JOGA UMA CARTA CONTRA O ADVERSÁRIO ---
    public String jogarCarta(int indiceCarta, Jogador adversario) {
        if (!isVivo()) {
            throw new IllegalStateException("O jogador foi derrotado e não pode jogar.");
        }

        if (adversario == null) {
            throw new IllegalArgumentException("O adversário não pode ser nulo.");
        }

        if (indiceCarta < 0 || indiceCarta >= mao.size()) {
            throw new IndexOutOfBoundsException("A posição da carta é inválida.");
        }

        Carta carta = mao.get(indiceCarta);

        if (!carta.podeSerJogada(energiaAtual)) {
            throw new IllegalStateException("Energia insuficiente para utilizar esta carta.");
        }

        energiaAtual -= carta.getCustoEnergia();
        aplicarCarta(carta, adversario);
        mao.remove(indiceCarta);
        descarte.add(carta);

        return nome + " utilizou " + carta.getNome() + ".";
    }

    // --- APLICA OS EFEITOS DE UMA CARTA ---
    private void aplicarCarta(Carta carta, Jogador adversario) {
        if (carta.getDano() > 0) {
            adversario.receberDano(carta.getDano() + calcularModificadorAtaque());
        }

        if (carta.getDefesa() > 0) {
            adicionarDefesa(Math.max(0, carta.getDefesa() + calcularModificadorDefesa()));
        }

        if (carta.getCura() > 0) {
            recuperarVida(carta.getCura());
        }

        EfeitoStatus efeito = carta.criarEfeito();

        if (efeito != null) {
            Jogador alvo = carta.getAlvo() == AlvoCarta.PROPRIO_JOGADOR ? this : adversario;
            alvo.adicionarEfeito(efeito);
        }
    }

    // --- RECEBE DANO CONSIDERANDO A DEFESA ATUAL ---
    public int receberDano(int dano) {
        if (dano <= 0) {
            return 0;
        }

        int danoBloqueado = Math.min(defesaAtual, dano);
        int danoRecebido = dano - danoBloqueado;

        defesaAtual -= danoBloqueado;
        vidaAtual = Math.max(0, vidaAtual - danoRecebido);

        return danoRecebido;
    }

    // --- RECUPERA PONTOS DE VIDA ---
    public int recuperarVida(int quantidade) {
        if (quantidade <= 0 || !isVivo()) {
            return 0;
        }

        int vidaAnterior = vidaAtual;
        vidaAtual = Math.min(vidaMaxima, vidaAtual + quantidade);
        return vidaAtual - vidaAnterior;
    }

    // --- ADICIONA DEFESA TEMPORÁRIA ---
    public void adicionarDefesa(int quantidade) {
        if (quantidade > 0) {
            defesaAtual += quantidade;
        }
    }

    // --- ADICIONA OU RENOVA UM EFEITO ---
    public void adicionarEfeito(EfeitoStatus novoEfeito) {
        if (novoEfeito == null) {
            return;
        }

        efeitosAtivos.removeIf(efeito -> efeito.getTipo() == novoEfeito.getTipo());
        efeitosAtivos.add(novoEfeito);
    }

    // --- PROCESSA VENENO E REGENERAÇÃO ---
    private void processarEfeitosPeriodicos(List<String> acontecimentos) {
        for (EfeitoStatus efeito : efeitosAtivos) {
            if (efeito.getTipo() == TipoEfeito.VENENO) {
                int danoRecebido = receberDanoDireto(efeito.getIntensidade());
                acontecimentos.add(nome + " sofreu " + danoRecebido + " de dano por veneno.");
            }

            if (efeito.getTipo() == TipoEfeito.REGENERACAO) {
                int vidaRecuperada = recuperarVida(efeito.getIntensidade());
                acontecimentos.add(nome + " recuperou " + vidaRecuperada + " de vida por regeneração.");
            }

            efeito.reduzirDuracao();
        }
    }

    // --- RECEBE DANO QUE IGNORA A DEFESA ---
    private int receberDanoDireto(int dano) {
        int danoRecebido = Math.max(0, dano);
        vidaAtual = Math.max(0, vidaAtual - danoRecebido);
        return danoRecebido;
    }

    // --- REMOVE EFEITOS QUE TERMINARAM ---
    private void removerEfeitosExpirados(List<String> acontecimentos) {
        Iterator<EfeitoStatus> iterador = efeitosAtivos.iterator();

        while (iterador.hasNext()) {
            EfeitoStatus efeito = iterador.next();

            if (efeito.isExpirado()) {
                acontecimentos.add("O efeito " + efeito.getTipo().getNomeFormatado() + " de " + nome + " terminou.");
                iterador.remove();
            }
        }
    }

    // --- CALCULA O MODIFICADOR TOTAL DE ATAQUE ---
    public int calcularModificadorAtaque() {
        int modificador = 0;

        for (EfeitoStatus efeito : efeitosAtivos) {
            if (efeito.getTipo() == TipoEfeito.AUMENTO_ATAQUE) {
                modificador += efeito.getIntensidade();
            }

            if (efeito.getTipo() == TipoEfeito.REDUCAO_ATAQUE) {
                modificador -= efeito.getIntensidade();
            }
        }

        return modificador;
    }

    // --- CALCULA O MODIFICADOR TOTAL DE DEFESA ---
    public int calcularModificadorDefesa() {
        int modificador = 0;

        for (EfeitoStatus efeito : efeitosAtivos) {
            if (efeito.getTipo() == TipoEfeito.AUMENTO_DEFESA) {
                modificador += efeito.getIntensidade();
            }

            if (efeito.getTipo() == TipoEfeito.REDUCAO_DEFESA) {
                modificador -= efeito.getIntensidade();
            }
        }

        return modificador;
    }

    // --- COMPRA UMA CARTA DO BARALHO ---
    public Carta comprarCarta() {
        if (mao.size() >= TAMANHO_MAXIMO_MAO) {
            return null;
        }

        if (baralho.isEmpty()) {
            reciclarDescarte();
        }

        if (baralho.isEmpty()) {
            return null;
        }

        Carta carta = baralho.remove(baralho.size() - 1);
        mao.add(carta);
        return carta;
    }

    // --- COMPRA A MÃO INICIAL ---
    public void comprarMaoInicial(int quantidade) {
        for (int indice = 0; indice < quantidade; indice++) {
            comprarCarta();
        }
    }

    // --- DEVOLVE AS CARTAS DESCARTADAS AO BARALHO ---
    private void reciclarDescarte() {
        if (descarte.isEmpty()) {
            return;
        }

        baralho.addAll(descarte);
        descarte.clear();
        embaralhar();
    }

    // --- EMBARALHA O BARALHO ---
    private void embaralhar() {
        Collections.shuffle(baralho, aleatorio);
    }

    // --- VERIFICA SE EXISTE ALGUMA CARTA JOGÁVEL ---
    public boolean possuiCartaJogavel() {
        for (Carta carta : mao) {
            if (carta.podeSerJogada(energiaAtual)) {
                return true;
            }
        }

        return false;
    }

    // --- VERIFICA SE O JOGADOR CONTINUA VIVO ---
    public boolean isVivo() {
        return vidaAtual > 0;
    }

    public String getNome() {
        return nome;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getEnergiaMaxima() {
        return energiaMaxima;
    }

    public int getEnergiaAtual() {
        return energiaAtual;
    }

    public int getDefesaAtual() {
        return defesaAtual;
    }

    public List<Carta> getMao() {
        return Collections.unmodifiableList(mao);
    }

    public List<Carta> getBaralho() {
        return Collections.unmodifiableList(baralho);
    }

    public List<Carta> getDescarte() {
        return Collections.unmodifiableList(descarte);
    }

    public List<EfeitoStatus> getEfeitosAtivos() {
        return Collections.unmodifiableList(efeitosAtivos);
    }

    public int getQuantidadeCartasBaralho() {
        return baralho.size();
    }

    public int getQuantidadeCartasDescarte() {
        return descarte.size();
    }
}

