package games.plataforma.minigames.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LeitorArquivo {
    public static List<String> lerLinhas(String caminho) throws IOException {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                LeitorArquivo.class.getResourceAsStream(caminho)))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha.trim());
            }
        } catch (NullPointerException e) {
            try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    linhas.add(linha.trim());
                }
            }
        }
        return linhas;
    }
}
