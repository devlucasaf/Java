package application.system.voo.model;

import application.system.voo.enums.CategoriaVoo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Voo {
    private static int contadorId = 1;
    private int             id;
    private String          origem;
    private String          destino;
    private LocalDateTime   dataHoraPartida;
    private int             duracaoMinutos;
    private int             capacidadeTotal;
    private int             lugaresOcupados;
    private double          precoBase;
    private CategoriaVoo    categoria;
    private boolean         ativo;

    public Voo(String origem, String destino, LocalDateTime dataHoraPartida, int duracaoMinutos,
               int capacidadeTotal, double precoBase, CategoriaVoo categoria) {
        this.id = contadorId++;
        this.origem = origem;
        this.destino = destino;
        this.dataHoraPartida = dataHoraPartida;
        this.duracaoMinutos = duracaoMinutos;
        this.capacidadeTotal = capacidadeTotal;
        this.lugaresOcupados = 0;
        this.precoBase = precoBase;
        this.categoria = categoria;
        this.ativo = true;
    }

    public boolean temVagasDisponiveis() {
        return lugaresOcupados < capacidadeTotal;
    }

    public int vagasDisponiveis() {
        return capacidadeTotal - lugaresOcupados;
    }

    public void ocuparLugar() {
        if (temVagasDisponiveis()) {
            lugaresOcupados++;
        }
    }

    public void liberarLugar() {
        if (lugaresOcupados > 0) {
            lugaresOcupados--;
        }
    }

    public void exibirInformacoes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("--- VOO #" + id + " ---");
        System.out.println("Origem: " + origem);
        System.out.println("Destino: " + destino);
        System.out.println("Partida: " + dataHoraPartida.format(formatter));
        System.out.println("Duração: " + duracaoMinutos + " min");
        System.out.println("Capacidade: " + capacidadeTotal + " (ocupados: " + lugaresOcupados + ")");
        System.out.println("Preço base: R$" + precoBase);
        System.out.println("Categoria: " + categoria);
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
    }

    public int getId() {
        return id;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDateTime getDataHoraPartida() {
        return dataHoraPartida;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public int getCapacidadeTotal() {
        return capacidadeTotal;
    }

    public int getLugaresOcupados() {
        return lugaresOcupados;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public CategoriaVoo getCategoria() {
        return categoria;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
