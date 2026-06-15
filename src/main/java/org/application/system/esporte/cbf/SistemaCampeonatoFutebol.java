package org.application.system.esporte.cbf;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SistemaCampeonatoFutebol {
    public static void main(String[] args) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Criar estádios
        Estadio estadio1 = new Estadio("Morumbi", "São Paulo", "SP", 72000, 1960);
        Estadio estadio2 = new Estadio("Maracanã", "Rio de Janeiro", "RJ", 78838, 1950);
        Estadio estadio3 = new Estadio("Arena da Baixada", "Curitiba", "PR", 42000, 1914);

        // Criar clubes
        Clube saoPaulo = new Clube("São Paulo FC", "11.111.111/0001-11", "1930", estadio1);
        Clube flamengo = new Clube("Flamengo", "22.222.222/0001-22", "1895", estadio2);
        Clube athletico = new Clube("Athletico Paranaense", "33.333.333/0001-33", "1924", estadio3);

        // Criar jogadores
        Jogador j1 = new Jogador("Antonio Carlos", "111.222.333-44", LocalDate.parse("15/03/1995", dateFormatter),
                "Brasileiro", "Tony", Posicao.ATACANTE, 10, PeDominante.DESTRO, 1.82, 78.0);
        Jogador j2 = new Jogador("Bruno Mendes", "222.333.444-55", LocalDate.parse("22/07/1998", dateFormatter),
                "Brasileiro", "Bruno", Posicao.MEIA_OFENSIVO, 8, PeDominante.CANHOTO, 1.75, 70.0);
        Jogador j3 = new Jogador("Carlos Eduardo", "333.444.555-66", LocalDate.parse("10/12/1996", dateFormatter),
                "Brasileiro", "Carlão", Posicao.ZAGUEIRO, 4, PeDominante.DESTRO, 1.88, 82.0);
        Jogador j4 = new Jogador("Felipe Neto", "444.555.666-77", LocalDate.parse("05/05/1997", dateFormatter),
                "Brasileiro", "Felipinho", Posicao.ATACANTE, 9, PeDominante.AMBIDESTRO, 1.79, 74.0);
        Jogador j5 = new Jogador("Gabriel Silva", "555.666.777-88", LocalDate.parse("01/01/1994", dateFormatter),
                "Brasileiro", "Gabi", Posicao.GOLEIRO, 1, PeDominante.DESTRO, 1.90, 85.0);

        // Adicionar jogadores aos clubes
        saoPaulo.adicionarJogador(j1);
        saoPaulo.adicionarJogador(j2);
        flamengo.adicionarJogador(j3);
        flamengo.adicionarJogador(j4);
        athletico.adicionarJogador(j5);

        // Criar técnicos
        Tecnico tecnico1 = new Tecnico("Muricy Ramalho", "123.456.789-00", LocalDate.parse("15/12/1955", dateFormatter),
                "Brasileiro", "TEC-001", FuncaoTecnico.TECNICO_PRINCIPAL);
        Tecnico tecnico2 = new Tecnico("Jorge Jesus", "987.654.321-11", LocalDate.parse("24/07/1954", dateFormatter),
                "Português", "TEC-002", FuncaoTecnico.TECNICO_PRINCIPAL);

        tecnico1.assumirClube(saoPaulo);
        tecnico2.assumirClube(flamengo);

        // Criar árbitros
        Arbitro arb1 = new Arbitro("Anderson Daronco", "111.222.333-44", LocalDate.parse("01/01/1980", dateFormatter),
                "Brasileiro", "ARB-001", TipoArbitro.ARBITRO_PRINCIPAL);
        Arbitro arb2 = new Arbitro("Bruno Arleu", "222.333.444-55", LocalDate.parse("15/05/1982", dateFormatter),
                "Brasileiro", "ARB-002", TipoArbitro.ASSISTENTE_1);
        Arbitro arb3 = new Arbitro("Rafael Traci", "333.444.555-66", LocalDate.parse("10/10/1985", dateFormatter),
                "Brasileiro", "ARB-003", TipoArbitro.ASSISTENTE_2);

        // Criar federação
        Federacao cbf = new Federacao("Confederação Brasileira de Futebol", "CBF", "Brasil", "Ednaldo Rodrigues");
        cbf.filiarArbitro(arb1);
        cbf.filiarArbitro(arb2);
        cbf.filiarArbitro(arb3);

        // Criar campeonato
        Campeonato brasileirao = new Campeonato("Campeonato Brasileiro", 2025, cbf);
        brasileirao.inscreverClube(saoPaulo);
        brasileirao.inscreverClube(flamengo);
        brasileirao.inscreverClube(athletico);
        brasileirao.gerarTabela();
        brasileirao.iniciar();

        // Criar e registrar partidas (simuladas)
        LocalDateTime dataPartida = LocalDateTime.now().plusDays(7);
        Partida p1 = new Partida(saoPaulo, flamengo, dataPartida, estadio1, arb1, arb2, arb3);
        p1.registrarResultado(2, 1); // São Paulo 2 x 1 Flamengo

        Partida p2 = new Partida(flamengo, athletico, dataPartida.plusDays(14), estadio2, arb1, arb2, arb3);
        p2.registrarResultado(3, 0);

        Partida p3 = new Partida(athletico, saoPaulo, dataPartida.plusDays(21), estadio3, arb1, arb2, arb3);
        p3.registrarResultado(1, 1);

        // Exibir classificação
        brasileirao.exibirClassificacao();

        // Finalizar campeonato
        brasileirao.finalizar();

        // Exibir informações detalhadas
        System.out.println("\n=== INFORMAÇÕES DOS CLUBES ===");
        saoPaulo.exibirElenco();
        saoPaulo.exibirEstatisticas();
        flamengo.exibirElenco();
        flamengo.exibirEstatisticas();

        System.out.println("\n=== JOGADOR EM DESTAQUE ===");
        j1.exibirInformacoes();
        j1.registrarGol();
        j1.receberCartaoAmarelo();

        System.out.println("\n=== FEDERAÇÃO ===");
        cbf.exibirInformacoes();

        // Polimorfismo com pessoas
        System.out.println("\n=== POLIMORFISMO: LISTA DE PESSOAS ===");
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(j1);
        pessoas.add(tecnico1);
        pessoas.add(arb1);
        for (Pessoa p : pessoas) {
            p.exibirInformacoes();
            System.out.println("-------------------");
        }
    }
}
