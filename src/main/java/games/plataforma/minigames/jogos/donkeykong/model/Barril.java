package games.plataforma.minigames.jogos.donkeykong.model;

import java.awt.*;

public class Barril {
    Rectangle   retangulo;
    int         velocidadeX;
    int         velocidadeY;
    boolean     emPlataforma;
    int         plataformaIndex;

    public Barril(int x, int y) {
        retangulo = new Rectangle(x, y, 20, 20);
        velocidadeX = 2;
        velocidadeY = 0;
        emPlataforma = false;
        plataformaIndex = -1;
    }
}
