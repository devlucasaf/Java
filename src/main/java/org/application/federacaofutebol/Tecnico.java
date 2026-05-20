package org.application.federacaofutebol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tecnico extends Pessoa {
    private String          registroProfissional;
    private FuncaoTecnico   funcao;
    private List<Clube>     clubesTrabalhados;
    private Clube           clubeAtual;
    private int             titulosConquistados;

    public Tecnico(String nome, String cpf, LocalDate dataNascimento, String nacionalidade,
                   String registroProfissional, FuncaoTecnico funcao) {
        super(nome, cpf, dataNascimento, nacionalidade);
        this.registroProfissional = registroProfissional;
        this.funcao = funcao;
        this.clubesTrabalhados = new ArrayList<>();
        this.titulosConquistados = 0;
    }

    public void assumirClube(Clube clube) {
        if (clubeAtual != null) {
            clubesTrabalhados.add(clubeAtual);
        }
        this.clubeAtual = clube;
        clube.setTecnico(this);
        System.out.println(nome + " agora é técnico do " + clube.getNome());
    }

    public void escalarTime(List<Jogador> titulares, List<Jogador> reservas) {
        System.out.println("\n--- ESCALAÇÃO DO " + clubeAtual.getNome().toUpperCase() + " ---");
        System.out.println("Técnico: " + nome);
        System.out.println("Titulares:");

        for (Jogador j : titulares) {
            System.out.println("  " + j.getNumeroCamisa() + " - " + j.getNome() + " (" + j.getPosicaoPrincipal() + ")");
        }
        System.out.println("Reservas: " + reservas.size() + " jogadores");
    }

    public void comemorarTitulo() {
        titulosConquistados++;
        System.out.println("Técnico " + nome + " conquistou seu " + titulosConquistados + "º título!");
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- TÉCNICO ---");
        System.out.println("Nome: " + nome);
        System.out.println("Função: " + funcao);
        System.out.println("Registro: " + registroProfissional);
        System.out.println("Idade: " + calcularIdade() + " anos");
        System.out.println("Títulos: " + titulosConquistados);
        if (clubeAtual != null) {
            System.out.println("Clube atual: " + clubeAtual.getNome());
        }
    }

    public Clube getClubeAtual() {
        return clubeAtual;
    }
}