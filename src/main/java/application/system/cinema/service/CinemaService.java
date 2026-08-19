package application.system.cinema.service;

import application.system.cinema.enums.FormaPagamento;
import application.system.cinema.enums.StatusSessao;
import application.system.cinema.enums.TipoIngresso;
import application.system.cinema.model.*;

public class CinemaService {
    private final Cinema cinema;

    public CinemaService(Cinema cinema) {
        this.cinema = cinema;
    }

    public void adicionarFilme(Filme filme) {
        cinema.adicionarFilme(filme);
    }

    public void adicionarSala(Sala sala) {
        cinema.adicionarSala(sala);
    }

    public void adicionarSessao(Sessao sessao) {
        cinema.adicionarSessao(sessao);
    }

    public void cadastrarCliente(Cliente cliente) {
        cinema.cadastrarCliente(cliente);
    }

    public void contratarFuncionario(Funcionario funcionario) {
        cinema.contratarFuncionario(funcionario);
    }

    // Lógica de compra
    public Compra iniciarCompra(Cliente cliente, FormaPagamento formaPagamento) {
        Compra compra = new Compra(cliente, formaPagamento);
        cinema.adicionarCompra(compra);
        return compra;
    }

    public boolean adicionarIngressoCompra(Compra compra, int idSessao, String codigoAssento, TipoIngresso tipo) {
        Sessao sessao = buscarSessaoPorId(idSessao);
        if (sessao == null || sessao.getStatus() != StatusSessao.DISPONIVEL) {
            return false;
        }

        Sala sala = sessao.getSala();
        Assento assento = sala.buscarAssento(codigoAssento);

        if (assento == null || !assento.isDisponivel()) {
            return false;
        }

        Cliente cliente = compra.getCliente();
        Ingresso ingresso = new Ingresso(sessao, assento, cliente, tipo);
        compra.adicionarIngresso(ingresso);
        return true;
    }

    public boolean finalizarCompra(Compra compra) {
        if (compra.finalizar()) {
            cinema.adicionarCaixa(compra.getValorTotal());
            return true;
        }
        return false;
    }

    private Sessao buscarSessaoPorId(int id) {
        return cinema.getSessoes().stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
