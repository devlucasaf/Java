package application.utilitarios.monitor;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        int iteracoes = args.length > 0 ? Integer.parseInt(args[0]) : 5;
        int intervaloMs = args.length > 1 ? Integer.parseInt(args[1]) : 2000;

        for (int i = 1; i <= iteracoes; i++) {
            System.out.print("\033[H\033[2J");
            System.out.println("Snapshot " + i + "/" + iteracoes + "\n");
            System.out.println(monitor.snapshot());
            if (i < iteracoes) {
                Thread.sleep(intervaloMs);
            }
        }
    }
}

