package games.text.termo.futebol;

import games.text.termo.EstadoCor;

import java.text.Normalizer;
import java.util.*;

public class TermoGameFutebol {

    // Códigos ANSI
    private static final String RESET = "\u001B[0m";
    private static final String VERDE_BG = "\u001B[42m\u001B[30m";
    private static final String AMARELO_BG = "\u001B[43m\u001B[30m";
    private static final String CINZA_BG = "\u001B[40m\u001B[37m";

    private List<String>        dicionarioJogadores;
    private String              jogadorSecreto;
    private String              secretoLogica;
    private int                 tentativasMaximas;
    private Scanner             scanner;

    public TermoGameFutebol() {

        dicionarioJogadores = Arrays.asList(
                "MESSI", "RONALDO", "GARNACHO", "NEYMAR", "RIVALDO",
                "KAKA", "ROMARIO", "PELÉ", "ZICO",
                "MBAPPE", "HAALAND", "MODRIC", "SUAREZ",
                "VINICIUS", "GARRINCHA", "RONALDINHO", "FALCAO", "ZIDANE",
                "DE-BRUYNE", "FERNANDO-TORRES", "CRISTIANO-RONALDO", "PERISIC",
                "MARADONA", "CRUYFF", "GULLIT", "VAN-BASTEN", "VIOLA", "SAVARINO",
                "SOTELDO", "OSCAR", "KANE", "OLISE", "FIRMINO", "PEDRO", "GABIGOL",
                "WEVERTON", "JHON-ARIAS", "GEROMEL", "GANSO", "PATO", "IBRAHIMOVIC",
                "MALDINI", "MARCELO", "NESTA", "FIGO", "NEDVED", "SIMEONE", "DINIZ", "ZANETTI"
        );

        Random rand = new Random();
        jogadorSecreto = dicionarioJogadores.get(rand.nextInt(dicionarioJogadores.size()));
        secretoLogica = removerAcentos(jogadorSecreto.toUpperCase());

        tentativasMaximas = 6;
        scanner = new Scanner(System.in);
    }

    private String removerAcentos(String texto) {
        String normalized = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    private EstadoCor[] calcularCores(String chute) {

        int tamanho = secretoLogica.length();
        EstadoCor[] resultado = new EstadoCor[tamanho];
        Arrays.fill(resultado, EstadoCor.CINZA);

        char[] secreta = secretoLogica.toCharArray();
        char[] chuteChars = chute.toCharArray();

        Map<Character, Integer> contagem = new HashMap<>();

        for (char c : secreta) {
            contagem.put(c, contagem.getOrDefault(c, 0) + 1);
        }

        // Verdes
        for (int i = 0; i < tamanho; i++) {
            if (chuteChars[i] == secreta[i]) {
                resultado[i] = EstadoCor.VERDE;
                contagem.put(chuteChars[i], contagem.get(chuteChars[i]) - 1);
            }
        }

        // Amarelos
        for (int i = 0; i < tamanho; i++) {
            if (resultado[i] == EstadoCor.VERDE) {
                continue;
            }

            char letra = chuteChars[i];
            if (contagem.containsKey(letra) && contagem.get(letra) > 0) {
                resultado[i] = EstadoCor.AMARELO;
                contagem.put(letra, contagem.get(letra) - 1);
            }
        }

        return resultado;
    }

    private void animacaoTerminal(String palavra, EstadoCor[] cores) {
        System.out.print("\r" + " ".repeat(60) + "\r");

        for (int i = 0; i < palavra.length(); i++) {
            String cor;
            switch (cores[i]) {
                case VERDE -> cor = VERDE_BG;
                case AMARELO -> cor = AMARELO_BG;
                default -> cor = CINZA_BG;
            }

            System.out.print(cor + " " + palavra.charAt(i) + " " + RESET + " ");
            System.out.flush();
            sleep(350);
        }
        System.out.println();
    }

    private void animarTextoMatrix(String frase) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%";
        Random rand = new Random();
        char[] atual = new char[frase.length()];
        Arrays.fill(atual, ' ');

        for (int i = 0; i < frase.length(); i++) {
            for (int j = 0; j < 10; j++) {
                atual[i] = chars.charAt(rand.nextInt(chars.length()));
                System.out.print("\r" + new String(atual));
                System.out.flush();
                sleep(20);
            }
            atual[i] = frase.charAt(i);
        }
        System.out.println("\n");
    }

    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void jogar() {

        limparTela();
        System.out.println("JOGO DO TERMO – FUTEBOL");
        System.out.println("--------------------------------");
        System.out.println("Dica: Nome do jogador tem " + secretoLogica.length() + " letras");

        for (int i = 0; i < tentativasMaximas; i++) {

            System.out.print("\nTentativa " + (i + 1) + "/" + tentativasMaximas + ": ");
            String chute = scanner.nextLine().toUpperCase().trim();
            String chuteLogico = removerAcentos(chute);

            if (chuteLogico.length() != secretoLogica.length()) {
                System.out.println("O nome precisa ter exatamente "
                        + secretoLogica.length() + " letras!");
                i--;
                continue;
            }

            EstadoCor[] cores = calcularCores(chuteLogico);
            animacaoTerminal(chute, cores);

            if (chuteLogico.equals(secretoLogica)) {
                animarTextoMatrix("GOLAÇO! VOCÊ ACERTOU!");
                System.out.println("Jogador: " + jogadorSecreto);
                return;
            }
        }

        System.out.println("\nFim de jogo!");
        System.out.println("O jogador era: " + jogadorSecreto);
    }

    public static void main(String[] args) {
        new TermoGameFutebol().jogar();
    }
}
