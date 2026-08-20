package application.system.voo.model;

import java.util.ArrayList;
import java.util.List;

public class SistemaVoo {
    private String              nome;
    private String              cnpj;
    private List<Voo>           voos;
    private List<Passageiro>    passageiros;
    private List<Reserva>       reservas;
    private double              caixa;

    public SistemaVoo(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.voos = new ArrayList<>();
        this.passageiros = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.caixa = 0.0;
    }

    public void adicionarVoo(Voo v) {
        voos.add(v);
    }

    public void adicionarPassageiro(Passageiro p) {
        passageiros.add(p);
    }

    public void adicionarReserva(Reserva r) {
        reservas.add(r);
    }

    public void adicionarCaixa(double valor) {
        this.caixa += valor;
    }

    public Voo buscarVooPorId(int id) {
        for (Voo v : voos) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    public Passageiro buscarPassageiroPorCpf(String cpf) {
        for (Passageiro p : passageiros) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    public Reserva buscarReservaPorId(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public List<Voo> getVoos() {
        return voos;
    }

    public List<Passageiro> getPassageiros() {
        return passageiros;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public double getCaixa() {
        return caixa;
    }

}
