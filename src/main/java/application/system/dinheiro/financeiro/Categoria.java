package application.system.dinheiro.financeiro;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private static int contadorId = 1;
    private int             id;
    private String          nome;
    private TipoTransacao   tipoPermitido;
    private List<Categoria> subcategorias;

    public Categoria(String nome, TipoTransacao tipoPermitido) {
        this.id = contadorId++;
        this.nome = nome;
        this.tipoPermitido = tipoPermitido;
        this.subcategorias = new ArrayList<>();
    }

    public void adicionarSubcategoria(Categoria sub) {
        if (sub.getTipoPermitido() == this.tipoPermitido) {
            subcategorias.add(sub);
        } else {
            System.out.println("Erro: tipo de subcategoria incompatível.");
        }
    }

    public boolean isDespesa() {
        return tipoPermitido == TipoTransacao.DESPESA;
    }

    public boolean isReceita() {
        return tipoPermitido == TipoTransacao.RECEITA;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoTransacao getTipoPermitido() {
        return tipoPermitido;
    }

    public List<Categoria> getSubcategorias() {
        return subcategorias;
    }

    @Override
    public String toString() {
        return "Categoria{" + nome + " (" + tipoPermitido + ")}";
    }
}