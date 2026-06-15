package org.application.system.restaurante;

import java.util.ArrayList;
import java.util.List;

public class Garcom extends Funcionario {
    private double  taxaGorjeta;
    private double  totalGorjetas;
    private int     totalPedidosAtendidos;
    private final List<Mesa> mesasAtendidas = new ArrayList<>();

    public Garcom(String nome, String idFuncionario, double salarioBase, double taxaGorjeta) {
        super(nome, idFuncionario, Cargo.GARCOM, salarioBase);
        setTaxaGorjeta(taxaGorjeta);
        this.totalGorjetas = 0;
        this.totalPedidosAtendidos = 0;
    }

    public double getTaxaGorjeta() {
        return taxaGorjeta;
    }

    public void setTaxaGorjeta(double taxaGorjeta) {
        if (taxaGorjeta < 0 || taxaGorjeta > 1) {
            throw new IllegalArgumentException("A taxa de gorjeta deve estar entre 0 e 1");
        }
        this.taxaGorjeta = taxaGorjeta;
    }

    public double getTotalGorjetas() {
        return totalGorjetas;
    }

    public void registrarAtendimento(double valorPedido) {
        totalGorjetas += valorPedido * taxaGorjeta;
        totalPedidosAtendidos++;
    }

    public void atribuirMesa(Mesa mesa) {
        mesasAtendidas.add(mesa);
    }

    public void liberarMesa(Mesa mesa) {
        mesasAtendidas.remove(mesa);
    }

    public List<Mesa> getMesasAtendidas() {
        return mesasAtendidas;
    }

    public int getTotalPedidosAtendidos() {
        return totalPedidosAtendidos;
    }

    @Override
    public double calcularRemuneracaoTotal() {
        return getSalarioBase() + totalGorjetas;
    }
}

