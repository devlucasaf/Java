package org.application.scoreboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Tela principal do placar - Exibe pontuação e registra pontos.
 */
public class TelaPlacar {

    private final DataBase dados;
    private final JLabel        rotuloSetsJogador1;
    private final JLabel        rotuloSetsJogador2;
    private final JLabel        rotuloPontosJogador1;
    private final JLabel        rotuloPontosJogador2;
    private final JFrame        janela;

    public TelaPlacar(DataBase dados) {
        this.dados = dados;

        janela = new JFrame("Placar - Partida");
        JPanel painel = new JPanel();

        janela.setSize(360, 640);
        janela.add(painel);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        painel.setLayout(null);

        // Campo de entrada para registrar ponto
        JLabel rotuloInstrucao = new JLabel("Quem fez o ponto? (digite o nome)");
        rotuloInstrucao.setBounds(10, 10, 300, 40);
        painel.add(rotuloInstrucao);

        JTextField campoEntrada = new JTextField();
        campoEntrada.setBounds(10, 50, 320, 45);
        painel.add(campoEntrada);

        // === Jogador 1 ===
        JLabel rotuloNomeJogador1 = new JLabel("Sets " + dados.getNomeJogador1());
        rotuloNomeJogador1.setBounds(15, 105, 200, 40);
        rotuloNomeJogador1.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(rotuloNomeJogador1);

        rotuloSetsJogador1 = new JLabel(String.valueOf(dados.getSetsJogador1()));
        rotuloSetsJogador1.setBounds(15, 135, 150, 50);
        rotuloSetsJogador1.setFont(new Font("Arial", Font.BOLD, 35));
        rotuloSetsJogador1.setForeground(Color.RED);
        painel.add(rotuloSetsJogador1);

        rotuloPontosJogador1 = new JLabel(String.valueOf(dados.getPontosJogador1()));
        rotuloPontosJogador1.setBounds(120, 160, 200, 200);
        rotuloPontosJogador1.setFont(new Font("Arial", Font.BOLD, 140));
        rotuloPontosJogador1.setForeground(Color.RED);
        rotuloPontosJogador1.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(rotuloPontosJogador1);

        // Separador
        JSeparator separador = new JSeparator();
        separador.setBounds(10, 350, 320, 2);
        painel.add(separador);

        // === Jogador 2 ===
        JLabel rotuloNomeJogador2 = new JLabel("Sets " + dados.getNomeJogador2());
        rotuloNomeJogador2.setBounds(15, 360, 200, 40);
        rotuloNomeJogador2.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(rotuloNomeJogador2);

        rotuloSetsJogador2 = new JLabel(String.valueOf(dados.getSetsJogador2()));
        rotuloSetsJogador2.setBounds(15, 390, 150, 50);
        rotuloSetsJogador2.setFont(new Font("Arial", Font.BOLD, 35));
        rotuloSetsJogador2.setForeground(Color.BLUE);
        painel.add(rotuloSetsJogador2);

        rotuloPontosJogador2 = new JLabel(String.valueOf(dados.getPontosJogador2()));
        rotuloPontosJogador2.setBounds(120, 400, 200, 200);
        rotuloPontosJogador2.setFont(new Font("Arial", Font.BOLD, 140));
        rotuloPontosJogador2.setForeground(Color.BLUE);
        rotuloPontosJogador2.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(rotuloPontosJogador2);

        // Evento de teclado
        campoEntrada.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                if (evento.getKeyCode() == KeyEvent.VK_ENTER) {
                    registrarPonto(campoEntrada);
                }
            }
        });

        janela.setVisible(true);
    }

    private void registrarPonto(JTextField campoEntrada) {
        String nomeDigitado = campoEntrada.getText().trim();

        if (nomeDigitado.isBlank()) {
            JOptionPane.showMessageDialog(janela, "Digite o nome de quem fez o ponto!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verifica se o nome corresponde a algum jogador
        if (!nomeDigitado.equalsIgnoreCase(dados.getNomeJogador1()) &&
            !nomeDigitado.equalsIgnoreCase(dados.getNomeJogador2())) {
            JOptionPane.showMessageDialog(janela,
                    "Nome não reconhecido! Digite \"" + dados.getNomeJogador1() +
                    "\" ou \"" + dados.getNomeJogador2() + "\".",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean partidaAcabou = dados.registrarPonto(nomeDigitado);
        atualizarPlacar();
        campoEntrada.setText("");

        if (partidaAcabou) {
            janela.dispose();
            new TelaResultado(dados);
        }
    }

    private void atualizarPlacar() {
        rotuloPontosJogador1.setText(String.valueOf(dados.getPontosJogador1()));
        rotuloPontosJogador2.setText(String.valueOf(dados.getPontosJogador2()));
        rotuloSetsJogador1.setText(String.valueOf(dados.getSetsJogador1()));
        rotuloSetsJogador2.setText(String.valueOf(dados.getSetsJogador2()));
    }
}

