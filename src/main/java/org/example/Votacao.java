package org.example;

import java.util.*;

public class Votacao {

    // Lista que armazena todos os votos recebidos
    static ArrayList<Integer> votosTotais = new ArrayList<>();
    // Variável que guarda o nome do candidato eleito
    static String eleito = "";

    public static void main(String[] args) {
        // Exibe as opções de candidatos e comandos
        System.out.println("""
                Escolha entre as seguintes opções de candidatos:
                22 - Leanderson
                13 - Mario Bitcoin
                14 - Lucao
                0  - NULO
                9  - Encerrar
                """);

        // Inicia o processo de eleição
        eleicao();
    }

    public static void eleicao() {
        Scanner sc = new Scanner(System.in);

        // Loop principal para receber votos até que seja encerrado
        while (true) {
            System.out.print("Digite seu voto: ");
            int voto = sc.nextInt();

            // Caso o usuário digite 9, encerra a votação
            if (voto == 9) {
                System.out.println("Votação encerrada!");
                break;
            }

            // Verifica se o voto é válido (candidato ou nulo)
            if (voto == 22 || voto == 13 || voto == 14 || voto == 0) {
                System.out.println("Voto Confirmado!");
                votosTotais.add(voto);
            } else {
                System.out.println("Número digitado não identificado a nenhum candidato! Tente outro número!");
            }

            // Atualiza quem está na frente de forma temporária
            if (Collections.frequency(votosTotais, 22) > Collections.frequency(votosTotais, 13)
                    && Collections.frequency(votosTotais, 22) > Collections.frequency(votosTotais, 14)) {
                eleito = "Leanderson";
            } else if (Collections.frequency(votosTotais, 13) > Collections.frequency(votosTotais, 22)
                    && Collections.frequency(votosTotais, 13) > Collections.frequency(votosTotais, 14)) {
                eleito = "Mario Bitcoin";
            } else if (Collections.frequency(votosTotais, 14) > Collections.frequency(votosTotais, 22)
                    && Collections.frequency(votosTotais, 14) > Collections.frequency(votosTotais, 13)) {
                eleito = "Lucao";
            }
        }

        // Após encerrar a votação, verifica se houve votos
        if (votosTotais.size() != 0) {
            // Conta os votos de cada candidato
            int votosLeanderson = Collections.frequency(votosTotais, 22);
            int votosMario = Collections.frequency(votosTotais, 13);
            int votosLucao = Collections.frequency(votosTotais, 14);
            int votosNulo = Collections.frequency(votosTotais, 0);
            int totalVotos = votosTotais.size();

            // Cria um mapa com os resultados
            Map<String, Integer> resultados = new HashMap<>();
            resultados.put("Leanderson", votosLeanderson);
            resultados.put("Mario Bitcoin", votosMario);
            resultados.put("Lucao", votosLucao);

            // Determina o candidato eleito (com mais votos válidos)
            eleito = Collections.max(resultados.entrySet(), Map.Entry.comparingByValue()).getKey();
            int votosEleito = resultados.get(eleito);

            // Calcula porcentagens de cada candidato e dos votos nulos
            double porc1 = (votosLeanderson / (double) totalVotos) * 100;
            double porc2 = (votosMario / (double) totalVotos) * 100;
            double porc3 = (votosLucao / (double) totalVotos) * 100;
            double porcNull = (votosNulo / (double) totalVotos) * 100;

            // Caso os votos nulos sejam maiores que os votos do eleito
            if (votosNulo > votosEleito) {
                System.out.println("Votação anulada! Os votos nulos venceram com " + votosNulo);
                System.out.println("\nVotação recomeçada!\n");

                // Reinicia a votação
                System.out.println("""
                        Escolha entre as seguintes opções de candidatos:
                        22 - Leanderson
                        13 - Mario Bitcoin
                        14 - Lucao
                        0  - NULO
                        9  - Encerrar
                        """);

                votosTotais.clear(); // Limpa votos anteriores
                eleicao();           // Chama novamente a eleição
                return;
            } else {
                // Exibe os resultados finais
                if (totalVotos == 1) {
                    System.out.println("Nesta eleição houve " + totalVotos + " voto!");
                } else {
                    System.out.println("Nesta eleição houve " + totalVotos + " votos!");
                    System.out.printf("Leanderson obteve %d votos com %.2f%%\n", votosLeanderson, porc1);
                    System.out.printf("Mario Bitcoin obteve %d votos com %.2f%%\n", votosMario, porc2);
                    System.out.printf("Lucao obteve %d votos com %.2f%%\n", votosLucao, porc3);
                    System.out.printf("Obteve %d votos nulos com %.2f%%\n", votosNulo, porcNull);
                    System.out.printf("O candidato %s foi eleito com %d votos!\n", eleito, votosEleito);
                }
            }

        }
        else {
            // Caso não tenha votos
            System.out.println("Não se obteve votos.");
        }
    }
}
