package games.plataforma.doodlejump;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DoodleJump {

    private static final int LARGURA = 20;
    private static final int ALTURA = 18;
    private static final int GRAVIDADE = 1;
    private static final int FORCA_PULO = 4;
    private static final int LIMITE_SUBIDA = 6;

    private final Random sorteador = new Random();
    private final Scanner entrada = new Scanner(System.in);
    private final List<Plataforma> plataformas = new ArrayList<>();

    private int     posicaoX;
    private int     posicaoY;
    private int     velocidadeY;
    private int     pontuacao;
    private int     recorde;
    private boolean vivo;

    public static void main(String[] args) {
        new DoodleJump().iniciar();
    }

    public void iniciar() {
        System.out.println("=== DOODLE JUMP ===");
        System.out.println("Comandos: a (esquerda) | d (direita) | enter (manter)");
        System.out.println("Pressione ENTER para comecar...");
        entrada.nextLine();

        boolean jogarNovamente = true;
        while (jogarNovamente) {
            reiniciar();
            executarPartida();
            System.out.println("\nGAME OVER!");
            System.out.println("Pontuacao: " + pontuacao);
            System.out.println("Recorde: " + recorde);
            System.out.print("Jogar de novo? (s/n): ");
            jogarNovamente = entrada.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    private void reiniciar() {
        plataformas.clear();
        posicaoX = LARGURA / 2;
        posicaoY = ALTURA - 2;
        velocidadeY = -FORCA_PULO;
        pontuacao = 0;
        vivo = true;

        plataformas.add(new Plataforma(posicaoX - 1, ALTURA - 1, 3));
        for (int i = 1; i < 8; i++) {
            adicionarPlataformaAleatoria(ALTURA - 1 - i * 2);
        }
    }

    private void adicionarPlataformaAleatoria(int y) {
        int largura = 2 + sorteador.nextInt(3);
        int x = sorteador.nextInt(LARGURA - largura);
        plataformas.add(new Plataforma(x, y, largura));
    }

    private void executarPartida() {
        while (vivo) {
            desenhar();
            System.out.print("Acao: ");
            String comando = entrada.nextLine().trim().toLowerCase();
            processarComando(comando);
            atualizarFisica();
        }
    }

    private void processarComando(String comando) {
        if (comando.equals("a")) {
            posicaoX--;
        } else if (comando.equals("d")) {
            posicaoX++;
        }

        if (posicaoX < 0) {
            posicaoX = LARGURA - 1;
        } else if (posicaoX >= LARGURA) {
            posicaoX = 0;
        }
    }

    private void atualizarFisica() {
        int proximoY = posicaoY + velocidadeY;

        if (velocidadeY > 0) {
            for (Plataforma p : plataformas) {
                if (p.colide(posicaoX, posicaoY, proximoY)) {
                    velocidadeY = -FORCA_PULO;
                    proximoY = p.getY() - 1;
                    break;
                }
            }
        }

        posicaoY = proximoY;
        velocidadeY += GRAVIDADE;

        if (posicaoY < LIMITE_SUBIDA) {
            int desloca = LIMITE_SUBIDA - posicaoY;
            posicaoY = LIMITE_SUBIDA;
            for (Plataforma p : plataformas) {
                p.descer(desloca);
            }

            pontuacao += desloca;
            if (pontuacao > recorde) {
                recorde = pontuacao;
            }
        }

        plataformas.removeIf(p -> p.getY() >= ALTURA);
        while (plataformas.size() < 8) {
            adicionarPlataformaAleatoria(-1 - sorteador.nextInt(3));
        }

        if (posicaoY >= ALTURA) {
            vivo = false;
        }
    }

    private void desenhar() {
        char[][] tela = new char[ALTURA][LARGURA];
        for (int y = 0; y < ALTURA; y++) {
            for (int x = 0; x < LARGURA; x++) {
                tela[y][x] = ' ';
            }
        }
        for (Plataforma p : plataformas) {
            if (p.getY() >= 0 && p.getY() < ALTURA) {
                for (int i = 0; i < p.getLargura(); i++) {
                    int x = p.getX() + i;
                    if (x >= 0 && x < LARGURA) {
                        tela[p.getY()][x] = '=';
                    }
                }
            }
        }

        if (posicaoY >= 0 && posicaoY < ALTURA) {
            tela[posicaoY][posicaoX] = 'O';
        }

        System.out.println();
        System.out.println("Pontuacao: " + pontuacao + "   Recorde: " + recorde);
        StringBuilder borda = new StringBuilder("+");

        for (int i = 0; i < LARGURA; i++) borda.append('-');
        borda.append('+');
        System.out.println(borda);
        for (int y = 0; y < ALTURA; y++) {
            System.out.print('|');
            for (int x = 0; x < LARGURA; x++) {
                System.out.print(tela[y][x]);
            }
            System.out.println('|');
        }
        System.out.println(borda);
    }
}

