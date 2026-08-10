package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.interfaces.Calculo;
import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.geometria.AreaVolume;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraAreaVolumePainel extends JPanel {
    public CalculadoraAreaVolumePainel() {
        super(new BorderLayout(10,10));
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10,10,10,10));

        JTabbedPane abas = new JTabbedPane();
        abas.setBackground(TemaEscuro.FUNDO);
        abas.setForeground(TemaEscuro.TEXTO);
        abas.addTab("Área", criarPainelArea());
        abas.addTab("Volume", criarPainelVolume());
        add(abas, BorderLayout.CENTER);
    }

    private JPanel criarPainelArea() {
        JPanel panel = new JPanel(new GridLayout(3,1,10,10));
        panel.setBackground(TemaEscuro.FUNDO);
        panel.add(criarPainelFigura("Círculo", () -> {
            String raioCirculo = JOptionPane.showInputDialog("Raio:");
            return AreaVolume.areaCirculo(Double.parseDouble(raioCirculo));
        }));
        panel.add(criarPainelFigura("Triângulo", () -> {
            String baseTriangulo = JOptionPane.showInputDialog("Base:");
            String alturaTriangulo = JOptionPane.showInputDialog("Altura:");
            return AreaVolume.areaTriangulo(Double.parseDouble(baseTriangulo), Double.parseDouble(alturaTriangulo));
        }));
        panel.add(criarPainelFigura("Retângulo", () -> {
            String larguraRetangulo = JOptionPane.showInputDialog("Largura:");
            String alturaRetangulo = JOptionPane.showInputDialog("Altura:");
            return AreaVolume.areaRetangulo(Double.parseDouble(larguraRetangulo), Double.parseDouble(alturaRetangulo));
        }));
        return panel;
    }

    private JPanel criarPainelVolume() {
        JPanel panel = new JPanel(new GridLayout(4,1,10,10));
        panel.setBackground(TemaEscuro.FUNDO);
        panel.add(criarPainelFigura("Cubo", () -> {
            String ladoCubo = JOptionPane.showInputDialog("Lado:");
            return AreaVolume.volumeCubo(Double.parseDouble(ladoCubo));
        }));
        panel.add(criarPainelFigura("Esfera", () -> {
            String raioEsfera = JOptionPane.showInputDialog("Raio:");
            return AreaVolume.volumeEsfera(Double.parseDouble(raioEsfera));
        }));
        panel.add(criarPainelFigura("Cilindro", () -> {
            String raioCilindro = JOptionPane.showInputDialog("Raio:");
            String alturaCilindro = JOptionPane.showInputDialog("Altura:");
            return AreaVolume.volumeCilindro(Double.parseDouble(raioCilindro), Double.parseDouble(alturaCilindro));
        }));
        panel.add(criarPainelFigura("Cone (raio, altura)", () -> {
            String raioCone = JOptionPane.showInputDialog("Raio:");
            String alturaCone = JOptionPane.showInputDialog("Altura:");
            return AreaVolume.volumeCone(Double.parseDouble(raioCone), Double.parseDouble(alturaCone));
        }));
        return panel;
    }

    private JPanel criarPainelFigura(String nome, Calculo calculo) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(TemaEscuro.FUNDO);
        JButton btnCalular = new JButton("Calcular " + nome);
        btnCalular.setBackground(TemaEscuro.BOTAO);
        btnCalular.setForeground(TemaEscuro.TEXTO);
        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        btnCalular.addActionListener(e -> {
            try {
                double r = calculo.calcular();
                lblResultado.setText("Resultado: " + String.format("%.4f", r));
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
        panel.add(btnCalular);
        panel.add(lblResultado);
        return panel;
    }

}
