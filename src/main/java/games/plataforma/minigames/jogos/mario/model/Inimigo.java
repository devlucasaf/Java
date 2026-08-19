package games.plataforma.minigames.jogos.mario.model;

import java.awt.*;

public class Inimigo {
    Rectangle   retangulo;
    int         velocidadeX;
    boolean     ativo;

    public Inimigo(int x, int y) {
        retangulo = new Rectangle(x, y, 30, 30);
        velocidadeX = -2;
        ativo = true;
    }
}
