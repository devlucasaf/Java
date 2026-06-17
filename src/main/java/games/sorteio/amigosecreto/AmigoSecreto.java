package games.sorteio.amigosecreto;

import java.util.Map;
import java.util.Scanner;

public class AmigoSecreto {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Sorteador sorteador = new Sorteador();

        exibirCabecalho();

        System.out.println("\n--- Cadastro de participantes ---");
        System.out.println("(digite 'fim' no nome para encerrar o cadastro)");
        int contador = 1;
        while (true) {
            System.out.printf("%nParticipante %d%n", contador);
            System.out.print("  Nome: ");
            String nome = entrada.nextLine().trim();
            if (nome.equalsIgnoreCase("fim")) {
                break;
            }

            if (nome.isEmpty()) {
                System.out.println("  ⚠ Nome não pode estar vazio.");
                continue;
            }
            System.out.print("  E-mail (opcional, enter para pular): ");
            String email = entrada.nextLine().trim();

            sorteador.adicionar(new Participante(nome, email));
            contador++;
        }

        if (sorteador.getParticipantes().size() < 2) {
            System.out.println("\nSorteio cancelado: são necessários pelo menos 2 participantes.");
            return;
        }

        System.out.print("\nDeseja adicionar restrições (pessoas que não podem se tirar)? (s/n): ");
        if (entrada.nextLine().trim().equalsIgnoreCase("s")) {
            while (true) {
                System.out.print("\nPrimeiro nome (ou enter para terminar): ");
                String n1 = entrada.nextLine().trim();
                if (n1.isEmpty()) {
                    break;
                }

                System.out.print("Segundo nome: ");
                String n2 = entrada.nextLine().trim();
                if (n2.isEmpty()) {
                    break;
                }
                sorteador.adicionarRestricao(n1, n2);
                System.out.println("✓ Restrição adicionada: " + n1 + " ↔ " + n2);
            }
        }

        try {
            Map<Participante, Participante> resultado = sorteador.sortear();
            exibirResultado(resultado, entrada);
        } catch (IllegalStateException e) {
            System.out.println("\n " + e.getMessage());
        }
    }

    private static void exibirCabecalho() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("               AMIGO SECRETO                  ");
        System.out.println("==============================================");
    }

    private static void exibirResultado(Map<Participante, Participante> resultado, Scanner entrada) {
        System.out.println("\nSorteio realizado com sucesso!");
        System.out.println("Cada participante poderá ver apenas seu próprio amigo secreto.\n");

        for (Map.Entry<Participante, Participante> par : resultado.entrySet()) {
            System.out.println("------------------------------------------------");
            System.out.printf("Vez de: %s%n", par.getKey().getNome());
            System.out.print("Pressione ENTER para revelar seu amigo secreto... ");
            entrada.nextLine();
            System.out.printf("Você tirou: %s%n", par.getValue().getNome());
            System.out.print("Pressione ENTER para passar ao próximo... ");
            entrada.nextLine();

            for (int i = 0; i < 30; i++) {
                System.out.println();
            }
        }

        System.out.println("==============================================");
        System.out.println("Sorteio finalizado! Bom presente a todos!");
    }
}

