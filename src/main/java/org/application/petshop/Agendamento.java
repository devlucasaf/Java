package org.application.petshop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Agendamento {
    private static int contadorId = 1;
    private int                 id;
    private ClientePetshop      cliente;
    private Animal              animal;
    private Servico             servico;
    private LocalDateTime       dataHora;
    private StatusAgendamento   status;
    private Veterinario         veterinarioResponsavel;

    public Agendamento(ClientePetshop cliente, Animal animal, Servico servico, LocalDateTime dataHora) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.animal = animal;
        this.servico = servico;
        this.dataHora = dataHora;
        this.status = StatusAgendamento.PENDENTE;
    }

    public void confirmar() {
        this.status = StatusAgendamento.CONFIRMADO;
        System.out.println("Agendamento #" + id + " confirmado.");
    }

    public void cancelar() {
        this.status = StatusAgendamento.CANCELADO;
        System.out.println("Agendamento #" + id + " cancelado.");
    }

    public void realizar() {
        this.status = StatusAgendamento.REALIZADO;
        System.out.println("Serviço " + servico.getTipo() + " realizado para " + animal.getNome());
    }

    public void exibirDetalhes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("Agendamento #" + id + " - Cliente: " + cliente.getNome() + " | Animal: " + animal.getNome());
        System.out.println("Serviço: " + servico.getTipo() + " | Data: " + dataHora.format(formatter));
        System.out.println("Status: " + status);

        if (veterinarioResponsavel != null && servico.getTipo() == TipoServico.CONSULTA_VETERINARIA) {
            System.out.println("Veterinário: " + veterinarioResponsavel.getNome());
        }
    }

    public int getId() {
        return id;
    }

    public ClientePetshop getCliente() {
        return cliente;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Servico getServico() {
        return servico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setVeterinarioResponsavel(Veterinario veterinarioResponsavel) {
        this.veterinarioResponsavel = veterinarioResponsavel;
    }
}