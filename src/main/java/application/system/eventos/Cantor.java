package application.system.eventos;

import java.time.LocalDate;

public class Cantor extends Artista {
    private String  tipoVoz;
    private boolean possuiBandaApoio;

    public Cantor(String nome, String nomeArtistico, LocalDate dataNascimento, String nacionalidade,
                  double cacheBase, String tipoVoz, boolean possuiBandApoio) {
        super(nome, nomeArtistico, dataNascimento, nacionalidade, cacheBase);
        this.tipoVoz = tipoVoz;
        this.possuiBandaApoio = possuiBandApoio;
    }

    @Override
    public double calcularCacheEvento() {
        return cacheBase;
    }

    @Override
    public void apresentar() {
        System.out.println(nomeArtistico + " está cantando ao vivo com sua voz " + tipoVoz + "!");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tipo de Voz: " + tipoVoz);
        System.out.println("Possui banda de apoio: " + (possuiBandaApoio ? "Sim" : "Não"));
    }
}
