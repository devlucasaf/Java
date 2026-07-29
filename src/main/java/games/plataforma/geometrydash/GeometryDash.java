package games.plataforma.geometrydash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GeometryDash {

    private static final int LARGURA = 40;
    private static final int ALTURA = 10;
    private static final int CHAO = ALTURA - 2;
    private static final int FORCA_PULO = 3;
    private static final int GRAVIDADE = 1;

    private final Random        sorteador = new Random();
    private final Scanner       entrada = new Scanner(System.in);
    private final List<int[]>   obstaculos = new ArrayList<>();

    private int     cuboY = CHAO;
    private int     velocidadeY = 0;
    private int     distancia = 0;
    private int     velocidade = 1;
    private int     gravidadeInvertida = 1;
    private boolean vivo = true;

    public static void main(String[] args) {
        new GeometryDash().iniciar();
    }

    public void iniciar() {
        System.out.println("=== GEOMETRY DASH ===");
        System.out.println("Pule sobre os espinhos. Portais (Q) invertem a gravidade.");
        System.out.println("Comandos: espaco ou w (pular) | enter (continuar)");
        System.out.print("Pressione ENTER para comecar...");
        entrada.nextLine();

        while (vivo) {
            atualizarMundo();
            desenhar();
            System.out.print("Acao: ");
            String comando = entrada.nextLine().trim().toLowerCase();
            if ((comando.equals("w") || comando.equals(" ") || comando.equals("espaco")) && estaNoChao()) {
                velocidadeY = -FORCA_PULO * gravidadeInvertida;
            }

            aplicarFisica();
            verificarColisoes();
            distancia += velocidade;
            if (distancia % 60 == 0 && velocidade < 3) {
                velocidade++;
            }
        }

        System.out.println("\nGAME OVER!");
        System.out.println("Distancia: " + distancia);
    }

    private boolean estaNoChao() {
        if (gravidadeInvertida == 1) {
            return cuboY >= CHAO;
        }
        return cuboY <= 1;
    }

    private void atualizarMundo() {
        Iterator<int[]> it = obstaculos.iterator();
        while (it.hasNext()) {
            int[] o = it.next();
            o[0] -= velocidade;
            if (o[0] < 0) {
                it.remove();
            }
        }

        if (sorteador.nextInt(4) == 0) {
            int tipo = sorteador.nextInt(10);
            if (tipo < 6) {
                obstaculos.add(new int[]{LARGURA - 1, CHAO, 0});
            } else if (tipo < 8) {
                obstaculos.add(new int[]{LARGURA - 1, CHAO - 1, 1});
            } else {
                obstaculos.add(new int[]{LARGURA - 1, 2 + sorteador.nextInt(CHAO - 3), 2});
            }
        }
    }

    private void aplicarFisica() {
        cuboY += velocidadeY;
        velocidadeY += GRAVIDADE * gravidadeInvertida;
        if (gravidadeInvertida == 1) {
            if (cuboY >= CHAO) {
                cuboY = CHAO;
                velocidadeY = 0;
            }

            if (cuboY < 1) {
                cuboY = 1;
            }
        } else {
            if (cuboY <= 1) {
                cuboY = 1;
                velocidadeY = 0;
            }

            if (cuboY > CHAO) {
                cuboY = CHAO;
            }
        }
    }

    private void verificarColisoes() {
        int cuboX = 5;
        Iterator<int[]> it = obstaculos.iterator();
        while (it.hasNext()) {
            int[] o = it.next();
            if (o[0] == cuboX && o[1] == cuboY) {
                if (o[2] == 2) {
                    gravidadeInvertida = -gravidadeInvertida;
                    it.remove();
                } else {
                    vivo = false;
                    return;
                }
            }
        }
    }

    private void desenhar() {
        char[][] tela = new char[ALTURA][LARGURA];
        for (int y = 0; y < ALTURA; y++) {
            for (int x = 0; x < LARGURA; x++) {
                tela[y][x] = ' ';
            }
        }

        for (int x = 0; x < LARGURA; x++) {
            tela[CHAO + 1][x] = '_';
            tela[0][x] = '_';
        }
        for (int[] o : obstaculos) {
            if (o[0] >= 0 && o[0] < LARGURA) {
                char c = switch (o[2]) {
                    case 0 -> '^';
                    case 1 -> '#';
                    case 2 -> 'Q';
                    default -> '?';
                };
                tela[o[1]][o[0]] = c;
            }
        }
        tela[cuboY][5] = '[';

        System.out.println();
        System.out.println(
                "Distancia: " + distancia
                        + "   Vel: " + velocidade
                        + "   Gravidade: " + (gravidadeInvertida == 1 ? "normal" : "invertida")
        );
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

