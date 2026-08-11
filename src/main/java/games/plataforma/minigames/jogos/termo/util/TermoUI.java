package games.plataforma.minigames.jogos.termo.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.termo.model.Termo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TermoUI extends JanelaJogo {

    private Termo       model;
    private JTextField  entrada;
    private JPanel      gradePanel;
    private JLabel      statusLabel;
    private JButton     tentarButton;
    private JButton     novaPalavraButton;
    private JLabel[][]  labels;

    public TermoUI() {
        super("Termo");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new Termo();
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        statusLabel = new JLabel("Digite uma palavra de 5 letras", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        topPanel.add(statusLabel, BorderLayout.CENTER);

        gradePanel = new JPanel(new GridLayout(model.getMaxTentativas(), 5, 5, 5));
        gradePanel.setBackground(new Color(45, 45, 45));
        gradePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labels = new JLabel[model.getMaxTentativas()][5];
        for (int i = 0; i < model.getMaxTentativas(); i++) {
            for (int j = 0; j < 5; j++) {
                JLabel lbl = new JLabel(" ", SwingConstants.CENTER);
                lbl.setFont(new Font("Arial", Font.BOLD, 24));
                lbl.setBackground(new Color(64, 64, 64));
                lbl.setOpaque(true);
                lbl.setForeground(Color.WHITE);
                lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                labels[i][j] = lbl;
                gradePanel.add(lbl);
            }
        }

        JPanel entradaPanel = new JPanel(new FlowLayout());
        entradaPanel.setOpaque(false);
        entrada = new JTextField(5);
        entrada.setFont(new Font("Arial", Font.BOLD, 24));
        entrada.setHorizontalAlignment(JTextField.CENTER);
        entrada.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (getLength() + str.length() > 5) {
                    return;
                }
                super.insertString(offs, str, a);
            }
        });
        entrada.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tentar();
                }
            }
        });

        tentarButton = new JButton("Tentar");
        tentarButton.setBackground(new Color(64, 64, 64));
        tentarButton.setForeground(Color.WHITE);
        tentarButton.setFocusPainted(false);
        tentarButton.addActionListener(e -> tentar());

        entradaPanel.add(new JLabel("Palavra: "));
        entradaPanel.add(entrada);
        entradaPanel.add(tentarButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(entradaPanel, BorderLayout.CENTER);

        JPanel botoesPanel = criarPainelBotoesVoltar();
        novaPalavraButton = new JButton("Nova Palavra");
        novaPalavraButton.setBackground(new Color(64, 64, 64));
        novaPalavraButton.setForeground(Color.WHITE);
        novaPalavraButton.setFocusPainted(false);
        novaPalavraButton.addActionListener(e -> novaPalavra());
        botoesPanel.add(novaPalavraButton);
        bottomPanel.add(botoesPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(gradePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setSize(500, 600);
        setLocationRelativeTo(null);
        entrada.requestFocus();
    }

    private void tentar() {
        String palavra = entrada.getText().trim();
        if (palavra.length() != 5) {
            JOptionPane.showMessageDialog(this, "Digite exatamente 5 letras.");
            return;
        }

        if (model.isFinalizado()) {
            JOptionPane.showMessageDialog(this, "O jogo já acabou. Clique em 'Nova Palavra'.");
            return;
        }

        boolean ok = model.tentarPalavra(palavra);
        if (!ok) {
            return;
        }
        entrada.setText("");
        atualizarGrade();

        if (model.isFinalizado()) {
            if (model.isVenceu()) {
                statusLabel.setText("Parabéns! Você acertou a palavra: " + model.getPalavraSecreta());
            } else {
                statusLabel.setText("Que pena! A palavra era: " + model.getPalavraSecreta());
            }
            entrada.setEnabled(false);
            tentarButton.setEnabled(false);
        } else {
            statusLabel.setText("Tentativa " + model.getTentativas() + "/" + model.getMaxTentativas());
        }
        entrada.requestFocus();
    }

    private void atualizarGrade() {
        int tent = model.getTentativas();
        for (int i = 0; i < tent; i++) {
            String palavra = model.getHistorico(i);
            for (int j = 0; j < 5; j++) {
                JLabel lbl = labels[i][j];
                lbl.setText(String.valueOf(palavra.charAt(j)));
                int cor = model.getCor(i, j);
                switch (cor) {
                    case 0:
                        lbl.setBackground(new Color(80, 80, 80));
                        break;
                    case 1:
                        lbl.setBackground(new Color(200, 200, 0));
                        break;
                    case 2:
                        lbl.setBackground(new Color(0, 200, 0));
                        break;
                }
            }
        }
    }

    private void novaPalavra() {
        model.novaPalavra();
        for (int i = 0; i < model.getMaxTentativas(); i++) {
            for (int j = 0; j < 5; j++) {
                labels[i][j].setText(" ");
                labels[i][j].setBackground(new Color(64, 64, 64));
            }
        }

        statusLabel.setText("Digite uma palavra de 5 letras");
        entrada.setText("");
        entrada.setEnabled(true);
        tentarButton.setEnabled(true);
        entrada.requestFocus();
    }
}
