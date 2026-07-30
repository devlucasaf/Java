package application.simuladores.produtorconsumidor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumidor implements Runnable {

    private final String                nome;
    private final BlockingQueue<Item>   fila;
    private final AtomicBoolean         executando;
    private final int                   atrasoMinMs;
    private final int                   atrasoMaxMs;
    private int                         consumidos = 0;

    public Consumidor(String nome, BlockingQueue<Item> fila, AtomicBoolean executando,
                      int atrasoMinMs, int atrasoMaxMs) {
        this.nome = nome;
        this.fila = fila;
        this.executando = executando;
        this.atrasoMinMs = atrasoMinMs;
        this.atrasoMaxMs = atrasoMaxMs;
    }

    public int getConsumidos() {
        return consumidos;
    }

    @Override
    public void run() {
        try {
            while (executando.get() || !fila.isEmpty()) {
                Item item = fila.poll(500, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (item == null) {
                    continue;
                }
                consumidos++;
                System.out.println("  [CONSUMIDOR " + nome + "] consumiu " + item
                        + " | latencia="
                        + (System.currentTimeMillis() - item.getTimestamp()) + "ms");
                Thread.sleep(ThreadLocalRandom.current()
                        .nextInt(atrasoMinMs, atrasoMaxMs + 1));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("  [CONSUMIDOR " + nome + "] encerrou. Total: " + consumidos);
    }
}

