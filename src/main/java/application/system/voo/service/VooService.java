package application.system.voo.service;

import application.system.voo.enums.ClasseAssento;
import application.system.voo.enums.FormaPagamento;
import application.system.voo.enums.StatusReserva;
import application.system.voo.exception.*;
import application.system.voo.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class VooService {
    private final SistemaVoo sistema;
    private final ICalculadoraPrecoService calculadora;

    public VooService(SistemaVoo sistema, ICalculadoraPrecoService calculadora) {
        this.sistema = sistema;
        this.calculadora = calculadora;
    }

    // --- Cadastros ---
    public void cadastrarVoo(Voo voo) {
        sistema.adicionarVoo(voo);
    }

    public void cadastrarPassageiro(Passageiro passageiro) {
        sistema.adicionarPassageiro(passageiro);
    }

    // --- Reserva ---
    public Reserva fazerReserva(int idVoo, String cpfPassageiro, ClasseAssento classe,
                                FormaPagamento formaPagamento)
            throws VooIndisponivelException, PassageiroNaoEncontradoException, DataInvalidaException {

        // Buscar voo
        Voo voo = sistema.buscarVooPorId(idVoo);
        if (voo == null || !voo.isAtivo()) {
            throw new VooIndisponivelException("Voo #" + idVoo + " não encontrado ou inativo.");
        }

        // Verificar disponibilidade
        if (!voo.temVagasDisponiveis()) {
            throw new VooIndisponivelException("Voo #" + idVoo + " está lotado.");
        }

        // Verificar se a data do voo já passou
        if (voo.getDataHoraPartida().isBefore(LocalDateTime.now())) {
            throw new DataInvalidaException("O voo já partiu. Não é possível fazer reserva.");
        }

        // Buscar passageiro
        Passageiro passageiro = sistema.buscarPassageiroPorCpf(cpfPassageiro);
        if (passageiro == null || !passageiro.isAtivo()) {
            throw new PassageiroNaoEncontradoException("Passageiro com CPF " + cpfPassageiro + " não encontrado ou inativo.");
        }

        // Calcular preço
        double preco = calculadora.calcular(voo, classe);

        // Criar reserva
        Reserva reserva = new Reserva(voo, passageiro, classe, formaPagamento, preco);
        voo.ocuparLugar();
        passageiro.adicionarReserva(reserva);

        int pontos = (int) (preco / 10);
        passageiro.adicionarPontos(pontos);

        sistema.adicionarReserva(reserva);
        sistema.adicionarCaixa(preco);

        System.out.println("Reserva #" + reserva.getId() + " confirmada para " + passageiro.getNome());
        return reserva;
    }

    // --- Cancelamento ---
    public void cancelarReserva(int idReserva) throws ReservaNaoEncontradaException {
        Reserva reserva = sistema.buscarReservaPorId(idReserva);
        if (reserva == null) {
            throw new ReservaNaoEncontradaException("Reserva #" + idReserva + " não encontrada.");
        }

        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            System.out.println("Reserva já está cancelada.");
            return;
        }
        reserva.cancelar();
    }

    // --- Consultas ---
    public List<Voo> listarVoosDisponiveis() {
        return sistema.getVoos().stream()
                .filter(v -> v.isAtivo() && v.temVagasDisponiveis())
                .toList();
    }

    public List<Reserva> listarReservasPorPassageiro(String cpf) {
        Passageiro p = sistema.buscarPassageiroPorCpf(cpf);
        if (p == null) {
            return List.of();
        }
        return p.getReservas();
    }

    public Passageiro buscarPassageiroPorCpf(String cpf) {
        return sistema.buscarPassageiroPorCpf(cpf);
    }
}
