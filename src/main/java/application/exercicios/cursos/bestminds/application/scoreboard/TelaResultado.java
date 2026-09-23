package application.exercicios.cursos.bestminds.application.scoreboard;

import javax.swing.*;
import java.awt.*;

public class TelaResultado {

    public TelaResultado(DataBase dados) {
        JFrame janela = new JFrame("Placar - Resultado Final");
        JPanel painel = new JPanel();

        janela.setSize(360, 640);
        janela.add(painel);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        painel.setLayout(null);

        // Mensagem de parabéns
        String nomeVencedor = dados.getNomeVencedor();
        Color corVencedor = dados.vencedorEhJogador1() ? Color.RED : Color.BLUE;

        JLabel lblRotuloParabens = new JLabel("Parabéns, " + nomeVencedor + "!");
        lblRotuloParabens.setBounds(20, 80, 320, 60);
        lblRotuloParabens.setFont(new Font("Arial", Font.BOLD, 18));
        lblRotuloParabens.setForeground(corVencedor);
        lblRotuloParabens.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblRotuloParabens);

        JLabel lblRotuloSubtitulo = new JLabel("Você venceu a partida!");
        lblRotuloSubtitulo.setBounds(20, 130, 320, 40);
        lblRotuloSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRotuloSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblRotuloSubtitulo);

        // Placar final
        JLabel lblRotuloPlacarFinal = new JLabel("PLACAR FINAL");
        lblRotuloPlacarFinal.setBounds(20, 220, 320, 40);
        lblRotuloPlacarFinal.setFont(new Font("Arial", Font.BOLD, 22));
        lblRotuloPlacarFinal.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblRotuloPlacarFinal);

        // Jogador 1
        JLabel lblRotuloJogador1 = new JLabel(dados.getNomeJogador1());
        lblRotuloJogador1.setBounds(20, 270, 140, 30);
        lblRotuloJogador1.setFont(new Font("Arial", Font.BOLD, 14));
        lblRotuloJogador1.setForeground(Color.RED);
        lblRotuloJogador1.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblRotuloJogador1);

        JLabel lblRotuloPontosJ1 = new JLabel(dados.getSetsJogador1() + " SETS");
        lblRotuloPontosJ1.setBounds(20, 300, 140, 50);
        lblRotuloPontosJ1.setFont(new Font("Arial", Font.BOLD, 24));
        lblRotuloPontosJ1.setForeground(Color.RED);
        lblRotuloPontosJ1.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblRotuloPontosJ1);

        // X
        JLabel lblRotuloX = new JLabel("X");
        lblRotuloX.setBounds(145, 300, 50, 50);
        lblRotuloX.setFont(new Font("Arial", Font.BOLD, 24));
        lblRotuloX.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblRotuloX);

        // Jogador 2
        JLabel lblRotuloJogador2 = new JLabel(dados.getNomeJogador2());
        lblRotuloJogador2.setBounds(190, 270, 140, 30);
        lblRotuloJogador2.setFont(new Font("Arial", Font.BOLD, 14));
        lblRotuloJogador2.setForeground(Color.BLUE);
        lblRotuloJogador2.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblRotuloJogador2);

        JLabel lblRotuloPontosJ2 = new JLabel(dados.getSetsJogador2() + " SETS");
        lblRotuloPontosJ2.setBounds(190, 300, 140, 50);
        lblRotuloPontosJ2.setFont(new Font("Arial", Font.BOLD, 24));
        lblRotuloPontosJ2.setForeground(Color.BLUE);
        lblRotuloPontosJ2.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblRotuloPontosJ2);

        // Botões
        JButton botaoReiniciar = new JButton("Novo Jogo");
        botaoReiniciar.setBounds(40, 450, 120, 50);
        painel.add(botaoReiniciar);

        JButton botaoFechar = new JButton("Encerrar");
        botaoFechar.setBounds(180, 450, 120, 50);
        painel.add(botaoFechar);

        botaoReiniciar.addActionListener(e -> {
            janela.dispose();
            ScoreBoard.main(new String[]{});
        });

        botaoFechar.addActionListener(e -> {
            janela.dispose();
            System.exit(0);
        });

        janela.setVisible(true);
    }
}

