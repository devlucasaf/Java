package games.sorteio.roleta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogoRoleta {

    private final Scanner   entrada = new Scanner(System.in);
    private final Roleta    roleta  = new Roleta();
    private Jogador         jogador;

    public static void main(String[] args) {
        new JogoRoleta().iniciar();
    }

    public void iniciar() {
        exibirCabecalho();
        cadastrarJogador();

        boolean continuar = true;
        while (continuar && jogador.getSaldo() > 0) {
            exibirSaldo();
            List<Aposta> apostas = coletarApostas();

            if (apostas.isEmpty()) {
                System.out.println("Nenhuma aposta válida feita. Saindo...");
                break;
            }

            int sorteado = roleta.sortearComAnimacao();
            apurarResultados(apostas, sorteado);

            if (jogador.getSaldo() <= 0) {
                System.out.println("\nSeu saldo acabou! Fim de jogo.");
                break;
            }

            continuar = perguntarContinuar();
        }

        exibirEstatisticasFinais();
    }

    private void exibirCabecalho() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("               ROLETA EUROPEIA                ");
        System.out.println("==============================================");
        System.out.println("  37 casas (0 a 36) | Zero é verde            ");
        System.out.println("  Boa sorte!                                  ");
        System.out.println("==============================================");
    }

    private void cadastrarJogador() {
        System.out.print("\nQual é o seu nome? ");
        String nome = entrada.nextLine().trim();
        if (nome.isEmpty()) {
            nome = "Jogador";
        }

        double saldo = lerDoublePositivo("Quanto deseja começar (em R$)? ");
        jogador = new Jogador(nome, saldo);
        System.out.printf("Bem-vindo(a), %s! Saldo inicial: R$ %.2f%n", jogador.getNome(), jogador.getSaldo());
    }

    private void exibirSaldo() {
        System.out.println();
        System.out.println("------------------------------------------------");
        System.out.printf ("  Jogador: %s  |  Saldo: R$ %.2f%n", jogador.getNome(), jogador.getSaldo());
        System.out.println("------------------------------------------------");
    }

    private List<Aposta> coletarApostas() {
        List<Aposta> apostas = new ArrayList<>();
        boolean adicionarMais = true;

        while (adicionarMais && jogador.getSaldo() > 0) {
            exibirMenuTipos();
            int opcao = lerInteiro("Escolha o tipo de aposta (1-13): ", 1, 13);
            TipoAposta tipo = TipoAposta.values()[opcao - 1];

            int numeroEscolhido = -1;
            if (tipo == TipoAposta.NUMERO_UNICO) {
                numeroEscolhido = lerInteiro("Digite o número (0 a 36): ", 0, 36);
            }

            double valor = lerDoublePositivo(
                String.format("Valor da aposta (saldo: R$ %.2f): ", jogador.getSaldo()));

            if (!jogador.podeApostar(valor)) {
                System.out.println("⚠ Saldo insuficiente. Aposta cancelada.");
                continue;
            }

            Aposta aposta = new Aposta(tipo, valor, numeroEscolhido);
            apostas.add(aposta);
            jogador.debitar(valor);

            System.out.println("✓ Aposta registrada: " + aposta);
            System.out.printf("  Saldo disponível: R$ %.2f%n", jogador.getSaldo());

            if (jogador.getSaldo() > 0) {
                adicionarMais = lerSimNao("Deseja adicionar outra aposta? (s/n): ");
            }
        }

        return apostas;
    }

    private void exibirMenuTipos() {
        System.out.println("\n--- Tipos de aposta ---");
        TipoAposta[] tipos = TipoAposta.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("  %2d. %-30s (paga %dx)%n",
                    i + 1, tipos[i].getDescricao(), tipos[i].getMultiplicador());
        }
    }

    private void apurarResultados(List<Aposta> apostas, int sorteado) {
        CorNumero cor = CorNumero.deNumero(sorteado);
        System.out.println("\n=========== RESULTADO DO GIRO ===========");
        System.out.printf ("  Número sorteado: %s  (%s)%n",
                cor.pintar(String.valueOf(sorteado)), cor.getNome());
        System.out.println("-----------------------------------------");

        double totalApostado = 0;
        double totalRecebido = 0;

        for (Aposta aposta : apostas) {
            totalApostado += aposta.getValor();
            if (aposta.venceu(sorteado)) {
                double ganho = aposta.calcularGanho();
                double lucro = ganho - aposta.getValor();
                jogador.creditar(ganho);
                jogador.registrarVitoria(lucro);
                totalRecebido += ganho;
                System.out.printf("  GANHOU %s  →  recebeu R$ %.2f (lucro R$ %.2f)%n",
                        aposta, ganho, lucro);
            } else {
                jogador.registrarDerrota(aposta.getValor());
                System.out.printf("  PERDEU %s%n", aposta);
            }
        }

        double saldoRodada = totalRecebido - totalApostado;
        System.out.println("-----------------------------------------");
        System.out.printf ("  Apostado: R$ %.2f  |  Recebido: R$ %.2f%n", totalApostado, totalRecebido);
        System.out.printf ("  Resultado da rodada: %s R$ %.2f%n",
                saldoRodada >= 0 ? "+" : "-", Math.abs(saldoRodada));
        System.out.printf ("  Saldo atual: R$ %.2f%n", jogador.getSaldo());
        System.out.println("=========================================");
    }

    private boolean perguntarContinuar() {
        return lerSimNao("\nDeseja jogar outra rodada? (s/n): ");
    }

    private void exibirEstatisticasFinais() {
        System.out.println();
        System.out.println("============== ESTATÍSTICAS ==============");
        System.out.printf ("  Jogador: %s%n", jogador.getNome());
        System.out.printf ("  Rodadas jogadas: %d%n", jogador.getRodadasJogadas());
        System.out.printf ("  Apostas vencidas: %d%n", jogador.getRodadasGanhas());
        System.out.printf ("  Apostas perdidas: %d%n", jogador.getRodadasPerdidas());
        System.out.printf ("  Maior ganho em uma aposta: R$ %.2f%n", jogador.getMaiorGanho());
        System.out.printf ("  Maior perda em uma aposta: R$ %.2f%n", jogador.getMaiorPerda());
        System.out.printf ("  Saldo final: R$ %.2f%n", jogador.getSaldo());
        System.out.println("==========================================");
        System.out.println("Obrigado por jogar!");
    }

    // ----------- utilitários de leitura -----------

    private int lerInteiro(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String linha = entrada.nextLine().trim();
            try {
                int valor = Integer.parseInt(linha);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.printf("⚠ Digite um número entre %d e %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private double lerDoublePositivo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = entrada.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(linha);
                if (valor > 0) {
                    return valor;
                }
                System.out.println("⚠ O valor deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrada inválida. Digite um número.");
            }
        }
    }

    private boolean lerSimNao(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = entrada.nextLine().trim().toLowerCase();
            if (linha.equals("s") || linha.equals("sim")) {
                return true;
            }

            if (linha.equals("n") || linha.equals("nao") || linha.equals("não")) {
                return false;
            }
            System.out.println("⚠ Responda com 's' ou 'n'.");
        }
    }
}

