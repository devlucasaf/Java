package org.games.puzzle.memoria.genius;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaPrincipal extends JFrame implements ActionListener {
    private JButton[] botoes = new JButton[4];
    private Color[]  cores = {
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.BLUE
    };
    private String[] nomesCores = {
            "Vermelho",
            "Verde",
            "Amarelo",
            "Azul"
    };
    private ArrayList<Integer>  sequencia;
    private ArrayList<Integer>  jogadaAtual;
    private JButton             botaoReiniciar;
    private JPanel              painelBotoes;
    private JLabel              labelRodada;
    private int                 rodada;
    private int                 indiceAnimacao;
    private boolean             aguardandoJogador;
    private boolean             jogoAtivo;
    private Timer               timerSequencia;

    public TelaPrincipal() {
        setTitle("Jogo Genius");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sequencia = new ArrayList<>();
        jogadaAtual = new ArrayList<>();
        rodada = 0;
        aguardandoJogador = false;
        jogoAtivo = false;

        painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(2, 2, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 4; i++) {
            botoes[i] = new JButton();
            botoes[i].setBackground(cores[i]);
            botoes[i].setOpaque(true);
            botoes[i].setBorderPainted(false);
            botoes[i].setFocusPainted(false);
            botoes[i].setText(nomesCores[i]);
            botoes[i].setFont(new Font("Arial", Font.BOLD, 16));
            botoes[i].setForeground(Color.BLACK);
            botoes[i].addActionListener(this);
            painelBotoes.add(botoes[i]);
        }

        JPanel painelSuperior = new JPanel(new BorderLayout());
        labelRodada = new JLabel("Clique em 'Iniciar' para começar", SwingConstants.CENTER);
        labelRodada.setFont(new Font("Arial", Font.BOLD, 18));
        botaoReiniciar = new JButton("Iniciar / Reiniciar");
        botaoReiniciar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoReiniciar.addActionListener(e -> reiniciarJogo());

        painelSuperior.add(labelRodada, BorderLayout.CENTER);
        painelSuperior.add(botaoReiniciar, BorderLayout.SOUTH);
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(painelSuperior, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);

        setBotoesHabilitados(false);
    }

    private void reiniciarJogo() {
        if (timerSequencia != null && timerSequencia.isRunning()) {
            timerSequencia.stop();
        }

        sequencia.clear();
        jogadaAtual.clear();
        rodada = 0;
        jogoAtivo = true;
        aguardandoJogador = false;

        adicionarNovaCor();
        labelRodada.setText("Rodada: " + rodada);

        mostrarSequencia();
    }

    private void adicionarNovaCor() {
        int novaCor = (int) (Math.random() * 4);
        sequencia.add(novaCor);
        rodada++;
        labelRodada.setText("Rodada: " + rodada);
    }

    private void mostrarSequencia() {
        if (!jogoAtivo) {
            return;
        }

        setBotoesHabilitados(false);
        aguardandoJogador = false;

        indiceAnimacao = 0;
        timerSequencia = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceAnimacao < sequencia.size()) {
                    int corIndex = sequencia.get(indiceAnimacao);
                    piscarBotao(corIndex);
                    indiceAnimacao++;
                } else {
                    timerSequencia.stop();
                    setBotoesHabilitados(true);
                    aguardandoJogador = true;
                    jogadaAtual.clear();
                }
            }
        });
        timerSequencia.start();
    }

    private void piscarBotao(int indiceCor) {
        JButton botao = botoes[indiceCor];
        Color corOriginal = cores[indiceCor];
        Color corClara = corOriginal.brighter();

        botao.setBackground(corClara);

        Timer restaurador = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botao.setBackground(corOriginal);
                ((Timer)e.getSource()).stop();
            }
        });
        restaurador.setRepeats(false);
        restaurador.start();
    }

    private void setBotoesHabilitados(boolean habilitado) {
        for (JButton botao : botoes) {
            botao.setEnabled(habilitado);
        }
    }

    private void verificarJogada(int corClicada) {
        if (!jogoAtivo || !aguardandoJogador) {
            return;
        }

        jogadaAtual.add(corClicada);

        for (int i = 0; i < jogadaAtual.size(); i++) {
            if (jogadaAtual.get(i) != sequencia.get(i)) {
                fimDeJogo();
                return;
            }
        }

        if (jogadaAtual.size() == sequencia.size()) {
            aguardandoJogador = false;
            setBotoesHabilitados(false);

            adicionarNovaCor();
            mostrarSequencia();
        }
    }

    private void fimDeJogo() {
        jogoAtivo = false;
        aguardandoJogador = false;
        setBotoesHabilitados(false);

        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Fim de jogo! Você chegou até a rodada " + rodada + ".\nDeseja jogar novamente?",
                "Fim de jogo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );

        if (opcao == JOptionPane.YES_OPTION) {
            reiniciarJogo();
        } else {
            labelRodada.setText("Jogo encerrado. Clique em 'Iniciar/Reiniciar' para uma nova partida.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < botoes.length; i++) {
            if (e.getSource() == botoes[i]) {
                piscarBotao(i);
                verificarJogada(i);
                break;
            }
        }
    }
}