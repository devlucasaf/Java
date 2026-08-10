package math.calculadora.completa.gui.frame;

import math.calculadora.completa.gui.painel.*;
import math.calculadora.completa.gui.tema.TemaEscuro;

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
        tabbedPane.addTab("Padrão", new CalculadoraPadraoPainel());
        tabbedPane.addTab("Científica", new CalculadoraCientificaPainel());
        tabbedPane.addTab("Datas", new CalculadoraDatasPainel());
        tabbedPane.addTab("Horas", new CalculadoraHorasPainel());
        tabbedPane.addTab("Moeda", new CalculadoraMoedaPainel());
        tabbedPane.addTab("Volume", new CalculadoraConversorPainel("Volume", new String[]{"Litros", "Mililitros", "Metros Cúbicos"}, new double[]{1.0, 1000.0, 0.001}));
        tabbedPane.addTab("Comprimento", new CalculadoraConversorPainel("Comprimento", new String[]{"Metros", "Centímetros", "Milímetros", "Quilômetros"}, new double[]{1.0, 100.0, 1000.0, 0.001}));
        tabbedPane.addTab("Peso/Massa", new CalculadoraConversorPainel("Peso/Massa", new String[]{"Gramas", "Quilogramas", "Toneladas"}, new double[]{1.0, 0.001, 0.000001}));
        tabbedPane.addTab("Temperatura", new CalculadoraTemperaturaPainel());
        tabbedPane.addTab("Área", new CalculadoraConversorPainel("Área", new String[]{"Metros Quadrados", "Hectares", "Quilômetros Quadrados"}, new double[]{1.0, 0.0001, 0.000001}));
        tabbedPane.addTab("Velocidade", new CalculadoraConversorPainel("Velocidade", new String[]{"km/h", "m/s", "mph"}, new double[]{1.0, 0.2777778, 0.621371}));
        tabbedPane.addTab("Tempo", new CalculadoraConversorPainel("Tempo", new String[]{"Segundos", "Minutos", "Horas", "Dias"}, new double[]{1.0, 1.0/60, 1.0/3600, 1.0/86400}));
        tabbedPane.addTab("Pressão", new CalculadoraConversorPainel("Pressão", new String[]{"Pascal", "Bar", "atm"}, new double[]{1.0, 0.00001, 0.00000986923}));
        tabbedPane.addTab("Ângulo", new CalculadoraAnguloPainel());
        tabbedPane.addTab("Bases", new CalculadoraBasesPainel());
        tabbedPane.addTab("Dados", new CalculadoraDadosPainel());
        tabbedPane.addTab("Combustível", new CalculadoraCombustivelPainel());
        tabbedPane.addTab("Juros", new CalculadoraJurosPainel());
        tabbedPane.addTab("Regra de 3", new CalculadoraRegraDeTresPainel());
        tabbedPane.addTab("IMC", new CalculadoraIMCPainel());
        tabbedPane.addTab("Bit a Bit", new CalculadoraBitwisePainel());
        tabbedPane.addTab("Histórico", new CalculadoraHistoricoPainel());
        tabbedPane.addTab("Romanos", new CalculadoraRomanosPainel());
        tabbedPane.addTab("Tabela Verdade", new CalculadoraTabelaVerdadePainel());
        tabbedPane.addTab("Cores", new CalculadoraCoresPainel());
        tabbedPane.addTab("Idade", new CalculadoraIdadePainel());
        tabbedPane.addTab("Estatística", new CalculadoraEstatisticaPainel());
        tabbedPane.addTab("Bhaskara", new CalculadoraBhaskaraPainel());
        tabbedPane.addTab("Fatorial", new CalculadoraFatorialPainel());
        tabbedPane.addTab("Logaritmo", new CalculadoraLogaritmoPainel());
        tabbedPane.addTab("Primos", new CalculadoraPrimosPainel());
        tabbedPane.addTab("Fibonacci", new CalculadoraFibonacciPainel());
        tabbedPane.addTab("Margem de Lucro", new CalculadoraMargemLucroPainel());
        tabbedPane.addTab("Gorjeta", new CalculadoraGorjetaPainel());
        tabbedPane.addTab("Equações", new CalculadoraEquacoesPainel());
        tabbedPane.addTab("Matrizes", new CalculadoraMatrizesPainel());
        tabbedPane.addTab("Área/Volume", new CalculadoraAreaVolumePainel());

        add(tabbedPane);
        setVisible(true);
    }
}
