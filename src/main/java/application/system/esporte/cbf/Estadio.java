package application.system.esporte.cbf;

public class Estadio {
    private String  nome;
    private String  cidade;
    private String  estado;
    private int     capacidade;
    private int     anoInauguracao;

    public Estadio(String nome, String cidade, String estado, int capacidade, int anoInauguracao) {
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
        this.capacidade = capacidade;
        this.anoInauguracao = anoInauguracao;
    }

    public void exibirInformacoes() {
        System.out.println("--- ESTÁDIO ---");
        System.out.println("Nome: " + nome);
        System.out.println("Local: " + cidade + "/" + estado);
        System.out.println("Capacidade: " + capacidade + " pessoas");
        System.out.println("Inauguração: " + anoInauguracao);
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
