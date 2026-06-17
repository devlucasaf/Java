package application.system.restaurante;

import java.util.ArrayList;
import java.util.List;

public class Cozinheiro extends Funcionario {
    private String  especialidade;
    private int     pratosPreprados;
    private final List<ItemCardapio> pratosAssinatura = new ArrayList<>();

    public Cozinheiro(String nome, String idFuncionario, Cargo cargo, double salarioBase, String especialidade) {
        super(nome, idFuncionario, cargo, salarioBase);
        setEspecialidade(especialidade);
        this.pratosPreprados = 0;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.isBlank()) {
            throw new IllegalArgumentException("A especialidade não pode ser vazia");
        }
        this.especialidade = especialidade;
    }

    public int getPratosPreprados() {
        return pratosPreprados;
    }

    public void prepararPrato() {
        pratosPreprados++;
    }

    public void adicionarPratoAssinatura(ItemCardapio item) {
        pratosAssinatura.add(item);
    }

    public List<ItemCardapio> getPratosAssinatura() {
        return pratosAssinatura;
    }

    @Override
    public double calcularRemuneracaoTotal() {
        return getSalarioBase() + (pratosPreprados * 2.0);
    }
}

