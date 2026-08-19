package application.system.automovel.locadora;

import application.system.automovel.locadora.enums.*;
import application.system.automovel.locadora.exception.*;
import application.system.automovel.locadora.model.*;
import application.system.automovel.locadora.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Locadora locadora = new Locadora("AutoLux Locadora", "12.345.678/0001-90", "Av. Brasil, 500");
        ICalculadoraDiariaService calculadora = new CalculadoraPadraoService();
        LocadoraService service = new LocadoraService(locadora, calculadora);
        RelatorioService relatorio = new RelatorioService(locadora);

        inicializarDados(service);

        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> cadastrarVeiculo(service);
                case 2 -> cadastrarCliente(service);
                case 3 -> relatorio.exibirVeiculosDisponiveis();
                case 4 -> realizarLocacao(service);
                case 5 -> devolverVeiculo(service);
                case 6 -> relatorio.exibirLocacoesAtivas();
                case 7 -> relatorio.exibirHistoricoLocacoes();
                case 8 -> relatorio.exibirRelatorioFinanceiro();
                case 9 -> relatorio.exibirClientesFieis();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        }
        while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n===== LOCADORA DE VEÍCULOS =====");
        System.out.println("1 - Cadastrar veículo");
        System.out.println("2 - Cadastrar cliente");
        System.out.println("3 - Listar veículos disponíveis");
        System.out.println("4 - Realizar locação");
        System.out.println("5 - Registrar devolução");
        System.out.println("6 - Exibir locações ativas");
        System.out.println("7 - Exibir histórico de locações");
        System.out.println("8 - Relatório financeiro");
        System.out.println("9 - Clientes fiéis (mais pontos)");
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

    private static LocalDate lerData(String mensagem) {
        System.out.print(mensagem);
        String dataStr = scanner.nextLine().trim();
        try {
            return LocalDate.parse(dataStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido. Use dd/MM/yyyy.");
            return null;
        }
    }

    private static void inicializarDados(LocadoraService service) {
        try {
            Veiculo veiculo1 = new Veiculo("ABC-1234", "Fiesta", "Ford", 2020,
                    CategoriaVeiculo.ECONOMICO, 80.0, 15000
            );

            Veiculo veiculo2 = new Veiculo("XYZ-5678", "Civic", "Honda", 2022,
                    CategoriaVeiculo.INTERMEDIARIO, 120.0, 8000
            );

            Veiculo veiculo3 = new Veiculo("MNO-9012", "Compass", "Jeep", 2023,
                    CategoriaVeiculo.SUV, 180.0, 5000
            );
            service.cadastrarVeiculo(veiculo1);
            service.cadastrarVeiculo(veiculo2);
            service.cadastrarVeiculo(veiculo3);

            Cliente cliente1 = new Cliente("João Silva", "111.111.111-11", "(11) 99999-1111", "joao@email.com",
                    "123456789", LocalDate.parse("10/12/2026", DATE_FORMATTER));
            Cliente cliente2 = new Cliente("Maria Souza", "222.222.222-22", "(11) 98888-2222", "maria@email.com",
                    "987654321", LocalDate.parse("05/05/2025", DATE_FORMATTER));
            service.cadastrarCliente(cliente1);
            service.cadastrarCliente(cliente2);

            System.out.println("Dados iniciais carregados com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao inicializar dados: " + e.getMessage());
        }
    }

    private static void cadastrarVeiculo(LocadoraService service) {
        System.out.println("\n--- CADASTRO DE VEÍCULO ---");
        String placa = lerString("Placa: ");
        String modelo = lerString("Modelo: ");
        String marca = lerString("Marca: ");
        int ano = lerInteiro("Ano: ");
        System.out.println("Categorias: ECONOMICO, INTERMEDIARIO, EXECUTIVO, SUV, LUXO");
        String catStr = lerString("Categoria: ").toUpperCase();
        CategoriaVeiculo categoria;

        try {
            categoria = CategoriaVeiculo.valueOf(catStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Categoria inválida. Usando ECONOMICO.");
            categoria = CategoriaVeiculo.ECONOMICO;
        }

        double diaria = lerDouble("Valor da diária: R$");
        double km = lerDouble("Quilometragem atual: ");

        Veiculo veiculo = new Veiculo(placa, modelo, marca, ano, categoria, diaria, km);
        service.cadastrarVeiculo(veiculo);
        System.out.println("Veículo cadastrado com sucesso!");
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

    private static void cadastrarCliente(LocadoraService service) {
        System.out.println("\n--- CADASTRO DE CLIENTE ---");
        String nome = lerString("Nome: ");
        String cpf = lerString("CPF: ");
        String telefone = lerString("Telefone: ");
        String email = lerString("Email: ");
        String cnh = lerString("Número da CNH: ");
        LocalDate validade = lerData("Data de validade da CNH (dd/MM/yyyy): ");
        if (validade == null) {
            return;
        }

        Cliente cliente = new Cliente(nome, cpf, telefone, email, cnh, validade);
        service.cadastrarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void realizarLocacao(LocadoraService service) {
        System.out.println("\n--- REALIZAR LOCAÇÃO ---");
        String placa = lerString("Placa do veículo: ");
        String cpf = lerString("CPF do cliente: ");
        LocalDate retirada = lerData("Data de retirada (dd/MM/yyyy): ");
        if (retirada == null) {
            return;
        }

        LocalDate devolucao = lerData("Data prevista de devolução (dd/MM/yyyy): ");
        if (devolucao == null) {
            return;
        }

        System.out.println("Seguros: BASICO, COMPLETO, APENAS_TERCEIROS");
        String seguroStr = lerString("Tipo de seguro: ").toUpperCase();
        TipoSeguro seguro;
        try {
            seguro = TipoSeguro.valueOf(seguroStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Seguro inválido. Usando BASICO.");
            seguro = TipoSeguro.BASICO;
        }

        System.out.println("Formas de pagamento: DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO, PIX, VALE_ALIMENTACAO");
        String pagStr = lerString("Forma de pagamento: ").toUpperCase();
        FormaPagamento pagamento;
        try {
            pagamento = FormaPagamento.valueOf(pagStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Forma inválida. Usando DINHEIRO.");
            pagamento = FormaPagamento.DINHEIRO;
        }

        try {
            Locacao locacao = service.realizarLocacao(placa, cpf, retirada, devolucao, seguro, pagamento);
            System.out.println("Locação realizada com sucesso!");
            System.out.println("ID da locação: " + locacao.getId());
            System.out.println("Valor total: R$" + locacao.getValorTotal());
        } catch (VeiculoIndisponivelException | ClienteNaoHabilitadoException | DataInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void devolverVeiculo(LocadoraService service) {
        System.out.println("\n--- DEVOLUÇÃO DE VEÍCULO ---");
        int id = lerInteiro("ID da locação: ");
        LocalDate dataDevolucao = lerData("Data de devolução real (dd/MM/yyyy): ");

        if (dataDevolucao == null) {
            return;
        }
        double km = lerDouble("Quilometragem atual: ");

        try {
            service.devolverVeiculo(id, dataDevolucao, km);
        } catch (LocacaoNaoEncontradaException | DataInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
