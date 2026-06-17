package games.text.digitacao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DigitacaoRapida {

    private final Scanner entrada = new Scanner(System.in);

    private final List<String> frases = Arrays.asList(
            "O rato roeu a roupa do rei de Roma",
            "Programar em Java pode ser muito divertido",
            "A persistencia e o caminho do exito",
            "Quem nao arrisca nao petisca",
            "O sol brilha forte no meio do verao",
            "A pratica leva a perfeicao com o tempo",
            "Cada dia e uma nova oportunidade de aprender",
            "Java e uma linguagem orientada a objetos poderosa",
            "Computadores nao cometem erros eles executam ordens",
            "Aprender algoritmos melhora o raciocinio logico"
    );

    public static void main(String[] args) {
        new DigitacaoRapida().iniciar();
    }

    public void iniciar() {
        System.out.println("=== DIGITACAO RAPIDA ===");
        System.out.println("Voce vera uma frase e devera digita-la o mais rapido possivel.");
        System.out.println("Sera medido seu WPM (palavras por minuto) e a precisao.");
        System.out.print("Quantas frases? ");
        int qtd;
        try {
            qtd = Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            qtd = 3;
        }
        if (qtd > frases.size()) {
            qtd = frases.size();
        }

        List<String> selecionadas = new java.util.ArrayList<>(frases);
        Collections.shuffle(selecionadas);

        double somaWpm = 0;
        double somaPrecisao = 0;

        for (int i = 0; i < qtd; i++) {
            String frase = selecionadas.get(i);
            System.out.printf("%n[%d/%d] Pressione ENTER para comecar...", i + 1, qtd);
            entrada.nextLine();
            System.out.println("Digite: " + frase);
            long inicio = System.currentTimeMillis();
            String digitado = entrada.nextLine();
            long fim = System.currentTimeMillis();

            double segundos = (fim - inicio) / 1000.0;
            double minutos = segundos / 60.0;
            int palavras = frase.split("\\s+").length;
            double wpm = palavras / minutos;
            double precisao = calcularPrecisao(frase, digitado);

            System.out.printf("Tempo: %.2fs | WPM: %.1f | Precisao: %.1f%%%n", segundos, wpm, precisao);
            somaWpm += wpm;
            somaPrecisao += precisao;
        }

        System.out.println("\n===== RESULTADO FINAL =====");
        System.out.printf("WPM medio: %.1f%n", somaWpm / qtd);
        System.out.printf("Precisao media: %.1f%%%n", somaPrecisao / qtd);
        classificar(somaWpm / qtd);
    }

    private double calcularPrecisao(String esperado, String digitado) {
        int acertos = 0;
        int tamanho = Math.max(esperado.length(), digitado.length());
        int minimo = Math.min(esperado.length(), digitado.length());
        for (int i = 0; i < minimo; i++) {
            if (esperado.charAt(i) == digitado.charAt(i)) {
                acertos++;
            }
        }
        return (acertos * 100.0) / tamanho;
    }

    private void classificar(double wpm) {
        String nivel;
        if (wpm < 20) {
            nivel = "Iniciante";
        } else if (wpm < 40) {
            nivel = "Mediano";
        } else if (wpm < 60) {
            nivel = "Avancado";
        } else if (wpm < 80) {
            nivel = "Profissional";
        } else {
            nivel = "Mestre";
        }
        System.out.println("Nivel: " + nivel);
    }
}

