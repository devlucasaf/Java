package games.strategy.cardbattle;

import java.util.Objects;

public class EfeitoStatus {

    private final TipoEfeito    tipo;
    private final int           intensidade;
    private int                 turnosRestantes;

    public EfeitoStatus(TipoEfeito tipo, int intensidade, int turnosRestantes) {
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo do efeito não pode ser nulo.");
        }

        if (intensidade <= 0) {
            throw new IllegalArgumentException("A intensidade deve ser maior que zero.");
        }

        if (turnosRestantes <= 0) {
            throw new IllegalArgumentException("A duração deve ser maior que zero.");
        }

        this.tipo = tipo;
        this.intensidade = intensidade;
        this.turnosRestantes = turnosRestantes;
    }

    // --- REDUZ A DURAÇÃO DO EFEITO EM UM TURNO ---
    public void reduzirDuracao() {
        if (turnosRestantes > 0) {
            turnosRestantes--;
        }
    }

    // --- VERIFICA SE O EFEITO TERMINOU ---
    public boolean isExpirado() {
        return turnosRestantes <= 0;
    }

    // --- CRIA UMA CÓPIA INDEPENDENTE DO EFEITO ---
    public EfeitoStatus copiar() {
        return new EfeitoStatus(tipo, intensidade, turnosRestantes);
    }

    public TipoEfeito getTipo() {
        return tipo;
    }

    public int getIntensidade() {
        return intensidade;
    }

    public int getTurnosRestantes() {
        return turnosRestantes;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof EfeitoStatus outroEfeito)) {
            return false;
        }

        return tipo == outroEfeito.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo);
    }

    @Override
    public String toString() {
        return tipo.getNomeFormatado() + " +" + intensidade + " por " + turnosRestantes + " turno(s)";
    }
}

