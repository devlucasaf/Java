package application.exercicios.faculdade.bsbcompute.core;

import application.exercicios.faculdade.bsbcompute.config.*;
import application.exercicios.faculdade.bsbcompute.model.*;
import application.exercicios.faculdade.bsbcompute.scheduler.Scheduler;
import application.exercicios.faculdade.bsbcompute.worker.Worker;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Master {

    public static int run(Config config, String policy) {

        BlockingQueue<EventMessage> outQueue = new LinkedBlockingQueue<>();
        AtomicBoolean stop = new AtomicBoolean(false);

        List<WorkerInfo> workers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (ServidorConfig s : config.servidores) {
            BlockingQueue<TaskMessage> q = new LinkedBlockingQueue<>();
            WorkerInfo w = new WorkerInfo(s.id, s.capacidade, q);
            workers.add(w);

            Thread t = new Thread(new Worker(s.id, s.capacidade, q, outQueue, stop));
            threads.add(t);
            t.start();
        }

        List<Request> pending = new ArrayList<>();
        for (RequisicaoConfig r : config.requisicoes) {
            pending.add(new Request(r.id, r.tipo, r.prioridade, r.tempoExecucao, 0));
        }

        double start = System.nanoTime() / 1e9;

        while (!pending.isEmpty()) {

            WorkerInfo w = Scheduler.chooseBestWorker(workers);
            if (w == null) {
                continue;
            }

            Request r = Scheduler.pickNext(pending, policy, 0);
            pending.remove(r);

            System.out.println("Requisição " + r.id + " atribuída ao Servidor " + w.id);

            w.taskQueue.offer(new TaskMessage(r, start));
        }

        stop.set(true);
        return 0;
    }
}
