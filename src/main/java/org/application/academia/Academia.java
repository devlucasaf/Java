package org.application.academia;

import java.util.ArrayList;
import java.util.List;

public class Academia {
    private String              nome;
    private String              cnpj;
    private String              endereco;
    private List<Aluno>         alunos;
    private List<Funcionario>   funcionarios;
    private List<Plano>         planos;
    private List<Treino>        treinos;
    private double              caixa;
    private List<Pagamento>     pagamentosRegistrados;

    public Academia(String nome, String cnpj, String endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.alunos = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.planos = new ArrayList<>();
        this.treinos = new ArrayList<>();
        this.pagamentosRegistrados = new ArrayList<>();
        this.caixa = 0.0;
    }

    public void matricularAluno(Aluno aluno) {
        alunos.add(aluno);
        System.out.println("Aluno " + aluno.getNome() + " matriculado na academia " + nome);
    }

    public void contratarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
        System.out.println("Funcionário " + funcionario.getNome() + " contratado.");
    }

    public void adicionarPlano(Plano plano) {
        planos.add(plano);
        System.out.println("Plano " + plano.getTipo() + " adicionado.");
    }

    public void adicionarTreino(Treino treino) {
        treinos.add(treino);
        System.out.println("Treino '" + treino.getNome() + "' criado.");
    }

    public void registrarPagamentoAcademia(Pagamento pagamento) {
        pagamentosRegistrados.add(pagamento);
        if (pagamento.getStatus() == StatusPagamento.PAGO) {
            caixa += pagamento.getValor();
            System.out.println("Pagamento de R$" + pagamento.getValor() + " registrado no caixa.");
        }
    }

    public void exibirRelatorioFinanceiro() {
        double totalRecebido = 0;
        double totalPendente = 0;

        for (Pagamento p : pagamentosRegistrados) {
            if (p.getStatus() == StatusPagamento.PAGO) {
                totalRecebido += p.getValor();
            } else if (p.getStatus() == StatusPagamento.PENDENTE || p.getStatus() == StatusPagamento.ATRASADO) {
                totalPendente += p.getValor();
            }
        }
        System.out.println("\n--- RELATÓRIO FINANCEIRO DA ACADEMIA ---");
        System.out.println("Caixa atual: R$" + caixa);
        System.out.println("Total recebido (histórico): R$" + totalRecebido);
        System.out.println("Total pendente: R$" + totalPendente);
    }

    public void exibirAlunosInadimplentes() {
        System.out.println("\n--- ALUNOS INADIMPLENTES ---");
        for (Aluno a : alunos) {
            boolean inadimplente = false;
            for (Pagamento p : pagamentosRegistrados) {
                if (p.getAluno().equals(a) && p.getStatus() == StatusPagamento.ATRASADO) {
                    inadimplente = true;
                    break;
                }
            }
            if (inadimplente) {
                System.out.println("- " + a.getNome() + " (" + a.getMatricula() + ")");
            }
        }
    }

    public void exibirResumoGeral() {
        System.out.println("\n========== RESUMO DA ACADEMIA ==========");
        System.out.println("Academia: " + nome);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("Endereço: " + endereco);
        System.out.println("Alunos ativos: " + alunos.size());
        System.out.println("Funcionários: " + funcionarios.size());
        System.out.println("Planos disponíveis: " + planos.size());
        System.out.println("Treinos cadastrados: " + treinos.size());
        System.out.println("Caixa: R$" + caixa);
        System.out.println("========================================\n");
    }

    public String getNome() {
        return nome;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
}