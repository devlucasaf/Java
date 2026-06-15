package org.games.sorteio.nomes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SorteadorNomes {

    private final List<String> nomes = new ArrayList<>();
    private final Random sorteador = new Random();

    public void adicionar(String nome) {
        if (nome != null && !nome.isBlank()) {
            nomes.add(nome.trim());
        }
    }

    public void carregarDeArquivo(String caminho) throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int adicionados = 0;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.isBlank()) {
                    nomes.add(linha.trim());
                    adicionados++;
                }
            }
            System.out.printf("%d nomes carregados de %s%n", adicionados, caminho);
        }
    }

    public List<String> sortear(int quantidade) {
        if (quantidade < 1) {
            throw new IllegalArgumentException("Quantidade deve ser >= 1");
        }

        if (quantidade > nomes.size()) {
            throw new IllegalArgumentException("Não há nomes suficientes (pedido: " + quantidade + ", disponíveis: " + nomes.size() + ")");
        }
        List<String> copia = new ArrayList<>(nomes);
        Collections.shuffle(copia, sorteador);
        return copia.subList(0, quantidade);
    }

    public void salvarResultado(String caminho, List<String> ganhadores) throws IOException {
        try (PrintWriter escritor = new PrintWriter(caminho)) {
            String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            escritor.println("=== Resultado do sorteio (" + dataHora + ") ===");
            escritor.println("Total de participantes: " + nomes.size());
            escritor.println("Ganhadores:");
            for (int i = 0; i < ganhadores.size(); i++) {
                escritor.printf("  %d. %s%n", i + 1, ganhadores.get(i));
            }
        }
    }

    public int total() {
        return nomes.size();
    }

    // ------------------------ programa principal ------------------------

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        SorteadorNomes sorteador = new SorteadorNomes();

        System.out.println();
        System.out.println("==============================================");
        System.out.println("              SORTEADOR DE NOMES              ");
        System.out.println("==============================================");

        boolean executando = true;
        while (executando) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Adicionar nome manualmente");
            System.out.println("2. Carregar nomes de arquivo (.txt)");
            System.out.println("3. Listar nomes cadastrados");
            System.out.println("4. Sortear ganhadores");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            String op = entrada.nextLine().trim();

            switch (op) {
                case "1" -> {
                    System.out.print("Nome: ");
                    sorteador.adicionar(entrada.nextLine());
                    System.out.printf("✓ Total cadastrado: %d%n", sorteador.total());
                }
                case "2" -> {
                    System.out.print("Caminho do arquivo: ");
                    try {
                        sorteador.carregarDeArquivo(entrada.nextLine().trim());
                    } catch (IOException e) {
                        System.out.println("⚠ Erro ao ler arquivo: " + e.getMessage());
                    }
                }
                case "3" -> {
                    if (sorteador.total() == 0) {
                        System.out.println("Nenhum nome cadastrado.");
                    } else {
                        System.out.printf("%n%d nomes:%n", sorteador.total());
                        for (int i = 0; i < sorteador.nomes.size(); i++) {
                            System.out.printf("  %d. %s%n", i + 1, sorteador.nomes.get(i));
                        }
                    }
                }
                case "4" -> {
                    System.out.print("Quantos ganhadores? ");
                    try {
                        int qtd = Integer.parseInt(entrada.nextLine().trim());
                        List<String> ganhadores = sorteador.sortear(qtd);
                        System.out.println("\n GANHADORES ");
                        for (int i = 0; i < ganhadores.size(); i++) {
                            System.out.printf("  %d. %s%n", i + 1, ganhadores.get(i));
                        }
                        System.out.print("\nDeseja salvar o resultado em arquivo? (s/n): ");
                        if (entrada.nextLine().trim().equalsIgnoreCase("s")) {
                            System.out.print("Nome do arquivo: ");
                            String caminho = entrada.nextLine().trim();
                            try {
                                sorteador.salvarResultado(caminho, ganhadores);
                                System.out.println("✓ Resultado salvo em " + caminho);
                            } catch (IOException e) {
                                System.out.println("⚠ Erro ao salvar: " + e.getMessage());
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Quantidade inválida.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠ " + e.getMessage());
                    }
                }
                case "0" -> executando = false;
                default -> System.out.println("⚠ Opção inválida.");
            }
        }
        System.out.println("Encerrado.");
    }
}

