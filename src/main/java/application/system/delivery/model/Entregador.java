package application.system.delivery.model;

import application.system.delivery.enums.Status;

public class Entregador {

    private final String    id;
    private final String    nome;
    private String          bairroAtual;
    private Status          status;
    private double          avaliacaoMedia;
    private int             totalEntregas;

    public Entregador(String id, String nome, String bairroInicial) {
        this.id = id;
        this.nome = nome;
        this.bairroAtual = bairroInicial;
        this.status = Status.DISPONIVEL;
        this.avaliacaoMedia = 5.0;
        this.totalEntregas = 0;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getBairroAtual() {
        return bairroAtual;
    }

    public void moverPara(String bairro) {
        this.bairroAtual = bairro;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void registrarEntregaConcluida(double notaAvaliacao) {
        this.totalEntregas++;
        this.avaliacaoMedia = (this.avaliacaoMedia + notaAvaliacao) / 2;
    }

    public double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public int getTotalEntregas() {
        return totalEntregas;
    }
}
