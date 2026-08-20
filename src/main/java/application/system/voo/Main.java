package application.system.voo;

import application.system.voo.enums.*;
import application.system.voo.exception.*;
import application.system.voo.model.*;
import application.system.voo.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SistemaVoo sistema = new SistemaVoo("Asas do Brasil", "12.345.678/0001-90");
        ICalculadoraPrecoService calculadora = new CalculadoraPadraoService();
        VooService service = new VooService(sistema, calculadora);
        RelatorioService relatorio = new RelatorioService(sistema);

        inicializarDados(service);

        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> cadastrarVoo(service);
                case 2 -> cadastrarPassageiro(service);
                case 3 -> relatorio.exibirVoosDisponiveis();
                case 4 -> fazerReserva(service);
                case 5 -> cancelarReserva(service);
                case 6 -> relatorio.exibirTodasReservas();
                case 7 -> buscarReservasPorPassageiro(service, relatorio);
                case 8 -> relatorio.exibirRelatorioFinanceiro();
                case 9 -> relatorio.exibirPassageirosFieis();
                case 0 -> System.out.println("Encerrando o sistema.");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n===== SISTEMA DE RESERVAS DE VOOS =====");
        System.out.println("1 - Cadastrar voo");
        System.out.println("2 - Cadastrar passageiro");
        System.out.println("3 - Listar voos disponíveis");
        System.out.println("4 - Fazer reserva");
        System.out.println("5 - Cancelar reserva");
        System.out.println("6 - Listar todas as reservas");
        System.out.println("7 - Buscar reservas por passageiro (CPF)");
        System.out.println("8 - Relatório financeiro");
        System.out.println("9 - Passageiros mais fiéis");
        System.out.println("0 - Sair");
    }

    private static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.print("Valor inválido. Digite um número: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static LocalDateTime lerDataHora(String mensagem) {
        System.out.print(mensagem);
        String dataStr = scanner.nextLine().trim();
        try {
            return LocalDateTime.parse(dataStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido. Use dd/MM/yyyy HH:mm");
            return null;
        }
    }

    private static double lerDouble(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextDouble()) {
            System.out.print("Valor inválido. Digite um número: ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    private static void inicializarDados(VooService service) {
        try {
            // Voos
            Voo v1 = new Voo("São Paulo", "Rio de Janeiro",
                    LocalDateTime.parse("10/09/2026 08:00", DATE_TIME_FORMATTER),
                    60, 100, 250.0, CategoriaVoo.NACIONAL);
            Voo v2 = new Voo("Brasília", "Miami",
                    LocalDateTime.parse("15/09/2026 22:30", DATE_TIME_FORMATTER),
                    480, 200, 1200.0, CategoriaVoo.INTERNACIONAL);
            Voo v3 = new Voo("Curitiba", "Porto Alegre",
                    LocalDateTime.parse("12/09/2026 14:15", DATE_TIME_FORMATTER),
                    45, 80, 180.0, CategoriaVoo.NACIONAL);
            service.cadastrarVoo(v1);
            service.cadastrarVoo(v2);
            service.cadastrarVoo(v3);

            // Passageiros
            Passageiro p1 = new Passageiro("Ana Costa", "111.111.111-11", "(11) 99999-1111", "ana@email.com");
            Passageiro p2 = new Passageiro("Carlos Lima", "222.222.222-22", "(21) 98888-2222", "carlos@email.com");
            service.cadastrarPassageiro(p1);
            service.cadastrarPassageiro(p2);

            System.out.println("Dados iniciais carregados com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao inicializar dados: " + e.getMessage());
        }
    }

    private static void cadastrarVoo(VooService service) {
        System.out.println("\n--- CADASTRO DE VOO ---");
        String origem = lerString("Origem: ");
        String destino = lerString("Destino: ");
        LocalDateTime partida = lerDataHora("Data e hora de partida (dd/MM/yyyy HH:mm): ");
        if (partida == null) {
            return;
        }

        int duracao = lerInteiro("Duração em minutos: ");
        int capacidade = lerInteiro("Capacidade total: ");
        double precoBase = lerDouble("Preço base (R$): ");
        System.out.println("Categorias: NACIONAL, INTERNACIONAL");
        String catStr = lerString("Categoria: ").toUpperCase();
        CategoriaVoo categoria;
        try {
            categoria = CategoriaVoo.valueOf(catStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Categoria inválida. Usando NACIONAL.");
            categoria = CategoriaVoo.NACIONAL;
        }

        Voo voo = new Voo(origem, destino, partida, duracao, capacidade, precoBase, categoria);
        service.cadastrarVoo(voo);
        System.out.println("Voo cadastrado com sucesso! ID: " + voo.getId());
    }

    private static void cadastrarPassageiro(VooService service) {
        System.out.println("\n--- CADASTRO DE PASSAGEIRO ---");
        String nome = lerString("Nome completo: ");
        String cpf = lerString("CPF: ");
        String telefone = lerString("Telefone: ");
        String email = lerString("Email: ");

        Passageiro p = new Passageiro(nome, cpf, telefone, email);
        service.cadastrarPassageiro(p);
        System.out.println("Passageiro cadastrado com sucesso! ID: " + p.getId());
    }

    private static void fazerReserva(VooService service) {
        System.out.println("\n--- FAZER RESERVA ---");
        int idVoo = lerInteiro("ID do voo: ");
        String cpf = lerString("CPF do passageiro: ");

        System.out.println("Classes: ECONOMICA, EXECUTIVA, PRIMEIRA_CLASSE");
        String classeStr = lerString("Classe desejada: ").toUpperCase();
        ClasseAssento classe;
        try {
            classe = ClasseAssento.valueOf(classeStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Classe inválida. Usando ECONOMICA.");
            classe = ClasseAssento.ECONOMICA;
        }

        System.out.println("Formas de pagamento: DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO, PIX, BOLETO");
        String pagStr = lerString("Forma de pagamento: ").toUpperCase();
        FormaPagamento pagamento;
        try {
            pagamento = FormaPagamento.valueOf(pagStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Forma inválida. Usando DINHEIRO.");
            pagamento = FormaPagamento.DINHEIRO;
        }

        try {
            Reserva reserva = service.fazerReserva(idVoo, cpf, classe, pagamento);
            System.out.println("Reserva realizada com sucesso!");
            System.out.println("ID da reserva: " + reserva.getId());
            System.out.println("Preço pago: R$" + reserva.getPrecoPago());
        } catch (VooIndisponivelException | PassageiroNaoEncontradoException | DataInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void cancelarReserva(VooService service) {
        System.out.println("\n--- CANCELAR RESERVA ---");
        int id = lerInteiro("ID da reserva: ");
        try {
            service.cancelarReserva(id);
        } catch (ReservaNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void buscarReservasPorPassageiro(VooService service, RelatorioService relatorio) {
        String cpf = lerString("CPF do passageiro: ");
        Passageiro p = service.buscarPassageiroPorCpf(cpf);
        if (p == null) {
            System.out.println("Passageiro não encontrado.");
            return;
        }
        relatorio.exibirReservasPorPassageiro(cpf);
    }
}
