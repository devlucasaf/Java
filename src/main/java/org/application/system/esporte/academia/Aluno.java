package org.application.system.esporte.academia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa {
    private String                  matricula;
    private LocalDate               dataMatricula;
    private Plano                   plano;
    private boolean                 ativo;
    private List<Treino>            treinosAtribuidos;
    private List<AvaliacaoFisica>   avaliacoes;
    private List<Pagamento>         pagamentos;
    private Instrutor               instrutorResponsavel;

    public Aluno(String nome, String cpf, String telefone, String email, String endereco,
                 String matricula, LocalDate dataMatricula, Plano plano) {
        super(nome, cpf, telefone, email, endereco);
        this.matricula = matricula;
        this.dataMatricula = dataMatricula;
        this.plano = plano;
        this.ativo = true;
        this.treinosAtribuidos = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
    }

    public void atribuirTreino(Treino treino) {
        treinosAtribuidos.add(treino);
        System.out.println("Treino '" + treino.getNome() + "' atribuído ao aluno " + nome);
    }

    public void adicionarAvaliacao(AvaliacaoFisica avaliacao) {
        avaliacoes.add(avaliacao);
        System.out.println("Avaliação física registrada para " + nome);
    }

    public void registrarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
        if (pagamento.getStatus() == StatusPagamento.PAGO) {
            System.out.println("Pagamento de R$" + pagamento.getValor() + " registrado para " + nome);
        } else {
            System.out.println("Pagamento pendente para " + nome);
        }
    }

    public void verificarStatusFinanceiro() {
        boolean possuiPendencia = false;
        for (Pagamento p : pagamentos) {
            if (p.getStatus() != StatusPagamento.PAGO && p.getDataVencimento().isBefore(LocalDate.now())) {
                possuiPendencia = true;
                break;
            }
        }

        if (possuiPendencia) {
            System.out.println("ALERTA: Aluno " + nome + " possui pagamentos pendentes!");
        } else {
            System.out.println("Situação financeira do aluno " + nome + " está regular.");
        }
    }

    public void exibirHistoricoAvaliacoes() {
        System.out.println("\n--- Histórico de Avaliações de " + nome + " ---");
        for (AvaliacaoFisica a : avaliacoes) {
            a.exibirResumo();
        }
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- ALUNO ---");
        System.out.println("Nome: " + nome);
        System.out.println("Matrícula: " + matricula);
        System.out.println("Plano: " + plano.getTipo());
        System.out.println("Status: " + (ativo ? "Ativo" : "Inativo"));
        System.out.println("Data matrícula: " + dataMatricula);

        if (instrutorResponsavel != null)
            System.out.println("Instrutor responsável: " + instrutorResponsavel.getNome());
        System.out.println("Treinos atribuídos: " + treinosAtribuidos.size());
    }

    public String getMatricula() {
        return matricula;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Treino> getTreinosAtribuidos() {
        return treinosAtribuidos;
    }

    public Instrutor getInstrutorResponsavel() {
        return instrutorResponsavel;
    }

    public void setInstrutorResponsavel(Instrutor instrutorResponsavel) {
        this.instrutorResponsavel = instrutorResponsavel;
    }
}
