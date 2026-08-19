package application.system.automovel.locadora.model;

import java.util.ArrayList;
import java.util.List;

public class Locadora {
    private String          nome;
    private String          cnpj;
    private String          endereco;
    private List<Veiculo>   veiculos;
    private List<Cliente>   clientes;
    private List<Locacao>   locacoes;
    private double          caixa;

    public Locadora(String nome, String cnpj, String endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.veiculos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.locacoes = new ArrayList<>();
        this.caixa = 0.0;
    }

    public void adicionarVeiculo(Veiculo v) {
        veiculos.add(v);
    }

    public void adicionarCliente(Cliente c) {
        clientes.add(c);
    }

    public void adicionarLocacao(Locacao l) {
        locacoes.add(l);
    }

    public void adicionarCaixa(double valor) {
        this.caixa += valor;
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    public Locacao buscarLocacaoPorId(int id) {
        for (Locacao l : locacoes) {
            if (l.getId() == id) {
                return l;
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

    public String getEndereco() {
        return endereco;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Locacao> getLocacoes() {
        return locacoes;
    }

    public double getCaixa() {
        return caixa;
    }

}
