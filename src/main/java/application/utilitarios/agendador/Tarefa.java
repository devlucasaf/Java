package application.utilitarios.agendador;

import java.time.LocalDateTime;

public class Tarefa {

    private final String        id;
    private final String        nome;
    private final ExpressaoCron cron;
    private final Runnable      acao;
    private LocalDateTime       ultimaExecucao;
    private int                 execucoes;

    public Tarefa(String id, String nome, ExpressaoCron cron, Runnable acao) {
        this.id = id;
        this.nome = nome;
        this.cron = cron;
        this.acao = acao;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public ExpressaoCron getCron() {
        return cron;
    }

    public int getExecucoes() {
        return execucoes;
    }

    public LocalDateTime getUltimaExecucao() {
        return ultimaExecucao;
    }

    public void executar() {
        try {
            acao.run();
            execucoes++;
            ultimaExecucao = LocalDateTime.now();
        } catch (Exception e) {
            System.err.println("Erro na tarefa " + nome + ": " + e.getMessage());
        }
    }
}

