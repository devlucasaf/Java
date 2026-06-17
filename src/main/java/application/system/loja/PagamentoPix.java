package application.system.loja;

public class PagamentoPix extends Pagamento {
    private String chavePix;

    public PagamentoPix(double valor, String chavePix) {
        super(valor);
        setChavePix(chavePix);
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        if (chavePix == null || chavePix.isBlank()) {
            throw new IllegalArgumentException("A chave Pix não pode ser vazia");
        }
        this.chavePix = chavePix;
    }

    @Override
    public String processarPagamento() {
        return String.format("Pagamento PIX aprovado. chave=%s, valor=%.2f", chavePix, getValor());
    }
}
