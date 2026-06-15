package org.application.system.cinema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Sessao {
    private static int contadorId = 1;
    private int             id;
    private Filme           filme;
    private Sala            sala;
    private LocalDateTime   horario;
    private double          precoBase;
    private StatusSessao    status;
    private List<Ingresso>  ingressosVendidos;

    public Sessao(Filme filme, Sala sala, LocalDateTime horario, double precoBase) {
        this.id = contadorId++;
        this.filme = filme;
        this.sala = sala;
        this.horario = horario;
        this.precoBase = precoBase;
        this.status = StatusSessao.DISPONIVEL;
        this.ingressosVendidos = new ArrayList<>();
    }

    public double calcularPrecoIngresso(TipoIngresso tipo) {
        double preco = precoBase;
        if (sala.getTipo() == TipoSala.TRIDIMENSIONAL) {
            preco *= 1.3;
        }

        if (sala.getTipo() == TipoSala.IMAX) {
            preco *= 1.5;
        }

        if (sala.getTipo() == TipoSala.VIP) {
            preco *= 2.0;
        }

        if (tipo == TipoIngresso.MEIA) {
            preco /= 2;
        }

        if (tipo == TipoIngresso.VIP) {
            preco *= 1.2;
        }
        return preco;
    }

    public boolean venderIngresso(Ingresso ingresso) {
        if (status != StatusSessao.DISPONIVEL) {
            return false;
        }

        if (ingresso.getAssento() != null && !ingresso.getAssento().isDisponivel()) {
            return false;
        }

        if (ingressosVendidos.size() >= sala.getCapacidade()) {
            status = StatusSessao.ESGOTADO;
            return false;
        }
        ingressosVendidos.add(ingresso);
        if (ingresso.getAssento() != null) {
            ingresso.getAssento().ocupar();
        }

        if (ingressosVendidos.size() == sala.getCapacidade()) {
            status = StatusSessao.ESGOTADO;
        }
        return true;
    }

    public int lugaresDisponiveis() {
        return sala.getCapacidade() - ingressosVendidos.size();
    }

    public void exibirInformacoes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("Sessão #" + id + " - " + filme.getTitulo());
        System.out.println("Sala " + sala.getNumero() + " (" + sala.getTipo() + ") - Horário: " + horario.format(formatter));
        System.out.println("Preço base: R$" + precoBase);
        System.out.println("Lugares disponíveis: " + lugaresDisponiveis() + "/" + sala.getCapacidade());
        System.out.println("Status: " + status);
    }

    public int getId() {
        return id;
    }

    public Filme getFilme() {
        return filme;
    }

    public Sala getSala() {
        return sala;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public StatusSessao getStatus() {
        return status;
    }

    public List<Ingresso> getIngressosVendidos() {
        return ingressosVendidos;
    }
}