package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

// Classe que representa um jogo da Mega Sena
class MegaSena {
    private List<Integer> NumerosJogosAJogar; // Lista que guarda os números sorteados para o jogo

    // Método para gerar um jogo com a quantidade de dezenas escolhida
    public List<Integer> gerarJogo (int quantidadeNumeros) {
        // Valida se a quantidade de dezenas está dentro do intervalo permitido (6 a 20)
        if (quantidadeNumeros < 6 || quantidadeNumeros > 20) {
            throw new IllegalArgumentException("Para jogar, escolha entre 6 e 20 dezenas.");
        }

        Set<Integer> selecao = new HashSet<>(); // Usado para evitar números repetidos
        Random random = new Random();

        // Sorteia números aleatórios entre 1 e 60 até atingir a quantidade desejada
        while (selecao.size() < quantidadeNumeros) {
            selecao.add(random.nextInt(60) + 1);
        }

        // Converte o conjunto em lista e ordena os números
        this.NumerosJogosAJogar = new ArrayList<>(selecao);
        Collections.sort(this.NumerosJogosAJogar);

        return this.NumerosJogosAJogar;
    }

    // Método que retorna o custo do jogo de acordo com a quantidade de dezenas escolhida
    public double custoJogoMegaSena (int tamanho) {
        return switch (tamanho) {
            case 6 -> 5.00;
            case 7 -> 35.00;
            case 8 -> 140.00;
            case 9 -> 420.00;
            case 10 -> 1050.00;
            case 11 -> 2310.00;
            case 12 -> 4620.00;
            case 13 -> 8580.00;
            case 14 -> 15015.00;
            case 15 -> 25035.00;
            case 16 -> 40040.00;
            case 17 -> 61880.00;
            case 18 -> 92820.00;
            case 19 -> 135600.00;
            case 20 -> 193800.00;
            default -> 0.0; // Caso inválido
        };
    }

    // Método para gravar os jogos em um arquivo de texto
    public void gravarJogos() {
        try (FileWriter fileWriter = new FileWriter("mega-teste.txt", true); // Abre arquivo em modo append
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Escreve informações formatadas no arquivo
            printWriter.println("     Jogo para a Mega da Virada     ");
            printWriter.println("                |                   ");
            printWriter.println("------------------------------------");
            printWriter.println("    " + this.NumerosJogosAJogar);
            printWriter.println("------------------------------------");
            printWriter.printf("- Valor total: R$%.2f ------\n", custoJogoMegaSena(this.NumerosJogosAJogar.size()));
            printWriter.println("\n");

        }
        catch (IOException e) {
            // Caso ocorra erro ao salvar o arquivo
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }
}

// Classe principal que executa o programa
public class JogoMegaSena {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner para entrada de dados
        MegaSena ms = new MegaSena(); // Instância da classe MegaSena

        // Pergunta quantos jogos o usuário deseja fazer
        System.out.print("Digite quantos jogos de Mega Sena quer fazer: ");
        int numJogos = Integer.parseInt(scanner.nextLine());

        // Loop para gerar a quantidade de jogos solicitada
        for (int i = 0; i < numJogos; i++) {
            System.out.println("\nNovo jogo!");
            System.out.print("Digite a quantidade de dezenas (6-20): ");
            int qtd = Integer.parseInt(scanner.nextLine());

            try {
                // Gera o jogo e mostra os números sorteados
                List<Integer> jogo = ms.gerarJogo(qtd);
                System.out.printf("Seu jogo está pronto! Números: %s\n", jogo);
                System.out.printf("Custo da aposta: R$%.2f\n", ms.custoJogoMegaSena(qtd));

                // Grava o jogo no arquivo
                ms.gravarJogos();
            }
            catch (IllegalArgumentException e) {
                // Caso o usuário informe quantidade inválida de dezenas
                System.out.println("Erro: " + e.getMessage());
            }
        }
        scanner.close(); // Fecha o scanner
    }
}
