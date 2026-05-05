package org.application.loja;

public class PagamentoCartaoCredito extends Pagamento {
    private String  bandeiraCartao;
    private int     parcelas;

    public PagamentoCartaoCredito(double valor, String bandeiraCartao, int parcelas) {
        super(valor);
        setBandeiraCartao(bandeiraCartao);
        setParcelas(parcelas);
    }

    public String getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(String bandeiraCartao) {
        if (bandeiraCartao == null || bandeiraCartao.isBlank()) {
            throw new IllegalArgumentException("A bandeira do cartão não pode ser vazia");
        }
        this.bandeiraCartao = bandeiraCartao;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        if (parcelas <= 0) {
            throw new IllegalArgumentException("O número de parcelas deve ser maior que zero");
        }
        this.parcelas = parcelas;
    }

    @Override
    public String processarPagamento() {
        return String.format("Pagamento cartão de crédito aprovado. bandeira=%s, parcelas=%d, valor=%.2f",
                bandeiraCartao, parcelas, getValor());
    }
}
