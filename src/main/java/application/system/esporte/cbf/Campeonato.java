package application.system.esporte.cbf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Campeonato {
    private String nome;
    private int ano;
    private List<Clube> clubesParticipantes;
    private List<Partida> partidas;
    private StatusCampeonato status;
    private Federacao federacaoOrganizadora;

    public Campeonato(String nome, int ano, Federacao federacaoOrganizadora) {
        this.nome = nome;
        this.ano = ano;
        this.federacaoOrganizadora = federacaoOrganizadora;
        this.clubesParticipantes = new ArrayList<>();
        this.partidas = new ArrayList<>();
        this.status = StatusCampeonato.EM_PREPARACAO;
    }

    public void inscreverClube(Clube clube) {
        if (!clubesParticipantes.contains(clube)) {
            clubesParticipantes.add(clube);
            System.out.println(clube.getNome() + " inscrito no " + nome);
        }
    }

    public void gerarTabela() {
        if (clubesParticipantes.size() < 2) {
            System.out.println("Número insuficiente de clubes para gerar tabela.");
            return;
        }

        for (int i = 0; i < clubesParticipantes.size(); i++) {
            for (int j = i + 1; j < clubesParticipantes.size(); j++) {
                Clube mandante = clubesParticipantes.get(i);
                Clube visitante = clubesParticipantes.get(j);

                Partida partida = new Partida(mandante, visitante, null, mandante.getEstadio(), null, null, null);
                partidas.add(partida);
            }
        }
        System.out.println("Tabela gerada com " + partidas.size() + " partidas.");
    }

    public void realizarPartida(int indice, int golsM, int golsV) {
        if (indice < 0 || indice >= partidas.size()) {
            System.out.println("Partida inválida.");
            return;
        }
        Partida partida = partidas.get(indice);

        if (!partida.isRealizada()) {
            partida.registrarResultado(golsM, golsV);
        } else {
            System.out.println("Partida já foi realizada.");
        }
    }

    public void exibirClassificacao() {
        System.out.println("\n===== CLASSIFICAÇÃO DO " + nome.toUpperCase() + " =====");
        clubesParticipantes.sort(Comparator.comparingInt(Clube::getPontos).reversed()
                .thenComparingInt(Clube::getSaldoGols).reversed()
                .thenComparingInt(Clube::getGolsPro).reversed());
        System.out.printf("%-20s %4s %4s %4s %4s %6s %6s %6s%n",
                "Clube", "Pts", "J", "V", "E", "D", "GP", "GC", "SG");

        for (Clube c : clubesParticipantes) {
            int jogos = c.getVitorias() + c.getEmpates() + c.getDerrotas();
            System.out.printf("%-20s %4d %4d %4d %4d %4d %6d %6d %+5d%n",
                    c.getNome(), c.getPontos(), jogos, c.getVitorias(),
                    c.getEmpates(), c.getDerrotas(), c.getGolsPro(),
                    c.getGolsContra(), c.getSaldoGols());
        }
    }

    public void iniciar() {
        if (status == StatusCampeonato.EM_PREPARACAO && clubesParticipantes.size() >= 2) {
            status = StatusCampeonato.EM_ANDAMENTO;
            System.out.println("Campeonato " + nome + " INICIADO!");
        } else {
            System.out.println("Não é possível iniciar: verifique se há clubes suficientes.");
        }
    }

    public void finalizar() {
        if (status == StatusCampeonato.EM_ANDAMENTO) {
            status = StatusCampeonato.FINALIZADO;
            System.out.println("Campeonato " + nome + " FINALIZADO!");
            exibirClassificacao();
            Clube campeao = clubesParticipantes.get(0);
            System.out.println("\n*** CAMPEÃO: " + campeao.getNome().toUpperCase() + " ***");
        }
    }

    public void exibirInformacoes() {
        System.out.println("--- CAMPEONATO ---");
        System.out.println("Nome: " + nome);
        System.out.println("Ano: " + ano);
        System.out.println("Status: " + status);
        System.out.println("Clubes: " + clubesParticipantes.size());
        System.out.println("Partidas: " + partidas.size());
    }

    public String getNome() {
        return nome;
    }
}
