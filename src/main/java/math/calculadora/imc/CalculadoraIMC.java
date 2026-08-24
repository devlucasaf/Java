package math.calculadora.imc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CalculadoraIMC extends JFrame {

    private JTextField  txtCampoPeso;
    private JTextField  txtCampoAltura;
    private JButton     btnCalcular;
    private JLabel      lblRotuloResultado;

    // Cores do tema escuro
    private static final Color COR_FUNDO = new Color(45, 45, 45);
    private static final Color COR_FUNDO_CAMPO = new Color(60, 63, 65);
    private static final Color COR_TEXTO = new Color(220, 220, 220);
    private static final Color COR_BOTAO = new Color(70, 130, 180);
    private static final Color COR_BOTAO_TEXTO = Color.WHITE;
    private static final Color COR_TITULO = new Color(255, 200, 100);

    public CalculadoraIMC() {
        super("Calculadora de IMC");
        configurarJanela();
        construirInterface();
        aplicarTemaEscuro();
        setVisible(true);
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null); // Centraliza na tela
        setResizable(false);
    }

    private void construirInterface() {
        // Painel principal com borda espaçada
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Título
        JLabel lblTitulo = new JLabel("Calculadora de IMC");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(COR_TITULO); // Será aplicado no tema escuro, mas forçamos a cor
        painelPrincipal.add(lblTitulo);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Campo Peso
        JLabel lblRotuloPeso = new JLabel("Peso (kg):");
        lblRotuloPeso.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblRotuloPeso.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painelPrincipal.add(lblRotuloPeso);
        txtCampoPeso = new JTextField();
        txtCampoPeso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtCampoPeso.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painelPrincipal.add(txtCampoPeso);
        painelPrincipal.add(Box.createVerticalStrut(10));

        // Campo Altura
        JLabel lblRotuloAltura = new JLabel("Altura (m):");
        lblRotuloAltura.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblRotuloAltura.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painelPrincipal.add(lblRotuloAltura);
        txtCampoAltura = new JTextField();
        txtCampoAltura.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtCampoAltura.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painelPrincipal.add(txtCampoAltura);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Botão de cálculo
        btnCalcular = new JButton("Calcular IMC");
        btnCalcular.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCalcular.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCalcular.setFocusPainted(false);
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularIMC();
            }
        });
        painelPrincipal.add(btnCalcular);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Área de resultado
        lblRotuloResultado = new JLabel("<html><center>Insira os dados e clique em Calcular</center></html>");
        lblRotuloResultado.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRotuloResultado.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblRotuloResultado.setHorizontalAlignment(SwingConstants.CENTER);
        painelPrincipal.add(lblRotuloResultado);

        add(painelPrincipal);
    }

    private void aplicarTemaEscuro() {
        getContentPane().setBackground(COR_FUNDO);

        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBackground(COR_FUNDO);
            }
        }

        // Configura cores dos componentes
        txtCampoPeso.setBackground(COR_FUNDO_CAMPO);
        txtCampoPeso.setForeground(COR_TEXTO);
        txtCampoPeso.setCaretColor(COR_TEXTO);
        txtCampoPeso.setBorder(BorderFactory.createLineBorder(COR_FUNDO_CAMPO.darker()));

        txtCampoAltura.setBackground(COR_FUNDO_CAMPO);
        txtCampoAltura.setForeground(COR_TEXTO);
        txtCampoAltura.setCaretColor(COR_TEXTO);
        txtCampoAltura.setBorder(BorderFactory.createLineBorder(COR_FUNDO_CAMPO.darker()));

        btnCalcular.setBackground(COR_BOTAO);
        btnCalcular.setForeground(COR_BOTAO_TEXTO);
        btnCalcular.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        lblRotuloResultado.setForeground(COR_TEXTO);

        // Define cor de todos os JLabel
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                for (Component inner : ((JPanel) comp).getComponents()) {
                    if (inner instanceof JLabel) {
                        inner.setForeground(COR_TEXTO);
                    }
                }
            }
        }
    }

    private void calcularIMC() {
        String txtPeso = txtCampoPeso.getText().trim();
        String txtAltura = txtCampoAltura.getText().trim();

        if (txtPeso.isEmpty() || txtAltura.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha os campos de peso e altura.",
                    "Campos vazios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double peso;
        double altura;

        try {
            peso = Double.parseDouble(txtPeso.replace(',', '.'));
            altura = Double.parseDouble(txtAltura.replace(',', '.'));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Insira apenas valores numéricos válidos.",
                    "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (peso <= 0 || altura <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Peso e altura devem ser valores positivos.",
                    "Valores inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double imc = peso / (altura * altura);
        String classificacao = classificarIMC(imc);
        DecimalFormat df = new DecimalFormat("#0.0");

        lblRotuloResultado.setText(String.format(
                "<html><center>IMC: <b>%s</b><br>Classificação: <b>%s</b></center></html>",
                df.format(imc), classificacao));
    }

    private String classificarIMC(double imc) {
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25.0) {
            return "Peso normal";
        } else if (imc < 30.0) {
            return "Sobrepeso";
        } else if (imc < 35.0) {
            return "Obesidade grau I";
        } else if (imc < 40.0) {
            return "Obesidade grau II";
        } else {
            return "Obesidade grau III (mórbida)";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculadoraIMC();
            }
        });
    }
}
