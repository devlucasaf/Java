package application.estruturas.grafos;

import java.util.List;

public class Resultado {
    public final List<String>   caminho;
    public final double         custoTotal;
    public final int            verticesVisitados;

    public Resultado(List<String> caminho, double custoTotal, int verticesVisitados) {
        this.caminho = caminho;
        this.custoTotal = custoTotal;
        this.verticesVisitados = verticesVisitados;
    }
}
