package application.exercicios.faculdade.einstein;

import java.util.Scanner;

public class Jogo {
    private static final Scanner entrada = new Scanner(System.in);
    private static Tabuleiro tabuleiro;

    public static void main(String[] args) {
        System.out.println("=== MINI GAME: TESTE DE EINSTEIN ===");
        System.out.println("Descubra as características de cada casa.");
        System.out.println("Comandos:");
        System.out.println("  atribuir <casa> <categoria> <valor>");
        System.out.println("  remover <casa> <categoria>");
        System.out.println("  sair");
        System.out.println("Exemplo: atribuir 1 nacionalidade norueguês");
        System.out.println("Categorias: nacionalidade, cor, bebida, cigarro, animal");
        System.out.println("As casas são numeradas de 1 a 5.\n");

        tabuleiro = new Tabuleiro();
        tabuleiro.exibir();

        while (true) {
            System.out.print("> ");
            String linha = entrada.nextLine().trim();
            if (linha.equalsIgnoreCase("sair")) {
                System.out.println("Jogo encerrado.");
                break;
            }

            String[] partes = linha.split("\\s+");
            if (partes.length == 0) {
                continue;
            }

            String comando = partes[0].toLowerCase();
            if (comando.equals("atribuir") && partes.length >= 4) {
                try {
                    int casa = Integer.parseInt(partes[1]);
                    Categoria cat = parseCategoria(partes[2]);
                    if (cat == null) {
                        System.out.println("Categoria inválida. Use: nacionalidade, cor, bebida, cigarro, animal.");
                        continue;
                    }
                    StringBuilder valorBuilder = new StringBuilder(partes[3]);
                    for (int i = 4; i < partes.length; i++) {
                        valorBuilder.append(" ").append(partes[i]);
                    }
                    String valor = valorBuilder.toString();

                    tabuleiro.atribuir(casa - 1, cat, valor);
                } catch (NumberFormatException e) {
                    System.out.println("Número da casa inválido.");
                }
            } else if (comando.equals("remover") && partes.length >= 3) {
                try {
                    int casa = Integer.parseInt(partes[1]);
                    Categoria cat = parseCategoria(partes[2]);
                    if (cat == null) {
                        System.out.println("Categoria inválida.");
                        continue;
                    }
                    tabuleiro.remover(casa - 1, cat);
                } catch (NumberFormatException e) {
                    System.out.println("Número da casa inválido.");
                }
            } else {
                System.out.println("Comando inválido. Use: atribuir <casa> <categoria> <valor> ou remover <casa> <categoria>");
                continue;
            }

            tabuleiro.exibir();

            if (tabuleiro.estaCompleto()) {
                System.out.println("PARABÉNS! Você resolveu o enigma de Einstein!");
                System.out.println("O dono do peixe é o Alemão, que vive na casa Verde, bebe Café e fuma Prince.");
                break;
            }
        }

        entrada.close();
    }

    private static Categoria parseCategoria(String str) {
        switch (str.toLowerCase()) {
            case "nacionalidade":
                return Categoria.NACIONALIDADE;
            case "cor":
                return Categoria.COR;
            case "bebida":
                return Categoria.BEBIDA;
            case "cigarro":
                return Categoria.CIGARRO;
            case "animal":
                return Categoria.ANIMAL;
            default:
                return null;
        }
    }
}