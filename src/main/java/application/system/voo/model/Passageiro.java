package application.system.voo.model;

import java.util.ArrayList;
import java.util.List;

public class Passageiro {
    private static int contadorId = 1;
    private int             id;
    private String          nome;
    private String          cpf;
    private String          telefone;
    private String          email;
    private int             pontosFidelidade;
    private List<Reserva>   reservas;
    private boolean         ativo;

    public Passageiro(String nome, String cpf, String telefone, String email) {
        this.id = contadorId++;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.pontosFidelidade = 0;
        this.reservas = new ArrayList<>();
        this.ativo = true;
    }

    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public void adicionarPontos(int pontos) {
        this.pontosFidelidade += pontos;
        System.out.println("Passageiro " + nome + " ganhou " + pontos + " pontos. Total: " + pontosFidelidade);
    }

    public void exibirInformacoes() {
        System.out.println("--- PASSAGEIRO ---");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
        System.out.println("Pontos fidelidade: " + pontosFidelidade);
        System.out.println("Reservas realizadas: " + reservas.size());
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public int getPontosFidelidade() {
        return pontosFidelidade;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
