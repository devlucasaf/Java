package application.system.eventos.model.artista;

import java.time.LocalDate;

public abstract class Instrumentista extends Artista {
    protected String instrumentoPrincipal;

    public Instrumentista(String nome, String nomeArtistico, LocalDate dataNascimento, String nacionalidade,
                          double cacheBase, String instrumentoPrincipal) {
        super(nome, nomeArtistico, dataNascimento, nacionalidade, cacheBase);
        this.instrumentoPrincipal = instrumentoPrincipal;
        adicionarInstrumento(instrumentoPrincipal);
    }

    public void afinarInstrumento() {
        System.out.println(nomeArtistico + " está afinando o " + instrumentoPrincipal);
    }

    @Override
    public double calcularCacheEvento() {
        return cacheBase * 0.8;
    }

    public String getInstrumentoPrincipal() {
        return instrumentoPrincipal;
    }

    public void setInstrumentoPrincipal(String instrumentoPrincipal) {
        this.instrumentoPrincipal = instrumentoPrincipal;
    }
}
