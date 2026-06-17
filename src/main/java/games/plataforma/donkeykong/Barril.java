package games.plataforma.donkeykong;

public class Barril {

    private int x;
    private int y;
    private int direcao;

    public Barril(int x, int y, int direcao) {
        this.x = x;
        this.y = y;
        this.direcao = direcao;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void mover() {
        x += direcao;
    }

    public void cair() {
        y++;
    }

    public void inverter() {
        direcao = -direcao;
    }
}

