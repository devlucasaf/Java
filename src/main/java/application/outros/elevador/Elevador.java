package application.outros.elevador;


import java.util.ArrayList;
import java.util.List;

class Elevador {
    private int                 totalAndares;
    private int                 andarAtual;
    private List<Passageiro>    passageiros;
    private List<Passageiro>    chamadas;
    private int                 movimentos;

    public Elevador() {
        this(100);
    }

    public Elevador(int totalAndares) {
        this.totalAndares = totalAndares;
        this.andarAtual = 0;
        this.passageiros = new ArrayList<>();
        this.chamadas = new ArrayList<>();
        this.movimentos = 0;
    }

    public void adicionarChamada(Passageiro passageiro) {
        if (passageiro.getAndarOrigem() < 0 || passageiro.getAndarOrigem() >= totalAndares ||
                passageiro.getAndarDestino() < 0 || passageiro.getAndarDestino() >= totalAndares) {
            return;
        }

        chamadas.add(passageiro);
        System.out.println("📞 Chamada: " + passageiro.getAndarOrigem() + " - " + passageiro.getAndarDestino());
    }

    private Integer escolherDestino() {
        List<Integer> destinos = new ArrayList<>();

        for (Passageiro p : passageiros) {
            destinos.add(p.getAndarDestino());
        }

        for (Passageiro p : chamadas) {
            destinos.add(p.getAndarOrigem());
        }

        if (destinos.isEmpty()) {
            return null; // Sem destino
        }

        Integer destinoMaisProximo = destinos.get(0);
        int menorDistancia = Math.abs(destinoMaisProximo - andarAtual);

        for (int i = 1; i < destinos.size(); i++) {
            int distancia = Math.abs(destinos.get(i) - andarAtual);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                destinoMaisProximo = destinos.get(i);
            }
        }

        return destinoMaisProximo;
    }

    public void mover() {
        Integer destino = escolherDestino();

        if (destino == null) {
            System.out.println("⏸ Nenhuma chamada ou passageiro no momento.");
            return;
        }

        if (andarAtual < destino) {
            andarAtual++;
            movimentos++;
            System.out.println("🔼 Subiu para " + andarAtual);
        } else if (andarAtual > destino) {
            andarAtual--;
            movimentos++;
            System.out.println("🔽 Desceu para " + andarAtual);
        } else {
            System.out.println("🚪 Chegou no andar " + andarAtual);

            boolean entrouPessoa = false;
            List<Passageiro> chamadasParaRemover = new ArrayList<>();

            for (Passageiro p : chamadas) {
                if (p.getAndarOrigem() == andarAtual) {
                    p.setNoElevador(true);
                    passageiros.add(p);
                    chamadasParaRemover.add(p);
                    entrouPessoa = true;
                    System.out.println("⬆️ Entrou: " + p);
                }
            }
            chamadas.removeAll(chamadasParaRemover);

            boolean saiuPessoa = false;
            List<Passageiro> passageirosParaRemover = new ArrayList<>();

            for (Passageiro p : passageiros) {
                if (p.getAndarDestino() == andarAtual) {
                    p.setNoElevador(false);
                    passageirosParaRemover.add(p);
                    saiuPessoa = true;
                    System.out.println("⬇️ Saiu: " + p);
                }
            }
            passageiros.removeAll(passageirosParaRemover);

            if (!entrouPessoa && !saiuPessoa) {
                System.out.println("🕓 Sem embarque/desembarque neste andar.");
            }
        }
    }

    public void status() {
        System.out.println("🏢 Andar atual: " + andarAtual);
        System.out.println("📞 Chamadas: " + (chamadas.isEmpty() ? "—" : chamadas));
        System.out.println("🛗 Passageiros: " + (passageiros.isEmpty() ? "—" : passageiros));
        System.out.println("-".repeat(40));
    }

    public int getTotalAndares() {
        return totalAndares;
    }

    public int getAndarAtual() {
        return andarAtual;
    }

    public int getMovimentos() {
        return movimentos;
    }
}
