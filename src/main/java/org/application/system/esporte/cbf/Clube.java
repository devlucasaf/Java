package org.application.system.esporte.cbf;

import java.util.ArrayList;
import java.util.List;

public class Clube {
    private String          nome;
    private String          cnpj;
    private String          anoFundacao;
    private Estadio         estadio;
    private List<Jogador>   jogadores;
    private Tecnico         tecnico;
    private int             pontos;
    private int             vitorias;
    private int             empates;
    private int             derrotas;
    private int             golsPro;
    private int             golsContra;

    public Clube(String nome, String cnpj, String anoFundacao, Estadio estadio) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.anoFundacao = anoFundacao;
        this.estadio = estadio;
        this.jogadores = new ArrayList<>();
        this.pontos = 0;
        this.vitorias = 0;
        this.empates = 0;
        this.derrotas = 0;
        this.golsPro = 0;
        this.golsContra = 0;
    }

    public void adicionarJogador(Jogador jogador) {
        if (!jogadores.contains(jogador)) {
            jogadores.add(jogador);
            jogador.setClubeAtual(this);
        }
    }

    public void removerJogador(Jogador jogador) {
        jogadores.remove(jogador);
    }

    public void registrarPartida(int golsMarcados, int golsSofridos) {
        golsPro += golsMarcados;
        golsContra += golsSofridos;

        if (golsMarcados > golsSofridos) {
            vitorias++;
            pontos += 3;
            System.out.println(nome + " VENCEU!");
        } else if (golsMarcados == golsSofridos) {
            empates++;
            pontos += 1;
            System.out.println(nome + " empatou.");
        } else {
            derrotas++;
            System.out.println(nome + " perdeu.");
        }
    }

    public void exibirElenco() {
        System.out.println("\n--- ELENCO DO " + nome.toUpperCase() + " ---");
        System.out.println("Técnico: " + (tecnico != null ? tecnico.getNome() : "Não definido"));
        System.out.println("Jogadores (" + jogadores.size() + "):");
        for (Jogador j : jogadores) {
            System.out.println("  " + j.getNumeroCamisa() + " - " + j.getNome() + " (" + j.getPosicaoPrincipal() + ")");
        }
    }

    public void exibirEstatisticas() {
        System.out.println("\n--- ESTATÍSTICAS DO " + nome + " ---");
        System.out.println("Pontos: " + pontos);
        System.out.println("Jogos: V=" + vitorias + " E=" + empates + " D=" + derrotas);
        System.out.println("Gols: " + golsPro + " marcados, " + golsContra + " sofridos");
        System.out.println("Saldo: " + (golsPro - golsContra));
    }

    public String getNome() {
        return nome;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public int getPontos() {
        return pontos;
    }

    public int getVitorias() {
        return vitorias;
    }

    public int getEmpates() {
        return empates;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getGolsPro() {
        return golsPro;
    }

    public int getGolsContra() {
        return golsContra;
    }

    public int getSaldoGols() {
        return golsPro - golsContra;
    }
}
