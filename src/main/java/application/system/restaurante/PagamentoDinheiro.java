package application.system.restaurante;

public class PagamentoDinheiro extends Pagamento {
    private double valorRecebido;

    public PagamentoDinheiro(double valor, double valorRecebido) {
        super(valor);
        setValorRecebido(valorRecebido);
    }

    public double getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(double valorRecebido) {
        if (valorRecebido < getValor()) {
            throw new IllegalArgumentException("Valor recebido insuficiente");
        }
        this.valorRecebido = valorRecebido;
    }

    public double getTroco() {
        return valorRecebido - getValor();
    }

    @Override
    public String processarPagamento() {
        double troco = getTroco();
        String trocoInfo = troco > 0 ? String.format(" | Troco: R$%.2f", troco) : "";
        return String.format("Pagamento de R$%.2f em Dinheiro (Recebido: R$%.2f)%s processado com sucesso!",
                getValor(),
                valorRecebido,
                trocoInfo
        );
    }

    @Override
    public String getTipoPagamento() {
        return "Dinheiro";
    }
}

