package org.games.narrativo.detetive;

class Suspeito {
    String  nome;
    String  descricao;
    String  motivacao;
    String  alibi;
    boolean comportamentoSuspeito;

    public Suspeito(String nome, String descricao, String motivacao, String alibi) {
        this.nome = nome;
        this.descricao = descricao;
        this.motivacao = motivacao;
        this.alibi = alibi;
        this.comportamentoSuspeito = false;
    }

    public void interrogar(int nivelInvestigacao) {
        System.out.println("\nInterrogando " + nome + ":");
        System.out.println("Descrição: " + descricao);
        System.out.println("Motivação: " + motivacao);

        if (nivelInvestigacao >= 2) {
            System.out.println("Álibi: " + alibi);
        } else {
            System.out.println("Ele(a) parece hesitar antes de falar sobre o álibi...");
            comportamentoSuspeito = true;
        }
    }
}
