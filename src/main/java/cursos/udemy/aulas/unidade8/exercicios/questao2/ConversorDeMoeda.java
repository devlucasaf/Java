package cursos.udemy.aulas.unidade8.exercicios.questao2;

public class ConversorDeMoeda {

    public static final double IOF = 0.06;

    public static double converterParaReais(double cotacaoDolar, double quantidadeDolares) {

        double valorEmReais = cotacaoDolar * quantidadeDolares;
        return valorEmReais + valorEmReais * IOF;
    }
}

