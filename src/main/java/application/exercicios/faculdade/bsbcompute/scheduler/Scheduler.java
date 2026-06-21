package application.exercicios.faculdade.bsbcompute.scheduler;

import application.exercicios.faculdade.bsbcompute.model.*;
import java.util.*;

public class Scheduler {

    public static Request pickNext(List<Request> pending, String policy, int rrIndex) {
        return switch (policy) {
            case "sjf" -> Collections.min(pending, Comparator.comparingDouble(r -> r.tempoExecucao));
            case "priority" -> Collections.min(pending, Comparator.comparingInt(r -> r.prioridade));
            default -> pending.get(rrIndex % pending.size());
        };
    }

    public static WorkerInfo chooseBestWorker(List<WorkerInfo> workers) {
        return workers.stream()
                .filter(w -> w.active < w.capacidade)
                .min(Comparator.comparingDouble(w -> (double) w.active / w.capacidade))
                .orElse(null);
    }
}