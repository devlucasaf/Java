package application.exercicios.faculdade.pacman;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Layout {

    private final int           largura;
    private final int           altura;
    private final boolean[][]   paredes;
    private final boolean[][]   comidas;
    private final List<Posicao> capsulas;
    private final Posicao       posicaoPacman;
    private final List<Posicao> posicoesFantasmas;
    private final int           totalComida;

    private Layout(int largura, int altura, boolean[][] paredes, boolean[][] comidas,
                   List<Posicao> capsulas, Posicao posicaoPacman,
                   List<Posicao> posicoesFantasmas, int totalComida) {
        this.largura = largura;
        this.altura = altura;
        this.paredes = paredes;
        this.comidas = comidas;
        this.capsulas = capsulas;
        this.posicaoPacman = posicaoPacman;
        this.posicoesFantasmas = posicoesFantasmas;
        this.totalComida = totalComida;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public boolean ehParede(int x, int y) {
        if (x < 0 || y < 0 || x >= largura || y >= altura) {
            return true;
        }
        return paredes[x][y];
    }

    public boolean[][] copiarComidas() {
        boolean[][] copia = new boolean[largura][altura];
        for (int i = 0; i < largura; i++) {
            System.arraycopy(comidas[i], 0, copia[i], 0, altura);
        }
        return copia;
    }

    public List<Posicao> getCapsulas() {
        return capsulas;
    }

    public Posicao getPosicaoPacman() {
        return posicaoPacman;
    }

    public List<Posicao> getPosicoesFantasmas() {
        return posicoesFantasmas;
    }

    public int getTotalComida() {
        return totalComida;
    }

    public static Layout carregar(String nome) throws IOException {
        Path caminho = localizarArquivo(nome);
        List<String> linhas = Files.readAllLines(caminho);
        while (!linhas.isEmpty() && linhas.get(linhas.size() - 1).trim().isEmpty()) {
            linhas.remove(linhas.size() - 1);
        }

        int altura = linhas.size();
        int largura = linhas.get(0).length();

        boolean[][] paredes = new boolean[largura][altura];
        boolean[][] comidas = new boolean[largura][altura];
        List<Posicao> capsulas = new ArrayList<>();
        Posicao pacman = null;
        List<Posicao> fantasmas = new ArrayList<>();
        int totalComida = 0;

        for (int linha = 0; linha < altura; linha++) {
            String texto = linhas.get(linha);
            int y = altura - linha - 1;
            for (int x = 0; x < largura && x < texto.length(); x++) {
                char c = texto.charAt(x);
                switch (c) {
                    case '%':
                        paredes[x][y] = true;
                        break;
                    case '.':
                        comidas[x][y] = true;
                        totalComida++;
                        break;
                    case 'o':
                        capsulas.add(new Posicao(x, y));
                        break;
                    case 'P':
                        pacman = new Posicao(x, y);
                        break;
                    case 'G':
                        fantasmas.add(new Posicao(x, y));
                        break;
                    default:
                        break;
                }
            }
        }

        if (pacman == null) {
            throw new IllegalStateException("Layout sem posicao inicial do Pac-Man (marcador 'P').");
        }

        return new Layout(largura, altura, paredes, comidas,
                Collections.unmodifiableList(capsulas), pacman,
                Collections.unmodifiableList(fantasmas), totalComida);
    }

    private static Path localizarArquivo(String nome) {
        String arquivo = nome.endsWith(".lay") ? nome : nome + ".lay";
        Path[] candidatos = {
                Path.of("layouts", arquivo),
                Path.of("src", "main", "resources", "layouts", arquivo),
                Path.of("src", "main", "java", "application", "exercicios",
                        "faculdade", "pacman", "layouts", arquivo)
        };
        for (Path p : candidatos) {
            if (Files.exists(p)) {
                return p;
            }
        }
        return candidatos[candidatos.length - 1];
    }
}

