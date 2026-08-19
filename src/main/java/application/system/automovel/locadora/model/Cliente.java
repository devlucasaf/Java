package application.system.automovel.locadora.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    private String          numeroCnh;
    private LocalDate       dataValidadeCnh;
    private int             pontosFidelidade;
    private List<Locacao>   locacoes;
    private boolean         ativo;

    public Cliente(String nome, String cpf, String telefone, String email,
                   String numeroCnh, LocalDate dataValidadeCnh) {
        super(nome, cpf, telefone, email);
        this.numeroCnh = numeroCnh;
        this.dataValidadeCnh = dataValidadeCnh;
        this.pontosFidelidade = 0;
        this.locacoes = new ArrayList<>();
        this.ativo = true;
    }

    public void adicionarLocacao(Locacao locacao) {
        locacoes.add(locacao);
    }

    public void adicionarPontos(int pontos) {
        this.pontosFidelidade += pontos;
        System.out.println("Cliente " + nome + " ganhou " + pontos + " pontos. Total: " + pontosFidelidade);
    }

    public boolean isHabilitado() {
        return ativo && dataValidadeCnh != null && dataValidadeCnh.isAfter(LocalDate.now());
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- CLIENTE ---");
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
        System.out.println("CNH: " + numeroCnh + " (válida até " + dataValidadeCnh + ")");
        System.out.println("Pontos fidelidade: " + pontosFidelidade);
        System.out.println("Locações realizadas: " + locacoes.size());
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
    }

    public String getNumeroCnh() {
        return numeroCnh;
    }

    public LocalDate getDataValidadeCnh() {
        return dataValidadeCnh;
    }

    public int getPontosFidelidade() {
        return pontosFidelidade;
    }

    public List<Locacao> getLocacoes() {
        return locacoes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
