package application.system.restaurante;

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
            throw new IllegalArgumentException("A chave PIX não pode ser vazia");
        }
        this.chavePix = chavePix;
    }

    @Override
    public String processarPagamento() {
        return String.format("Pagamento de R$%.2f via PIX (chave: %s) processado com sucesso!", getValor(), chavePix);
    }

    @Override
    public String getTipoPagamento() {
        return "PIX";
    }
}

