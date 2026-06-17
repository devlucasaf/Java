package games.sorteio.roleta;

public enum TipoAposta {
    NUMERO_UNICO  (35, "Número único (0-36)"),
    COR_VERMELHA  (1,  "Cor vermelha"),
    COR_PRETA     (1,  "Cor preta"),
    PAR           (1,  "Par"),
    IMPAR         (1,  "Ímpar"),
    BAIXOS        (1,  "Baixos (1-18)"),
    ALTOS         (1,  "Altos (19-36)"),
    PRIMEIRA_DUZIA(2,  "1ª dúzia (1-12)"),
    SEGUNDA_DUZIA (2,  "2ª dúzia (13-24)"),
    TERCEIRA_DUZIA(2,  "3ª dúzia (25-36)"),
    PRIMEIRA_COLUNA(2, "1ª coluna (1,4,7...34)"),
    SEGUNDA_COLUNA (2, "2ª coluna (2,5,8...35)"),
    TERCEIRA_COLUNA(2, "3ª coluna (3,6,9...36)");

    private final int    multiplicador;
    private final String descricao;

    TipoAposta(int multiplicador, String descricao) {
        this.multiplicador = multiplicador;
        this.descricao = descricao;
    }

    public int getMultiplicador() {
        return multiplicador;
    }

    public String getDescricao() {
        return descricao;
    }
}

