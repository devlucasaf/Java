/*
    Código de Sistema de um Elevador
    Código original desenvolvido em python por @DediXt04
    https://github.com/DediXt04/Elevator link para conferirem o código original
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Classe que representa o Elevador
class Elevador {
    private int totalAndares;              // Número total de andares do prédio
    private int andarAtual;                // Andar atual do elevador
    private List<Passageiro> passageiros;  // Lista de passageiros dentro do elevador
    private List<Passageiro> chamadas;     // Lista de chamadas (passageiros esperando)
    private int movimentos;                // Contador de movimentos realizados pelo elevador

    // Construtor padrão: prédio com 100 andares
    public Elevador() {
        this(100);
    }

    // Construtor com número de andares definido
    public Elevador(int totalAndares) {
        this.totalAndares = totalAndares;
        this.andarAtual = 0; // Elevador começa no térreo
        this.passageiros = new ArrayList<>();
        this.chamadas = new ArrayList<>();
        this.movimentos = 0;
    }

    // Adiciona uma chamada de passageiro ao elevador
    public void adicionarChamada(Passageiro passageiro) {
        // Verifica se origem/destino são válidos
        if (passageiro.getAndarOrigem() < 0 || passageiro.getAndarOrigem() >= totalAndares ||
                passageiro.getAndarDestino() < 0 || passageiro.getAndarDestino() >= totalAndares) {
            return; // Ignora chamadas inválidas
        }

        chamadas.add(passageiro); // Adiciona à lista de chamadas
        System.out.println("📞 Chamada: " + passageiro.getAndarOrigem() + " - " + passageiro.getAndarDestino());
    }

    // Escolhe o próximo destino do elevador (mais próximo)
    private Integer escolherDestino() {
        List<Integer> destinos = new ArrayList<>();

        // Passageiros dentro do elevador querem ir ao destino
        for (Passageiro p : passageiros) {
            destinos.add(p.getAndarDestino());
        }

        // Passageiros que chamaram querem ser buscados
        for (Passageiro p : chamadas) {
            destinos.add(p.getAndarOrigem());
        }

        if (destinos.isEmpty()) {
            return null; // Sem destino
        }

        // Escolhe o destino mais próximo do andar atual
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

    // Move o elevador em direção ao destino
    public void mover() {
        Integer destino = escolherDestino();

        if (destino == null) {
            System.out.println("⏸ Nenhuma chamada ou passageiro no momento.");
            return;
        }

        // Move um andar por vez
        if (andarAtual < destino) {
            andarAtual++;
            movimentos++;
            System.out.println("🔼 Subiu para " + andarAtual);

        } else if (andarAtual > destino) {
            andarAtual--;
            movimentos++;
            System.out.println("🔽 Desceu para " + andarAtual);

        } else {
            // Chegou ao andar
            System.out.println("🚪 Chegou no andar " + andarAtual);

            boolean entrouPessoa = false;
            List<Passageiro> chamadasParaRemover = new ArrayList<>();

            // Passageiros entram no elevador
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

            // Passageiros saem do elevador
            for (Passageiro p : passageiros) {
                if (p.getAndarDestino() == andarAtual) {
                    p.setNoElevador(false);
                    passageirosParaRemover.add(p);
                    saiuPessoa = true;
                    System.out.println("⬇️ Saiu: " + p);
                }
            }
            passageiros.removeAll(passageirosParaRemover);

            // Caso não tenha embarque ou desembarque
            if (!entrouPessoa && !saiuPessoa) {
                System.out.println("🕓 Sem embarque/desembarque neste andar.");
            }
        }
    }

    // Exibe o status atual do elevador
    public void status() {
        System.out.println("🏢 Andar atual: " + andarAtual);
        System.out.println("📞 Chamadas: " +
                (chamadas.isEmpty() ? "—" : chamadas));
        System.out.println("🛗 Passageiros: " +
                (passageiros.isEmpty() ? "—" : passageiros));
        System.out.println("-".repeat(40));
    }

    // Getters
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

// Classe que representa um Passageiro
class Passageiro {
    private int andarOrigem;    // Andar de origem
    private int andarDestino;   // Andar de destino
    private boolean noElevador; // Estado: dentro ou fora do elevador

    public Passageiro(int andarOrigem, int andarDestino) {
        this.andarOrigem = andarOrigem;
        this.andarDestino = andarDestino;
        this.noElevador = false;
    }

    // Getters e Setters
    public int getAndarOrigem() {
        return andarOrigem;
    }

    public int getAndarDestino() {
        return andarDestino;
    }

    public boolean isNoElevador() {
        return noElevador;
    }

    public void setNoElevador(boolean noElevador) {
        this.noElevador = noElevador;
    }

    @Override
    public String toString() {
        String estado = noElevador ? "🟢" : "⚪"; // Ícone para indicar se está dentro ou fora
        return estado + "👤(" + andarOrigem + "->" + andarDestino + ")";
    }
}

// Classe principal para executar a simulação
public class ElevadorMain {
    public static void main(String[] args) {
        final int TOTAL_ANDARES = 100; // Número de andares do prédio
        Elevador elevador = new Elevador(TOTAL_ANDARES);

        List<Passageiro> passageiros = new ArrayList<>();
        Random random = new Random();

        // Cria 100 passageiros com origem e destino aleatórios
        for (int i = 0; i < 100; i++) {
            int origem = random.nextInt(TOTAL_ANDARES);
            int destino = random.nextInt(TOTAL_ANDARES);

            // Garante que origem e destino não sejam iguais
            while (destino == origem) {
                destino = random.nextInt(TOTAL_ANDARES);
            }

            passageiros.add(new Passageiro(origem, destino));
        }

        // Adiciona todos os passageiros como chamadas
        for (Passageiro p : passageiros) {
            elevador.adicionarChamada(p);
        }

        // Simula 20 movimentos do elevador
        for (int i = 0; i < 20; i++) {
            elevador.status();
            elevador.mover();
        }

        // Exibe status final
        elevador.status();
        System.out.println("Total de movimentos: " + elevador.getMovimentos());
    }
}
