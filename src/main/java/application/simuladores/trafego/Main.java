package application.simuladores.trafego;

public class Main {

    public static void main(String[] args) {
        Semaforo semaforo = new Semaforo(8, 2, 6, Estado.VERDE);
        SimuladorTrafego simulador = new SimuladorTrafego(60, 40, semaforo);

        System.out.println("Legenda: '.' via livre | 'C' carro | 'V' semaforo verde | '|' semaforo fechado");
        System.out.println();

        simulador.simular(100, 0.35);
    }
}
