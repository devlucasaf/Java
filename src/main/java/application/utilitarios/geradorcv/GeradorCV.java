package application.utilitarios.geradorcv;

import application.utilitarios.geradorpdf.GeradorPDF;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.file.Path;

public class GeradorCV extends JFrame {

    private final JTextField    nome = new JTextField(30);
    private final JTextField    cargo = new JTextField(30);
    private final JTextField    email = new JTextField(30);
    private final JTextField    telefone = new JTextField(30);
    private final JTextField    endereco = new JTextField(30);
    private final JTextArea     objetivo = new JTextArea(3, 40);
    private final JTextArea     experiencia = new JTextArea(6, 40);
    private final JTextArea     formacao = new JTextArea(4, 40);
    private final JTextArea     habilidades = new JTextArea(3, 40);
    private final JTextArea     idiomas = new JTextArea(2, 40);

    public GeradorCV() {
        super("Gerador de Curriculo em PDF");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int linha = 0;
        adicionarCampo(formulario, gbc, linha++, "Nome:",        nome);
        adicionarCampo(formulario, gbc, linha++, "Cargo alvo:",  cargo);
        adicionarCampo(formulario, gbc, linha++, "E-mail:",      email);
        adicionarCampo(formulario, gbc, linha++, "Telefone:",    telefone);
        adicionarCampo(formulario, gbc, linha++, "Endereco:",    endereco);
        adicionarArea(formulario, gbc, linha++, "Objetivo:",     objetivo);
        adicionarArea(formulario, gbc, linha++, "Experiencia:",  experiencia);
        adicionarArea(formulario, gbc, linha++, "Formacao:",     formacao);
        adicionarArea(formulario, gbc, linha++, "Habilidades:",  habilidades);
        adicionarArea(formulario, gbc, linha++, "Idiomas:",      idiomas);

        JButton gerar = new JButton("Gerar PDF");
        gerar.addActionListener(e -> gerar());

        add(new JScrollPane(formulario), BorderLayout.CENTER);
        add(gerar, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(600, 700));
        pack();
        setLocationRelativeTo(null);
    }

    private void adicionarCampo(JPanel p, GridBagConstraints g, int y, String label, JTextField f) {
        g.gridx = 0; g.gridy = y; g.weightx = 0;
        p.add(new JLabel(label), g);
        g.gridx = 1; g.weightx = 1;
        p.add(f, g);
    }

    private void adicionarArea(JPanel p, GridBagConstraints g, int y, String label, JTextArea a) {
        g.gridx = 0; g.gridy = y; g.weightx = 0;
        p.add(new JLabel(label), g);
        g.gridx = 1; g.weightx = 1;
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
        a.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY));
        p.add(new JScrollPane(a), g);
    }

    private void gerar() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("curriculo.pdf"));
            if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
            Path destino = chooser.getSelectedFile().toPath();

            GeradorPDF pdf = new GeradorPDF().titulo(nome.getText().isBlank() ? "Curriculo" : nome.getText());

            if (!cargo.getText().isBlank()) pdf.adicionar(cargo.getText());
            pdf.adicionar("Email: " + email.getText())
               .adicionar("Telefone: " + telefone.getText())
               .adicionar("Endereco: " + endereco.getText())
               .pular();

            secao(pdf, "OBJETIVO", objetivo.getText());
            secao(pdf, "EXPERIENCIA PROFISSIONAL", experiencia.getText());
            secao(pdf, "FORMACAO", formacao.getText());
            secao(pdf, "HABILIDADES", habilidades.getText());
            secao(pdf, "IDIOMAS", idiomas.getText());

            pdf.salvar(destino);
            JOptionPane.showMessageDialog(this, "PDF gerado em:\n" + destino.toAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void secao(GeradorPDF pdf, String titulo, String texto) {
        if (texto.isBlank()) return;
        pdf.cabecalho(titulo);
        for (String linha : texto.split("\n")) pdf.adicionar(linha);
        pdf.pular();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GeradorCV().setVisible(true));
    }
}

