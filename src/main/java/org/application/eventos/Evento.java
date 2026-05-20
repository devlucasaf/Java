package org.application.eventos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String              nome;
    private TipoEvento          tipo;
    private LocalDate           data;
    private LocalEvento         local;
    private double              custoEstimado;
    private double              receitaIngressos;
    private StatusEvento        status;
    private EmpresaResponsavel  organizador;
    private List<Artista>       artistasContratados;
    private List<Banda>         bandasContratadas;
    private List<Patrocinio>    patrocinios;
    private double              totalArrecadado;

    public Evento(String nome, TipoEvento tipo, LocalDate data, LocalEvento local,
                  double custoEstimado, EmpresaResponsavel organizador) {
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
        this.local = local;
        this.custoEstimado = custoEstimado;
        this.organizador = organizador;
        this.status = StatusEvento.PLANEJADO;
        this.artistasContratados = new ArrayList<>();
        this.bandasContratadas = new ArrayList<>();
        this.patrocinios = new ArrayList<>();
        this.receitaIngressos = 0;
        this.totalArrecadado = 0;
    }

    public void contratarArtista(Artista artista) {
        artistasContratados.add(artista);
        System.out.println(artista.getNomeArtistico() + " contratado para o evento " + nome);
    }

    public void contratarBanda(Banda banda) {
        bandasContratadas.add(banda);
        System.out.println("Banda " + banda.getNome() + " contratada para o evento " + nome);
    }

    public void adicionarPatrocinio(Patrocinio p) {
        patrocinios.add(p);
        totalArrecadado += p.getValor();
        System.out.println("Patrocínio de " + p.getNomeEmpresa() + " adicionado ao evento.");
    }

    public void venderIngressos(int quantidade, double precoUnitario) {
        if (!local.validarCapacidade(quantidade + (int)(receitaIngressos / precoUnitario))) {
            System.out.println("Erro: Quantidade de ingressos excede a capacidade do local!");
            return;
        }
        double arrecadacao = quantidade * precoUnitario;
        receitaIngressos += arrecadacao;
        totalArrecadado += arrecadacao;
        System.out.println(quantidade + " ingressos vendidos. Arrecadação: R$" + arrecadacao);
    }

    public void confirmarEvento() {
        if (totalArrecadado >= custoEstimado * 0.5) {
            this.status = StatusEvento.CONFIRMADO;
            System.out.println("Evento " + nome + " CONFIRMADO!");
        } else {
            System.out.println("Evento " + nome + " ainda não atingiu meta mínima de arrecadação.");
        }
    }

    public void iniciar() {
        if (status == StatusEvento.CONFIRMADO) {
            this.status = StatusEvento.EM_ANDAMENTO;
            System.out.println("Evento " + nome + " começou no " + local.getTipoLocal() + "!");
            for (Artista a : artistasContratados) {
                a.apresentar();
            }
            for (Banda b : bandasContratadas) {
                b.tocarMusica("Sucesso da banda");
            }
        } else {
            System.out.println("Não é possível iniciar: evento não confirmado.");
        }
    }

    public void finalizar() {
        if (status == StatusEvento.EM_ANDAMENTO || status == StatusEvento.CONFIRMADO) {
            this.status = StatusEvento.REALIZADO;
            System.out.println("Evento " + nome + " finalizado.");
            double lucro = totalArrecadado - custoEstimado;
            System.out.println("Arrecadação total: R$" + totalArrecadado);
            System.out.println("Custo estimado: R$" + custoEstimado);
            System.out.println("Lucro/Prejuízo: R$" + (lucro >= 0 ? lucro : "(" + lucro + ")"));
        }
    }

    public void exibirDetalhes() {
        System.out.println("\n===== DETALHES DO EVENTO =====");
        System.out.println("Nome: " + nome);
        System.out.println("Tipo: " + tipo);
        System.out.println("Data: " + data);
        local.exibirLocal();
        System.out.println("Status: " + status);
        System.out.println("Custo estimado: R$" + custoEstimado);
        System.out.println("Total arrecadado: R$" + totalArrecadado);
        System.out.println("Artistas solo: " + artistasContratados.size());

        for (Artista a : artistasContratados) {
            System.out.println("  - " + a.getNomeArtistico());
        }

        System.out.println("Bandas: " + bandasContratadas.size());
        for (Banda b : bandasContratadas) {
            System.out.println("  - " + b.getNome());
        }

        System.out.println("Patrocínios: " + patrocinios.size());
        for (Patrocinio p : patrocinios) {
            p.exibirInfo();
        }
    }

    public String getNome() {
        return nome;
    }

    public StatusEvento getStatus() {
        return status;
    }

    public double getTotalArrecadado() {
        return totalArrecadado;
    }

    public LocalEvento getLocal() {
        return local;
    }
}