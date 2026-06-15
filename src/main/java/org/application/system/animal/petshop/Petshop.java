package org.application.system.animal.petshop;

import java.util.ArrayList;
import java.util.List;

public class Petshop {
    private String                  nome;
    private String                  cnpj;
    private String                  endereco;
    private List<ClientePetshop>    clientes;
    private List<Animal>            animais;
    private List<Veterinario>       veterinarios;
    private List<Servico>           servicos;
    private List<Produto>           produtos;
    private List<Agendamento>       agendamentos;
    private List<Venda>             vendas;
    private double                  caixa;

    public Petshop(String nome, String cnpj, String endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.clientes = new ArrayList<>();
        this.animais = new ArrayList<>();
        this.veterinarios = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.produtos = new ArrayList<>();
        this.agendamentos = new ArrayList<>();
        this.vendas = new ArrayList<>();
        this.caixa = 0.0;
    }

    // Cadastros
    public void cadastrarCliente(ClientePetshop cliente) {
        clientes.add(cliente);
        System.out.println("Cliente " + cliente.getNome() + " cadastrado.");
    }

    public void cadastrarAnimal(Animal animal) {
        animais.add(animal);
        System.out.println("Animal " + animal.getNome() + " cadastrado no petshop.");
    }

    public void contratarVeterinario(Veterinario vet) {
        veterinarios.add(vet);
        System.out.println("Veterinário " + vet.getNome() + " contratado.");
    }

    public void adicionarServico(Servico servico) {
        servicos.add(servico);
        System.out.println("Serviço " + servico.getTipo() + " adicionado.");
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        System.out.println("Produto " + produto.getNome() + " adicionado ao estoque.");
    }

    // Agendamentos
    public void criarAgendamento(ClientePetshop cliente, Animal animal, Servico servico,
                                 java.time.LocalDateTime dataHora, Veterinario vet) {
        if (servico.getTipo() == TipoServico.CONSULTA_VETERINARIA && vet == null) {
            System.out.println("Consulta veterinária requer um veterinário.");
            return;
        }
        Agendamento ag = new Agendamento(cliente, animal, servico, dataHora);
        if (vet != null) ag.setVeterinarioResponsavel(vet);
        agendamentos.add(ag);
        System.out.println("Agendamento criado para " + cliente.getNome() + " - " + animal.getNome());
    }

    // Vendas
    public Venda iniciarVenda(ClientePetshop cliente, FormaPagamentoPetshop forma) {
        Venda venda = new Venda(cliente, forma);
        vendas.add(venda);
        return venda;
    }

    // Relatórios
    public void exibirClientes() {
        System.out.println("\n=== CLIENTES ===");
        for (ClientePetshop c : clientes) {
            c.exibirInformacoes();
            System.out.println("------------------");
        }
    }

    public void exibirAnimais() {
        System.out.println("\n=== ANIMAIS ===");
        for (Animal a : animais) {
            a.exibirInformacoes();
            System.out.println("------------------");
        }
    }

    public void exibirAgendamentosDoDia() {
        System.out.println("\n=== AGENDAMENTOS DE HOJE ===");
        java.time.LocalDate hoje = java.time.LocalDate.now();
        for (Agendamento a : agendamentos) {
            if (a.getDataHora().toLocalDate().equals(hoje)) {
                a.exibirDetalhes();
                System.out.println("------------------");
            }
        }
    }

    public void exibirEstoqueBaixo(int limite) {
        System.out.println("\n=== PRODUTOS COM ESTOQUE BAIXO (<= " + limite + ") ===");
        for (Produto p : produtos) {
            if (p.getEstoque() <= limite) {
                p.exibirInformacoes();
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public List<ClientePetshop> getClientes() {
        return clientes;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}