package org.application.eventos;

import java.util.ArrayList;
import java.util.List;

public class Patrocinio {
    private String          nomeEmpresa;
    private double          valor;
    private NivelPatrocinio nivel;
    private List<String>    beneficios;

    public Patrocinio(String nomeEmpresa, double valor, NivelPatrocinio nivel) {
        this.nomeEmpresa = nomeEmpresa;
        this.valor = valor;
        this.nivel = nivel;
        this.beneficios = new ArrayList<>();
    }

    public void adicionarBeneficio(String beneficio) {
        beneficios.add(beneficio);
    }

    public void exibirInfo() {
        System.out.println("Patrocinador: " + nomeEmpresa + " - Nível " + nivel + " - R$" + valor);
        System.out.println("Benefícios: " + (beneficios.isEmpty() ? "Nenhum" : String.join(", ", beneficios)));
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public double getValor() {
        return valor;
    }

    public NivelPatrocinio getNivel() {
        return nivel;
    }
}
