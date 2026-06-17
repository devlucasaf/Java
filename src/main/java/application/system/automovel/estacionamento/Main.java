package application.system.automovel.estacionamento;

import java.util.Scanner;

public class Main {
    private static Estacionamento   estacionamento = new Estacionamento();
    private static Scanner          scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro();
            processarOpcao(opcao);
        }
        while (opcao != 5);
    }

    private static void exibirMenu() {
        System.out.println("\n--- SISTEMA DE ESTACIONAMENTO ---");
        System.out.println("1 - Entrada de Veículo");
        System.out.println("2 - Saída de Veículo");
        System.out.println("3 - Listar Veículos");
        System.out.println("4 - Buscar Veículo");
        System.out.println("5 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> registrarEntrada();
            case 2 -> registrarSaida();
            case 3 -> listarVeiculos();
            case 4 -> buscarVeiculo();
            case 5 -> System.out.println("Encerrando sistema...");
            default -> System.out.println("Opção inválida!");
        }
    }

    private static void registrarEntrada() {
        System.out.print("Placa: ");
        String placa = scanner.next();
        System.out.print("Tipo (1-Carro, 2-Moto, 3-Caminhão): ");
        int tipo = lerInteiro();

        Veiculo v = switch (tipo) {
            case 1 -> new Carro(placa);
            case 2 -> new Moto(placa);
            case 3 -> new Caminhao(placa);
            default -> null;
        };

        if (v != null && estacionamento.estacionar(v)) {
            System.out.println("Entrada registrada com sucesso!");
        } else if (v == null) {
            System.out.println("Tipo de veículo inválido.");
        }
    }

    private static void registrarSaida() {
        System.out.print("Informe a placa para saída: ");
        String placa = scanner.next();
        double valor = estacionamento.processarSaida(placa);

        if (valor != -1) {
            System.out.println("Veículo liberado! Total a pagar: " + Utils.formatarMoeda(valor));
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }

    private static void listarVeiculos() {
        var lista = estacionamento.getVeiculosAtuais();
        if (lista.isEmpty()) {
            System.out.println("Estacionamento vazio.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private static void buscarVeiculo() {
        System.out.print("Placa: ");
        String placa = scanner.next();
        estacionamento.buscarPorPlaca(placa)
                .ifPresentOrElse(System.out::println,
                        () -> System.out.println("Veículo não encontrado."));
    }

    private static int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, digite um número válido.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}