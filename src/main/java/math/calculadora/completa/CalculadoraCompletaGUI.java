package math.calculadora.completa;

import math.calculadora.completa.painel.*;
import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;

public class CalculadoraCompletaGUI extends JFrame {

    private JTabbedPane tabbedPane;

    public CalculadoraCompletaGUI() {
        setTitle("Calculadora Multifuncional");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        TemaEscuro.aplicar();

        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(TemaEscuro.FUNDO);
        tabbedPane.setForeground(TemaEscuro.TEXTO);

        // Adiciona as abas
        tabbedPane.addTab("Padrão", new CalculadoraPainelPadrao());
        tabbedPane.addTab("Científica", new CalculadoraPainelCientifica());
        tabbedPane.addTab("Datas", new CalculadoraPainelDatas());
        tabbedPane.addTab("Horas", new CalculadoraPainelHoras());
        tabbedPane.addTab("Moeda", new CalculadoraPainelMoeda());
        tabbedPane.addTab("Volume", new CalculadoraPainelConversor("Volume", new String[]{"Litros", "Mililitros", "Metros Cúbicos"}, new double[]{1.0, 1000.0, 0.001}));
        tabbedPane.addTab("Comprimento", new CalculadoraPainelConversor("Comprimento", new String[]{"Metros", "Centímetros", "Milímetros", "Quilômetros"}, new double[]{1.0, 100.0, 1000.0, 0.001}));
        tabbedPane.addTab("Peso/Massa", new CalculadoraPainelConversor("Peso/Massa", new String[]{"Gramas", "Quilogramas", "Toneladas"}, new double[]{1.0, 0.001, 0.000001}));
        tabbedPane.addTab("Temperatura", new CalculadoraPainelTemperatura());
        tabbedPane.addTab("Área", new CalculadoraPainelConversor("Área", new String[]{"Metros Quadrados", "Hectares", "Quilômetros Quadrados"}, new double[]{1.0, 0.0001, 0.000001}));
        tabbedPane.addTab("Velocidade", new CalculadoraPainelConversor("Velocidade", new String[]{"km/h", "m/s", "mph"}, new double[]{1.0, 0.2777778, 0.621371}));
        tabbedPane.addTab("Tempo", new CalculadoraPainelConversor("Tempo", new String[]{"Segundos", "Minutos", "Horas", "Dias"}, new double[]{1.0, 1.0/60, 1.0/3600, 1.0/86400}));
        tabbedPane.addTab("Pressão", new CalculadoraPainelConversor("Pressão", new String[]{"Pascal", "Bar", "atm"}, new double[]{1.0, 0.00001, 0.00000986923}));
        tabbedPane.addTab("Ângulo", new CalculadoraPainelAngulo());
        tabbedPane.addTab("Bases", new CalculadoraPainelBases());
        tabbedPane.addTab("Dados", new CalculadoraPainelDados());
        tabbedPane.addTab("Combustível", new CalculadoraPainelCombustivel());
        tabbedPane.addTab("Juros", new CalculadoraPainelJuros());
        tabbedPane.addTab("Regra de 3", new CalculadoraPainelRegraTres());
        tabbedPane.addTab("IMC", new CalculadoraPainelIMC());
        tabbedPane.addTab("Bit a Bit", new CalculadoraPainelBitwise());
        tabbedPane.addTab("Histórico", new CalculadoraPainelHistorico());
        tabbedPane.addTab("Romanos", new CalculadoraPainelRomanos());
        tabbedPane.addTab("Tabela Verdade", new CalculadoraPainelTabelaVerdade());
        tabbedPane.addTab("Cores", new CalculadoraPainelCores());
        tabbedPane.addTab("Idade", new CalculadoraPainelIdade());
        tabbedPane.addTab("Estatística", new CalculadoraPainelEstatistica());
        tabbedPane.addTab("Bhaskara", new CalculadoraPainelBhaskara());
        tabbedPane.addTab("Fatorial", new CalculadoraPainelFatorial());
        tabbedPane.addTab("Logaritmo", new CalculadoraPainelLogaritmo());
        tabbedPane.addTab("Primos", new CalculadoraPainelPrimos());

        add(tabbedPane);
        setVisible(true);
    }

    // --- MAIN ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CalculadoraCompletaGUI();
        });
    }
}