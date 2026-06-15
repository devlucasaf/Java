package org.application.system.restaurante;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma reserva de mesa.
 */
public class Reserva {
    private static int sequencia = 1;

    private final int           id;
    private final Cliente       cliente;
    private final Mesa          mesa;
    private final LocalDateTime dataHoraReserva;
    private final int           numeroPessoas;
    private boolean             ativa;
    private String              observacao;

    public Reserva(Cliente cliente, Mesa mesa, LocalDateTime dataHoraReserva, int numeroPessoas) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo");
        }

        if (mesa == null) {
            throw new IllegalArgumentException("A mesa não pode ser nula");
        }

        if (dataHoraReserva == null) {
            throw new IllegalArgumentException("A data/hora não pode ser nula");
        }

        if (numeroPessoas <= 0) {
            throw new IllegalArgumentException("Número de pessoas deve ser maior que zero");
        }

        if (numeroPessoas > mesa.getCapacidade()) {
            throw new IllegalArgumentException("Número de pessoas excede a capacidade da mesa");
        }

        this.id = sequencia++;
        this.cliente = cliente;
        this.mesa = mesa;
        this.dataHoraReserva = dataHoraReserva;
        this.numeroPessoas = numeroPessoas;
        this.ativa = true;
        this.observacao = "";
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public LocalDateTime getDataHoraReserva() {
        return dataHoraReserva;
    }

    public int getNumeroPessoas() {
        return numeroPessoas;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void cancelar() {
        this.ativa = false;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao != null ? observacao : "";
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Reserva #%d | Cliente: %s | Mesa %d | %s | %d pessoas | %s",
                id,
                cliente.getNome(),
                mesa.getNumero(),
                dataHoraReserva.format(fmt),
                numeroPessoas,
                ativa ? "Ativa" : "Cancelada"
        );
    }
}

