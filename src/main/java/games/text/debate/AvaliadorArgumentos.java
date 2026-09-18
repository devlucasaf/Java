package games.text.debate;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class AvaliadorArgumentos {

    private static final Set<String> CONECTIVOS = Set.of("porque", "portanto", "pois", "assim", "contudo", "entretanto", "alem", "consequentemente", "embora", "logo", "porem", "desse modo", "por isso");
    private static final Set<String> MARCADORES_EVIDENCIA = Set.of("dados", "pesquisa", "estudo", "exemplo", "estatistica", "resultado", "evidencia", "levantamento", "analise");
    private static final Set<String> MARCADORES_REFUTACAO = Set.of("discordo", "contrario", "entretanto", "contudo", "refuto", "insuficiente", "equivocado");
    private static final Set<String> MARCADORES_SOLUCAO = Set.of("solucao", "proposta", "implementar", "criar", "melhorar", "reduzir", "resolver");

    // --- AVALIA UM ARGUMENTO DE ACORDO COM O TEMA ---
    public AvaliacaoArgumento avaliar(Argumento argumento, TemaDebate tema, String argumentoAnterior) {
        if (argumento == null || tema == null) {
            throw new IllegalArgumentException("O argumento e o tema devem ser informados.");
        }

        String textoNormalizado = normalizarTexto(argumento.getTexto());
        List<String> palavras = separarPalavras(textoNormalizado);
        List<String> frases = separarFrases(argumento.getTexto());

        Map<CriterioAvaliacao, Double> notas = new EnumMap<>(CriterioAvaliacao.class);
        notas.put(CriterioAvaliacao.COERENCIA, avaliarCoerencia(palavras, frases));
        notas.put(CriterioAvaliacao.RELEVANCIA, avaliarRelevancia(textoNormalizado, tema));
        notas.put(CriterioAvaliacao.CLAREZA, avaliarClareza(palavras, frases));
        notas.put(CriterioAvaliacao.EVIDENCIAS, avaliarEvidencias(textoNormalizado));
        notas.put(CriterioAvaliacao.ESTRUTURA, avaliarEstrutura(textoNormalizado, palavras, argumento.getEstrategia(), argumentoAnterior));
        notas.put(CriterioAvaliacao.VOCABULARIO, avaliarVocabulario(palavras));

        String observacao = criarObservacao(notas);
        return new AvaliacaoArgumento(argumento, notas, observacao);
    }

    // --- AVALIA A COERÊNCIA DO TEXTO ---
    private double avaliarCoerencia(List<String> palavras, List<String> frases) {
        if (palavras.isEmpty()) {
            return 0;
        }

        double nota = 3.0;
        int conectivosEncontrados = contarOcorrencias(palavras, CONECTIVOS);

        nota += Math.min(3.0, conectivosEncontrados * 0.75);

        if (palavras.size() >= 15) {
            nota += 1.5;
        }

        if (palavras.size() >= 30) {
            nota += 1.0;
        }

        if (frases.size() >= 2) {
            nota += 1.0;
        }

        if (possuiRepeticaoExcessiva(palavras)) {
            nota -= 1.5;
        }

        return limitarNota(nota);
    }

    // --- AVALIA A RELAÇÃO DO ARGUMENTO COM O TEMA ---
    private double avaliarRelevancia(String texto, TemaDebate tema) {
        if (tema.getPalavrasChave().isEmpty()) {
            return 6.0;
        }

        int palavrasEncontradas = 0;

        for (String palavraChave : tema.getPalavrasChave()) {
            if (texto.contains(normalizarTexto(palavraChave))) {
                palavrasEncontradas++;
            }
        }

        double proporcao = palavrasEncontradas / (double) tema.getPalavrasChave().size();
        return limitarNota(3.0 + proporcao * 7.0);
    }

    // --- AVALIA A CLAREZA DO TEXTO ---
    private double avaliarClareza(List<String> palavras, List<String> frases) {
        if (palavras.isEmpty() || frases.isEmpty()) {
            return 0;
        }

        double mediaPalavrasPorFrase = palavras.size() / (double) frases.size();
        double nota = 8.0;

        if (mediaPalavrasPorFrase > 35) {
            nota -= 3.0;
        } else if (mediaPalavrasPorFrase > 25) {
            nota -= 1.5;
        }

        if (palavras.size() < 8) {
            nota -= 3.0;
        }

        if (palavras.size() >= 15 && palavras.size() <= 120) {
            nota += 1.0;
        }

        return limitarNota(nota);
    }

    // --- AVALIA O USO DE EXEMPLOS E MARCADORES DE EVIDÊNCIA ---
    private double avaliarEvidencias(String texto) {
        List<String> palavras = separarPalavras(texto);
        int marcadores = contarOcorrencias(palavras, MARCADORES_EVIDENCIA);
        int numeros = contarNumeros(texto);
        double nota = 2.0 + Math.min(5.0, marcadores * 1.5) + Math.min(3.0, numeros);

        return limitarNota(nota);
    }

    // --- AVALIA A ESTRUTURA E O USO DA ESTRATÉGIA ESCOLHIDA ---
    private double avaliarEstrutura(String texto, List<String> palavras, EstrategiaArgumentacao estrategia, String argumentoAnterior) {
        double nota = 4.0;

        if (palavras.size() >= 20) {
            nota += 2.0;
        }

        if (contarOcorrencias(palavras, CONECTIVOS) > 0) {
            nota += 1.5;
        }

        if (estrategia == EstrategiaArgumentacao.REFUTACAO && contarOcorrencias(palavras, MARCADORES_REFUTACAO) > 0) {
            nota += 2.0;
        }

        if (estrategia == EstrategiaArgumentacao.PROPOSTA_SOLUCAO && contarOcorrencias(palavras, MARCADORES_SOLUCAO) > 0) {
            nota += 2.0;
        }

        if (estrategia == EstrategiaArgumentacao.EXEMPLO_PRATICO && texto.contains("exemplo")) {
            nota += 2.0;
        }

        if (estrategia == EstrategiaArgumentacao.CAUSA_E_EFEITO && (texto.contains("porque") || texto.contains("consequentemente") || texto.contains("por isso"))) {
            nota += 2.0;
        }

        if (estrategia == EstrategiaArgumentacao.COMPARACAO && (texto.contains("compar") || texto.contains("enquanto") || texto.contains("diferente"))) {
            nota += 2.0;
        }

        if (estrategia == EstrategiaArgumentacao.REFUTACAO && argumentoAnterior != null && !argumentoAnterior.isBlank()) {
            nota += calcularSobreposicaoTextual(texto, normalizarTexto(argumentoAnterior)) * 2.0;
        }

        return limitarNota(nota);
    }

    // --- AVALIA A DIVERSIDADE DO VOCABULÁRIO ---
    private double avaliarVocabulario(List<String> palavras) {
        if (palavras.isEmpty()) {
            return 0;
        }

        Set<String> palavrasUnicas = new HashSet<>(palavras);
        double diversidade = palavrasUnicas.size() / (double) palavras.size();
        double nota = diversidade * 10.0;

        if (palavras.size() < 10) {
            nota -= 2.0;
        }

        return limitarNota(nota);
    }

    // --- CALCULA A SOBREPOSIÇÃO ENTRE DOIS TEXTOS ---
    private double calcularSobreposicaoTextual(String primeiroTexto, String segundoTexto) {
        Set<String> primeirasPalavras = new HashSet<>(separarPalavras(primeiroTexto));
        Set<String> segundasPalavras = new HashSet<>(separarPalavras(segundoTexto));

        if (primeirasPalavras.isEmpty() || segundasPalavras.isEmpty()) {
            return 0;
        }

        primeirasPalavras.retainAll(segundasPalavras);
        return Math.min(1.0, primeirasPalavras.size() / 8.0);
    }

    // --- VERIFICA SE EXISTE REPETIÇÃO EXCESSIVA ---
    private boolean possuiRepeticaoExcessiva(List<String> palavras) {
        if (palavras.size() < 8) {
            return false;
        }

        Map<String, Integer> frequencias = new java.util.HashMap<>();

        for (String palavra : palavras) {
            frequencias.put(palavra, frequencias.getOrDefault(palavra, 0) + 1);
        }

        for (int frequencia : frequencias.values()) {
            if (frequencia > palavras.size() * 0.25) {
                return true;
            }
        }

        return false;
    }

    // --- CONTA MARCADORES PRESENTES NO TEXTO ---
    private int contarOcorrencias(List<String> palavras, Set<String> marcadores) {
        int quantidade = 0;

        for (String palavra : palavras) {
            if (marcadores.contains(palavra)) {
                quantidade++;
            }
        }

        return quantidade;
    }

    // --- CONTA VALORES NUMÉRICOS PRESENTES NO TEXTO ---
    private int contarNumeros(String texto) {
        int quantidade = 0;

        for (String parte : texto.split("\\s+")) {
            if (parte.matches(".*\\d.*")) {
                quantidade++;
            }
        }

        return quantidade;
    }

    // --- CRIA UM RETORNO TEXTUAL SOBRE A AVALIAÇÃO ---
    private String criarObservacao(Map<CriterioAvaliacao, Double> notas) {
        CriterioAvaliacao melhorCriterio = null;
        CriterioAvaliacao criterioMelhoria = null;
        double maiorNota = Double.NEGATIVE_INFINITY;
        double menorNota = Double.POSITIVE_INFINITY;

        for (Map.Entry<CriterioAvaliacao, Double> entrada : notas.entrySet()) {
            if (entrada.getValue() > maiorNota) {
                maiorNota = entrada.getValue();
                melhorCriterio = entrada.getKey();
            }

            if (entrada.getValue() < menorNota) {
                menorNota = entrada.getValue();
                criterioMelhoria = entrada.getKey();
            }
        }

        return "Ponto forte: " + melhorCriterio.getNomeFormatado() + ". Principal aspecto para melhoria: " + criterioMelhoria.getNomeFormatado() + ".";
    }

    // --- SEPARA AS PALAVRAS DE UM TEXTO ---
    private List<String> separarPalavras(String texto) {
        if (texto == null || texto.isBlank()) {
            return new ArrayList<>();
        }

        return Arrays.stream(normalizarTexto(texto).split("\\s+")).filter(palavra -> !palavra.isBlank()).toList();
    }

    // --- SEPARA AS FRASES DE UM TEXTO ---
    private List<String> separarFrases(String texto) {
        if (texto == null || texto.isBlank()) {
            return new ArrayList<>();
        }

        return Arrays.stream(texto.split("[.!?]+")).map(String::trim).filter(frase -> !frase.isEmpty()).toList();
    }

    // --- NORMALIZA O TEXTO PARA COMPARAÇÕES ---
    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }

        String textoSemAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return textoSemAcentos.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9\\s]", " ").replaceAll("\\s+", " ").trim();
    }

    // --- LIMITA UMA NOTA AO INTERVALO DE ZERO A DEZ ---
    private double limitarNota(double nota) {
        return Math.max(0.0, Math.min(10.0, nota));
    }
}

