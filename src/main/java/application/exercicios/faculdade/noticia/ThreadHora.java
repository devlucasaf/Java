package application.exercicios.faculdade.noticia;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ThreadHora implements Runnable {
    @Override
    public void run() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            for (int i = 0; i < 5; i++) {
                String horaAtual = LocalTime.now().format(formatador);
                System.out.println("\n********** [HORA " + (i + 1) + "/5]: " + horaAtual + " **********\n");

                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread de hora interrompida.");
        }
        System.out.println(">> Fim do Relógio.");
    }
}
