package org.math;

import java.util.Scanner;

public class MatrizCalculadora {

    public static double[][] soma(double[][] a, double[][] b) {
        int linhas = a.length, colunas = a[0].length;
        double[][] resultado = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado[i][j] = a[i][j] + b[i][j];
            }
        }
        return resultado;
    }

    public static double[][] subtracao(double[][] a, double[][] b) {
        int linhas = a.length, colunas = a[0].length;
        double[][] resultado = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado[i][j] = a[i][j] - b[i][j];
            }
        }
        return resultado;
    }

    public static double[][] multiplicacao(double[][] a, double[][] b) {
        int linhasA = a.length, colunasA = a[0].length, colunasB = b[0].length;
        double[][] resultado = new double[linhasA][colunasB];
        for (int i = 0; i < linhasA; i++) {
            for (int j = 0; j < colunasB; j++) {
                for (int k = 0; k < colunasA; k++) {
                    resultado[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return resultado;
    }

    public static double[][] transposta(double[][] matriz) {
        int linhas = matriz.length, colunas = matriz[0].length;
        double[][] resultado = new double[colunas][linhas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado[j][i] = matriz[i][j];
            }
        }
        return resultado;
    }

    public static double determinante(double[][] matriz) {
        int n = matriz.length;
        if (n == 1) {
            return matriz[0][0];
        }

        if (n == 2) {
            return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
        }

        if (n == 3) {
            return matriz[0][0] * (matriz[1][1] * matriz[2][2] - matriz[1][2] * matriz[2][1])
                 - matriz[0][1] * (matriz[1][0] * matriz[2][2] - matriz[1][2] * matriz[2][0])
                 + matriz[0][2] * (matriz[1][0] * matriz[2][1] - matriz[1][1] * matriz[2][0]);
        }
        throw new IllegalArgumentException("Determinante suportado apenas para matrizes até 3x3");
    }

    public static double[][] multiplicacaoEscalar(double[][] matriz, double escalar) {
        int linhas = matriz.length, colunas = matriz[0].length;
        double[][] resultado = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado[i][j] = matriz[i][j] * escalar;
            }
        }
        return resultado;
    }

    public static void imprimir(double[][] matriz) {
        for (double[] linha : matriz) {
            System.out.print("| ");
            for (double val : linha) {
                System.out.printf("%8.2f ", val);
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public static double[][] lerMatriz(Scanner sc, int linhas, int colunas) {
        double[][] matriz = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.printf("  [%d][%d]: ", i, j);
                matriz[i][j] = sc.nextDouble();
            }
        }
        return matriz;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("      CALCULADORA DE MATRIZES         ");
            System.out.println(" 1. Soma                              ");
            System.out.println(" 2. Subtração                         ");
            System.out.println(" 3. Multiplicação                     ");
            System.out.println(" 4. Transposta                        ");
            System.out.println(" 5. Determinante (2x2 ou 3x3)         ");
            System.out.println(" 6. Multiplicação por escalar         ");
            System.out.println(" 0. Sair                              ");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1, 2, 3 -> {
                    System.out.print("Linhas da matriz A: ");
                    int linhasA = sc.nextInt();
                    System.out.print("Colunas da matriz A: ");
                    int colunasA = sc.nextInt();

                    System.out.println("Matriz A:");
                    double[][] a = lerMatriz(sc, linhasA, colunasA);

                    int linhasB, colunasB;
                    if (opcao == 3) {
                        linhasB = colunasA;
                        System.out.print("Colunas da matriz B: ");
                        colunasB = sc.nextInt();
                    } else {
                        linhasB = linhasA;
                        colunasB = colunasA;
                    }

                    System.out.println("Matriz B:");
                    double[][] b = lerMatriz(sc, linhasB, colunasB);

                    double[][] resultado = switch (opcao) {
                        case 1 -> soma(a, b);
                        case 2 -> subtracao(a, b);
                        case 3 -> multiplicacao(a, b);
                        default -> null;
                    };

                    System.out.println("Resultado:");
                    imprimir(resultado);
                }
                case 4 -> {
                    System.out.print("Linhas: ");
                    int l = sc.nextInt();
                    System.out.print("Colunas: ");
                    int c = sc.nextInt();
                    System.out.println("Matriz:");
                    double[][] m = lerMatriz(sc, l, c);
                    System.out.println("Transposta:");
                    imprimir(transposta(m));
                }
                case 5 -> {
                    System.out.print("Tamanho (2 ou 3): ");
                    int n = sc.nextInt();
                    System.out.println("Matriz:");
                    double[][] m = lerMatriz(sc, n, n);
                    System.out.printf("Determinante: %.2f%n%n", determinante(m));
                }
                case 6 -> {
                    System.out.print("Linhas: ");
                    int l = sc.nextInt();
                    System.out.print("Colunas: ");
                    int c = sc.nextInt();
                    System.out.println("Matriz:");
                    double[][] m = lerMatriz(sc, l, c);
                    System.out.print("Escalar: ");
                    double escalar = sc.nextDouble();
                    System.out.println("Resultado:");
                    imprimir(multiplicacaoEscalar(m, escalar));
                }
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        sc.close();
    }
}

