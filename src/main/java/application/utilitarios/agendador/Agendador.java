package application.utilitarios.agendador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Agendador {

    private final List<Tarefa> tarefas = new CopyOnWriteArrayList<>();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    private LocalDateTime ultimoTick;

    // --- ADICIONA UMA TAREFA A LISTA DO AGENDADOR ---
    public void agendar(Tarefa tarefa) {
        tarefas.add(tarefa);
        System.out.println("Tarefa agendada: " + tarefa.getNome() + " (" + tarefa.getCron().getTextoOriginal() + ")");
    }

    // --- INICIA O AGENDADOR COM VERIFICACOES A CADA MINUTO ---
    public void iniciar() {
        System.out.println("Agendador iniciado. Tarefas registradas: " + tarefas.size());
        long delayInicial = 60 - LocalDateTime.now().getSecond();
        executor.scheduleAtFixedRate(this::tick, delayInicial, 60, TimeUnit.SECONDS);
    }

    // --- INICIA O AGENDADOR COM UM INTERVALO PERSONALIZADO EM SEGUNDOS ---
    public void iniciarModoRapido(long intervaloSegundos) {
        System.out.println("Agendador em modo rapido: verificando a cada " + intervaloSegundos + "s");
        executor.scheduleAtFixedRate(this::tick, 0, intervaloSegundos, TimeUnit.SECONDS);
    }

    // --- VERIFICA E EXECUTA AS TAREFAS COMPATIVEIS COM O HORARIO ATUAL ---
    private void tick() {
        LocalDateTime agora = LocalDateTime.now().withSecond(0).withNano(0);

        if (agora.equals(ultimoTick)) {
            return;
        }
        ultimoTick = agora;

        for (Tarefa t : tarefas) {
            if (t.getCron().casaCom(agora)) {
                System.out.println("[" + agora.format(DateTimeFormatter.ISO_LOCAL_TIME) + "] disparando: " + t.getNome());
                t.executar();
            }
        }
    }

    // --- ENCERRA O SERVICO RESPONSAVEL PELO AGENDAMENTO ---
    public void parar() {
        executor.shutdown();
    }

    // --- RETORNA A LISTA DE TAREFAS REGISTRADAS ---
    public List<Tarefa> getTarefas() {
        return tarefas;
    }
}

