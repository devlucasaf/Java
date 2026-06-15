package org.games.sorteio.rifa;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Rifa {

    private final Map<Integer, String> numerosVendidos = new LinkedHashMap<>();
    private final Random sorteador = new Random();
    private final int totalNumeros;
    private final double precoUnitario;

    public Rifa(int totalNumeros, double precoUnitario) {
        if (totalNumeros < 1) {
            throw new IllegalArgumentException("Total de números deve ser >= 1");
        }

        this.totalNumeros = totalNumeros;
        this.precoUnitario = precoUnitario;
    }

    public boolean vender(int numero, String comprador) {
        if (numero < 1 || numero > totalNumeros) {
            return false;
        }

        if (numerosVendidos.containsKey(numero)) {
            return false;
        }
        numerosVendidos.put(numero, comprador);
        return true;
    }

    public int totalVendidos() {
        return numerosVendidos.size();
    }

    public double arrecadado() {
        return totalVendidos() * precoUnitario;
    }

    /** Sorteia somente entre os números vendidos. */
    public int sortearEntreVendidos() {
        if (numerosVendidos.isEmpty()) {
            throw new IllegalStateException("Não há números vendidos para sortear.");
        }
        Integer[] vendidos = numerosVendidos.keySet().toArray(new Integer[0]);
        return vendidos[sorteador.nextInt(vendidos.length)];
    }

    public String getComprador(int numero) {
        return numerosVendidos.get(numero);
    }

    public Map<Integer, String> getNumerosVendidos() {
        return numerosVendidos;
    }

    public int getTotalNumeros() {
        return totalNumeros;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    // ----------------- programa principal -----------------

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println();
        System.out.println("==============================================");
        System.out.println("              ️   SISTEMA DE RIFA               ");
        System.out.println("==============================================");

        System.out.print("\nQuantos números terá a rifa? ");
        int total = Integer.parseInt(entrada.nextLine().trim());
        System.out.print("Preço de cada número (R$): ");
        double preco = Double.parseDouble(entrada.nextLine().trim().replace(',', '.'));

        Rifa rifa = new Rifa(total, preco);

        boolean executando = true;
        while (executando) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Vender número");
            System.out.println("2. Listar números vendidos");
            System.out.println("3. Realizar sorteio");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            String opcao = entrada.nextLine().trim();

            switch (opcao) {
                case "1" -> venderNumero(entrada, rifa);
                case "2" -> listarVendidos(rifa);
                case "3" -> {
                    realizarSorteio(rifa);
                    executando = false;
                }
                case "0" -> executando = false;
                default  -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void venderNumero(Scanner entrada, Rifa rifa) {
        System.out.printf("Número (1 a %d): ", rifa.getTotalNumeros());
        int num;
        try {
            num = Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Número inválido.");
            return;
        }
        System.out.print("Nome do comprador: ");
        String comprador = entrada.nextLine().trim();
        if (comprador.isEmpty()) {
            System.out.println("⚠ Nome obrigatório.");
            return;
        }

        if (rifa.vender(num, comprador)) {
            System.out.printf("✓ Número %d vendido para %s.%n", num, comprador);
        } else {
            System.out.println("⚠ Número fora do intervalo ou já vendido.");
        }
    }

    private static void listarVendidos(Rifa rifa) {
        if (rifa.getNumerosVendidos().isEmpty()) {
            System.out.println("Nenhum número vendido.");
            return;
        }
        System.out.println("\n-- Números vendidos --");
        rifa.getNumerosVendidos().forEach((num, comprador) ->
                System.out.printf("  %3d → %s%n", num, comprador));
        System.out.printf("Total: %d / %d   |   Arrecadado: R$ %.2f%n",
                rifa.totalVendidos(), rifa.getTotalNumeros(), rifa.arrecadado());
    }

    private static void realizarSorteio(Rifa rifa) {
        if (rifa.totalVendidos() == 0) {
            System.out.println("Não há números vendidos para sortear.");
            return;
        }
        System.out.println("\nRealizando sorteio...");
        try {
            Thread.sleep(800);
        } catch (InterruptedException ignored) {}


        int sorteado = rifa.sortearEntreVendidos();
        String ganhador = rifa.getComprador(sorteado);
        System.out.println("==============================================");
        System.out.printf ("Número sorteado: %d%n", sorteado);
        System.out.printf ("Ganhador(a): %s%n", ganhador);
        System.out.printf ("Total arrecadado: R$ %.2f%n", rifa.arrecadado());
        System.out.println("==============================================");
    }
}

