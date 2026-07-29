package application.simuladores.produtorconsumidor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimuladorProdutorConsumidor {

    public static void main(String[] args) throws InterruptedException {
        int capacidadeFila = 10;
        int numProdutores = 3;
        int numConsumidores = 2;
        int itensPorProdutor = 8;

        BlockingQueue<Item> fila = new ArrayBlockingQueue<>(capacidadeFila);
        AtomicBoolean produtoresAtivos = new AtomicBoolean(true);

        List<Thread> threadsProdutores = new ArrayList<>();
        for (int i = 1; i <= numProdutores; i++) {
            Produtor p = new Produtor("P" + i, fila, itensPorProdutor, 100, 400);
            Thread t = new Thread(p);
            t.start();
            threadsProdutores.add(t);
        }

        List<Thread> threadsConsumidores = new ArrayList<>();
        List<Consumidor> consumidores = new ArrayList<>();
        for (int i = 1; i <= numConsumidores; i++) {
            Consumidor c = new Consumidor("C" + i, fila, produtoresAtivos, 200, 600);
            Thread t = new Thread(c);
            t.start();
            threadsConsumidores.add(t);
            consumidores.add(c);
        }

        for (Thread t : threadsProdutores) t.join();
        System.out.println("\n=== Todos produtores terminaram ===\n");
        produtoresAtivos.set(false);

        for (Thread t : threadsConsumidores) t.join();

        int totalConsumido = 0;
        for (Consumidor c : consumidores) totalConsumido += c.getConsumidos();
        int totalProduzido = numProdutores * itensPorProdutor;

        System.out.println("\n=== RESUMO ===");
        System.out.println("Produzido: " + totalProduzido);
        System.out.println("Consumido: " + totalConsumido);
        System.out.println("Sobrando na fila: " + fila.size());
    }
}

