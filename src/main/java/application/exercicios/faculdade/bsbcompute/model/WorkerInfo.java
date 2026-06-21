package application.exercicios.faculdade.bsbcompute.model;

import java.util.concurrent.BlockingQueue;

public class WorkerInfo {
    public int                          id;
    public int                          capacidade;
    public BlockingQueue<TaskMessage>   taskQueue;

    public volatile int active = 0;
    public double totalEffectiveWork = 0.0;

    public WorkerInfo(int id, int capacidade, BlockingQueue<TaskMessage> queue) {
        this.id = id;
        this.capacidade = capacidade;
        this.taskQueue = queue;
    }
}