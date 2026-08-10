package math.calculadora.completa.model.utilidades.resultados;

public class ResultadoContagem {
    public int caracteres;
    public int caracteresSemEspacos;
    public int palavras;
    public int linhas;
    public int vogais;
    public int consoantes;

    public ResultadoContagem(int caracteres, int semEspacos, int palavras, int linhas, int vogais, int consoantes) {
        this.caracteres = caracteres;
        this.caracteresSemEspacos = semEspacos;
        this.palavras = palavras;
        this.linhas = linhas;
        this.vogais = vogais;
        this.consoantes = consoantes;
    }
}