package application.system.condominio;

import java.math.BigDecimal;
import java.time.LocalTime;

public class AreaComum {

    private final long identificador;
    private String nome;
    private int capacidadeMaxima;
    private BigDecimal valorReserva;
    private LocalTime horarioAbertura;
    private LocalTime horarioFechamento;
    private boolean ativa;

    public AreaComum(long identificador, String nome, int capacidadeMaxima, BigDecimal valorReserva, LocalTime horarioAbertura, LocalTime horarioFechamento) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da área não pode estar vazio.");
        }

        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser maior que zero.");
        }

        if (valorReserva == null || valorReserva.signum() < 0) {
            throw new IllegalArgumentException("O valor da reserva não pode ser negativo.");
        }

        if (horarioAbertura == null || horarioFechamento == null || !horarioAbertura.isBefore(horarioFechamento)) {
            throw new IllegalArgumentException("O horário de funcionamento é inválido.");
        }

        this.identificador = identificador;
        this.nome = nome.trim();
        this.capacidadeMaxima = capacidadeMaxima;
        this.valorReserva = valorReserva;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.ativa = true;
    }

    // --- VERIFICA SE O HORÁRIO ESTÁ DENTRO DO FUNCIONAMENTO ---
    public boolean aceitaHorario(LocalTime inicio, LocalTime fim) {
        return inicio != null && fim != null && inicio.isBefore(fim) && !inicio.isBefore(horarioAbertura) && !fim.isAfter(horarioFechamento);
    }

    public long getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public BigDecimal getValorReserva() {
        return valorReserva;
    }

    public LocalTime getHorarioAbertura() {
        return horarioAbertura;
    }

    public LocalTime getHorarioFechamento() {
        return horarioFechamento;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    @Override
    public String toString() {
        return identificador + " - " + nome;
    }
}

