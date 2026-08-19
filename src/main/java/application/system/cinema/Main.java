package application.system.cinema;

import application.system.cinema.enums.*;
import application.system.cinema.model.*;
import application.system.cinema.service.CinemaService;
import application.system.cinema.service.RelatorioService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Cinema cinema = new Cinema("Cineplex", "12.345.678/0001-90", "Av. Paulista, 1000");
        CinemaService service = new CinemaService(cinema);
        RelatorioService relatorio = new RelatorioService(cinema);  // <-- novo

        Filme filme1 = new Filme("Avatar 3", "James Cameron", GeneroFilme.FICCAO_CIENTIFICA, 180,
                ClassificacaoIndicativa.DOZE_ANOS, "Aventura em Pandora", LocalDate.parse("15/12/2025", formatter));
        Filme filme2 = new Filme("Comédia Divertida", "John Smith", GeneroFilme.COMEDIA, 110,
                ClassificacaoIndicativa.LIVRE, "Muita risada", LocalDate.parse("01/01/2025", formatter));
        service.adicionarFilme(filme1);
        service.adicionarFilme(filme2);

        Sala sala1 = new Sala(1, TipoSala.TRIDIMENSIONAL, 50);
        Sala sala2 = new Sala(2, TipoSala.NORMAL, 40);
        service.adicionarSala(sala1);
        service.adicionarSala(sala2);

        LocalDateTime dataHora1 = LocalDateTime.of(2025, 5, 28, 19, 0);
        LocalDateTime dataHora2 = LocalDateTime.of(2025, 5, 28, 21, 30);
        Sessao sessao1 = new Sessao(filme1, sala1, dataHora1, 30.0);
        Sessao sessao2 = new Sessao(filme2, sala2, dataHora2, 20.0);
        service.adicionarSessao(sessao1);
        service.adicionarSessao(sessao2);

        Cliente cliente = new Cliente("João Silva", "123.456.789-00", "(11) 98765-4321",
                "joao@email.com", "C001");
        service.cadastrarCliente(cliente);

        Funcionario atendente = new Funcionario("Maria Oliveira", "111.222.333-44",
                "(11) 91234-5678", "maria@cinema.com", "F001", "Atendente", 2500.0,
                LocalDate.parse("10/01/2023", formatter));
        service.contratarFuncionario(atendente);

        Compra compra = service.iniciarCompra(cliente, FormaPagamento.PIX);

        service.adicionarIngressoCompra(compra, sessao1.getId(), "A1", TipoIngresso.INTEIRA);
        service.adicionarIngressoCompra(compra, sessao1.getId(), "A2", TipoIngresso.MEIA);
        service.adicionarIngressoCompra(compra, sessao2.getId(), "B5", TipoIngresso.VIP);

        service.finalizarCompra(compra);

        cliente.resgatarMeiaEntrada();

        // Agora usamos o relatorio para exibir
        relatorio.exibirProgramacao();
        relatorio.exibirFilmesEmCartaz();
        relatorio.exibirSessoesPorFilme("Avatar 3");
        relatorio.exibirClientesFieis();
        relatorio.exibirRelatorioFinanceiro();

        compra.exibirResumo();

        System.out.println("\n=== PESSOAS DO CINEMA ===");
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(cliente);
        pessoas.add(atendente);
        for (Pessoa p : pessoas) {
            p.exibirInformacoes();
            System.out.println("------------------");
        }
    }
}
