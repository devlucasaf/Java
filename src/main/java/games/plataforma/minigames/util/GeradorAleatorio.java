package games.plataforma.minigames.util;

import java.util.Random;

public class GeradorAleatorio {
    private static final Random RANDOM = new Random();

    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static int nextInt(int min, int max) {
        return min + RANDOM.nextInt(max - min);
    }

    public static <T> T escolher(T[] array) {
        return array[RANDOM.nextInt(array.length)];
    }

    public static <T> T escolher(java.util.List<T> lista) {
        return lista.get(RANDOM.nextInt(lista.size()));
    }
}
