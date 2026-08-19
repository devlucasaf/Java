package application.system.automovel.locadora.service;

import application.system.automovel.locadora.enums.TipoSeguro;
import application.system.automovel.locadora.enums.FormaPagamento;
import application.system.automovel.locadora.enums.StatusLocacao;
import application.system.automovel.locadora.exception.ClienteNaoHabilitadoException;
import application.system.automovel.locadora.exception.DataInvalidaException;
import application.system.automovel.locadora.exception.LocacaoNaoEncontradaException;
import application.system.automovel.locadora.exception.VeiculoIndisponivelException;
import application.system.automovel.locadora.model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LocadoraService {
    private final Locadora locadora;
    private final ICalculadoraDiariaService calculadora;

    public LocadoraService(Locadora locadora, ICalculadoraDiariaService calculadora) {
        this.locadora = locadora;
        this.calculadora = calculadora;
    }

    // --- Cadastros ---
    public void cadastrarVeiculo(Veiculo veiculo) {
        locadora.adicionarVeiculo(veiculo);
    }

    public void cadastrarCliente(Cliente cliente) {
        locadora.adicionarCliente(cliente);
    }

    // --- Lógica de locação ---
    public Locacao realizarLocacao(String placa, String cpfCliente, LocalDate dataRetirada,
                                   LocalDate dataDevolucaoPrevista, TipoSeguro seguro,
                                   FormaPagamento formaPagamento)
            throws VeiculoIndisponivelException, ClienteNaoHabilitadoException, DataInvalidaException {

        // Validar datas
        if (dataDevolucaoPrevista.isBefore(dataRetirada) || dataDevolucaoPrevista.isEqual(dataRetirada)) {
            throw new DataInvalidaException("A data de devolução deve ser posterior à data de retirada.");
        }

        Veiculo veiculo = locadora.buscarVeiculoPorPlaca(placa);
        if (veiculo == null) {
            throw new VeiculoIndisponivelException("Veículo com placa " + placa + " não encontrado.");
        }

        if (!veiculo.isDisponivel()) {
            throw new VeiculoIndisponivelException("Veículo com placa " + placa + " não está disponível.");
        }

        Cliente cliente = locadora.buscarClientePorCpf(cpfCliente);
        if (cliente == null) {
            throw new ClienteNaoHabilitadoException("Cliente com CPF " + cpfCliente + " não cadastrado.");
        }

        if (!cliente.isHabilitado()) {
            throw new ClienteNaoHabilitadoException("Cliente " + cliente.getNome() + " não está habilitado (CNH vencida ou inativa).");
        }

        Locacao locacao = new Locacao(veiculo, cliente, dataRetirada, dataDevolucaoPrevista, seguro, formaPagamento);
        double valor = calculadora.calcular(locacao);
        locacao.setValorTotal(valor);

        veiculo.alugar();
        cliente.adicionarLocacao(locacao);
        cliente.adicionarPontos((int) (valor / 50));

        locadora.adicionarLocacao(locacao);
        return locacao;
    }

    public void devolverVeiculo(int idLocacao, LocalDate dataDevolucaoReal, double quilometragemAtual)
            throws LocacaoNaoEncontradaException, DataInvalidaException {
        Locacao locacao = locadora.buscarLocacaoPorId(idLocacao);
        if (locacao == null) {
            throw new LocacaoNaoEncontradaException("Locação #" + idLocacao + " não encontrada.");
        }

        if (locacao.getStatus() != StatusLocacao.ATIVA) {
            throw new LocacaoNaoEncontradaException("Locação #" + idLocacao + " não está ativa.");
        }

        if (dataDevolucaoReal.isBefore(locacao.getDataRetirada())) {
            throw new DataInvalidaException("Data de devolução não pode ser anterior à retirada.");
        }

        long diasPrevistos = locacao.calcularDiasPrevistos();
        long diasEfetivos = Math.max(1, ChronoUnit.DAYS.between(locacao.getDataRetirada(), dataDevolucaoReal));
        double multa = 0.0;

        if (diasEfetivos > diasPrevistos) {
            multa = (diasEfetivos - diasPrevistos) * locacao.getVeiculo().getValorDiaria() * 0.5;
        }
        double valorBase = locacao.getVeiculo().getValorDiaria() * diasEfetivos;
        double adicionalSeguro = (locacao.getSeguro() == TipoSeguro.COMPLETO) ? 50.0 : 0.0;
        double valorTotal = valorBase + adicionalSeguro + multa;

        locacao.finalizar(dataDevolucaoReal, valorTotal);
        locacao.getVeiculo().devolver(quilometragemAtual);
        locadora.adicionarCaixa(valorTotal);
        System.out.println("Devolução concluída. Valor total: R$" + valorTotal);
    }

    public void cancelarLocacao(int idLocacao) throws LocacaoNaoEncontradaException {
        Locacao locacao = locadora.buscarLocacaoPorId(idLocacao);
        if (locacao == null || locacao.getStatus() != StatusLocacao.ATIVA) {
            throw new LocacaoNaoEncontradaException("Locação ativa #" + idLocacao + " não encontrada.");
        }
        locacao.cancelar();
        System.out.println("Locação #" + idLocacao + " cancelada.");
    }

    // --- Consultas ---
    public List<Veiculo> listarVeiculosDisponiveis() {
        List<Veiculo> disponiveis = new ArrayList<>();
        for (Veiculo v : locadora.getVeiculos()) {
            if (v.isDisponivel()) {
                disponiveis.add(v);
            }
        }
        return disponiveis;
    }

    public List<Locacao> listarLocacoesAtivas() {
        List<Locacao> ativas = new ArrayList<>();
        for (Locacao l : locadora.getLocacoes()) {
            if (l.getStatus() == StatusLocacao.ATIVA) {
                ativas.add(l);
            }
        }
        return ativas;
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return locadora.buscarClientePorCpf(cpf);
    }
}
