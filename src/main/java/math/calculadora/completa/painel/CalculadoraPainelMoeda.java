package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class CalculadoraPainelMoeda extends JPanel {

    private static final String[] MOEDAS = {"USD", "EUR", "BRL", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "ARS"};
    private static final String API_URL = "https://api.frankfurter.app/latest?amount=%s&from=%s&to=%s";

    public CalculadoraPainelMoeda() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblValor, gbc);

        JTextField txtValor = new JTextField("1", 10);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtValor, gbc);

        JLabel lblDe = new JLabel("De:");
        lblDe.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblDe, gbc);

        JComboBox<String> cbDe = new JComboBox<>(MOEDAS);
        cbDe.setSelectedItem("USD");
        cbDe.setBackground(TemaEscuro.BOTAO);
        cbDe.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(cbDe, gbc);

        JLabel lblPara = new JLabel("Para:");
        lblPara.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        add(lblPara, gbc);

        JComboBox<String> cbPara = new JComboBox<>(MOEDAS);
        cbPara.setSelectedItem("BRL");
        cbPara.setBackground(TemaEscuro.BOTAO);
        cbPara.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 2;
        add(cbPara, gbc);

        JButton btnConverter = new JButton("Converter");
        btnConverter.setBackground(TemaEscuro.BOTAO);
        btnConverter.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnConverter, gbc);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(lblResultado, gbc);

        JLabel lblAviso = new JLabel("Taxas fornecidas pela API Frankfurter (dados do BCE)");
        lblAviso.setForeground(Color.LIGHT_GRAY);
        lblAviso.setFont(lblAviso.getFont().deriveFont(11f));
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(lblAviso, gbc);

        btnConverter.addActionListener(e -> {
            String textoValor = txtValor.getText().trim();
            String de = (String) cbDe.getSelectedItem();
            String para = (String) cbPara.getSelectedItem();

            double valor;
            try {
                valor = Double.parseDouble(textoValor);
            } catch (NumberFormatException ex) {
                lblResultado.setText("Erro no valor!");
                return;
            }

            if (de.equals(para)) {
                lblResultado.setText(String.format("Resultado: %.4f %s", valor, para));
                return;
            }

            btnConverter.setEnabled(false);
            lblResultado.setText("Buscando taxa de câmbio...");

            SwingWorker<Double, Void> worker = new SwingWorker<Double, Void>() {
                @Override
                protected Double doInBackground() throws Exception {
                    return buscarConversao(valor, de, para);
                }

                @Override
                protected void done() {
                    btnConverter.setEnabled(true);
                    try {
                        double resultado = get();
                        lblResultado.setText(String.format("Resultado: %.4f %s", resultado, para));
                    } catch (Exception ex) {
                        lblResultado.setText("Erro ao buscar a taxa de câmbio!");
                    }
                }
            };
            worker.execute();
        });
    }

    private double buscarConversao(double valor, String de, String para) throws Exception {
        String urlStr = String.format(API_URL, valor, de, para);
        URI uri = URI.create(urlStr);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        StringBuilder resposta = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                resposta.append(linha);
            }
        } finally {
            conn.disconnect();
        }

        return extrairValor(resposta.toString(), para);
    }

    private double extrairValor(String json, String moeda) {
        String chave = "\"" + moeda + "\":";
        int inicio = json.indexOf(chave);
        if (inicio == -1) {
            throw new IllegalStateException("Moeda não encontrada na resposta: " + moeda);
        }
        inicio += chave.length();
        int fim = inicio;
        while (fim < json.length() && "0123456789.-".indexOf(json.charAt(fim)) >= 0) {
            fim++;
        }
        return Double.parseDouble(json.substring(inicio, fim));
    }
}