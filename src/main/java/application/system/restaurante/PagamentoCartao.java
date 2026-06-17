package application.system.restaurante;

public class PagamentoCartao extends Pagamento {
    private String  numeroCartao;
    private boolean credito;
    private int     parcelas;

    public PagamentoCartao(double valor, String numeroCartao, boolean credito, int parcelas) {
        super(valor);
        setNumeroCartao(numeroCartao);
        this.credito = credito;
        setParcelas(parcelas);
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        if (numeroCartao == null || numeroCartao.isBlank()) {
            throw new IllegalArgumentException("O número do cartão não pode ser vazio");
        }
        this.numeroCartao = numeroCartao;
    }

    public boolean isCredito() {
        return credito;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        if (credito && parcelas < 1) {
            throw new IllegalArgumentException("Parcelas deve ser pelo menos 1");
        }

        if (!credito && parcelas != 1) {
            throw new IllegalArgumentException("Débito não permite parcelamento");
        }
        this.parcelas = parcelas;
    }

    @Override
    public String processarPagamento() {
        String tipo = credito ? "Crédito" : "Débito";
        String parcelaInfo = parcelas > 1 ? String.format(" em %dx de R$%.2f", parcelas, getValor() / parcelas) : "";
        return String.format("Pagamento de R$%.2f via Cartão %s (****%s)%s processado com sucesso!",
                getValor(),
                tipo,
                numeroCartao,
                parcelaInfo
        );
    }

    @Override
    public String getTipoPagamento() {
        return credito ? "Cartão Crédito" : "Cartão Débito";
    }
}

