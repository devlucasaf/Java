package games.arcade.corrida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Corrida {
    private final List<Corredor>    corredores;
    private final List<Corredor>    classificacao;
    private volatile boolean        temVencedor = false;
    private final int               distancia;

    public Corrida(int distancia) {
        this.distancia = distancia;
        this.corredores = new ArrayList<>();
        this.classificacao = Collections.synchronizedList(new ArrayList<>());
    }

    public void adicionarCorredor(String nome, String emoji) {
        corredores.add(new Corredor(nome, emoji, distancia, this));
    }

    public synchronized void registrarChegada(Corredor corredor) {
        if (!classificacao.contains(corredor)) {
            classificacao.add(corredor);
            if (classificacao.size() == 1) {
                temVencedor = true;
            }
        }
    }

    public void iniciar() {
        System.out.println("\n3... 2... 1... VALENDO!\n");

        List<Thread> threads = new ArrayList<>();
        for (Corredor c : corredores) {
            Thread t = new Thread(c, c.getNome());
            threads.add(t);
            t.start();
        }

        // Thread de renderização
        while (!todasChegaram(threads)) {
            renderizar();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                break;
            }
        }

        // Espera todas terminarem
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {}
        }

        renderizar();
        mostrarResultado();
    }

    private boolean todasChegaram(List<Thread> threads) {
        return threads.stream().noneMatch(Thread::isAlive);
    }

    private void renderizar() {
        System.out.print("\033[" + (corredores.size() + 2) + "A");
        System.out.println("═".repeat(60));
        for (Corredor c : corredores) {
            System.out.println(c.getPista());
        }
        System.out.println("═".repeat(60));
    }

    private void mostrarResultado() {
        System.out.println("\n  === RESULTADO FINAL ===");
        for (int i = 0; i < classificacao.size(); i++) {
            String medal = switch (i) {
                case 0 -> " ";
                case 1 -> " ";
                case 2 -> " ";
                default -> (i + 1) + "º";
            };
            System.out.println(medal + " " + classificacao.get(i).getNome());
        }

        if (!classificacao.isEmpty()) {
            System.out.println("\n  Vencedor: " + classificacao.get(0).getNome() + "!");
        }
    }

    public boolean temVencedor() {
        return temVencedor;
    }

    public List<Corredor> getCorredores() {
        return corredores;
    }
}

