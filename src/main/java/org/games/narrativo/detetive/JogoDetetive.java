package org.games.narrativo.detetive;

import java.util.ArrayList;
import java.util.Scanner;

public class JogoDetetive {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Suspeito> suspeitos = new ArrayList<>();
    static ArrayList<Pista> pistas = new ArrayList<>();
    static int turnosRestantes = 10;
    static int nivelInvestigacao = 0;
    static String culpado = "Marcos Silva";

    public static void main(String[] args) {
        iniciarJogo();
    }

    public static void iniciarJogo() {
        System.out.println("========================================");
        System.out.println("      SOMBRAS NO SOLAR MENDES     ");
        System.out.println("========================================");
        System.out.println("O empresário Artur Mendes foi encontrado morto.");
        System.out.println("Você foi chamado para investigar o caso.");
        System.out.println("Descubra a verdade antes que o tempo acabe.\n");

        criarSuspeitos();
        criarPistas();

        while (turnosRestantes > 0) {
            System.out.println("\nTurnos restantes: " + turnosRestantes);
            System.out.println("Escolha uma ação:");
            System.out.println("1 - Interrogar suspeito");
            System.out.println("2 - Analisar pistas");
            System.out.println("3 - Revisar informações");
            System.out.println("4 - Fazer acusação");

            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1 -> interrogar();
                case 2 -> analisarPistas();
                case 3 -> revisarInfos();
                case 4 -> {
                    acusar();
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }

            turnosRestantes--;
            nivelInvestigacao++;
        }

        System.out.println("\nO tempo acabou! Você deve fazer uma acusação.");
        acusar();
    }

    public static void criarSuspeitos() {
        suspeitos.add(new Suspeito(
                "Marcos Silva",
                "Sócio da vítima, nervoso e endividado.",
                "Desvio de dinheiro descoberto por Artur.",
                "Alega estar no escritório, mas sem testemunhas."
        ));

        suspeitos.add(new Suspeito(
                "Helena Mendes",
                "Esposa fria e distante.",
                "Herança milionária.",
                "Diz que dormia no quarto no horário do crime."
        ));

        suspeitos.add(new Suspeito(
                "Rafael Costa",
                "Funcionário demitido recentemente.",
                "Vingança após demissão.",
                "Afirma estar em um bar, mas ninguém confirma."
        ));
    }

    public static void criarPistas() {
        pistas.add(new Pista("Pegadas masculinas sujas de lama no escritório.", true));
        pistas.add(new Pista("Câmeras de segurança desligadas manualmente.", true));
        pistas.add(new Pista("Uma luva feminina encontrada no jardim.", false));
        pistas.add(new Pista("Documento rasgado sobre fraude financeira.", true));
    }

    public static void interrogar() {
        System.out.println("\nEscolha um suspeito:");
        for (int i = 0; i < suspeitos.size(); i++) {
            System.out.println((i + 1) + " - " + suspeitos.get(i).nome);
        }

        int escolha = scanner.nextInt() - 1;

        if (escolha >= 0 && escolha < suspeitos.size()) {
            suspeitos.get(escolha).interrogar(nivelInvestigacao);
        } else {
            System.out.println("Suspeito inválido.");
        }
    }

    public static void analisarPistas() {
        System.out.println("\nAnalisando pistas encontradas:");
        for (Pista pista : pistas) {
            pista.mostrar();
        }
        System.out.println("Algumas pistas podem ser enganosas...");
    }

    public static void revisarInfos() {
        System.out.println("\n===== Revisão Geral =====");
        for (Suspeito s : suspeitos) {
            System.out.println("\nNome: " + s.nome);
            System.out.println("Motivação: " + s.motivacao);
            System.out.println("Álibi: " + s.alibi);
            if (s.comportamentoSuspeito) {
                System.out.println("Comportamento suspeito detectado.");
            }
        }
    }

    public static void acusar() {
        System.out.println("\nEscolha quem você acusa:");
        for (int i = 0; i < suspeitos.size(); i++) {
            System.out.println((i + 1) + " - " + suspeitos.get(i).nome);
        }

        int escolha = scanner.nextInt() - 1;

        if (escolha < 0 || escolha >= suspeitos.size()) {
            System.out.println("Acusação inválida.");
            return;
        }

        String acusado = suspeitos.get(escolha).nome;

        System.out.println("\n========================================");
        if (acusado.equals(culpado)) {
            System.out.println("Você acertou!");
            System.out.println("Marcos Silva matou Artur após ser confrontado sobre a fraude.");
            System.out.println("As pistas financeiras e o falso álibi confirmam o crime.");
        } else {
            System.out.println("Acusação incorreta.");
            System.out.println(acusado + " era inocente.");
            System.out.println("Marcos Silva era o verdadeiro culpado, motivado por ganância.");
        }
        System.out.println("========================================");
        System.out.println("FIM DE JOGO");
    }
}
