package org.application.biblioteca;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {
    private static int contadorId = 1;
    private int                 id;
    private UsuarioBiblioteca   usuario;
    private Publicacao          publicacao;
    private LocalDate           dataEmprestimo;
    private LocalDate           dataDevolucaoPrevista;
    private LocalDate           dataDevolucaoReal;
    private StatusEmprestimo    status;
    private double              multaCobrada;

    public Emprestimo(UsuarioBiblioteca usuario, Publicacao publicacao, LocalDate dataEmprestimo) {
        this.id = contadorId++;
        this.usuario = usuario;
        this.publicacao = publicacao;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(usuario.getPrazoEmprestimo());
        this.status = StatusEmprestimo.ATIVO;
        this.multaCobrada = 0.0;
    }

    public void devolver(LocalDate dataDevolucao) {
        this.dataDevolucaoReal = dataDevolucao;
        if (dataDevolucao.isAfter(dataDevolucaoPrevista)) {
            long diasAtraso = ChronoUnit.DAYS.between(dataDevolucaoPrevista, dataDevolucao);
            this.multaCobrada = diasAtraso * 2.0; // R$2,00 por dia de atraso
            this.status = StatusEmprestimo.ATRASADO;
            usuario.adicionarMulta(multaCobrada);
            System.out.println("Empréstimo devolvido com ATRASO de " + diasAtraso + " dias. Multa: R$" + multaCobrada);
        } else {
            this.status = StatusEmprestimo.DEVOLVIDO;
            System.out.println("Empréstimo devolvido no prazo.");
        }
        publicacao.devolverExemplar();
    }

    public void exibirDetalhes() {
        System.out.println("--- EMPRÉSTIMO #" + id + " ---");
        System.out.println("Usuário: " + usuario.getNome());
        System.out.println("Publicação: " + publicacao.getTitulo());
        System.out.println("Data empréstimo: " + dataEmprestimo);
        System.out.println("Data prevista: " + dataDevolucaoPrevista);
        System.out.println("Status: " + status);

        if (dataDevolucaoReal != null) {
            System.out.println("Data devolução: " + dataDevolucaoReal);
        }
    }

    public int getId() {
        return id;
    }

    public UsuarioBiblioteca getUsuario() {
        return usuario;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }
}