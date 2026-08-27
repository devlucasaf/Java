package math.probabilidade.csv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColunaCsv {

    private final String        nome;
    private final List<String>  valores;

    public ColunaCsv(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da coluna não pode estar vazio.");
        }

        this.nome = nome.trim();
        this.valores = new ArrayList<>();
    }

    // --- ADICIONA UM VALOR À COLUNA ---
    public void adicionarValor(String valor) {
        valores.add(valor == null ? "" : valor.trim());
    }

    // --- RETORNA APENAS OS VALORES NUMÉRICOS VÁLIDOS ---
    public List<Double> getValoresNumericos() {
        List<Double> valoresNumericos = new ArrayList<>();

        for (String valor : valores) {
            Double numero = converterParaNumero(valor);

            if (numero != null) {
                valoresNumericos.add(numero);
            }
        }

        return valoresNumericos;
    }

    // --- VERIFICA SE A COLUNA PODE SER CONSIDERADA NUMÉRICA ---
    public boolean isNumerica() {
        int quantidadePreenchida = 0;
        int quantidadeNumerica = 0;

        for (String valor : valores) {
            if (valor != null && !valor.trim().isEmpty()) {
                quantidadePreenchida++;

                if (converterParaNumero(valor) != null) {
                    quantidadeNumerica++;
                }
            }
        }

        return quantidadePreenchida > 0 && quantidadeNumerica == quantidadePreenchida;
    }

    // --- CONVERTE UM TEXTO PARA NÚMERO ---
    public static Double converterParaNumero(String valor) {
        if (valor == null) {
            return null;
        }

        String texto = valor.trim();

        if (texto.isEmpty() || texto.equalsIgnoreCase("null") || texto.equalsIgnoreCase("nan") || texto.equalsIgnoreCase("n/a")) {
            return null;
        }

        texto = normalizarNumero(texto);

        try {
            double numero = Double.parseDouble(texto);
            return Double.isFinite(numero) ? numero : null;
        } catch (NumberFormatException excecao) {
            return null;
        }
    }

    // --- NORMALIZA NÚMEROS COM VÍRGULA OU PONTO DECIMAL ---
    private static String normalizarNumero(String texto) {
        String numero = texto.replace(" ", "");

        if (numero.contains(",") && numero.contains(".")) {
            int ultimaVirgula = numero.lastIndexOf(",");
            int ultimoPonto = numero.lastIndexOf(".");

            if (ultimaVirgula > ultimoPonto) {
                numero = numero.replace(".", "").replace(",", ".");
            } else {
                numero = numero.replace(",", "");
            }
        } else if (numero.contains(",")) {
            numero = numero.replace(",", ".");
        }

        return numero;
    }

    // --- CONTA OS VALORES AUSENTES ---
    public int contarValoresAusentes() {
        int quantidade = 0;

        for (String valor : valores) {
            if (valor == null || valor.trim().isEmpty() || valor.equalsIgnoreCase("null") || valor.equalsIgnoreCase("n/a")) {
                quantidade++;
            }
        }

        return quantidade;
    }

    public String getNome() {
        return nome;
    }

    public List<String> getValores() {
        return Collections.unmodifiableList(valores);
    }

    public int getQuantidadeValores() {
        return valores.size();
    }
}

