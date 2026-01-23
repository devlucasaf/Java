import java.util.*;

public class JogoDaForca {
    // Scanner global para leitura de entradas do usuário
    private static final Scanner scanner = new Scanner(System.in);

    // Lista de palavras possíveis para o jogo
    public static List<String> palavraEscondida() {
        return Arrays.asList("banana", "lorenx", "batata", "amostradinho", "receba", "ceub", "apendicite", "guilherme", "santiago", "aula", "samambaia");
    }

    // Método para imprimir uma linha decorativa
    public static void linha() {
        System.out.println("+=+=".repeat(56));
    }

    // Escolhe aleatoriamente uma palavra da lista
    public static String escolherPalavra(List<String> palavras) {
        Random random = new Random();
        return palavras.get(random.nextInt(palavras.size())).trim();
    }

    // Mostra a palavra com letras descobertas e "_" para letras não adivinhadas
    public static String mostrarPalavra(String palavra, Set<Character> letraAdvinhada) {
        StringBuilder resultado = new StringBuilder();
        for (char letra : palavra.toCharArray()) {
            if (letraAdvinhada.contains(letra)) {
                resultado.append(letra).append(" "); // Mostra letra já adivinhada
            } else {
                resultado.append("_ "); // Mostra "_" para letra ainda não descoberta
            }
        }
        return resultado.toString().trim();
    }

    // Método principal que executa uma partida do jogo
    public static void jogar() {
        int vida = 6; // Número de tentativas (vidas)
        String coracao = "❤️"; // Ícone para representar vidas
        String palavraSecreta = escolherPalavra(palavraEscondida()); // Palavra sorteada
        Set<Character> letraAdvinhada = new HashSet<>(); // Letras corretas já descobertas
        Set<Character> letraErrada = new HashSet<>();    // Letras erradas já tentadas

        // Cabeçalho do jogo
        linha();
        System.out.printf("%112s%n", "Jogo da forca");
        linha();
        System.out.println("Essa sua palavra tem " + palavraSecreta.length() + " letras");

        // Loop principal do jogo
        while (vida > 0) {
            System.out.println(mostrarPalavra(palavraSecreta, letraAdvinhada)); // Mostra progresso da palavra
            System.out.println("Você tem " + coracao.repeat(vida)); // Mostra vidas restantes

            System.out.print("Digite uma letra: ");
            String input = scanner.nextLine().toLowerCase(); // Lê entrada do usuário
            if (input.isEmpty()) continue; // Ignora entradas vazias
            char letra = input.charAt(0); // Considera apenas o primeiro caractere

            // Verifica se a letra já foi digitada antes
            if (letraAdvinhada.contains(letra) || letraErrada.contains(letra)) {
                System.out.println("Você já digitou essa letra! Tente novamente!");
                continue;
            }

            // Caso a letra esteja na palavra secreta
            if (palavraSecreta.indexOf(letra) >= 0) {
                letraAdvinhada.add(letra); // Adiciona às letras corretas
                
                // Verifica se todas as letras foram descobertas
                boolean venceu = true;
                for (char c : palavraSecreta.toCharArray()) {
                    if (!letraAdvinhada.contains(c)) {
                        venceu = false;
                        break;
                    }
                }

                // Se venceu, encerra o jogo
                if (venceu) {
                    System.out.println("Você adivinhou a palavra secreta! A palavra secreta era: " + palavraSecreta);
                    break;
                }
            } else {
                // Caso a letra não esteja na palavra
                letraErrada.add(letra);
                vida--; // Perde uma vida
                if (vida == 0) {
                    System.out.println("Você perdeu o jogo! A palavra era: " + palavraSecreta);
                }
            }
        }
    }

    // Método principal que controla repetição de partidas
    public static void main(String[] args) {
        jogar(); // Inicia primeira partida

        // Loop para permitir jogar novamente
        while (true) {
            System.out.print("Digite [SIM] para continuar ou [NAO] para fechar o jogo: ");
            String escolha = scanner.nextLine().toUpperCase();

            if (escolha.equals("SIM")) {
                jogar(); // Inicia nova partida
            } else if (escolha.equals("NAO")) {
                break; // Encerra o programa
            } else {
                System.out.println("A palavra digitada não é aceita! Tente novamente!");
            }
        }
    }
}
