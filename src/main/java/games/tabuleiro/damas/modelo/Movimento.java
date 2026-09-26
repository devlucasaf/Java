package games.tabuleiro.damas.modelo;

import java.util.List;
import java.util.Objects;

/**
 * Jogada completa de uma peça: a casa de origem, as casas por onde ela pousa
 * (uma para movimentos simples, várias em capturas em sequência) e as peças capturadas.
 * <p>
 * Em uma captura, o passo {@code i} do caminho captura a peça {@code capturadas.get(i)}.
 */
public final class Movimento {

    private final Posicao origem;
    private final List<Posicao> caminho;
    private final List<Posicao> capturadas;

    public Movimento(Posicao origem, List<Posicao> caminho, List<Posicao> capturadas) {
        if (caminho.isEmpty()) {
            throw new IllegalArgumentException("O movimento precisa ter ao menos um destino.");
        }
        if (!capturadas.isEmpty() && capturadas.size() != caminho.size()) {
            throw new IllegalArgumentException("Cada passo de uma captura deve capturar exatamente uma peça.");
        }
        this.origem = Objects.requireNonNull(origem);
        this.caminho = List.copyOf(caminho);
        this.capturadas = List.copyOf(capturadas);
    }

    /** Cria um movimento simples (sem captura) de uma casa para outra. */
    public static Movimento simples(Posicao origem, Posicao destino) {
        return new Movimento(origem, List.of(destino), List.of());
    }

    public Posicao getOrigem() {
        return origem;
    }

    public Posicao getDestino() {
        return caminho.get(caminho.size() - 1);
    }

    public List<Posicao> getCaminho() {
        return caminho;
    }

    public List<Posicao> getCapturadas() {
        return capturadas;
    }

    public boolean isCaptura() {
        return !capturadas.isEmpty();
    }

    public int getQuantidadeCapturas() {
        return capturadas.size();
    }

    /** Peça capturada no passo informado, ou {@code null} se o movimento não for de captura. */
    public Posicao getCapturaDoPasso(int indicePasso) {
        return isCaptura() ? capturadas.get(indicePasso) : null;
    }

    /** Descrição legível, por exemplo "c3 → d4" ou "c3 × e5 × c7". */
    public String getDescricao() {
        String separador = isCaptura() ? " × " : " → ";
        StringBuilder texto = new StringBuilder(origem.notacao());
        for (Posicao passo : caminho) {
            texto.append(separador).append(passo.notacao());
        }
        return texto.toString();
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (!(outro instanceof Movimento movimento)) {
            return false;
        }
        return origem.equals(movimento.origem)
                && caminho.equals(movimento.caminho)
                && capturadas.equals(movimento.capturadas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origem, caminho, capturadas);
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
