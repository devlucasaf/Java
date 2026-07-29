package application.utilitarios.motorbusca;

public class Resultado {

    public final Documento  doc;
    public final double     score;

    public Resultado(Documento doc, double score) {
        this.doc = doc;
        this.score = score;
    }
}

