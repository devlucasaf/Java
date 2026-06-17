package application.system.colegio.biblioteca;

import java.time.LocalDate;

public class Multa {
    private static int contadorId = 1;
    private int                 id;
    private UsuarioBiblioteca   usuario;
    private double              valor;
    private String              motivo;
    private LocalDate           dataGeracao;
    private boolean             paga;

    public Multa(UsuarioBiblioteca usuario, double valor, String motivo, LocalDate dataGeracao) {
        this.id = contadorId++;
        this.usuario = usuario;
        this.valor = valor;
        this.motivo = motivo;
        this.dataGeracao = dataGeracao;
        this.paga = false;
    }

    public void pagar() {
        this.paga = true;
        usuario.pagarMulta(valor);
        System.out.println("Multa de R$" + valor + " paga por " + usuario.getNome());
    }

    public void exibirInformacoes() {
        System.out.println("Multa #" + id + " - " + usuario.getNome() + " - R$" + valor + " - " + motivo + " - " + (paga ? "Paga" : "Pendente"));
    }

    public double getValor() {
        return valor;
    }

    public boolean isPaga() {
        return paga;
    }
}