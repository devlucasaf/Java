package application.system.streaming.model;

public class Plano {

    private final String    nome;
    private final double    precoMensal;
    private final int       telasSimultaneas;
    private final boolean   permiteDownload;
    private final boolean   qualidade4K;

    public Plano(String nome, double precoMensal, int telasSimultaneas, boolean permiteDownload, boolean qualidade4K) {
        this.nome = nome;
        this.precoMensal = precoMensal;
        this.telasSimultaneas = telasSimultaneas;
        this.permiteDownload = permiteDownload;
        this.qualidade4K = qualidade4K;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoMensal() {
        return precoMensal;
    }

    public int getTelasSimultaneas() {
        return telasSimultaneas;
    }

    public boolean isPermiteDownload() {
        return permiteDownload;
    }

    public boolean isQualidade4K() {
        return qualidade4K;
    }

    @Override
    public String toString() {
        return String.format("%s - R$ %.2f/mes | %d tela(s) | download: %s | 4K: %s",
                nome, precoMensal, telasSimultaneas, permiteDownload ? "sim" : "nao", qualidade4K ? "sim" : "nao");
    }
}
