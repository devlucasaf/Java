package games.text.debate;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class AvaliacaoArgumento {

    private final Argumento                         argumento;
    private final Map<CriterioAvaliacao, Double>    notas;
    private final String                            observacao;
    private final double                            pontuacaoTotal;

    public AvaliacaoArgumento(Argumento argumento, Map<CriterioAvaliacao, Double> notas, String observacao) {
        if (argumento == null) {
            throw new IllegalArgumentException("O argumento não pode ser nulo.");
        }

        if (notas == null || notas.isEmpty()) {
            throw new IllegalArgumentException("As notas da avaliação devem ser informadas.");
        }

        this.argumento = argumento;
        this.notas = validarECopiarNotas(notas);
        this.observacao = observacao == null ? "" : observacao.trim();
        this.pontuacaoTotal = calcularPontuacaoTotal();
    }

    // --- VALIDA E COPIA AS NOTAS RECEBIDAS ---
    private Map<CriterioAvaliacao, Double> validarECopiarNotas(Map<CriterioAvaliacao, Double> notasRecebidas) {
        Map<CriterioAvaliacao, Double> copia = new EnumMap<>(CriterioAvaliacao.class);

        for (CriterioAvaliacao criterio : CriterioAvaliacao.values()) {
            double nota = notasRecebidas.getOrDefault(criterio, 0.0);

            if (!Double.isFinite(nota) || nota < 0 || nota > 10) {
                throw new IllegalArgumentException("As notas devem estar entre 0 e 10.");
            }

            copia.put(criterio, nota);
        }

        return copia;
    }

    // --- CALCULA A PONTUAÇÃO PONDERADA DO ARGUMENTO ---
    private double calcularPontuacaoTotal() {
        double coerencia = notas.get(CriterioAvaliacao.COERENCIA) * 0.25;
        double relevancia = notas.get(CriterioAvaliacao.RELEVANCIA) * 0.20;
        double clareza = notas.get(CriterioAvaliacao.CLAREZA) * 0.15;
        double evidencias = notas.get(CriterioAvaliacao.EVIDENCIAS) * 0.15;
        double estrutura = notas.get(CriterioAvaliacao.ESTRUTURA) * 0.15;
        double vocabulario = notas.get(CriterioAvaliacao.VOCABULARIO) * 0.10;
        return coerencia + relevancia + clareza + evidencias + estrutura + vocabulario;
    }

    public Argumento getArgumento() {
        return argumento;
    }

    public Map<CriterioAvaliacao, Double> getNotas() {
        return Collections.unmodifiableMap(notas);
    }

    public double getNota(CriterioAvaliacao criterio) {
        return notas.getOrDefault(criterio, 0.0);
    }

    public String getObservacao() {
        return observacao;
    }

    public double getPontuacaoTotal() {
        return pontuacaoTotal;
    }
}

