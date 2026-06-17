package games.plataforma.sonic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Sonic {

    private static final int LARGURA = 40;
    private static final int ALTURA = 12;
    private static final int CHAO = ALTURA - 2;
    private static final int FORCA_PULO = 4;
    private static final int GRAVIDADE = 1;

    private final Random sorteador = new Random();
    private final Scanner entrada = new Scanner(System.in);
    private final List<int[]> aneis = new ArrayList<>();
    private final List<int[]> inimigos = new ArrayList<>();
    private final List<int[]> rampas = new ArrayList<>();

    private int     jogadorX = 4;
    private int     jogadorY = CHAO;
    private int     velocidadeY = 0;
    private int     totalAneis = 0;
    private int     distancia = 0;
    private int     velocidade = 1;
    private boolean vivo = true;

    public static void main(String[] args) {
        new Sonic().iniciar();
    }

    public void iniciar() {
        System.out.println("=== SONIC ===");
        System.out.println("Colete aneis e evite robos. Voce corre automaticamente.");
        System.out.println("Comandos: w (pular) | enter (continuar)");
        System.out.print("Pressione ENTER para comecar...");
        entrada.nextLine();

        while (vivo) {
            atualizar();
            desenhar();
            System.out.print("Acao: ");
            String comando = entrada.nextLine().trim().toLowerCase();
            if (comando.equals("w") && jogadorY >= CHAO) {
                velocidadeY = -FORCA_PULO;
            }

            aplicarGravidade();
            verificarColisoes();
            distancia += velocidade;
            if (distancia % 30 == 0 && velocidade < 3) {
                velocidade++;
            }
        }

        System.out.println("\nGAME OVER!");
        System.out.println("Distancia percorrida: " + distancia);
        System.out.println("Aneis coletados: " + totalAneis);
    }

    private void atualizar() {
        Iterator<int[]> itA = aneis.iterator();
        while (itA.hasNext()) {
            int[] a = itA.next();
            a[0] -= velocidade;
            if (a[0] < 0) {
                itA.remove();
            }
        }
        Iterator<int[]> itI = inimigos.iterator();
        while (itI.hasNext()) {
            int[] i = itI.next();
            i[0] -= velocidade + 1;
            if (i[0] < 0) {
                itI.remove();
            }
        }

        Iterator<int[]> itR = rampas.iterator();
        while (itR.hasNext()) {
            int[] r = itR.next();
            r[0] -= velocidade;
            if (r[0] < 0) {
                itR.remove();
            }
        }

        if (sorteador.nextInt(3) == 0) {
            aneis.add(new int[]{LARGURA - 1, CHAO - sorteador.nextInt(4)});
        }

        if (sorteador.nextInt(8) == 0) {
            inimigos.add(new int[]{LARGURA - 1, CHAO});
        }

        if (sorteador.nextInt(15) == 0) {
            rampas.add(new int[]{LARGURA - 1, CHAO});
        }
    }

    private void aplicarGravidade() {
        jogadorY += velocidadeY;
        velocidadeY += GRAVIDADE;
        if (jogadorY >= CHAO) {
            jogadorY = CHAO;
            velocidadeY = 0;
        }

        if (jogadorY < 0) {
            jogadorY = 0;
            velocidadeY = 0;
        }
    }

    private void verificarColisoes() {
        Iterator<int[]> itA = aneis.iterator();
        while (itA.hasNext()) {
            int[] a = itA.next();
            if (a[0] == jogadorX && a[1] == jogadorY) {
                totalAneis++;
                itA.remove();
            }
        }

        for (int[] r : rampas) {
            if (r[0] == jogadorX && jogadorY == CHAO) {
                velocidadeY = -FORCA_PULO - 1;
            }
        }
        Iterator<int[]> itI = inimigos.iterator();
        while (itI.hasNext()) {
            int[] i = itI.next();
            if (i[0] == jogadorX && i[1] == jogadorY) {
                if (totalAneis == 0) {
                    vivo = false;
                    return;
                }
                totalAneis = 0;
                itI.remove();
                System.out.println("Voce perdeu seus aneis!");
            }
        }
    }

    private void desenhar() {
        char[][] tela = new char[ALTURA][LARGURA];
        for (int y = 0; y < ALTURA; y++) {
            for (int x = 0; x < LARGURA; x++) {
                tela[y][x] = (y == CHAO + 1) ? '_' : ' ';
            }
        }

        for (int[] r : rampas) {
            if (r[0] >= 0 && r[0] < LARGURA) {
                tela[r[1]][r[0]] = '/';
            }
        }

        for (int[] a : aneis) {
            if (a[0] >= 0 && a[0] < LARGURA && a[1] >= 0 && a[1] < ALTURA) {
                tela[a[1]][a[0]] = 'o';
            }
        }

        for (int[] i : inimigos) {
            if (i[0] >= 0 && i[0] < LARGURA && i[1] >= 0 && i[1] < ALTURA) {
                tela[i[1]][i[0]] = 'X';
            }
        }

        if (jogadorY >= 0 && jogadorY < ALTURA) {
            tela[jogadorY][jogadorX] = 'S';
        }

        System.out.println();
        System.out.println("Aneis: " + totalAneis + "   Distancia: " + distancia + "   Vel: " + velocidade);
        StringBuilder borda = new StringBuilder("+");
        for (int i = 0; i < LARGURA; i++) {
            borda.append('-');
        }

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

