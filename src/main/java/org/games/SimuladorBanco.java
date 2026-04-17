package org.games;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class SimuladorBanco {

    // Configurações do jogo
    static final int SALDO_INICIAL = 1000;
    static final int GANHO_TRABALHO = 200;
    static final int MAX_TURNOS = 10;

    static int saldo = SALDO_INICIAL;
    static int turnos = 0;

    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);
    static List<String> historico = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("SIMULADOR DE BANCO");
        System.out.println("Saldo inicial: R$ " + saldo);

        while (true) {
            if (saldo <= 0) {
                System.out.println("\nVocê ficou sem dinheiro!");
                break;
            }

            if (turnos >= MAX_TURNOS) {
                System.out.println("\n⏱Número máximo de turnos atingido!");
                break;
            }

            mostrarNivel();
            mostrarMenu();

            int opcao = scanner.nextInt();
            turnos++;

            switch (opcao) {
                case 1:
                    trabalhar();
                    break;
                case 2:
                    investir();
                    break;
                case 3:
                    apostar();
                    break;
                case 4:
                    mostrarSaldo();
                    turnos--; // Ver saldo não consome turno
                    break;
                case 5:
                    System.out.println("\nVocê saiu do jogo.");
                    encerrarJogo();
                    return;
                default:
                    System.out.println("Opção inválida!");
                    turnos--;
                    break;
            }

            eventoAleatorio();
        }

        encerrarJogo();
    }

    // ================= MÉTODOS =================

    static void trabalhar() {
        saldo += GANHO_TRABALHO;
        String msg = "💼 Trabalhou e ganhou R$ " + GANHO_TRABALHO;
        System.out.println(msg);
        historico.add(msg);
    }

    static void investir() {
        int valor = random.nextInt(401) - 200; // -200 a +200
        saldo += valor;

        String msg;
        if (valor >= 0) {
            msg = "Investimento deu lucro de R$ " + valor;
        } else {
            msg = "Investimento deu prejuízo de R$ " + Math.abs(valor);
        }

        System.out.println(msg);
        historico.add(msg);
    }

    static void apostar() {
        int valor = random.nextInt(801) - 400; // -400 a +400
        saldo += valor;

        String msg;
        if (valor >= 0) {
            msg = "Apostou e ganhou R$ " + valor;
        } else {
            msg = "Apostou e perdeu R$ " + Math.abs(valor);
        }

        System.out.println(msg);
        historico.add(msg);
    }

    static void mostrarSaldo() {
        System.out.println("Saldo atual: R$ " + saldo);
    }

    static void mostrarMenu() {
        System.out.println("\n------ TURNO " + (turnos + 1) + " ------");
        System.out.println("1 - Trabalhar");
        System.out.println("2 - Investir");
        System.out.println("3 - Apostar");
        System.out.println("4 - Ver saldo");
        System.out.println("5 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    static void mostrarNivel() {
        String nivel;

        if (saldo < 1500) {
            nivel = "Iniciante";
        } else if (saldo < 3000) {
            nivel = "Intermediário";
        } else {
            nivel = "Avançado";
        }

        System.out.println("\n🎖Nível atual: " + nivel);
    }

    static void eventoAleatorio() {
        int chance = random.nextInt(100);

        if (chance < 15) {
            int bonus = random.nextInt(201) + 100;
            saldo += bonus;
            String msg = "Bônus inesperado! Recebeu R$ " + bonus;
            System.out.println(msg);
            historico.add(msg);

        } else if (chance > 85) {
            int perda = random.nextInt(201) + 100;
            saldo -= perda;
            String msg = "Crise econômica! Perdeu R$ " + perda;
            System.out.println(msg);
            historico.add(msg);
        }
    }

    static void encerrarJogo() {
        System.out.println("\nFIM DO JOGO");
        System.out.println("Saldo final: R$ " + saldo);
        System.out.println("Turnos jogados: " + turnos);

        System.out.println("\n📜 Histórico de ações:");
        for (String h : historico) {
            System.out.println("- " + h);
        }

        System.out.println("\nObrigado por jogar!");
    }
}
