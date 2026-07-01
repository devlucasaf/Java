package games.tabuleiro.grid.service;

import games.tabuleiro.grid.model.Grade;
import games.tabuleiro.grid.model.Jogador;

import java.util.List;
import java.util.Scanner;

public class JogoService {

    private DadosService dadosService = new DadosService();
    private ValidadorService validadorService = new ValidadorService();

    public void iniciarJogo() {
        List<Jogador> jogadores = dadosService.carregarJogadores();


        Grade grade = new Grade(
                dadosService.paises(),
                dadosService.clubes()
        );

        Scanner scanner = new Scanner(System.in);
        int pontuacao = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grade.exibirGrade();
                System.out.println("\nPaís: " + grade.getLinhas()[i]);
                System.out.println("Clube: " + grade.getColunas()[j]);
                System.out.print("Digite o nome do jogador: ");

                String resposta = scanner.nextLine();

                boolean correto = validadorService.validarResposta(
                        resposta,
                        grade.getLinhas()[i],
                        grade.getColunas()[j],
                        jogadores
                );

                if (correto) {
                    System.out.println("Jogador adicionado");
                    grade.setRespostas(i, j, resposta);
                    pontuacao++;
                } else {
                    System.out.println("Resposta incorreta");
                    grade.setRespostas(i, j, "X");
                }
            }
        }

        grade.exibirGrade();
        System.out.println("\nPontuação final: " + pontuacao + "/9");
        scanner.close();
    }
}
