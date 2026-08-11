package games.plataforma.minigames.jogos.forca.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.forca.model.Forca;

import javax.swing.*;
import java.awt.*;

public class ForcaUI extends JanelaJogo {

    private Forca       model;
    private JLabel      palavraLabel;
    private JLabel      errosLabel;
    private JLabel      desenhoLabel;
    private JTextField  entradaLetra;
    private JButton     tentarButton;
    private JButton     novaPalavraButton;

    public ForcaUI() {
        super("Forca");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new Forca();
        setLayout(new BorderLayout(10, 10));

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setOpaque(false);

        desenhoLabel = new JLabel(obterDesenho(0), SwingConstants.CENTER);
        desenhoLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        desenhoLabel.setForeground(Color.WHITE);
        painelSuperior.add(desenhoLabel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        palavraLabel = new JLabel("Palavra: " + model.getProgressoString(), SwingConstants.CENTER);
        palavraLabel.setFont(new Font("Arial", Font.BOLD, 24));
        palavraLabel.setForeground(Color.WHITE);

        errosLabel = new JLabel("Erros: 0/" + model.getMaxErros(), SwingConstants.CENTER);
        errosLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        errosLabel.setForeground(Color.WHITE);
        infoPanel.add(palavraLabel);
        infoPanel.add(errosLabel);
        painelSuperior.add(infoPanel, BorderLayout.SOUTH);

        // Painel inferior: entrada e botões
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setOpaque(false);

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
        tentarButton.setBackground(new Color(64, 64, 64));
        tentarButton.setForeground(Color.WHITE);
        tentarButton.setFocusPainted(false);
        tentarButton.addActionListener(e -> tentarLetra());

        entradaPanel.add(new JLabel("Digite uma letra: "));
        entradaPanel.add(entradaLetra);
        entradaPanel.add(tentarButton);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesPanel.setOpaque(false);

        novaPalavraButton = new JButton("Nova Palavra");
        novaPalavraButton.setBackground(new Color(64, 64, 64));
        novaPalavraButton.setForeground(Color.WHITE);
        novaPalavraButton.setFocusPainted(false);
        novaPalavraButton.addActionListener(e -> novaPalavra());
        botoesPanel.add(novaPalavraButton);
        JPanel voltarPanel = criarPainelBotoesVoltar();
        botoesPanel.add(voltarPanel.getComponent(0));

        painelInferior.add(entradaPanel, BorderLayout.CENTER);
        painelInferior.add(botoesPanel, BorderLayout.SOUTH);

        add(painelSuperior, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        pack();
        setSize(600, 450);
        setLocationRelativeTo(null);
        entradaLetra.requestFocus();
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
    }

    private void atualizarTela() {
        palavraLabel.setText("Palavra: " + model.getProgressoString());
        errosLabel.setText("Erros: " + model.getErros() + "/" + model.getMaxErros());
        desenhoLabel.setText(obterDesenho(model.getErros()));
    }

    private String obterDesenho(int erros) {
        String[] estagios = {
                "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="
        };
        return erros < estagios.length ? estagios[erros] : estagios[estagios.length-1];
    }
}
