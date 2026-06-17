package games.plataforma.ascii;

class Fase {

    public int          cameraX = 0;
    private char[][]    mapa;
    private int         altura;

    public Fase(int altura) {
        this.altura = altura;
        gerarMapa();
    }

    private void gerarMapa() {
        int comprimento = 500;
        mapa = new char[altura][comprimento];

        // Espaço vazio
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < comprimento; x++) {
                mapa[y][x] = ' ';
            }
        }

        // Chão com buracos
        for (int x = 0; x < comprimento; x++) {
            if (x % 20 == 0 && x > 0) continue;
            mapa[altura - 1][x] = '=';
        }

        // Obstáculos
        for (int x = 15; x < comprimento; x += 30) {
            mapa[altura - 2][x] = 'X';
        }

        // Plataformas
        for (int x = 10; x < comprimento; x += 40) {
            mapa[altura - 6][x] = '#';
            mapa[altura - 6][x + 1] = '#';
            mapa[altura - 6][x + 2] = '#';
        }
    }

    public void moverCamera() {
        cameraX++;
    }

    public boolean ehChao(int x, int y) {
        return dentroDoMapa(x, y) && (mapa[y][x] == '=' || mapa[y][x] == '#');
    }

    public boolean ehBuraco(int x, int y) {
        return !dentroDoMapa(x, y) || mapa[y][x] == ' ';
    }

    public boolean ehObstaculo(int x, int y) {
        return dentroDoMapa(x, y) && mapa[y][x] == 'X';
    }

    public char[][] obterMapaVisivel(int largura) {
        char[][] visivel = new char[altura][largura];

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int xMundo = x + cameraX;
                visivel[y][x] = dentroDoMapa(xMundo, y) ? mapa[y][xMundo] : ' ';
            }
        }
        return visivel;
    }

    private boolean dentroDoMapa(int x, int y) {
        return y >= 0 && y < mapa.length &&
                x >= 0 && x < mapa[0].length;
    }
}