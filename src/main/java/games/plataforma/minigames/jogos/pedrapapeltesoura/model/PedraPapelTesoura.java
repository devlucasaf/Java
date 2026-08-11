package games.plataforma.minigames.jogos.pedrapapeltesoura.model;

import games.plataforma.minigames.util.GeradorAleatorio;

public class PedraPapelTesoura {
    private int     vitorias;
    private int     derrotas;
    private int     empates;
    private Jogada  jogadaComputador;
    private Jogada  jogadaJogador;
    private String  resultado;

    public PedraPapelTesoura() {
        reiniciarPlacar();
    }

    public void jogar(Jogada jogadaJogador) {
        this.jogadaJogador = jogadaJogador;
        jogadaComputador = Jogada.values()[GeradorAleatorio.nextInt(3)];
        resultado = determinarVencedor(jogadaJogador, jogadaComputador);
        atualizarPlacar();
    }

    private String determinarVencedor(Jogada jogada1, Jogada jogada2) {
        if (jogada1 == jogada2) {
            return "Empate";
        }

        if ((jogada1 == Jogada.PEDRA && jogada2 == Jogada.TESOURA) ||
                (jogada1 == Jogada.PAPEL && jogada2 == Jogada.PEDRA) ||
                (jogada1 == Jogada.TESOURA && jogada2 == Jogada.PAPEL)) {
            return "Jogador";
        } else {
            return "Computador";
        }
    }

    private void atualizarPlacar() {
        if (resultado.equals("Jogador")) {
            vitorias++;
        } else if (resultado.equals("Computador")) {
            derrotas++;
        } else {
            empates++;
        }
    }

    public void reiniciarPlacar() {
        vitorias = 0;
        derrotas = 0;
        empates = 0;
    }

    public Jogada getJogadaComputador() {
        return jogadaComputador;
    }

    public Jogada getJogadaJogador() {
        return jogadaJogador;
    }

    public String getResultado() {
        return resultado;
    }

    public int getVitorias() {
        return vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getEmpates() {
        return empates;
    }
}
