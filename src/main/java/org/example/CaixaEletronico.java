import java.util.Arrays;
import java.util.Scanner;

public class CaixaEletronico {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Cria um objeto Scanner para ler entradas do usuário
        
        // Vetor com os valores das notas disponíveis no caixa
        int[] notas = {200, 100, 50, 20, 10, 5, 2};
        
        // Vetor com a quantidade de cada nota disponível (estoque inicial: 50 de cada)
        int[] qntdNotaEstoque = {50, 50, 50, 50, 50, 50, 50};
        
        double valorTotal = 0; // Variável para armazenar o valor total disponível no caixa

        // Calcula o valor total disponível no caixa somando cada nota multiplicada pela sua quantidade
        for (int i = 0; i < notas.length; i++) {
            valorTotal += notas[i] * qntdNotaEstoque[i];
        }

        // Exibe o valor total disponível no caixa
        System.out.printf("Nesse caixa, tem disponível: R$ %.2f%n", valorTotal);

        // Loop principal para permitir múltiplos saques
        while (true) {
            System.out.print("Digite quanto você quer sacar: ");
            double saque = Double.parseDouble(scanner.nextLine()); // Lê o valor do saque

            // Verifica se o valor solicitado é maior que o disponível
            if (saque > valorTotal) {
                System.out.println("Saque indisponível! Valor maior do que disponível!");
                System.out.printf("O valor máximo permitido é: R$ %.2f%n", valorTotal);
                continue; // Volta para o início do loop
            }

            double resto = saque; // Variável para calcular o troco restante

            // Percorre as notas disponíveis para calcular quantas de cada serão usadas
            for (int nota : notas) {
                if (resto / nota >= 1) { // Se ainda cabe pelo menos uma nota desse valor
                    int qntdNecessaria = (int) (resto / nota); // Calcula quantas notas são necessárias
                    resto -= (qntdNecessaria * nota); // Atualiza o valor restante
                    System.out.println("A quantidade de notas de R$ " + nota + " é de: " + qntdNecessaria + " notas");
                }
            }

            // Atualiza o valor total do caixa após o saque
            valorTotal -= saque;
            System.out.printf("Neste caixa tem R$ %.2f em notas de %s%n", valorTotal, Arrays.toString(notas));

            // Se o caixa ficar sem dinheiro, encerra o programa
            if (valorTotal <= 0) {
                System.out.println("Você conseguiu estourar o caixa");
                break;
            }

            // Pergunta se o usuário deseja realizar outro saque
            System.out.print("Deseja realizar um novo saque? Sim/Não: ");
            String newSaque = scanner.nextLine().strip().toUpperCase();

            // Se a resposta for "NAO", encerra o loop
            if (newSaque.equals("NAO")) {
                break;
            }
        }
        scanner.close(); // Fecha o Scanner
    }
}
