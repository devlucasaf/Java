package games.tabuleiro.campominado;

public class Celula {

    private boolean mina;
    private boolean revelada;
    private boolean bandeira;
    private int     minasAoRedor;

    public Celula() {
        this.mina = false;
        this.revelada = false;
        this.bandeira = false;
        this.minasAoRedor = 0;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public boolean isRevelada() {
        return revelada;
    }

    public void revelar() {
        this.revelada = true;
    }

    public boolean isBandeira() {
        return bandeira;
    }

    public void alternarBandeira() {
        if (!revelada) {
            this.bandeira = !this.bandeira;
        }
    }

    public int getMinasAoRedor() {
        return minasAoRedor;
    }

    public void incrementarMinasAoRedor() {
        this.minasAoRedor++;
    }
}
