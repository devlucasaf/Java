package games.plataforma.minigames.jogos.sonic.model;

import java.awt.*;

public class Inimigo {
    Rectangle   rect;
    int         velocidadeX;
    boolean     ativo;

    Inimigo(int x, int y) {
        rect = new Rectangle(x, y, 30, 30);
        velocidadeX = -2;
        ativo = true;
    }
}
