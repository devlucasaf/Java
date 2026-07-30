package application.simuladores.memoriavirtual;

import java.util.LinkedList;
import java.util.List;

public interface Algoritmo {

    String getNome();

    int simular(List<Integer> referencias, int numeroFrames);

    static Algoritmo fifo() {
        return new Algoritmo() {
            @Override
            public String getNome() { return "FIFO"; }

            @Override
            public int simular(List<Integer> referencias, int numeroFrames) {
                LinkedList<Integer> fila = new LinkedList<>();
                int faults = 0;
                for (int p : referencias) {
                    if (!fila.contains(p)) {
                        if (fila.size() == numeroFrames) {
                            fila.removeFirst();
                        }
                        fila.addLast(p);
                        faults++;
                    }
                }
                return faults;
            }
        };
    }

    static Algoritmo lru() {
        return new Algoritmo() {
            @Override
            public String getNome() {
                return "LRU";
            }

            @Override
            public int simular(List<Integer> referencias, int numeroFrames) {
                LinkedList<Integer> ordem = new LinkedList<>();
                int faults = 0;
                for (int p : referencias) {
                    if (ordem.contains(p)) {
                        ordem.remove((Integer) p);
                    } else {
                        if (ordem.size() == numeroFrames) {
                            ordem.removeFirst();
                        }
                        faults++;
                    }
                    ordem.addLast(p);
                }
                return faults;
            }
        };
    }

    static Algoritmo otimo() {
        return new Algoritmo() {
            @Override
            public String getNome() { return "OTIMO"; }

            @Override
            public int simular(List<Integer> referencias, int numeroFrames) {
                LinkedList<Integer> frames = new LinkedList<>();
                int faults = 0;
                for (int i = 0; i < referencias.size(); i++) {
                    int p = referencias.get(i);
                    if (frames.contains(p)) {
                        continue;
                    }

                    if (frames.size() < numeroFrames) {
                        frames.add(p);
                    } else {
                        int indiceRemocao = -1;
                        int maiorFuturo = -1;
                        for (int f = 0; f < frames.size(); f++) {
                            int prox = -1;
                            for (int j = i + 1; j < referencias.size(); j++) {
                                if (referencias.get(j).equals(frames.get(f))) {
                                    prox = j;
                                    break;
                                }
                            }

                            if (prox == -1) {
                                indiceRemocao = f;
                                break;
                            }

                            if (prox > maiorFuturo) {
                                maiorFuturo = prox;
                                indiceRemocao = f;
                            }
                        }
                        frames.set(indiceRemocao, p);
                    }
                    faults++;
                }
                return faults;
            }
        };
    }
}

