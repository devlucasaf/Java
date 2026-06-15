package org.application.system.colegio.biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private String                  nome;
    private String                  cnpj;
    private String                  endereco;
    private List<Publicacao>        acervo;
    private List<UsuarioBiblioteca> usuarios;
    private List<Emprestimo>        emprestimos;
    private List<Reserva>           reservas;
    private List<Multa>             multas;

    public Biblioteca(String nome, String cnpj, String endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.acervo = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.multas = new ArrayList<>();
    }

    // Gerenciamento de acervo
    public void adicionarPublicacao(Publicacao p) {
        acervo.add(p);
        System.out.println("Publicação '" + p.getTitulo() + "' adicionada ao acervo.");
    }

    public void removerPublicacao(Publicacao p) {
        p.setAtivo(false);
        System.out.println("Publicação '" + p.getTitulo() + "' desativada.");
    }

    // Gerenciamento de usuários
    public void cadastrarUsuario(UsuarioBiblioteca u) {
        usuarios.add(u);
        System.out.println("Usuário " + u.getNome() + " cadastrado com matrícula " + u.getMatricula());
    }

    public UsuarioBiblioteca buscarUsuarioPorMatricula(String matricula) {
        return usuarios.stream().filter(u -> u.getMatricula().equals(matricula)).findFirst().orElse(null);
    }

    // Empréstimos
    public boolean realizarEmprestimo(String matricula, String tituloPublicacao, LocalDate data) {
        UsuarioBiblioteca u = buscarUsuarioPorMatricula(matricula);
        if (u == null || !u.isAtivo() || u.getMultaPendente() > 0) {
            System.out.println("Empréstimo negado: usuário inválido, inativo ou com multa pendente.");
            return false;
        }

        Publicacao p = buscarPublicacaoPorTitulo(tituloPublicacao);
        if (p == null || !p.estaDisponivel()) {
            System.out.println("Empréstimo negado: publicação não disponível.");
            return false;
        }

        // Verificar se há reserva ativa para outro usuário
        Reserva reservaAtiva = reservas.stream()
                .filter(r -> r.getPublicacao().equals(p) && r.isAtiva() && !r.getUsuario().equals(u))
                .findFirst().orElse(null);
        if (reservaAtiva != null) {
            System.out.println("Empréstimo negado: publicação reservada por " + reservaAtiva.getUsuario().getNome());
            return false;
        }

        if (p.emprestarExemplar()) {
            Emprestimo e = new Emprestimo(u, p, data);
            emprestimos.add(e);
            System.out.println("Empréstimo realizado com sucesso. Devolução até: " + e.getDataDevolucaoPrevista());
            return true;
        }
        return false;
    }

    public boolean realizarDevolucao(String matricula, String tituloPublicacao, LocalDate data) {
        Emprestimo e = buscarEmprestimoAtivo(matricula, tituloPublicacao);
        if (e == null) {
            System.out.println("Empréstimo ativo não encontrado para essa combinação.");
            return false;
        }
        e.devolver(data);
        return true;
    }

    private Emprestimo buscarEmprestimoAtivo(String matricula, String titulo) {
        return emprestimos.stream()
                .filter(e -> e.getUsuario().getMatricula().equals(matricula) &&
                        e.getPublicacao().getTitulo().equalsIgnoreCase(titulo) &&
                        (e.getStatus() == StatusEmprestimo.ATIVO || e.getStatus() == StatusEmprestimo.ATRASADO))
                .findFirst().orElse(null);
    }

    // Reservas
    public void realizarReserva(String matricula, String tituloPublicacao, LocalDate data) {
        UsuarioBiblioteca u = buscarUsuarioPorMatricula(matricula);
        Publicacao p = buscarPublicacaoPorTitulo(tituloPublicacao);
        if (u == null || p == null || !p.isAtivo()) {
            System.out.println("Reserva inválida.");
            return;
        }
        if (!p.estaDisponivel()) {
            Reserva r = new Reserva(u, p, data);
            reservas.add(r);
            System.out.println("Reserva realizada para " + u.getNome() + " sobre '" + p.getTitulo() + "'");
        } else {
            System.out.println("Publicação disponível. Não é necessário reserva.");
        }
    }

    private Publicacao buscarPublicacaoPorTitulo(String titulo) {
        return acervo.stream().filter(p -> p.getTitulo().equalsIgnoreCase(titulo) && p.isAtivo()).findFirst().orElse(null);
    }

    // Relatórios
    public void exibirAcervoCompleto() {
        System.out.println("\n===== ACERVO DA BIBLIOTECA =====");
        for (Publicacao p : acervo) {
            if (p.isAtivo()) {
                p.exibirInformacoes();
                System.out.println("----------------------");
            }
        }
    }

    public void exibirEmprestimosAtivos() {
        System.out.println("\n===== EMPRÉSTIMOS ATIVOS =====");
        for (Emprestimo e : emprestimos) {
            if (e.getStatus() == StatusEmprestimo.ATIVO || e.getStatus() == StatusEmprestimo.ATRASADO) {
                e.exibirDetalhes();
                System.out.println("----------------------");
            }
        }
    }

    public void exibirUsuariosComMulta() {
        System.out.println("\n===== USUÁRIOS COM MULTA =====");
        for (UsuarioBiblioteca u : usuarios) {
            if (u.getMultaPendente() > 0) {
                u.exibirInformacoes();
                System.out.println("----------------------");
            }
        }
    }

    public void exibirReservasAtivas() {
        System.out.println("\n===== RESERVAS ATIVAS =====");
        for (Reserva r : reservas) {
            if (r.isAtiva()) {
                r.exibirInformacoes();
            }
        }
    }
}
