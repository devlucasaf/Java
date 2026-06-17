package application.bestminds.scoreboard;

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

        JLabel rotuloParabens = new JLabel("Parabéns, " + nomeVencedor + "!");
        rotuloParabens.setBounds(20, 80, 320, 60);
        rotuloParabens.setFont(new Font("Arial", Font.BOLD, 18));
        rotuloParabens.setForeground(corVencedor);
        rotuloParabens.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(rotuloParabens);

        JLabel rotuloSubtitulo = new JLabel("Você venceu a partida!");
        rotuloSubtitulo.setBounds(20, 130, 320, 40);
        rotuloSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        rotuloSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(rotuloSubtitulo);

        // Placar final
        JLabel rotuloPlacarFinal = new JLabel("PLACAR FINAL");
        rotuloPlacarFinal.setBounds(20, 220, 320, 40);
        rotuloPlacarFinal.setFont(new Font("Arial", Font.BOLD, 22));
        rotuloPlacarFinal.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(rotuloPlacarFinal);

        // Jogador 1
        JLabel rotuloJogador1 = new JLabel(dados.getNomeJogador1());
        rotuloJogador1.setBounds(20, 270, 140, 30);
        rotuloJogador1.setFont(new Font("Arial", Font.BOLD, 14));
        rotuloJogador1.setForeground(Color.RED);
        rotuloJogador1.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(rotuloJogador1);

        JLabel rotuloPontosJ1 = new JLabel(dados.getSetsJogador1() + " SETS");
        rotuloPontosJ1.setBounds(20, 300, 140, 50);
        rotuloPontosJ1.setFont(new Font("Arial", Font.BOLD, 24));
        rotuloPontosJ1.setForeground(Color.RED);
        rotuloPontosJ1.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(rotuloPontosJ1);

        // X
        JLabel rotuloX = new JLabel("X");
        rotuloX.setBounds(145, 300, 50, 50);
        rotuloX.setFont(new Font("Arial", Font.BOLD, 24));
        rotuloX.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(rotuloX);

        // Jogador 2
        JLabel rotuloJogador2 = new JLabel(dados.getNomeJogador2());
        rotuloJogador2.setBounds(190, 270, 140, 30);
        rotuloJogador2.setFont(new Font("Arial", Font.BOLD, 14));
        rotuloJogador2.setForeground(Color.BLUE);
        rotuloJogador2.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(rotuloJogador2);

        JLabel rotuloPontosJ2 = new JLabel(dados.getSetsJogador2() + " SETS");
        rotuloPontosJ2.setBounds(190, 300, 140, 50);
        rotuloPontosJ2.setFont(new Font("Arial", Font.BOLD, 24));
        rotuloPontosJ2.setForeground(Color.BLUE);
        rotuloPontosJ2.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(rotuloPontosJ2);

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

