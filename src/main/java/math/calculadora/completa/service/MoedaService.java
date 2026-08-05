package math.calculadora.completa.service;

import math.calculadora.completa.util.Constantes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class MoedaService {

    public double buscarConversao(double valor, String de, String para) throws Exception {
        String urlStr = String.format(Constantes.API_MOEDA, valor, de, para);
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
