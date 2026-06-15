package org.math.geometria.tales;

import java.util.Scanner;

public class TeoremaTales {
	public static double calcularSegmentoDesconhecido(char segmentoDesconhecido, double a, double b, double c, double d) {
		switch (Character.toLowerCase(segmentoDesconhecido)) {
			case 'a':
				validarDivisor(b, "b");
				return (b * c) / d;
			case 'b':
				validarDivisor(c, "c");
				return (a * d) / c;
			case 'c':
				validarDivisor(b, "b");
				return (a * d) / b;
			case 'd':
				validarDivisor(a, "a");
				return (b * c) / a;
			default:
				throw new IllegalArgumentException("Segmento desconhecido inválido. Use apenas: a, b, c ou d.");
		}
	}

	private static void validarDivisor(double valor, String nomeSegmento) {
		if (valor == 0) {
			throw new IllegalArgumentException("O segmento '" + nomeSegmento + "' não pode ser zero para evitar divisão por zero.");
		}
	}

	private static double lerSegmentoPositivo(Scanner entrada, String nomeSegmento) {
		double valor;
		do {
			System.out.print("Digite o valor de " + nomeSegmento + ": ");
			valor = entrada.nextDouble();
			if (valor <= 0) {
				System.out.println("Valor inválido. Informe um número maior que zero.");
			}
		} while (valor <= 0);
		return valor;
	}

	private static void exibirResultado(double a, double b, double c, double d, char desconhecido) {
		char segmento = Character.toLowerCase(desconhecido);
		double valorDesconhecido = segmento == 'a' ? a : segmento == 'b' ? b : segmento == 'c' ? c : d;

		System.out.println("\n=== RESULTADO ===");
		System.out.printf("Proporção aplicada: a/b = c/d%n");
		System.out.printf("Substituindo os valores: %.2f/%.2f = %.2f/%.2f%n", a, b, c, d);
		System.out.printf("Segmento desconhecido (%c) = %.2f%n", segmento, valorDesconhecido);
	}

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);

		System.out.println("=== TEOREMA DE TALES ===");
		System.out.println("Relação usada: a/b = c/d");
		System.out.println("Informe qual segmento é desconhecido (a, b, c ou d): ");

		char segmentoDesconhecido = Character.toLowerCase(entrada.next().charAt(0));
		double a = 0;
		double b = 0;
		double c = 0;
		double d = 0;

		switch (segmentoDesconhecido) {
			case 'a':
				b = lerSegmentoPositivo(entrada, "b");
				c = lerSegmentoPositivo(entrada, "c");
				d = lerSegmentoPositivo(entrada, "d");
				a = calcularSegmentoDesconhecido('a', a, b, c, d);
				break;
			case 'b':
				a = lerSegmentoPositivo(entrada, "a");
				c = lerSegmentoPositivo(entrada, "c");
				d = lerSegmentoPositivo(entrada, "d");
				b = calcularSegmentoDesconhecido('b', a, b, c, d);
				break;
			case 'c':
				a = lerSegmentoPositivo(entrada, "a");
				b = lerSegmentoPositivo(entrada, "b");
				d = lerSegmentoPositivo(entrada, "d");
				c = calcularSegmentoDesconhecido('c', a, b, c, d);
				break;
			case 'd':
				a = lerSegmentoPositivo(entrada, "a");
				b = lerSegmentoPositivo(entrada, "b");
				c = lerSegmentoPositivo(entrada, "c");
				d = calcularSegmentoDesconhecido('d', a, b, c, d);
				break;
			default:
				System.out.println("Opção inválida. Encerrando o programa.");
				entrada.close();
				return;
		}

		exibirResultado(a, b, c, d, segmentoDesconhecido);
		entrada.close();
	}
}
