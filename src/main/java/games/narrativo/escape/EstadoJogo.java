package games.narrativo.escape;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EstadoJogo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Sala> salas;
    private final Inventario        inventario;
    private String                  identificadorSalaAtual;
    private TipoFinal               tipoFinal;
    private boolean                 jogoEncerrado;
    private int                     quantidadeAcoes;

    public EstadoJogo() {
        this.salas = new HashMap<>();
        this.inventario = new Inventario();
        this.tipoFinal = TipoFinal.NENHUM;
        this.jogoEncerrado = false;
        this.quantidadeAcoes = 0;
    }

    // --- ADICIONA UMA SALA AO ESTADO DO JOGO ---
    public void adicionarSala(Sala sala) {
        if (sala == null) {
            throw new IllegalArgumentException("A sala não pode ser nula.");
        }

        salas.put(sala.getIdentificador(), sala);
    }

    // --- RETORNA A SALA ATUAL DO JOGADOR ---
    public Sala getSalaAtual() {
        return salas.get(identificadorSalaAtual);
    }

    public Sala buscarSala(String identificador) {
        return salas.get(identificador);
    }

    public Map<String, Sala> getSalas() {
        return Collections.unmodifiableMap(salas);
    }

    public Inventario getInventario() {
        return inventario;
    }

    public String getIdentificadorSalaAtual() {
        return identificadorSalaAtual;
    }

    public void setIdentificadorSalaAtual(String identificadorSalaAtual) {
        this.identificadorSalaAtual = identificadorSalaAtual;
    }

    public TipoFinal getTipoFinal() {
        return tipoFinal;
    }

    public void setTipoFinal(TipoFinal tipoFinal) {
        this.tipoFinal = tipoFinal;
    }

    public boolean isJogoEncerrado() {
        return jogoEncerrado;
    }

    public void setJogoEncerrado(boolean jogoEncerrado) {
        this.jogoEncerrado = jogoEncerrado;
    }

    public int getQuantidadeAcoes() {
        return quantidadeAcoes;
    }

    public void registrarAcao() {
        quantidadeAcoes++;
    }
}

