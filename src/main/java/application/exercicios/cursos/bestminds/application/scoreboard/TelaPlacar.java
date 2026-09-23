package application.exercicios.cursos.bestminds.application.scoreboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaPlacar {

    private final DataBase      dados;
    private final JLabel        lblRotuloSetsJogador1;
    private final JLabel        lblRotuloSetsJogador2;
    private final JLabel        lblRotuloPontosJogador1;
    private final JLabel        lblRotuloPontosJogador2;
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
        JLabel lblRotuloInstrucao = new JLabel("Quem fez o ponto? (digite o nome)");
        lblRotuloInstrucao.setBounds(10, 10, 300, 40);
        painel.add(lblRotuloInstrucao);

        JTextField txtFieldCampoEntrada = new JTextField();
        txtFieldCampoEntrada.setBounds(10, 50, 320, 45);
        painel.add(txtFieldCampoEntrada);

        // === Jogador 1 ===
        JLabel lblRotuloNomeJogador1 = new JLabel("Sets " + dados.getNomeJogador1());
        lblRotuloNomeJogador1.setBounds(15, 105, 200, 40);
        lblRotuloNomeJogador1.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblRotuloNomeJogador1);

        lblRotuloSetsJogador1 = new JLabel(String.valueOf(dados.getSetsJogador1()));
        lblRotuloSetsJogador1.setBounds(15, 135, 150, 50);
        lblRotuloSetsJogador1.setFont(new Font("Arial", Font.BOLD, 35));
        lblRotuloSetsJogador1.setForeground(Color.RED);
        painel.add(lblRotuloSetsJogador1);

        lblRotuloPontosJogador1 = new JLabel(String.valueOf(dados.getPontosJogador1()));
        lblRotuloPontosJogador1.setBounds(120, 160, 200, 200);
        lblRotuloPontosJogador1.setFont(new Font("Arial", Font.BOLD, 140));
        lblRotuloPontosJogador1.setForeground(Color.RED);
        lblRotuloPontosJogador1.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblRotuloPontosJogador1);

        // Separador
        JSeparator separador = new JSeparator();
        separador.setBounds(10, 350, 320, 2);
        painel.add(separador);

        // === Jogador 2 ===
        JLabel lblRotuloNomeJogador2 = new JLabel("Sets " + dados.getNomeJogador2());
        lblRotuloNomeJogador2.setBounds(15, 360, 200, 40);
        lblRotuloNomeJogador2.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblRotuloNomeJogador2);

        lblRotuloSetsJogador2 = new JLabel(String.valueOf(dados.getSetsJogador2()));
        lblRotuloSetsJogador2.setBounds(15, 390, 150, 50);
        lblRotuloSetsJogador2.setFont(new Font("Arial", Font.BOLD, 35));
        lblRotuloSetsJogador2.setForeground(Color.BLUE);
        painel.add(lblRotuloSetsJogador2);

        lblRotuloPontosJogador2 = new JLabel(String.valueOf(dados.getPontosJogador2()));
        lblRotuloPontosJogador2.setBounds(120, 400, 200, 200);
        lblRotuloPontosJogador2.setFont(new Font("Arial", Font.BOLD, 140));
        lblRotuloPontosJogador2.setForeground(Color.BLUE);
        lblRotuloPontosJogador2.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(lblRotuloPontosJogador2);

        // Evento de teclado
        txtFieldCampoEntrada.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                if (evento.getKeyCode() == KeyEvent.VK_ENTER) {
                    registrarPonto(txtFieldCampoEntrada);
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
        lblRotuloPontosJogador1.setText(String.valueOf(dados.getPontosJogador1()));
        lblRotuloPontosJogador2.setText(String.valueOf(dados.getPontosJogador2()));
        lblRotuloSetsJogador1.setText(String.valueOf(dados.getSetsJogador1()));
        lblRotuloSetsJogador2.setText(String.valueOf(dados.getSetsJogador2()));
    }
}

