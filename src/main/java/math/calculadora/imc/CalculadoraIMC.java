package math.calculadora.imc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CalculadoraIMC extends JFrame {

    private JTextField  campoPeso;
    private JTextField  campoAltura;
    private JButton     botaoCalcular;
    private JLabel      rotuloResultado;

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
        JLabel titulo = new JLabel("Calculadora de IMC");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(COR_TITULO); // Será aplicado no tema escuro, mas forçamos a cor
        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Campo Peso
        JLabel rotuloPeso = new JLabel("Peso (kg):");
        rotuloPeso.setAlignmentX(Component.LEFT_ALIGNMENT);
        rotuloPeso.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painelPrincipal.add(rotuloPeso);
        campoPeso = new JTextField();
        campoPeso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campoPeso.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painelPrincipal.add(campoPeso);
        painelPrincipal.add(Box.createVerticalStrut(10));

        // Campo Altura
        JLabel rotuloAltura = new JLabel("Altura (m):");
        rotuloAltura.setAlignmentX(Component.LEFT_ALIGNMENT);
        rotuloAltura.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painelPrincipal.add(rotuloAltura);
        campoAltura = new JTextField();
        campoAltura.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campoAltura.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painelPrincipal.add(campoAltura);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Botão de cálculo
        botaoCalcular = new JButton("Calcular IMC");
        botaoCalcular.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoCalcular.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoCalcular.setFocusPainted(false);
        botaoCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularIMC();
            }
        });
        painelPrincipal.add(botaoCalcular);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Área de resultado
        rotuloResultado = new JLabel("<html><center>Insira os dados e clique em Calcular</center></html>");
        rotuloResultado.setAlignmentX(Component.CENTER_ALIGNMENT);
        rotuloResultado.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rotuloResultado.setHorizontalAlignment(SwingConstants.CENTER);
        painelPrincipal.add(rotuloResultado);

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
        campoPeso.setBackground(COR_FUNDO_CAMPO);
        campoPeso.setForeground(COR_TEXTO);
        campoPeso.setCaretColor(COR_TEXTO);
        campoPeso.setBorder(BorderFactory.createLineBorder(COR_FUNDO_CAMPO.darker()));

        campoAltura.setBackground(COR_FUNDO_CAMPO);
        campoAltura.setForeground(COR_TEXTO);
        campoAltura.setCaretColor(COR_TEXTO);
        campoAltura.setBorder(BorderFactory.createLineBorder(COR_FUNDO_CAMPO.darker()));

        botaoCalcular.setBackground(COR_BOTAO);
        botaoCalcular.setForeground(COR_BOTAO_TEXTO);
        botaoCalcular.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        rotuloResultado.setForeground(COR_TEXTO);

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
        String textoPeso = campoPeso.getText().trim();
        String textoAltura = campoAltura.getText().trim();

        if (textoPeso.isEmpty() || textoAltura.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha os campos de peso e altura.",
                    "Campos vazios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double peso, altura;
        try {
            peso = Double.parseDouble(textoPeso.replace(',', '.'));
            altura = Double.parseDouble(textoAltura.replace(',', '.'));
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

        rotuloResultado.setText(String.format(
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