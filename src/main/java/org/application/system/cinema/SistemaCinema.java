package org.application.system.cinema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SistemaCinema {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Criar cinema
        Cinema cinema = new Cinema("Cineplex", "12.345.678/0001-90", "Av. Paulista, 1000");

        // Criar filmes
        Filme filme1 = new Filme("Avatar 3", "James Cameron", GeneroFilme.FICCAO_CIENTIFICA, 180,
                ClassificacaoIndicativa.DOZE_ANOS, "Aventura em Pandora", LocalDate.parse("15/12/2025", formatter));
        Filme filme2 = new Filme("Comédia Divertida", "John Smith", GeneroFilme.COMEDIA, 110,
                ClassificacaoIndicativa.LIVRE, "Muita risada", LocalDate.parse("01/01/2025", formatter));
        cinema.adicionarFilme(filme1);
        cinema.adicionarFilme(filme2);

        // Criar salas
        Sala sala1 = new Sala(1, TipoSala.TRIDIMENSIONAL, 50);
        Sala sala2 = new Sala(2, TipoSala.NORMAL, 40);
        cinema.adicionarSala(sala1);
        cinema.adicionarSala(sala2);

        // Criar sessões
        LocalDateTime dataHora1 = LocalDateTime.of(2025, 5, 28, 19, 0);
        LocalDateTime dataHora2 = LocalDateTime.of(2025, 5, 28, 21, 30);
        Sessao sessao1 = new Sessao(filme1, sala1, dataHora1, 30.0);
        Sessao sessao2 = new Sessao(filme2, sala2, dataHora2, 20.0);
        cinema.adicionarSessao(sessao1);
        cinema.adicionarSessao(sessao2);

        // Criar cliente
        Cliente cliente = new Cliente("João Silva", "123.456.789-00", "(11) 98765-4321",
                "joao@email.com", "C001");
        cinema.cadastrarCliente(cliente);

        // Criar funcionário
        Funcionario atendente = new Funcionario("Maria Oliveira", "111.222.333-44",
                "(11) 91234-5678", "maria@cinema.com", "F001", "Atendente", 2500.0,
                LocalDate.parse("10/01/2023", formatter));
        cinema.contratarFuncionario(atendente);

        // Iniciar compra
        Compra compra = cinema.iniciarCompra(cliente, FormaPagamento.PIX);

        // Adicionar ingressos
        cinema.adicionarIngressoCompra(compra, sessao1.getId(), "A1", TipoIngresso.INTEIRA);
        cinema.adicionarIngressoCompra(compra, sessao1.getId(), "A2", TipoIngresso.MEIA);
        cinema.adicionarIngressoCompra(compra, sessao2.getId(), "B5", TipoIngresso.VIP);

        // Finalizar compra
        cinema.finalizarCompra(compra);

        // Cliente resgata meia-entrada
        cliente.resgatarMeiaEntrada();

        // Exibir relatórios
        cinema.exibirProgramacao();
        cinema.exibirFilmesEmCartaz();
        cinema.exibirSessoesPorFilme("Avatar 3");
        cinema.exibirClientesFieis();
        cinema.exibirRelatorioFinanceiro();

        // Exibir detalhes da compra
        compra.exibirResumo();

        // Polimorfismo: lista de pessoas
        System.out.println("\n=== PESSOAS DO CINEMA ===");
        java.util.List<Pessoa> pessoas = new java.util.ArrayList<>();
        pessoas.add(cliente);
        pessoas.add(atendente);

        for (Pessoa p : pessoas) {
            p.exibirInformacoes();
            System.out.println("------------------");
        }
    }
}
