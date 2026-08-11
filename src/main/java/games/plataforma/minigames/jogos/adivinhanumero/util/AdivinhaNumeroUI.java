package games.plataforma.minigames.jogos.adivinhanumero.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.adivinhanumero.model.AdivinhaNumero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdivinhaNumeroUI extends JanelaJogo {

    private AdivinhaNumero      model;
    private JLabel              lblMensagem;
    private JLabel              lblTentativas;
    private JTextField          txtEntrada;
    private JButton             btnTentar;

    public AdivinhaNumeroUI() {
        super("Adivinhe o Número");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new AdivinhaNumero(1, 100);
        setLayout(new BorderLayout(10, 10));

        JPanel painelCentral = new JPanel(new GridLayout(3, 1, 10, 10));
        painelCentral.setOpaque(false);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblMensagem = new JLabel("Tente adivinhar o número entre " + model.getMinimo() + " e " + model.getMaximo(), SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensagem.setForeground(Color.WHITE);

        JPanel panelEntrada = new JPanel(new FlowLayout());
        panelEntrada.setOpaque(false);
        txtEntrada = new JTextField(10);
        txtEntrada.setFont(new Font("Arial", Font.BOLD, 18));
        txtEntrada.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tentar();
                }
            }
        });

        btnTentar = new JButton("Tentar");
        btnTentar.setBackground(new Color(64, 64, 64));
        btnTentar.setForeground(Color.WHITE);
        btnTentar.setFocusPainted(false);
        btnTentar.addActionListener(e -> tentar());

        panelEntrada.add(new JLabel("Seu palpite: "));
        panelEntrada.add(txtEntrada);
        panelEntrada.add(btnTentar);

        lblTentativas = new JLabel("Tentativas: 0", SwingConstants.CENTER);
        lblTentativas.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTentativas.setForeground(Color.WHITE);

        painelCentral.add(lblMensagem);
        painelCentral.add(panelEntrada);
        painelCentral.add(lblTentativas);

        JPanel botoesPanel = criarPainelBotoesVoltar();
        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBackground(new Color(64, 64, 64));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.addActionListener(e -> reiniciar());
        botoesPanel.add(btnReiniciar);

        add(painelCentral, BorderLayout.CENTER);
        add(botoesPanel, BorderLayout.SOUTH);

        pack();
        setSize(500, 300);
        setLocationRelativeTo(null);
        txtEntrada.requestFocus();
    }

    private void tentar() {
        String texto = txtEntrada.getText().trim();
        if (texto.isEmpty()) {
            return;
        }

        try {
            int palpite = Integer.parseInt(texto);
            if (palpite < model.getMinimo() || palpite > model.getMaximo()) {
                JOptionPane.showMessageDialog(this, "Número fora do intervalo permitido.");
                return;
            }

            int resultado = model.tentar(palpite);
            txtEntrada.setText("");
            lblTentativas.setText("Tentativas: " + model.getTentativas());
            if (resultado == 0) {
                lblMensagem.setText("Parabéns! Você acertou o número " + model.getNumeroSecreto() + " em " + model.getTentativas() + " tentativas!");
                txtEntrada.setEnabled(false);
                btnTentar.setEnabled(false);
            } else if (resultado < 0) {
                lblMensagem.setText("O número é MAIOR que " + palpite);
            } else {
                lblMensagem.setText("O número é MENOR que " + palpite);
            }
            txtEntrada.requestFocus();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um número válido.");
        }
    }

    private void reiniciar() {
        model.reiniciar();
        lblMensagem.setText("Tente adivinhar o número entre " + model.getMinimo() + " e " + model.getMaximo());
        lblTentativas.setText("Tentativas: 0");
        txtEntrada.setText("");
        txtEntrada.setEnabled(true);
        btnTentar.setEnabled(true);
        txtEntrada.requestFocus();
    }
}
