package org.application.loja;

public class Smartphone extends Item {
    private int armazenamentoGb;
    private int cameraMp;

    public Smartphone(int id, String nome, double preco, int quantidadeEstoque, int armazenamentoGb, int cameraMp) {
        super(id, nome, preco, quantidadeEstoque);
        setArmazenamentoGb(armazenamentoGb);
        setCameraMp(cameraMp);
    }

    public int getArmazenamentoGb() {
        return armazenamentoGb;
    }

    public void setArmazenamentoGb(int armazenamentoGb) {
        if (armazenamentoGb <= 0) {
            throw new IllegalArgumentException("O armazenamento deve ser maior que zero");
        }
        this.armazenamentoGb = armazenamentoGb;
    }

    public int getCameraMp() {
        return cameraMp;
    }

    public void setCameraMp(int cameraMp) {
        if (cameraMp <= 0) {
            throw new IllegalArgumentException("A câmera (MP) deve ser maior que zero");
        }
        this.cameraMp = cameraMp;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Smartphone -> " + detalhesBase()
                + String.format(", armazenamento=%dGB, camera=%dMP", armazenamentoGb, cameraMp));
    }
}
