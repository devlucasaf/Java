package application.system.restaurante;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Restaurante {
    private String                  nome;
    private String                  endereco;
    private String                  telefone;
    private final Cardapio          cardapio;
    private final List<Mesa>        mesas;
    private final List<Funcionario> funcionarios;
    private final List<Cliente>     clientes;
    private final List<Pedido>      pedidos;
    private final List<Reserva>     reservas;
    private final List<Avaliacao>   avaliacoes;
    private double                  faturamentoTotal;

    public Restaurante(String nome, String endereco, String telefone) {
        setNome(nome);
        setEndereco(endereco);
        setTelefone(telefone);
        this.cardapio = new Cardapio();
        this.mesas = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.faturamentoTotal = 0;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.isBlank()) {
            throw new IllegalArgumentException("O endereço não pode ser vazio");
        }
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("O telefone não pode ser vazio");
        }
        this.telefone = telefone;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }


    public void adicionarMesa(Mesa mesa) {
        mesas.add(mesa);
    }

    public Mesa buscarMesa(int numero) {
        return mesas.stream()
                .filter(m -> m.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Mesa " + numero + " não encontrada"));
    }

    public List<Mesa> getMesasLivres() {
        return mesas.stream()
                .filter(m -> m.getStatus() == StatusMesa.LIVRE)
                .collect(Collectors.toList());
    }

    public List<Mesa> getMesasOcupadas() {
        return mesas.stream()
                .filter(m -> m.getStatus() == StatusMesa.OCUPADA)
                .collect(Collectors.toList());
    }

    public List<Mesa> getTodasMesas() {
        return Collections.unmodifiableList(mesas);
    }


    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public List<Funcionario> getFuncionarios() {
        return Collections.unmodifiableList(funcionarios);
    }

    public List<Garcom> getGarcons() {
        return funcionarios.stream()
                .filter(f -> f instanceof Garcom)
                .map(f -> (Garcom) f)
                .collect(Collectors.toList());
    }

    public List<Cozinheiro> getCozinheiros() {
        return funcionarios.stream()
                .filter(f -> f instanceof Cozinheiro)
                .map(f -> (Cozinheiro) f)
                .collect(Collectors.toList());
    }


    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> getClientes() {
        return Collections.unmodifiableList(clientes);
    }

    public void registrarPedido(Pedido pedido) {
        pedidos.add(pedido);
        faturamentoTotal += pedido.calcularTotal();
    }

    public List<Pedido> getPedidos() {
        return Collections.unmodifiableList(pedidos);
    }

    public List<Pedido> getPedidosAbertos() {
        return pedidos.stream()
                .filter(p -> !p.isFinalizado())
                .collect(Collectors.toList());
    }


    public Reserva fazerReserva(Cliente cliente, int numeroMesa, LocalDateTime dataHora, int numeroPessoas) {
        Mesa mesa = buscarMesa(numeroMesa);
        Reserva reserva = new Reserva(cliente, mesa, dataHora, numeroPessoas);
        mesa.reservar();
        reservas.add(reserva);
        return reserva;
    }

    public void cancelarReserva(int idReserva) {
        Reserva reserva = reservas.stream()
                .filter(r -> r.getId() == idReserva && r.isAtiva())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada ou já cancelada"));
        reserva.cancelar();
        reserva.getMesa().limpar();
    }

    public List<Reserva> getReservasAtivas() {
        return reservas.stream()
                .filter(Reserva::isAtiva)
                .collect(Collectors.toList());
    }


    public void adicionarAvaliacao(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
    }

    public double getNotaMediaGeral() {
        if (avaliacoes.isEmpty()) {
            return 0;
        }
        return avaliacoes.stream().mapToDouble(Avaliacao::getNotaMedia).average().orElse(0);
    }

    public List<Avaliacao> getAvaliacoes() {
        return Collections.unmodifiableList(avaliacoes);
    }


    public double getFaturamentoTotal() {
        return faturamentoTotal;
    }

    public int getTotalPedidosRealizados() {
        return pedidos.size();
    }

    public ItemCardapio getItemMaisPedido() {
        Map<ItemCardapio, Integer> contagem = new HashMap<>();
        for (Pedido pedido : pedidos) {
            for (PedidoItem pi : pedido.getItens()) {
                contagem.merge(pi.getItem(), pi.getQuantidade(), Integer::sum);
            }
        }
        return contagem.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Garcom getGarcomMaisAtendimentos() {
        return getGarcons().stream()
                .max(Comparator.comparingInt(Garcom::getTotalPedidosAtendidos))
                .orElse(null);
    }

    public void exibirRelatorio() {
        System.out.println("\n           RELATÓRIO DO RESTAURANTE                   ");
        System.out.printf(" Nome: %s%n", nome);
        System.out.printf(" Endereço: %s%n", endereco);
        System.out.printf(" Telefone: %s%n", telefone);
        System.out.printf(" Total de pedidos: %d%n", getTotalPedidosRealizados());
        System.out.printf(" Faturamento total: R$%.2f%n", faturamentoTotal);
        System.out.printf(" Nota média: %.1f/5.0%n", getNotaMediaGeral());
        System.out.printf(" Mesas livres: %d/%d%n", getMesasLivres().size(), mesas.size());
        System.out.printf(" Clientes cadastrados: %d%n", clientes.size());
        System.out.printf(" Funcionários: %d%n", funcionarios.size());

        ItemCardapio maisPedido = getItemMaisPedido();
        if (maisPedido != null) {
            System.out.printf(" Item mais pedido: %s%n", maisPedido.getNome());
        }

        Garcom melhorGarcom = getGarcomMaisAtendimentos();
        if (melhorGarcom != null) {
            System.out.printf(" Garçom destaque: %s (%d atendimentos)%n",
                    melhorGarcom.getNome(), melhorGarcom.getTotalPedidosAtendidos());
        }

    }

    public void exibirStatusMesas() {
        System.out.println("\n=== STATUS DAS MESAS ===");
        for (Mesa mesa : mesas) {
            System.out.println(mesa);
        }
    }
}

