package application.system.loja;

public class Laptop extends Item {
    private int     ramGb;
    private String  processador;

    public Laptop(int id, String nome, double preco, int quantidadeEstoque, int ramGb, String processador) {
        super(id, nome, preco, quantidadeEstoque);
        setRamGb(ramGb);
        setProcessador(processador);
    }

    public int getRamGb() {
        return ramGb;
    }

    public void setRamGb(int ramGb) {
        if (ramGb <= 0) {
            throw new IllegalArgumentException("A RAM deve ser maior que zero");
        }
        this.ramGb = ramGb;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        if (processador == null || processador.isBlank()) {
            throw new IllegalArgumentException("O processador não pode ser vazio");
        }
        this.processador = processador;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Laptop -> " + detalhesBase()
                + String.format(", ram=%dGB, processador=%s", ramGb, processador));
    }
}
