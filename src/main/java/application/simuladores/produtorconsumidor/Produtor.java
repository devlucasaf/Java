package application.simuladores.produtorconsumidor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Produtor implements Runnable {

    private static final AtomicInteger CONTADOR = new AtomicInteger(0);

    private final String nome;
    private final BlockingQueue<Item> fila;
    private final int quantidade;
    private final int atrasoMinMs;
    private final int atrasoMaxMs;

    public Produtor(String nome, BlockingQueue<Item> fila, int quantidade,
                    int atrasoMinMs, int atrasoMaxMs) {
        this.nome = nome;
        this.fila = fila;
        this.quantidade = quantidade;
        this.atrasoMinMs = atrasoMinMs;
        this.atrasoMaxMs = atrasoMaxMs;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < quantidade; i++) {
                int id = CONTADOR.incrementAndGet();
                Item item = new Item(id, "gerado por " + nome);
                fila.put(item);
                System.out.println("[PRODUTOR " + nome + "] produziu " + item
                        + " | fila=" + fila.size());
                Thread.sleep(ThreadLocalRandom.current()
                        .nextInt(atrasoMinMs, atrasoMaxMs + 1));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[PRODUTOR " + nome + "] interrompido");
        }
    }
}

