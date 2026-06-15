package org.games.text.termo;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

public class TermoGame {

    // Códigos ANSI para cores (Funcionam igual ao Ruby)
    private static final String RESET = "\u001B[0m";
    private static final String VERDE_BG = "\u001B[42m\u001B[30m";   // Fundo Verde, Letra Preta
    private static final String AMARELO_BG = "\u001B[43m\u001B[30m"; // Fundo Amarelo, Letra Preta
    private static final String CINZA_BG = "\u001B[40m\u001B[37m";   // Fundo Preto, Letra Branca

    private List<String>        dicionarioPalavras;
    private String              palavraSecreta;
    private String              secretaLogica;
    private int                 tentativasMaximas;
    private Scanner             scanner;

    public TermoGame() {
        dicionarioPalavras = Arrays.asList(
            "IDEIA", "CHAVE", "CENSO", "FURIA", "TEMPO", "FILHO", "FILHA",
            "QUASE", "FATOR", "LAMBE", "BALDE", "VIRAR", "JOGOS", "LAÇOS",
            "ILHAS", "PAPAI", "MAMAE", "MUNDO", "VULGO", "FORTE", "CULTO",
            "JUSTO", "HONRA", "VIGOR", "VASCO", "SAGAZ", "NOBRE", "ANEXO",
            "NEGRO", "MEXER", "PLENA", "FAZER", "MORAL", "DESDE", "JUSTO"
        );

        // Escolhe palavra aleatória
        Random rand = new Random();
        palavraSecreta = dicionarioPalavras.get(rand.nextInt(dicionarioPalavras.size()));
        
        // Versão sem acentos
        secretaLogica = removerAcentos(palavraSecreta);
        
        tentativasMaximas = 6;
        scanner = new Scanner(System.in);
    }

    private String removerAcentos(String texto) {
        String nfdNormalizedString = Normalizer.normalize(texto, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    private EstadoCor[] calcularCores(String chute) {
        char[] secretaChars = secretaLogica.toCharArray();
        char[] chuteChars = chute.toCharArray();
        
        EstadoCor[] resultadoCores = new EstadoCor[5];
        Arrays.fill(resultadoCores, EstadoCor.CINZA); // Começa tudo cinza
        
        Map<Character, Integer> contagemLetras = new HashMap<>();

        for (char c : secretaChars) {
            contagemLetras.put(c, contagemLetras.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < 5; i++) {
            if (chuteChars[i] == secretaChars[i]) {
                resultadoCores[i] = EstadoCor.VERDE;
                contagemLetras.put(chuteChars[i], contagemLetras.get(chuteChars[i]) - 1);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (resultadoCores[i] == EstadoCor.VERDE) {
                continue;
            }

            char letra = chuteChars[i];
            if (contagemLetras.containsKey(letra) && contagemLetras.get(letra) > 0) {
                resultadoCores[i] = EstadoCor.AMARELO;
                contagemLetras.put(letra, contagemLetras.get(letra) - 1);
            }
        }

        return resultadoCores;
    }

    private void animacaoTerminal(String palavra, EstadoCor[] cores) {
        System.out.print("\r" + " ".repeat(30) + "\r");

        char[] letras = palavra.toCharArray();
        
        for (int i = 0; i < letras.length; i++) {
            String corCode;
            switch (cores[i]) {
                case VERDE:
                    corCode = VERDE_BG;
                    break;
                case AMARELO:
                    corCode = AMARELO_BG;
                    break;
                default:
                    corCode = CINZA_BG;
                    break;
            }

            System.out.print(corCode + " " + letras[i] + " " + RESET + " ");
            System.out.flush();
            sleep(500);
        }
        System.out.println();
    }

    private void animarTextoMatrix(String fraseFinal) {
        String charsPossiveis = "ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%";
        Random rand = new Random();
        char[] palavraAtual = new char[fraseFinal.length()];
        Arrays.fill(palavraAtual, ' ');

        for (int i = 0; i < fraseFinal.length(); i++) {
            char letraAlvo = fraseFinal.charAt(i);

            for (int j = 0; j < 10; j++) {
                palavraAtual[i] = charsPossiveis.charAt(rand.nextInt(charsPossiveis.length()));
                System.out.print("\r" + new String(palavraAtual) + "");
                System.out.flush();
                sleep(20);
            }

            palavraAtual[i] = letraAlvo;
            System.out.print("\r" + new String(palavraAtual) + "");
        }
        System.out.println("\n");
    }

    private void limparTela() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void jogar() {
        limparTela();
        System.out.println(">>>>> JOGO DO TERMO <<<<<");
        System.out.println("----------------------------------------");

        for (int i = 0; i < tentativasMaximas; i++) {
            int tentativaAtual = i + 1;
            System.out.print("\nTentativa " + tentativaAtual + "/" + tentativasMaximas + ": ");

            String chute = scanner.nextLine().toUpperCase().trim();

            if (chute.length() != 5) {
                System.out.println("A palavra precisa ter 5 letras!");
                i--;
                continue;
            }

            String chuteLogico = removerAcentos(chute);
            EstadoCor[] cores = calcularCores(chuteLogico);

            animacaoTerminal(chute, cores);

            if (chuteLogico.equals(secretaLogica)) {
                System.out.println();
                animarTextoMatrix("PARABENS! VOCE VENCEU!");
                System.out.println("A palavra era: " + palavraSecreta);
                return;
            }
        }

        System.out.println("\nFim de jogo!");
        System.out.println("A palavra era: " + palavraSecreta);
    }

    public static void main(String[] args) {
        new TermoGame().jogar();
    }
}
