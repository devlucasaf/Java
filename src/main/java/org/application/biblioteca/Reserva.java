package org.application.biblioteca;

import java.time.LocalDate;

public class Reserva {
    private static int contadorId = 1;
    private int                 id;
    private UsuarioBiblioteca   usuario;
    private Publicacao          publicacao;
    private LocalDate           dataReserva;
    private boolean             ativa;

    public Reserva(UsuarioBiblioteca usuario, Publicacao publicacao, LocalDate dataReserva) {
        this.id = contadorId++;
        this.usuario = usuario;
        this.publicacao = publicacao;
        this.dataReserva = dataReserva;
        this.ativa = true;
    }

    public void cancelar() {
        this.ativa = false;
        System.out.println("Reserva cancelada para " + usuario.getNome() + " - " + publicacao.getTitulo());
    }

    public void exibirInformacoes() {
        System.out.println("Reserva #" + id + " - " + usuario.getNome() + " reservou '" + publicacao.getTitulo() + "' em " + dataReserva);
    }

    public UsuarioBiblioteca getUsuario() {
        return usuario;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public boolean isAtiva() {
        return ativa;
    }
}