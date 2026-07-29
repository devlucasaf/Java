package application.utilitarios.agendador;

import java.time.LocalDateTime;

public class ServidorAgendador {

    public static void main(String[] args) throws InterruptedException {
        Agendador agendador = new Agendador();

        agendador.agendar(new Tarefa("t1", "Backup diario",
                ExpressaoCron.parse("0 2 * * *"),
                () -> System.out.println("  -> Executando backup completo do sistema")));

        agendador.agendar(new Tarefa("t2", "Limpeza de cache",
                ExpressaoCron.parse("*/5 * * * *"),
                () -> System.out.println("  -> Limpando cache temporario")));

        agendador.agendar(new Tarefa("t3", "Relatorio semanal",
                ExpressaoCron.parse("0 9 * * 1"),
                () -> System.out.println("  -> Gerando relatorio semanal")));

        agendador.agendar(new Tarefa("t4", "Ping de vida",
                ExpressaoCron.parse("* * * * *"),
                () -> System.out.println("  -> Pong! " + LocalDateTime.now())));

        agendador.iniciarModoRapido(1);

        System.out.println("Rodando por 65 segundos...");
        Thread.sleep(65_000);

        agendador.parar();
        System.out.println("\n=== Estatisticas ===");
        for (Tarefa t : agendador.getTarefas()) {
            System.out.println(t.getNome() + " -> executada " + t.getExecucoes() + " vez(es)");
        }
    }
}

