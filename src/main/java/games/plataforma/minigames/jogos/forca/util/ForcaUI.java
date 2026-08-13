package games.plataforma.minigames.jogos.forca.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.forca.model.Forca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForcaUI extends JanelaJogo {
    private Forca       model;
    private JLabel      palavraLabel;
    private JLabel      errosLabel;
    private JPanel      desenhoPanel;
    private JTextField  entradaLetra;
    private JButton     tentarButton;
    private JButton     novaPalavraButton;
    private Timer       animTimer;
    private int         animStep;

    public ForcaUI() {
        super("Forca Animada");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new Forca();
        setLayout(new BorderLayout(10, 10));

        // Painel de desenho (personalizado)
        desenhoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenharEnforcado(g);
            }
        };
        desenhoPanel.setPreferredSize(new Dimension(300, 300));
        desenhoPanel.setBackground(new Color(45,45,45));

        // Info
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        palavraLabel = new JLabel("Palavra: " + model.getProgressoString(), SwingConstants.CENTER);
        palavraLabel.setFont(new Font("Arial", Font.BOLD, 28));
        palavraLabel.setForeground(Color.WHITE);
        errosLabel = new JLabel("Erros: 0/" + model.getMaxErros(), SwingConstants.CENTER);
        errosLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        errosLabel.setForeground(Color.WHITE);
        infoPanel.add(palavraLabel);
        infoPanel.add(errosLabel);

        // Entrada
        JPanel entradaPanel = new JPanel(new FlowLayout());
        entradaPanel.setOpaque(false);
        entradaLetra = new JTextField(2);
        entradaLetra.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (str.length() > 1) {
                    return;
                }
                super.insertString(offs, str, a);
            }
        });
        entradaLetra.setHorizontalAlignment(JTextField.CENTER);
        entradaLetra.setFont(new Font("Arial", Font.BOLD, 24));
        tentarButton = new JButton("Tentar");
        tentarButton.setBackground(new Color(64,64,64));
        tentarButton.setForeground(Color.WHITE);
        tentarButton.setFocusPainted(false);
        tentarButton.addActionListener(e -> tentarLetra());

        entradaPanel.add(new JLabel("Digite uma letra: "));
        entradaPanel.add(entradaLetra);
        entradaPanel.add(tentarButton);

        // Botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesPanel.setOpaque(false);
        novaPalavraButton = new JButton("Nova Palavra");
        novaPalavraButton.setBackground(new Color(64,64,64));
        novaPalavraButton.setForeground(Color.WHITE);
        novaPalavraButton.setFocusPainted(false);
        novaPalavraButton.addActionListener(e -> novaPalavra());
        botoesPanel.add(novaPalavraButton);
        JPanel voltarPanel = criarPainelBotoesVoltar();
        botoesPanel.add(voltarPanel.getComponent(0));

        // Montagem
        JPanel central = new JPanel(new BorderLayout());
        central.add(desenhoPanel, BorderLayout.CENTER);
        central.add(infoPanel, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(entradaPanel, BorderLayout.CENTER);
        bottom.add(botoesPanel, BorderLayout.SOUTH);

        add(central, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
        entradaLetra.requestFocus();

        animTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.isFinalizado()) {
                    animTimer.stop();
                }
                desenhoPanel.repaint();
            }
        });
        animTimer.start();
    }

    private void desenharEnforcado(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));

        int erros = model.getErros();
        int baseX = 100;
        int baseY = 250;
        int tamanho = 100;

        g2d.drawLine(baseX, baseY, baseX, baseY - tamanho);
        g2d.drawLine(baseX, baseY - tamanho, baseX + tamanho/2, baseY - tamanho);
        g2d.drawLine(baseX + tamanho/2, baseY - tamanho, baseX + tamanho/2, baseY - tamanho + 20);

        if (erros >= 1) {
            g2d.drawOval(baseX + tamanho/2 - 15, baseY - tamanho + 20, 30, 30);
        }

        if (erros >= 2) {
            g2d.drawLine(baseX + tamanho/2, baseY - tamanho + 50, baseX + tamanho/2, baseY - tamanho + 80);
        }

        if (erros >= 3) {
            g2d.drawLine(baseX + tamanho/2, baseY - tamanho + 60, baseX + tamanho/2 - 30, baseY - tamanho + 40);
        }

        if (erros >= 4) {
            g2d.drawLine(baseX + tamanho/2, baseY - tamanho + 60, baseX + tamanho/2 + 30, baseY - tamanho + 40);
        }

        if (erros >= 5) {
            g2d.drawLine(baseX + tamanho/2, baseY - tamanho + 80, baseX + tamanho/2 - 30, baseY - tamanho + 110);
        }

        if (erros >= 6) {
            g2d.drawLine(baseX + tamanho/2, baseY - tamanho + 80, baseX + tamanho/2 + 30, baseY - tamanho + 110);
        }

        if (model.isFinalizado() && model.isVenceu()) {
            g2d.setColor(Color.GREEN);
            g2d.drawArc(baseX + tamanho/2 - 10, baseY - tamanho + 25, 20, 15, 180, 180);
        }

        if (model.isFinalizado() && !model.isVenceu()) {
            g2d.setColor(Color.RED);
            g2d.drawLine(baseX + tamanho/2 - 10, baseY - tamanho + 25, baseX + tamanho/2 - 2, baseY - tamanho + 33);
            g2d.drawLine(baseX + tamanho/2 - 10, baseY - tamanho + 33, baseX + tamanho/2 - 2, baseY - tamanho + 25);
            g2d.drawLine(baseX + tamanho/2 + 2, baseY - tamanho + 25, baseX + tamanho/2 + 10, baseY - tamanho + 33);
            g2d.drawLine(baseX + tamanho/2 + 2, baseY - tamanho + 33, baseX + tamanho/2 + 10, baseY - tamanho + 25);
        }
    }

    private void tentarLetra() {
        if (model.isFinalizado()) {
            JOptionPane.showMessageDialog(this, "O jogo já acabou. Clique em 'Nova Palavra' para jogar novamente.");
            return;
        }
        String texto = entradaLetra.getText().trim();
        if (texto.isEmpty()) {
            return;
        }

        char letra = texto.charAt(0);
        entradaLetra.setText("");
        boolean acertou = model.tentarLetra(letra);
        atualizarTela();
        if (model.isFinalizado()) {
            if (model.isVenceu()) {
                JOptionPane.showMessageDialog(this, "Parabéns! Você acertou a palavra: " + model.getPalavraSecreta());
            } else {
                JOptionPane.showMessageDialog(this, "Que pena! A palavra era: " + model.getPalavraSecreta());
            }
        }
        entradaLetra.requestFocus();
    }

    private void novaPalavra() {
        model.novaPalavra();
        atualizarTela();
        entradaLetra.requestFocus();
        if (!animTimer.isRunning()) {
            animTimer.start();
        }
    }

    private void atualizarTela() {
        palavraLabel.setText("Palavra: " + model.getProgressoString());
        errosLabel.setText("Erros: " + model.getErros() + "/" + model.getMaxErros());
        desenhoPanel.repaint();
    }
}
