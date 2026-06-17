package games.sorteio.roleta;

import java.util.Random;

public class Roleta {

    private static final int NUMERO_MAXIMO = 36;
    private final Random sorteador = new Random();

    public int sortear() {
        return sorteador.nextInt(NUMERO_MAXIMO + 1);
    }

    public int sortearComAnimacao() {
        System.out.println("\nA roleta está girando...");
        try {
            int passos = 25;
            long espera = 40;
            for (int i = 0; i < passos; i++) {
                int n = sorteador.nextInt(NUMERO_MAXIMO + 1);
                CorNumero c = CorNumero.deNumero(n);
                System.out.print("\r  → " + c.pintar(String.format("[ %2d ]", n)) + "   ");
                Thread.sleep(espera);
                espera += 8;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int resultado = sortear();
        CorNumero corResultado = CorNumero.deNumero(resultado);
        System.out.print("\r  → " + corResultado.pintar(String.format("[ %2d ]", resultado))
                + "  (" + corResultado.getNome() + ")\n");
        return resultado;
    }
}

