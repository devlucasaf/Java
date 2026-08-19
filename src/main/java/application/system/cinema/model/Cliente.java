package application.system.cinema.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    private String          codigoCliente;
    private int             pontosFidelidade;
    private List<Compra>    compras;
    private boolean         ativo;

    public Cliente(String nome, String cpf, String telefone, String email, String codigoCliente) {
        super(nome, cpf, telefone, email);
        this.codigoCliente = codigoCliente;
        this.pontosFidelidade = 0;
        this.compras = new ArrayList<>();
        this.ativo = true;
    }

    public void adicionarPontos(int pontos) {
        this.pontosFidelidade += pontos;
        System.out.println("Cliente " + nome + " ganhou " + pontos + " pontos. Total: " + pontosFidelidade);
    }

    public boolean resgatarMeiaEntrada() {
        if (pontosFidelidade >= 50) {
            pontosFidelidade -= 50;
            System.out.println("Cliente " + nome + " resgatou uma meia-entrada. Pontos restantes: " + pontosFidelidade);
            return true;
        } else {
            System.out.println("Pontos insuficientes para meia-entrada. Necessário 50 pontos.");
            return false;
        }
    }

    public void adicionarCompra(Compra compra) {
        compras.add(compra);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- CLIENTE ---");
        System.out.println("Nome: " + nome);
        System.out.println("Código: " + codigoCliente);
        System.out.println("CPF: " + cpf);
        System.out.println("Telefone: " + telefone);
        System.out.println("Pontos fidelidade: " + pontosFidelidade);
        System.out.println("Compras realizadas: " + compras.size());
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public int getPontosFidelidade() {
        return pontosFidelidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
