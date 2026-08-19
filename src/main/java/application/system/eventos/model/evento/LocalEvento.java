package application.system.eventos.model.evento;

import application.system.eventos.enums.TipoLocal;

public class LocalEvento {
    private TipoLocal   tipoLocal;
    private String      pais;
    private String      estado;
    private String      cidade;
    private String      enderecoCompleto;
    private int         capacidade;

    public LocalEvento(TipoLocal tipoLocal, String pais, String estado, String cidade,
                       String enderecoCompleto, int capacidade) {
        this.tipoLocal = tipoLocal;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.enderecoCompleto = enderecoCompleto;
        this.capacidade = capacidade;
    }

    public void exibirLocal() {
        System.out.println("--- Local do Evento ---");
        System.out.println("Tipo: " + tipoLocal);
        System.out.println("País: " + pais);
        System.out.println("Estado: " + estado);
        System.out.println("Cidade: " + cidade);
        System.out.println("Endereço: " + enderecoCompleto);
        System.out.println("Capacidade: " + capacidade + " pessoas");
    }

    public boolean validarCapacidade(int ingressosVendidos) {
        return ingressosVendidos <= capacidade;
    }

    public TipoLocal getTipoLocal() {
        return tipoLocal;
    }

    public String getPais() {
        return pais;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
